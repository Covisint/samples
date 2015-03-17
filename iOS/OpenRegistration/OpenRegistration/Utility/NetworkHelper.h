//
//  NetworkHelper.h
//  OpenRegistration
//
//  Created by admin on 04/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import <Foundation/Foundation.h>
@protocol NetworkHelperDelegate <NSObject>
@required
- (void) networkResponse:(id)dataReceived andError : (id) error;

@end
@interface NetworkHelper : NSObject <NSURLConnectionDelegate>

+ (NetworkHelper *) sharedInstance;

- (NSString*) getTokenUrl;

- (void) getOrganizationsWithName:(NSString*) orgName requestor:(id<NetworkHelperDelegate>)requestor token:(BOOL)refreshToken;

- (NSString *) getToken : (BOOL) refresh error : (NSError**)tokenError;

@end
