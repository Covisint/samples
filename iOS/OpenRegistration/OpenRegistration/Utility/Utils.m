//
//  Utils.m
//  OpenRegistration
//
//  Created by admin on 04/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import "Utils.h"

@interface Utils ()
@property NSMutableDictionary *countryCountryCodeMap;
@property NSMutableDictionary *countryCodeISDMap;
@property NSArray *countryValues;
@end

@implementation Utils

@synthesize indicator;
@synthesize waitingAlert;
@synthesize dialogAlert;
@synthesize countryCountryCodeMap;
@synthesize countryCodeISDMap;

static Utils *sharedInstance;


+ (Utils*) sharedInstance {
    static dispatch_once_t dispatchOnceToken;
    
    dispatch_once(&dispatchOnceToken, ^{
        sharedInstance = [[Utils alloc] init];
    });
    return sharedInstance;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        // initialization statements here
        waitingAlert = [[UIAlertView alloc] initWithTitle:@"" message:@"" delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
        indicator = [[UIActivityIndicatorView alloc] init];
        indicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyleGray;
        [waitingAlert setValue:indicator forKey:@"accessoryView"];

        dialogAlert = [[UIAlertView alloc] initWithTitle:@"" message:@"" delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
        [self populateCountries];
        NSString *countriesLangISDSampleNumberPlistPath = [[NSBundle mainBundle] pathForResource:@"CountriesLangISDSampleNumber" ofType:@"plist"];
        NSArray *countryCodeISDSamples = [[NSArray alloc] initWithContentsOfFile:countriesLangISDSampleNumberPlistPath];
        countryCodeISDMap = [[NSMutableDictionary alloc] init];
        for (NSString *entry in countryCodeISDSamples) {
            NSArray* splitedEntry = [entry componentsSeparatedByString:@","];
            int i = 0;
            if (splitedEntry[i++]) {
                NSMutableArray *isdNSampleArray = [[NSMutableArray alloc] init];
                while (i < splitedEntry.count) {
                    [isdNSampleArray addObject:[splitedEntry objectAtIndex:i]];
                    i++;
                }
                [countryCodeISDMap setObject:isdNSampleArray forKey:splitedEntry[0]];
            }
        }
    }
    return self;
}

-(void) showWaitingAnimation:(NSString *)title withMessage:(NSString *)message andDelegate:(id)requestor {
    waitingAlert.title = title;
    waitingAlert.message = message;
    waitingAlert.delegate = requestor;
    [indicator startAnimating];
    [waitingAlert show];
}

-(void) dismissWaitingAnimation {
    [indicator stopAnimating];
    [waitingAlert dismissWithClickedButtonIndex:0 animated:NO];
}

- (void) showAlert : (NSString*) title withMessage:(NSString*) message  withButtonTexts:(NSArray*) buttonTexts andDelegate:(id)requestor {
    dialogAlert = [[UIAlertView alloc] initWithTitle:title message:message delegate:requestor cancelButtonTitle:nil otherButtonTitles:nil];
    for (NSString* text in buttonTexts) {
        [dialogAlert addButtonWithTitle:text];
    }
    [self.dialogAlert show];
}

- (void) dismissAlertWithClickedButtonIndex : (NSInteger) index{
    [dialogAlert dismissWithClickedButtonIndex:index animated:NO];
}

-(void) populateCountries {
    self.countryCountryCodeMap = [[NSMutableDictionary alloc] init];
    NSArray *countryArray = [NSLocale ISOCountryCodes];
    NSMutableArray *sortedCountryArray = [[NSMutableArray alloc] init];
    for (NSString* countryCode in countryArray) {
        NSString *identifier = [NSLocale localeIdentifierFromComponents: [NSDictionary dictionaryWithObject: countryCode forKey: NSLocaleCountryCode]];
        NSString *country = [[[NSLocale alloc] initWithLocaleIdentifier:@"en_US"] displayNameForKey: NSLocaleIdentifier value: identifier];
        [sortedCountryArray addObject: country];
        [self.countryCountryCodeMap setObject:countryCode forKey:country];
    }
    [sortedCountryArray sortUsingSelector:@selector(localizedCompare:)];
    self.countryValues = sortedCountryArray;
}

-(NSArray*) getCountries {
    return self.countryValues;
}

- (NSString *) getISDForCountry:(NSString *)country {
    NSString *isd = nil;
    NSString *countryCode = [self.countryCountryCodeMap objectForKey:country];
    NSArray * isdSample = [self.countryCodeISDMap objectForKey:countryCode];
    if (isdSample.count > 0) {
        isd = [isdSample objectAtIndex:0];
    }
    return isd;
}

- (NSString *) getSampleNumberForCountry:(NSString *)country {
    NSString *sampleNumber = nil;
    NSString *countryCode = [self.countryCountryCodeMap objectForKey:country];
    NSArray * isdSample = [self.countryCodeISDMap objectForKey:countryCode];
    if (isdSample.count > 1) {
        sampleNumber = [isdSample objectAtIndex:1];
    }
    return sampleNumber;
}
@end
