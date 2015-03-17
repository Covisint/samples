//
//  ViewController.h
//  OpenRegistration
//
//  Created by admin on 04/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NetworkHelper.h"

@interface SearchOrganizationViewController : UIViewController<NetworkHelperDelegate, UIAlertViewDelegate, UITextFieldDelegate>

-(void) organizationsReceived:(NSArray* )orgArray andError:(NSError*) error;

@end

