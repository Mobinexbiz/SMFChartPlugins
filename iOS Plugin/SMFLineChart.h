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
    
-(void)setData:(JSValue *)xArray
              :(JSValue *)yArray;
-(void)setColor:(JSValue *)color;
-(void)setColorRGB:(JSValue *)Red
                  :(JSValue *)Green
                  :(JSValue *)Blue;
-(void)setXAxisFontSize:(JSValue *)size;
-(void)setYAxisFontSize:(JSValue *)size;
    
    
@end

@interface SMFLineChart : SMFChart <SMFLineExport>

@property (copy) NSArray *xValues;
@property (copy) NSArray *yValues;

@property (strong) UIColor *barColor;    

@end
