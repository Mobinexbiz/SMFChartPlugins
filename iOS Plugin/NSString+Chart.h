//
//  NSString+Chart.h
//  Smartface
//
//  Created by Dogan Ekici on 11/14/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (Chart)

/*!
 * @discussion NSString
 * @return all numbers in string
 */
-(NSString*)getNumbersFromString;

/*!
 * @discussion String to UIcolor
 * @return uicolor
 */
-(UIColor*)giveColorfromStringColor;

/*!
 * @discussion String check only number
 * @return bool
 */
-(BOOL)checkOnlyNumber;
    
@end
