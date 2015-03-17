//
//  OrganizationListTableViewController.h
//  OpenRegistration
//
//  Created by admin on 06/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OrganizationListTableViewController : UITableViewController
@property (strong, nonatomic) NSArray *organizationList;
@property (strong, nonatomic) NSDictionary *organization;
-(id) initWithData : (id) orgJson;
@end
