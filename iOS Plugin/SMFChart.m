//
//  SMFChart.m
//  Smartface
//
//  Created by Dogan Ekici on 12/7/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "SMFChart.h"

@implementation SMFChart
    
- (instancetype)initWithDictionary:(NSString*)layoutFrame{
    self = [super init];
    if (self) {
      
    }
    return self;
}
    
-(void)setPosition:(JSValue *)frameLayout{
    NSDictionary *frame = [[NSDictionary alloc] initWithDictionary:[[frameLayout toDictionary] resizeWindowPercent]];
    
    self.layoutFrame = frame;
    
    self.frame = CGRectMake([[frame objectForKey:@"left"] floatValue],
                            [[frame objectForKey:@"top"] floatValue],
                            [[frame objectForKey:@"width"] floatValue],
                            [[frame objectForKey:@"height"] floatValue]);
    
    [self.chartView setFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
    [self.chartView setNeedsDisplay];
    
}
-(void)setValueFontSize:(JSValue *)size{
    self.vFontSize = [NSNumber numberWithDouble:[size toDouble]];
    [self updateChartData];
}
-(void)setValueColor:(JSValue *)color{
    self.vColor =  [[color toString] giveColorfromStringColor];
    [self updateChartData];
}
    
-(void)updateChartData{
    
}
    
- (NSString *)stringForValue:(double)value
                        axis:(ChartAxisBase *)axis{
    
        NSString *string = [NSString stringWithFormat:@"%f",value];
        return string;
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
