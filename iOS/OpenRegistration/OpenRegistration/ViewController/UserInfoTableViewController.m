//
//  UserInfoTableViewController.m
//  OpenRegistration
//
//  Created by admin on 09/03/15.
//  Copyright (c) 2015 Covisint. All rights reserved.
//

#import "UserInfoTableViewController.h"
#import "Utils.h"

@interface UserInfoTableViewController ()
@property (strong, nonatomic) IBOutlet UITableView *personDetailsTableView;

@property NSArray *prefixValues;
@property (weak, nonatomic) IBOutlet UITextField *prefixTextField;
@property (strong, nonatomic) UIPickerView *prefixPicker;

@property (weak, nonatomic) IBOutlet UITextField *firstNameField;
@property (weak, nonatomic) IBOutlet UITextField *middleNameField;
@property (weak, nonatomic) IBOutlet UITextField *lastNameField;
@property (weak, nonatomic) IBOutlet UITextField *jobTitleField;

@property (weak, nonatomic) IBOutlet UITextField *address1Field;
@property (weak, nonatomic) IBOutlet UITextField *address2Field;
@property (weak, nonatomic) IBOutlet UITextField *address3Field;
@property (weak, nonatomic) IBOutlet UITextField *cityField;
@property (weak, nonatomic) IBOutlet UITextField *stateField;
@property (weak, nonatomic) IBOutlet UITextField *postalCodeField;

@property (weak, nonatomic) IBOutlet UITextField *phoneISDField;
@property (weak, nonatomic) IBOutlet UITextField *phoneField;
@property (weak, nonatomic) IBOutlet UITextField *mobileISDField;
@property (weak, nonatomic) IBOutlet UITextField *mobileField;
@property (weak, nonatomic) IBOutlet UITextField *faxISDField;
@property (weak, nonatomic) IBOutlet UITextField *faxField;
@property (weak, nonatomic) IBOutlet UITextField *emailField;
@property (weak, nonatomic) IBOutlet UITextField *reEnterEmailField;

@property NSArray *countryValues;
@property (weak, nonatomic) IBOutlet UITextField *countryTextField;
@property (strong, nonatomic) UIPickerView *countryPicker;

@property NSArray *tzValues;
@property (weak, nonatomic) IBOutlet UITextField *tzTextField;
@property (strong, nonatomic) UIPickerView *tzPicker;

@property NSMutableArray *languageValues;
@property NSDictionary *languageCodeNDisplayMap;

@property (weak, nonatomic) IBOutlet UITextField *languageTextField;
@property (strong, nonatomic) UIPickerView *languagePicker;

@property (weak, nonatomic) IBOutlet UILabel *orgName;

@property NSInteger currentCountryIndex;
@end

@implementation UserInfoTableViewController

@synthesize organization;

-(void) addPickerForValues:(NSArray*)values andPicker:(UIPickerView*)picker forTextField:(UITextField*)textField withDelegate:(SEL)doneMethod {
    textField.delegate = self;
    picker.dataSource = self;
    picker.delegate = self;
    picker.showsSelectionIndicator = YES;
    UIBarButtonItem *doneButton = [[UIBarButtonItem alloc] initWithTitle:@"Done" style:UIBarButtonItemStyleDone target:self action:doneMethod];
    UIToolbar * toolbar = [[UIToolbar alloc] initWithFrame:CGRectMake(0, self.view.frame.size.height-50, self.view.frame.size.width, 50)];
    [toolbar setBarStyle:UIBarStyleDefault];
    NSArray *toolbarItems = [NSArray arrayWithObjects:doneButton, nil];
    [toolbar setItems:toolbarItems];
    textField.inputView = picker;
    textField.inputAccessoryView = toolbar;
}

-(void) countrySelected {
    NSInteger row = [self.countryPicker selectedRowInComponent:0];
    [self.countryTextField setText:[self.countryValues objectAtIndex:row]];
    [self.countryTextField resignFirstResponder];
}

-(void) populateCountries {
    NSArray *countryArray = [NSLocale ISOCountryCodes];
    NSMutableArray *sortedCountryArray = [[NSMutableArray alloc] init];
    for (NSString* countryCode in countryArray) {
        NSString *identifier = [NSLocale localeIdentifierFromComponents: [NSDictionary dictionaryWithObject: countryCode forKey: NSLocaleCountryCode]];
        NSString *country = [[[NSLocale alloc] initWithLocaleIdentifier:@"en_US"] displayNameForKey: NSLocaleIdentifier value: identifier];
        [sortedCountryArray addObject: country];
    }
    [sortedCountryArray sortUsingSelector:@selector(localizedCompare:)];
    self.countryValues = sortedCountryArray;
}

- (void) populateTimeZones {
    self.tzValues = [NSTimeZone knownTimeZoneNames];
}

-(void) populateLanguages {
    NSArray *availableIdentifiers = [NSLocale availableLocaleIdentifiers];
    self.languageCodeNDisplayMap = [[NSMutableDictionary alloc] init];
    self.languageValues = [[NSMutableArray alloc] init];
    NSLocale * currentLocale = [[NSLocale alloc] initWithLocaleIdentifier:@"en_US"];
    for (NSString * localeId in availableIdentifiers) {
        NSLocale *locale = [[NSLocale alloc] initWithLocaleIdentifier:localeId];
        NSString *language = [currentLocale displayNameForKey:NSLocaleIdentifier value:[locale localeIdentifier]];
        [self.languageValues addObject:language];
        [self.languageCodeNDisplayMap setValue:localeId forKey:language];
    }
}

-(void) timeZoneSelected {
    NSInteger row = [self.tzPicker selectedRowInComponent:0];
    [self.tzTextField setText:[self.tzValues objectAtIndex:row]];
    [self.tzTextField resignFirstResponder];
}

-(void) languageSelected {
    NSInteger row = [self.languagePicker selectedRowInComponent:0];
    [self.languageTextField setText:[self.languageValues objectAtIndex:row]];
    [self.languageTextField resignFirstResponder];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.personDetailsTableView.estimatedRowHeight = 100.0;
    self.personDetailsTableView.rowHeight = UITableViewAutomaticDimension;
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    self.orgName.text = [self.organization objectForKey:@"name"];
    //Adding picker for prefixes
    self.prefixValues = [NSArray arrayWithObjects:@"Mr.", @"Mrs.", @"Ms", @"Miss", nil];
    self.prefixPicker = [[UIPickerView alloc] init];
    [self addPickerForValues:self.prefixValues andPicker:self.prefixPicker forTextField:self.prefixTextField withDelegate:@selector(prefixSelected)];
    
    //Adding picker for country selection
    if (self.countryValues == nil || self.countryValues.count <= 0) {
        [self populateCountries];
    }
    self.countryPicker = [[UIPickerView alloc] init];
    [self addPickerForValues:self.countryValues andPicker:self.countryPicker forTextField:self.countryTextField withDelegate:@selector(countrySelected)];

    NSLocale *currentLocale = [NSLocale currentLocale];
    NSString *countryCode = [currentLocale objectForKey:NSLocaleCountryCode];
    NSString *identifier = [NSLocale localeIdentifierFromComponents: [NSDictionary dictionaryWithObject: countryCode forKey: NSLocaleCountryCode]];
    NSString *country = [[[NSLocale alloc] initWithLocaleIdentifier:@"en_US"] displayNameForKey: NSLocaleIdentifier value: identifier];
    NSInteger currentCountryIndex = [self.countryValues indexOfObject:country];
    [self.countryPicker selectRow:currentCountryIndex inComponent:0 animated:NO];
    self.countryTextField.text = [self.countryValues objectAtIndex:currentCountryIndex];
    
    //Adding picker for TimeZone
    if (self.tzValues == nil) {
        [self populateTimeZones];
    }
    self.tzPicker = [[UIPickerView alloc] init];
    [self addPickerForValues:self.tzValues andPicker:self.tzPicker forTextField:self.tzTextField withDelegate:@selector(timeZoneSelected)];
    NSTimeZone *currentTimeZone = [NSTimeZone localTimeZone];
    NSUInteger currentTZIndex = [self.tzValues indexOfObjectPassingTest:^BOOL(id obj, NSUInteger idx, BOOL *stop) {
        if ([((NSString*)obj) isEqualToString:currentTimeZone.name]) {
            *stop = YES;
            return YES;
        } else {
            return NO;
        }
    }];
    if (currentTZIndex != NSNotFound) {
        [self.tzPicker selectRow:currentTZIndex inComponent:0 animated:NO];
        self.tzTextField.text = [self.tzValues objectAtIndex:currentTZIndex];
    }
    
    //Adding picker for Language
    if (self.languageValues == nil) {
        [self populateLanguages];
    }
    self.languagePicker = [[UIPickerView alloc] init];
    [self addPickerForValues:self.languageValues andPicker:self.languagePicker forTextField:self.languageTextField withDelegate:@selector(languageSelected)];
    NSUInteger currentLanguageIndex = [self.languageValues indexOfObject:@"English (United States)"];
    if (currentLanguageIndex != NSNotFound) {
        [self.languagePicker selectRow:currentLanguageIndex inComponent:0 animated:NO];
        self.languageTextField.text = [self.languageValues objectAtIndex:currentLanguageIndex];
    }
    
    //make this controller as delegate for all textfields
    self.firstNameField.delegate = self;
    self.middleNameField.delegate = self;;
    self.lastNameField.delegate = self;;
    self.jobTitleField.delegate = self;;
    
    self.address1Field.delegate = self;;
    self.address2Field.delegate = self;;
    self.address3Field.delegate = self;;
    self.cityField.delegate = self;;
    self.stateField.delegate = self;;
    self.postalCodeField.delegate = self;;
    self.countryTextField.delegate = self;;
    
    self.phoneField.delegate = self;;
    self.mobileField.delegate = self;;
    self.faxField.delegate = self;;
    self.emailField.delegate = self;;
    self.reEnterEmailField.delegate = self;;

}

-(void) viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self.personDetailsTableView reloadData];
}

-(void) prefixSelected {
    NSInteger row = [self.prefixPicker selectedRowInComponent:0];
    [self.prefixTextField setText:[self.prefixValues objectAtIndex:row]];
    [self.prefixTextField resignFirstResponder];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source
/*
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
#warning Potentially incomplete method implementation.
    // Return the number of sections.
    return 4;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    // Return the number of rows in the section.
    NSInteger rowsInSection = -1;
    switch (section) {
        case 0:
            rowsInSection = 5;
            break;
        case 1:
            rowsInSection = 7;
            break;
        default:
            rowsInSection = 0;
            break;
    }
    return rowsInSection;
}
*/
/*
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:<#@"reuseIdentifier"#> forIndexPath:indexPath];
    
    // Configure the cell...
    
    return cell;
}
*/

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

-(NSInteger) numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 1;
}

-(NSInteger) pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    NSInteger numberOfRows = 0;
    if (pickerView == self.prefixPicker) {
        numberOfRows = self.prefixValues.count;
    } else if (pickerView == self.countryPicker) {
        numberOfRows = self.countryValues.count;
    } else if (pickerView == self.tzPicker) {
        numberOfRows = self.tzValues.count;
    } else if (pickerView == self.languagePicker) {
        numberOfRows = self.languageValues.count;
    }
    return numberOfRows;
}

-(NSString*) pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
    NSString *title = nil;
    NSArray *values = nil;
    if (pickerView == self.prefixPicker) {
        values = self.prefixValues;
    } else if (pickerView == self.countryPicker) {
        values = self.countryValues;
    } else if (pickerView == self.tzPicker) {
        values = self.tzValues;
    } else if (pickerView == self.languagePicker) {
        values = self.languageValues;
    }
    if (values != nil) {
        title = [values objectAtIndex:row];
    }
    return title;
}

-(void) pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    NSArray *values = nil;
    UITextField *textField = nil;
    if (pickerView == self.prefixPicker) {
        values = self.prefixValues;
        textField = self.prefixTextField;
    } else if (pickerView == self.countryPicker) {
        values = self.countryValues;
        textField = self.countryTextField;
    } else if (pickerView == self.tzPicker) {
        values = self.tzValues;
        textField = self.tzTextField;
    } else if (pickerView == self.languagePicker) {
        values = self.languageValues;
        textField = self.languageTextField;
    }
    if (values != nil && textField != nil) {
        [textField setText:[values objectAtIndex:row]];
    }
}

-(BOOL) textFieldShouldReturn:(UITextField *)textField {
    NSLog(@"Inside UserIndoTableViewController::textFieldShouldReturn");
    if (textField == self.prefixTextField) {
        [self.firstNameField becomeFirstResponder];
    } else if (textField == self.firstNameField) {
        [self.middleNameField becomeFirstResponder];
    } else if (textField == self.middleNameField) {
        [self.lastNameField becomeFirstResponder];
    } else if (textField == self.lastNameField) {
        [self.jobTitleField becomeFirstResponder];
    } else if (textField == self.jobTitleField) {
        [self.address1Field becomeFirstResponder];
    } else if (textField == self.address1Field) {
        [self.address2Field becomeFirstResponder];
    } else if (textField == self.address2Field) {
        [self.address3Field becomeFirstResponder];
    } else if (textField == self.address3Field) {
        [self.cityField becomeFirstResponder];
    } else if (textField == self.cityField) {
        [self.stateField becomeFirstResponder];
    } else if (textField == self.stateField) {
        [self.postalCodeField becomeFirstResponder];
    } else if (textField == self.postalCodeField) {
        [self.countryTextField becomeFirstResponder];
    } else if (textField == self.countryTextField) {
        [self.phoneField becomeFirstResponder];
    } else if (textField == self.phoneField) {
        [self.mobileField becomeFirstResponder];
    } else if (textField == self.mobileField) {
        [self.faxField becomeFirstResponder];
    } else if (textField == self.faxField) {
        [self.emailField becomeFirstResponder];
    } else if (textField == self.emailField) {
        [self.reEnterEmailField becomeFirstResponder];
    } else if (textField == self.reEnterEmailField) {
        [self.tzTextField becomeFirstResponder];
    } else if (textField == self.tzTextField) {
        [self.languageTextField becomeFirstResponder];
    } else {
        [textField resignFirstResponder];
    }
    return NO;
}

-(BOOL) textFieldShouldEndEditing:(UITextField *)textField {
    BOOL shouldEndEditing = YES;
    NSString *errorMessage = nil;
    // Check if the inout is empty
    if (textField == self.firstNameField) {
        errorMessage = @"First Name can not be empty!";
    } else if (textField == self.lastNameField) {
        errorMessage = @"Last Name can not be empty!";
    } else if (textField == self.address1Field) {
        errorMessage = @"Address1 can not be empty!";
    } else if (textField == self.address3Field) {
        errorMessage = @"Address3 can not be empty!";
    } else if (textField == self.cityField) {
        errorMessage = @"City can not be empty!";
    } else if (textField == self.stateField) {
        errorMessage = @"State can not be empty!";
    } else if (textField == self.postalCodeField) {
        errorMessage = @"Postal Code can not be empty!";
    } else if (textField == self.countryTextField) {
        errorMessage = @"Country can not be empty!";
    } else if (textField == self.phoneField) {
        errorMessage = @"Phone can not be empty!";
    } else if (textField == self.emailField) {
        errorMessage = @"email address can not be empty!";
    } else if (textField == self.reEnterEmailField) {
        errorMessage = @"Re-enter email address can not be empty!";
    }
    
    if (errorMessage && [self isInputEmpty:textField errorMessage:errorMessage]) {
        shouldEndEditing = NO;
    } else { // Check if input is phone number if the number is valid
        if (![self isValidPhoneNumber:textField]) {
            [[Utils sharedInstance] showAlert:@"Invalid number" withMessage:@"Please recheck the entered number" withButtonTexts:[NSArray arrayWithObject:@"OK"] andDelegate:nil];
            shouldEndEditing = NO;
        } else if (![self isValidEmailAddress:textField]) {
            [[Utils sharedInstance] showAlert:@"Invalid email" withMessage:@"Please recheck the entered email" withButtonTexts:[NSArray arrayWithObject:@"OK"] andDelegate:nil];
            shouldEndEditing = NO;
        } else if (textField == self.reEnterEmailField) {
            //Check is the email addresses are same
            NSString *email = self.emailField.text;
            NSString *reEnteredEmail = self.reEnterEmailField.text;
            if (![email isEqualToString:reEnteredEmail]) {
                [[Utils sharedInstance] showAlert:@"Invalid email" withMessage:@"This email address is different than the earlier address!" withButtonTexts:[NSArray arrayWithObject:@"OK"] andDelegate:nil];
                shouldEndEditing = NO;
            }
        }
    }
    return shouldEndEditing;
}
-(BOOL) isValidEmailAddress : (UITextField *) textField {
    BOOL isEmailValid = YES;
    NSString* pattern = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+";
    if (textField == self.emailField || textField == self.reEnterEmailField) {
        NSString *email = [textField text];
        NSPredicate* predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", pattern];
        if ([predicate evaluateWithObject:email] == YES) {
            isEmailValid = YES;
        } else {
            isEmailValid = NO;
        }
    }
    return isEmailValid;
}

-(BOOL) isValidPhoneNumber : (UITextField*) textField {
    BOOL isNumberValid = YES;
    NSString *phoneNumber = nil;
    if (textField == self.phoneField) {
        NSString *isdPart = self.phoneISDField.text;
        NSString *numberPart = self.phoneField.text;
        phoneNumber = [NSString stringWithFormat:@"%@%@", isdPart, numberPart];
    } else if (textField == self.mobileField) {
        if (textField.text.length > 0) {
            NSString *isdPart = self.mobileISDField.text;
            NSString *numberPart = self.mobileField.text;
            phoneNumber = [NSString stringWithFormat:@"%@%@", isdPart, numberPart];
        }
    } else if (textField == self.faxField) {
        if (textField.text.length > 0) {
            NSString *isdPart = self.faxISDField.text;
            NSString *numberPart = self.faxField.text;
            phoneNumber = [NSString stringWithFormat:@"%@%@", isdPart, numberPart];
        }
    }
    if (phoneNumber) {
        NSLog(@"number = %@", phoneNumber);
        NSError *error = nil;
        NSDataDetector *detector = [NSDataDetector dataDetectorWithTypes:NSTextCheckingTypePhoneNumber error:&error];
        NSRange inputRange = NSMakeRange(0, [phoneNumber length]);
        NSArray *matches = [detector matchesInString:phoneNumber options:0 range:inputRange];
        // No matches at all
        if ([matches count] == 0) {
            isNumberValid = NO;
        } else {
            NSTextCheckingResult *result = (NSTextCheckingResult *)[matches objectAtIndex:0];
            if ([result resultType] == NSTextCheckingTypePhoneNumber && result.range.location == inputRange.location && result.range.length == inputRange.length) {
                isNumberValid = YES;
            } else {
                isNumberValid = NO;
            }
        }
    }
    return isNumberValid;
}

-(BOOL) isInputEmpty : (UITextField *)inputTextField errorMessage : (NSString*) errorMessage {
    NSString *inputText = inputTextField.text;
    if (inputText == nil || (inputText != nil && [inputText length] <= 0)) {
        [[Utils sharedInstance] showAlert:@"Required field" withMessage:errorMessage withButtonTexts:[NSArray arrayWithObject:@"OK"] andDelegate:nil];
        return YES;
    }
    return NO;
}
@end
