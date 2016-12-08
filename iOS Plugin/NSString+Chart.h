//
//  NSString+Chart.h
//  Smartface
//
//  Created by Dogan Ekici on 11/14/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (Chart)

-(NSString*)getNumbersFromString;

-(UIColor*)giveColorfromStringColor;

-(BOOL)checkOnlyNumber;
    
@end
