//
//  UserInfoTableViewController.h
//  OpenRegistration
//
//  Created by admin on 09/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UserInfoTableViewController : UITableViewController<UIPickerViewDataSource, UIPickerViewDelegate, UITextFieldDelegate>
-(void) prefixSelected;
@property (strong, nonatomic) NSDictionary *organization;
@end
