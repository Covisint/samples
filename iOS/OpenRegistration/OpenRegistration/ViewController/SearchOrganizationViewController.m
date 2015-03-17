//
//  ViewController.m
//  OpenRegistration
//
//  Created by admin on 04/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import "SearchOrganizationViewController.h"
#import "NetworkHelper.h"
#import "Utils.h"
#import "OrganizationListTableViewController.h"
#import "UserInfoTableViewController.h"

@interface SearchOrganizationViewController ()
@property (weak, nonatomic) IBOutlet UIButton *searchButton;
@property (weak, nonatomic) IBOutlet UITextField *orgNameField;
@property (strong, atomic) NSError *networkHelperError;
@property (weak, nonatomic) IBOutlet UILabel *errorMessageLabel;
@end

@implementation SearchOrganizationViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    [self.errorMessageLabel setText:@""];
    self.orgNameField.delegate = self;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)handleButtonClick:(id)sender {
    NSString *orgName = [self.orgNameField text];
    [self.orgNameField resignFirstResponder];
    if ([orgName length] == 0) {
        [[Utils sharedInstance] showAlert:@"Please check..." withMessage:@"Organization name is required. If name is not known use 'all' to get list of organizations!" withButtonTexts:[NSArray arrayWithObjects:@"OK", nil] andDelegate:self];
        return;
    }
    self.networkHelperError = nil;
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [[NetworkHelper sharedInstance] getOrganizationsWithName:orgName requestor:self token:YES];
    });
    [[Utils sharedInstance] showWaitingAnimation:@"Please wait..." withMessage:@"Fetching organization(s)" andDelegate:self];
}

-(void) networkResponse:(id)dataReceived andError:(id)error{
    NSLog(@"Data Received = %@", dataReceived);
        dispatch_async(dispatch_get_main_queue(), ^{
            [self organizationsReceived:dataReceived andError:error];
        });
}

-(void) organizationsReceived:(NSArray* )orgArray andError:(NSError*) error {
    [[Utils sharedInstance] dismissWaitingAnimation];
    if (error == nil) {
        if (orgArray.count == 0) {
            //No Organization found
            [self.errorMessageLabel setText:@"No organization found with this name"];
        } else if (orgArray.count == 1) {
            // Jump to UserInfoScene
            UserInfoTableViewController *userInfoController = [self.storyboard instantiateViewControllerWithIdentifier:@"UserInfoScene"];
            userInfoController.organization = [orgArray objectAtIndex:0];
            UIBarButtonItem *newBackButton =
            [[UIBarButtonItem alloc] initWithTitle:@"Search again..."
                                             style:UIBarButtonItemStylePlain
                                            target:nil
                                            action:nil];
            [[self navigationItem] setBackBarButtonItem:newBackButton];
            [self.navigationController pushViewController:userInfoController animated:YES];
        } else {
            OrganizationListTableViewController *orgListController = [self.storyboard instantiateViewControllerWithIdentifier:@"OrgList"];
            orgListController.organizationList = orgArray;
            UIBarButtonItem *newBackButton =
            [[UIBarButtonItem alloc] initWithTitle:@"Search again..."
                                             style:UIBarButtonItemStylePlain
                                            target:nil
                                            action:nil];
            [[self navigationItem] setBackBarButtonItem:newBackButton];
            [self.navigationController pushViewController:orgListController animated:YES];
        }
    } else {
        //Error occurred
        [self.errorMessageLabel setText:[[error userInfo]objectForKey:NSLocalizedDescriptionKey]];
    }
}

- (void) alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    [[Utils sharedInstance]dismissAlertWithClickedButtonIndex:buttonIndex];
}

-(BOOL) textFieldShouldReturn:(UITextField *)textField {
    [self handleButtonClick:textField];
    return YES;
}
@end
