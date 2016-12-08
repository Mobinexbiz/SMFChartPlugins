//
//  NSArray+Charts.m
//  Smartface
//
//  Created by Dogan Ekici on 12/2/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "NSArray+Charts.h"
#import "NSString+Chart.h"

@implementation NSArray (Charts)

-(NSArray*)stringArrayToColorArray{
    NSMutableArray *colorArray = [[NSMutableArray alloc] init];
    
    for (int i =0; i < [self count]; i++) {
        NSString *colorString = [self objectAtIndex:i];
        
        [colorArray addObject:[colorString giveColorfromStringColor]];
        
    }
    return [colorArray copy];
}

@end
