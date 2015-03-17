//
//  NetworkHelper.m
//  OpenRegistration
//
//  Created by admin on 04/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import "NetworkHelper.h"

@interface NetworkHelper ()

@property NSMutableDictionary *networkProperties;
@property NSMutableData *_responseData;
@property NSString *token;
@end

@implementation NetworkHelper
@synthesize token;

static NetworkHelper *sharedInstance;

+ (NetworkHelper*) sharedInstance {
    static dispatch_once_t dispatchOnceToken;
    
    dispatch_once(&dispatchOnceToken, ^{
        sharedInstance = [[NetworkHelper alloc] init];
    });
    return sharedInstance;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        NSString *networkPropertiesPlistPath = [[NSBundle mainBundle] pathForResource:@"NetworkProperties" ofType:@"plist"];
        self.networkProperties = [[NSMutableDictionary alloc]initWithContentsOfFile:networkPropertiesPlistPath];
    }
    return self;
}

- (NSString*) getTokenUrl {
    NSDictionary *tokenProperties = [self.networkProperties valueForKey:@"Get Token"];
    return [tokenProperties valueForKey:@"Url"];
}

- (void) getOrganizationsWithName:(NSString*) orgName requestor:(id<NetworkHelperDelegate>)requestor token:(BOOL)refreshToken {
    id dataToReturn;
    NSError *errorToReturn = nil;
    
    NSError *tokenError = nil;
    NSString *tokenString = [self getToken:refreshToken error:&tokenError];
    if (tokenError == nil) {
        NSDictionary *getOrgProperties = [self.networkProperties valueForKey:@"Get Organizations"];
        NSString * getOrgUrl = [getOrgProperties valueForKey:@"Url"];
        if (![orgName isEqualToString:@"all"]) {
            NSString *utf8OrgName = [orgName stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            getOrgUrl = [getOrgUrl stringByAppendingString:[@"?name=" stringByAppendingString:utf8OrgName]];
        }
        NSMutableURLRequest *getOrgRequest = [[NSMutableURLRequest alloc]initWithURL:[NSURL URLWithString:getOrgUrl]];
        [getOrgRequest setHTTPMethod:@"GET"];
        for (id key in getOrgProperties) {
            id value = [getOrgProperties objectForKey:key];
            if ([key isKindOfClass:[NSString class]]) {
                NSString *headerKey = key;
                if ([headerKey isEqualToString:@"Authorization"]) {
                    value = [NSString stringWithFormat:value, tokenString];
                    [getOrgRequest setValue:value forHTTPHeaderField:headerKey];
                } else if (![headerKey isEqualToString:@"Url"]) {
                    [getOrgRequest setValue:value forHTTPHeaderField:headerKey];
                }
            }
        }
        NSError *networkError = nil;
        NSHTTPURLResponse *getOrgResponse = nil;
        NSData *orgResponseData = [NSURLConnection sendSynchronousRequest:getOrgRequest returningResponse:&getOrgResponse error:&networkError];
        if (networkError == nil) {
            NSError *parsingError;
            NSArray *orgResponseJson = [NSJSONSerialization JSONObjectWithData:orgResponseData options:NSJSONReadingMutableContainers error:&parsingError];
            if (parsingError == nil) {
                dataToReturn = orgResponseJson;
            } else {
                NSDictionary *errorResponseJson = [NSJSONSerialization JSONObjectWithData:orgResponseData options:NSJSONReadingMutableContainers error:&parsingError];
                dataToReturn = errorResponseJson;
            }
        } else {
            errorToReturn = [[NSError alloc] initWithDomain:[networkError domain] code:[networkError code] userInfo:[networkError userInfo]];
        }
    } else {
        errorToReturn = [[NSError alloc] initWithDomain:[tokenError domain] code:[tokenError code] userInfo:[tokenError userInfo]];
    }
    [requestor performSelector:@selector(networkResponse:andError:) withObject:dataToReturn withObject:errorToReturn];
}

- (NSString*) getToken:(BOOL)refresh error : (NSError**) tokenError{
    if (token == nil || refresh) {
        NSDictionary *tokenProperties = [self.networkProperties valueForKey:@"Get Token"];
        NSString * getTokenUrl = [tokenProperties valueForKey:@"Url"];
        NSMutableURLRequest *getTokenRequest = [[NSMutableURLRequest alloc]initWithURL:[NSURL URLWithString:getTokenUrl]];
        [getTokenRequest setHTTPMethod:@"GET"];
        NSString *clientId = nil;
        NSString *clientSecret = nil;
        for (id key in tokenProperties) {
            id value = [tokenProperties objectForKey:key];
            if ([key isKindOfClass:[NSString class]]) {
                NSString *headerKey = key;
                if ([headerKey isEqualToString:@"Client Id"]) {
                    clientId = value;
                } else if ([headerKey isEqualToString:@"Client Secret"]) {
                    clientSecret = value;
                } else if (![headerKey isEqualToString:@"Url"]) {
                    [getTokenRequest setValue:value forHTTPHeaderField:key];
                }
            }
        }
        NSString *clientIdSecret = [[clientId stringByAppendingString:@":"] stringByAppendingString:clientSecret];
        NSData *clientIdSecretUTF8 = [clientIdSecret dataUsingEncoding:NSUTF8StringEncoding];
        NSString *authorizationValue = [clientIdSecretUTF8 base64EncodedStringWithOptions:0];
        authorizationValue = [@"Basic " stringByAppendingString:authorizationValue];
        [getTokenRequest setValue:authorizationValue forHTTPHeaderField:@"Authorization"];
        NSError *networkError = nil;
        NSHTTPURLResponse *getTokenResponse = nil;
        NSData *tokenResponseData = [NSURLConnection sendSynchronousRequest:getTokenRequest returningResponse:&getTokenResponse error:&networkError];
        if (networkError == nil) {
            NSError *parsingError;
            NSDictionary *tokenResponseJson = [NSJSONSerialization JSONObjectWithData:tokenResponseData options:NSJSONReadingMutableContainers error:&parsingError];
            if (parsingError == nil) {
                token = [tokenResponseJson objectForKey:@"access_token"];
            } else {
                *tokenError = [[NSError alloc] initWithDomain:[parsingError domain] code:[parsingError code] userInfo:[parsingError userInfo]];
            }
        } else {
            *tokenError = [[NSError alloc] initWithDomain:[networkError domain] code:[networkError code] userInfo:[networkError userInfo]];
        }
    }
    return token;
}
@end
