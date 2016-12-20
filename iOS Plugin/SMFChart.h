//
//  SMFChart.h
//  Smartface
//
//  Created by Dogan Ekici on 12/7/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <JavaScriptCore/JavaScriptCore.h>
#import "NSArray+Charts.h"
#import "NSDictionary+Charts.h"
#import "NSString+Chart.h"
#import <Charts/Charts-Swift.h>

@protocol SMFChartExport<JSExport>

- (instancetype)initWithDictionary:(NSString*)layoutFrame;

/*!
 * @discussion View set frame method
 * @param (JSValue *)frameLayout must be view.frame Dictionary ex: { x : "", y : "" , width : "" , height : ""}
 */
-(void)setPosition:(JSValue *)frameLayout;

/*!
 * @discussion chart value font size
 * @param (JSValue *)size must be Double
 */
-(void)setValueFontSize:(JSValue *)size;

/*!
 * @discussion chart value color
 * @param (JSValue *)color must be String
 */
-(void)setValueColor:(JSValue *)color;

/*!
 * @brief SMFChartExport protocol' onError object.
 */
@property (strong) JSValue *onError;

/*!
 * @brief SMFChartExport protocol' onItemSelected object.
 */
@property (strong) JSValue *onItemSelected;

@end

@interface SMFChart : UIView<SMFChartExport,ChartViewDelegate,IChartAxisValueFormatter>

/*!
 * @brief SMFChart class' chartView object.
 */
@property (strong) ChartViewBase *chartView;

-(void)updateChartData;

@property (nonatomic, retain) NSDictionary *layoutFrame;

/*!
 * @brief SMFChart class' vFontSize object.
 */
@property (retain) NSNumber *vFontSize;

/*!
 * @brief SMFChart class' vColor object.
 */
@property (strong) UIColor *vColor;

/*!
 * @brief SMFChart class' onError object.
 */
@property (strong) JSValue *onError;

/*!
 * @brief SMFChart class' onItemSelected object.
 */
@property (strong) JSValue *onItemSelected;

@end
