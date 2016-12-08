//
//  NSDictionary+Charts.m
//  Smartface
//
//  Created by Dogan Ekici on 12/2/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "NSDictionary+Charts.h"
#import "NSString+Chart.h"

@implementation NSDictionary (Charts)

-(NSDictionary *)resizeWindowPercent{
    NSString * left;
    
    if ([[self objectForKey:@"left"] isKindOfClass:[NSNumber class]]) {
        
        left = [NSString stringWithFormat:@"%d",
                [[self objectForKey:@"left"] intValue]];
    }else{
        
        left = [NSString stringWithFormat:@"%f",
                [[[self objectForKey:@"left"] getNumbersFromString] floatValue] * [[UIScreen mainScreen] bounds].size.width / 100];
    }
    
    NSString * width;
    
    if ([[self objectForKey:@"width"] isKindOfClass:[NSNumber class]]) {
        
        width = [NSString stringWithFormat:@"%d",
                 [[self objectForKey:@"width"] intValue]];
    }else{
        
        width = [NSString stringWithFormat:@"%f",
                 [[[self objectForKey:@"width"] getNumbersFromString] floatValue] * [[UIScreen mainScreen] bounds].size.width / 100];
    }
    
    NSString * top;
    
    if ([[self objectForKey:@"top"] isKindOfClass:[NSNumber class]]) {
        
        top = [NSString stringWithFormat:@"%d",
               [[self objectForKey:@"top"]  intValue]];
    }else{
        
        top = [NSString stringWithFormat:@"%f",
               [[[self objectForKey:@"top"] getNumbersFromString] floatValue] * [[UIScreen mainScreen] bounds].size.height / 100];
    }
    
    NSString * height;
    
    if ([[self objectForKey:@"height"] isKindOfClass:[NSNumber class]]) {
        
        height = [NSString stringWithFormat:@"%d",
                  [[self objectForKey:@"height"] intValue]];
    }else{
        
        height = [NSString stringWithFormat:@"%f",
                  [[[self objectForKey:@"height"] getNumbersFromString] floatValue] * [[UIScreen mainScreen] bounds].size.height / 100];
    }
    
    NSDictionary *result = [NSDictionary dictionaryWithObjectsAndKeys:
                            [NSNumber numberWithFloat:[left floatValue]],@"left",
                            [NSNumber numberWithFloat:[top floatValue]],@"top",
                            [NSNumber numberWithFloat:[width floatValue]],@"width",
                            [NSNumber numberWithFloat:[height floatValue]],@"height",
                            nil];
    
    return  result;
}

@end
