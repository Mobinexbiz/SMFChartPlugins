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
    
-(void)setPosition:(JSValue *)frameLayout;
-(void)setValueFontSize:(JSValue *)size;
-(void)setValueColor:(JSValue *)color;
    
@property (strong) JSValue *onError;
@property (strong) JSValue *onItemSelected;
    
@end

@interface SMFChart : UIView<SMFChartExport,ChartViewDelegate,IChartAxisValueFormatter>

@property (strong) ChartViewBase *chartView;
    
-(void)updateChartData;
    
@property (nonatomic, retain) NSDictionary *layoutFrame;
    
@property (retain) NSNumber *vFontSize;
@property (strong) UIColor *vColor;
    
@property (strong) JSValue *onError;
@property (strong) JSValue *onItemSelected;
    
@end
