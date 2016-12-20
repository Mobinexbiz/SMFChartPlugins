//
//  SMFPieChart.h
//  Smartface
//
//  Created by Dogan Ekici on 30/11/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "SMFChart.h"
#import <JavaScriptCore/JavaScriptCore.h>

@protocol SMFPieExport<JSExport>

/*!
 * @discussion Chart set data function
 * @param (JSValue *) must be Array
 * @param (JSValue *) must be Array
 * @warning First parameter and second parameter must be same
 */
-(void)setData:(JSValue *)valueArray
              :(JSValue *)nameArray;

/*!
 * @discussion Pie chart data custom color
 * @param (JSValue *) must be Array
 * @param (JSValue *) must be Array
 * @param (JSValue *) must be Array
 */
-(void)setDataWithColors:(JSValue *)valueArray
                        :(JSValue *)nameArray
                        :(JSValue *)colorArray;

/*!
 * @discussion If you want change rotation status
 * @param (JSValue *) must be Bool
 */
-(void)setRotationEnabled:(JSValue *)boolValue;
    
@end

@interface SMFPieChart : SMFChart<SMFPieExport>

/*!
 * @brief SMFPieChart class' nameArray object.
 */
@property (copy) NSArray *nameArray;

/*!
 * @brief SMFPieChart class' valueArray object.
 */
@property (copy) NSArray *valueArray;

/*!
 * @brief SMFPieChart class' colorArray object.
 */
@property (copy) NSArray *colorArray;

@end
