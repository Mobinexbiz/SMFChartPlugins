//
//  SMFLineChart.h
//  Smartface
//
//  Created by Dogan Ekici on 12/7/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "SMFChart.h"
#import <JavaScriptCore/JavaScriptCore.h>

@protocol SMFLineExport<JSExport>

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

@interface SMFLineChart : SMFChart <SMFLineExport>

/*!
* @brief SMFLineChart class' xValues object.
*/
@property (copy) NSArray *xValues;

/*!
 * @brief SMFLineChart class' yValues object.
 */
@property (copy) NSArray *yValues;

/*!
 * @brief SMFLineChart class' barColor object.
 */
@property (strong) UIColor *barColor;    

@end
