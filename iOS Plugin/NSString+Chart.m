//
//  NSString+Chart.m
//  Smartface
//
//  Created by Dogan Ekici on 11/14/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "NSString+Chart.h"

@implementation NSString (Chart)

-(NSString*)getNumbersFromString{
    
    NSArray* Array = [self componentsSeparatedByCharactersInSet:
                      [[NSCharacterSet decimalDigitCharacterSet] invertedSet]];
    NSString* returnString = [Array componentsJoinedByString:@""];
    
    return (returnString);
    
}

-(BOOL)checkOnlyNumber{
    
    NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
    NSNumber *number = [formatter numberFromString:self];
    [formatter release];
    return !!number;
}

-(UIColor *)giveColorfromStringColor{
    UIColor *color;
    
    @try {
        SEL labelColor = NSSelectorFromString([NSString stringWithFormat:@"%@Color",[self lowercaseString]]);
        color = [UIColor performSelector:labelColor];
    } @catch (NSException *exception) {
        color = [UIColor blackColor];
    } @finally {
        
    }
    
    return color;
    
}
    
@end
