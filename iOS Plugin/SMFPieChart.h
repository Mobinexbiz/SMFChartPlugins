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

-(void)setData:(JSValue *)valueArray
              :(JSValue *)nameArray;

-(void)setDataWithColors:(JSValue *)valueArray
                        :(JSValue *)nameArray
                        :(JSValue *)colorArray;

-(void)setRotationEnabled:(JSValue *)boolValue;
    
@end

@interface SMFPieChart : SMFChart<SMFPieExport>

@property (copy) NSArray *nameArray;
@property (copy) NSArray *valueArray;
@property (copy) NSArray *colorArray;

@end
