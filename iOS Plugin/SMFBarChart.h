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
    
-(void)setData:(JSValue *)xArray
              :(JSValue *)yArray;
    
-(void)setColor:(JSValue *)color;
-(void)setColorRGB:(JSValue *)Red
               :(JSValue *)Green
               :(JSValue *)Blue;
-(void)setXAxisFontSize:(JSValue *)size;
-(void)setYAxisFontSize:(JSValue *)size;

@end

@interface SMFBarChart : SMFChart<SMFBarExport>

@property (copy) NSArray *xValues;
@property (copy) NSArray *yValues;

@property (strong) UIColor *barColor;
    
@end
