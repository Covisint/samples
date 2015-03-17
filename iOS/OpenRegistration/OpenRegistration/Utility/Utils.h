//
//  Utils.h
//  OpenRegistration
//
//  Created by admin on 04/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface Utils : NSObject

@property (retain, nonatomic) UIActivityIndicatorView *indicator;
@property (retain, nonatomic) UIAlertView *waitingAlert;
@property (retain, nonatomic) UIAlertView *dialogAlert;

+ (Utils*) sharedInstance;
- (void) showWaitingAnimation : (NSString*) title withMessage:(NSString*) message andDelegate:(id)requestor;
- (void) dismissWaitingAnimation;

- (void) showAlert : (NSString*) title withMessage:(NSString*) message  withButtonTexts:(NSArray*) buttonTexts andDelegate:(id)requestor;
- (void) dismissAlertWithClickedButtonIndex : (NSInteger) index;

@end
