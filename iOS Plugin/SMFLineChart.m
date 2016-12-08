//
//  SMFLineChart.m
//  Smartface
//
//  Created by Dogan Ekici v1 v2 on 12/7/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "SMFLineChart.h"

@implementation SMFLineChart
    
- (instancetype)initWithDictionary:(NSString*)layoutFrame {
    self = [super init];
    if (self) {
        
        self.xValues = [[NSMutableArray alloc] init];
        self.yValues = [[NSMutableArray alloc] init];

        self.barColor = [UIColor blueColor];
        
        self.vColor = [UIColor blackColor];
        self.vFontSize = [NSNumber numberWithFloat:13.f];
        
        self.chartView = [[LineChartView alloc] init];
        
        self.chartView.delegate = self;
        
        self.chartView.chartDescription.enabled = NO;
        
        ((LineChartView *)self.chartView).dragEnabled = YES;
        [((LineChartView *)self.chartView) setScaleEnabled:YES];
        ((LineChartView *)self.chartView).pinchZoomEnabled = NO;
        ((LineChartView *)self.chartView).drawGridBackgroundEnabled = NO;
        ((LineChartView *)self.chartView).highlightPerDragEnabled = YES;
        
        self.chartView.legend.enabled = NO;
        
        ChartXAxis *xAxis = self.chartView.xAxis;
        xAxis.labelPosition = XAxisLabelPositionBottom;
        xAxis.labelFont = [UIFont systemFontOfSize:10.f];
        xAxis.drawGridLinesEnabled = NO;
        xAxis.granularity = 1.0; // only intervals of 1 day
        xAxis.valueFormatter = self;
        
        NSNumberFormatter *percentFormatter = [[NSNumberFormatter alloc] init];
        
        ChartYAxis *leftAxis = ((LineChartView *)self.chartView).leftAxis;
        leftAxis.labelFont = [UIFont fontWithName:@"HelveticaNeue-Light" size:8.f];
        leftAxis.labelTextColor = UIColor.darkGrayColor;
        leftAxis.valueFormatter = [[ChartDefaultAxisValueFormatter alloc] initWithFormatter:percentFormatter];
        
        ((LineChartView *)self.chartView).rightAxis.enabled = NO;
        
        [self addSubview:self.chartView];
        
        
    }
    return self;
}
    
- (void)updateChartData{
    if (self.chartView.data)
    {
        self.chartView.data = nil;
    }
    
    [self setDataLine];
}
    
- (void)setDataLine{
    
    NSMutableArray *values = [[NSMutableArray alloc] init];
    NSMutableArray<UIColor *> *colors = [[NSMutableArray alloc] init];
    NSMutableArray<UIColor *> *valueColors = [[NSMutableArray alloc] init];
    
    [colors addObject:self.barColor];
    [valueColors addObject:self.vColor];
    
    for (int i = 0; i < [self.xValues count]; i++)
    {
        NSNumber *yValue = [self.yValues objectAtIndex:i];
        ChartDataEntry *entry = [[ChartDataEntry alloc] initWithX:i y:[yValue doubleValue]];
        [values addObject:entry];
        
    }
    
    LineChartDataSet *set = set = [[LineChartDataSet alloc] initWithValues:values label:@"Data"];
    set.colors = colors;
    set.valueColors = valueColors;
    set.drawCirclesEnabled = NO;
    
    LineChartData *data = [[LineChartData alloc] initWithDataSet:set];
    [data setValueFont:[UIFont systemFontOfSize:[self.vFontSize floatValue]]];
    
    NSNumberFormatter *formatter = [[NSNumberFormatter alloc] init];
    formatter.maximumFractionDigits = 1;
    [data setValueFormatter:[[ChartDefaultValueFormatter alloc] initWithFormatter:formatter]];
    
    self.chartView.data = data;
    
    [self.chartView animateWithYAxisDuration:1.4 easingOption:ChartEasingOptionEaseInOutQuart];
    
    [self.chartView setNeedsDisplay];

    
}
    
-(void)setData:(JSValue *)xArray
              :(JSValue *)yArray{
    dispatch_async(dispatch_get_main_queue(), ^{
        if ([xArray toArray].count != [yArray toArray].count) {
            
            NSDictionary *error = [[NSDictionary alloc] initWithObjectsAndKeys:
                                   @"X and Y data array length must be same",@"errorText" ,
                                   @"1002",@"errorCode",
                                   nil];
            
            [self.onError callWithArguments:@[error]];
            
            return ;
            
        }
        
        self.xValues = [xArray toArray];
        self.yValues = [yArray toArray];
        
        [self updateChartData];
    });
    
    
}
#pragma mark - ChartViewDelegate
    
- (void)chartValueSelected:(ChartViewBase * __nonnull)chartView entry:(ChartDataEntry * __nonnull)entry highlight:(ChartHighlight * __nonnull)highlight{
    
    NSNumber *yValue = [NSNumber numberWithDouble:highlight.y];
    NSNumber *index =  [NSNumber numberWithDouble:highlight.x];
    NSString *xValue = [self.xValues objectAtIndex:[index intValue]];
    
    NSDictionary *selected = [[NSDictionary alloc] initWithObjectsAndKeys:
                              [NSString stringWithFormat:@"%@",index],@"index" ,
                              xValue,@"x",
                              yValue,@"y",
                              nil];
    
    [self.onItemSelected callWithArguments:@[selected]];
    
}
    
- (NSString *)stringForValue:(double)value
                        axis:(ChartAxisBase *)axis{
    NSString *string = [NSString stringWithFormat:@"%@",[self.xValues objectAtIndex:value]];
    return string;
}
    
-(void)setXAxisFontSize:(JSValue *)size{
    ChartXAxis *xAxis = self.chartView.xAxis;
    xAxis.labelFont = [UIFont systemFontOfSize:[size toDouble]];
}
    
-(void)setYAxisFontSize:(JSValue *)size{
    ChartYAxis *leftAxis = ((BarChartView *)self.chartView).leftAxis;
    leftAxis.labelFont = [UIFont fontWithName:@"HelveticaNeue-Light" size:[size toDouble]];
}
    
-(void)setColor:(JSValue *)color{
    self.barColor =  [[color toString] giveColorfromStringColor];
    
    [self updateChartData];
}
    
-(void)setColorRGB:(JSValue *)Red
                  :(JSValue *)Green
                  :(JSValue *)Blue{
    
    if ([self checkRGBrange:Red green:Green blue:Blue]) {
        self.barColor = [UIColor colorWithRed:[Red toDouble]/255.0f
                                        green:[Green toDouble]/255.0f
                                         blue:[Blue toDouble]/255.0f
                                        alpha:1.0f];
        [self updateChartData];
    }else{
        NSDictionary *error = [[NSDictionary alloc] initWithObjectsAndKeys:
                               @"Please check your R-G-B value (Must be between 0-255)",@"errorText" ,
                               @"1003",@"errorCode",
                               nil];
        
        [self.onError callWithArguments:@[error]];
    }
    
}
    
-(BOOL)checkRGBrange:(JSValue *)Red
               green:(JSValue *)Green
                blue:(JSValue *)Blue{
    if ([Red toDouble] < 0 || [Red toDouble] > 255) {
        return NO;
    }
    
    if ([Green toDouble] < 0 || [Green toDouble] > 255) {
        return NO;
    }
    
    if ([Blue toDouble] < 0 || [Blue toDouble] > 255) {
        return NO;
    }
    
    return YES;
}
    @end
