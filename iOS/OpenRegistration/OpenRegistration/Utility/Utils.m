//
//  Utils.m
//  OpenRegistration
//
//  Created by admin on 04/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import "Utils.h"

@implementation Utils

@synthesize indicator;
@synthesize waitingAlert;
@synthesize dialogAlert;

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

@end
