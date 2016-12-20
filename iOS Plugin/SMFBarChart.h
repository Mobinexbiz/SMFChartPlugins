//
//  SMFBarChart.h
//  SMFBarChart
//
//  Created by Dogan Ekici on 11/11/16.
//  Copyright © 2016 Dogan Ekici. All rights reserved.
//

#import "SMFChart.h"
#import <JavaScriptCore/JavaScriptCore.h>

@protocol SMFBarExport<JSExport>

/*!
 * @discussion Chart set data function
 * @param (JSValue *) must be Array
 * @param (JSValue *) must be Array
 * @warning First parameter and second parameter must be same
 */
-(void)setData:(JSValue *)xArray
              :(JSValue *)yArray;

/*!
 * @discussion Chart bar set color function
 * @param (JSValue *) must be String
 */
-(void)setColor:(JSValue *)color;

/*!
 * @discussion Chart bar set color with rgb function
 * @param (JSValue *) must be String
 * @param (JSValue *) must be String
 * @param (JSValue *) must be String
 */
-(void)setColorRGB:(JSValue *)Red
                  :(JSValue *)Green
                  :(JSValue *)Blue;

/*!
 * @discussion Chart x axis font size
 * @param (JSValue *) must be Double
 */
-(void)setXAxisFontSize:(JSValue *)size;

/*!
 * @discussion Chart y axis font size
 * @param (JSValue *) must be Double
 */
-(void)setYAxisFontSize:(JSValue *)size;

@end

@interface SMFBarChart : SMFChart<SMFBarExport>

/*!
 * @brief SMFBarChart class' xValues object.
 */
@property (copy) NSArray *xValues;

/*!
 * @brief SMFBarChart class' yValues object.
 */
@property (copy) NSArray *yValues;


/*!
 * @brief SMFBarChart class' barColor object.
 */
@property (strong) UIColor *barColor;
    
@end
