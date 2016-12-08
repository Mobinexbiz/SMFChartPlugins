//
//  SMFPieChart.m
//  Smartface
//
//  Created by Dogan Ekici on 30/11/16.
//  Copyright © 2016 Smartface. All rights reserved.
//

#import "SMFPieChart.h"

@implementation SMFPieChart
    
- (instancetype)initWithDictionary:(NSString*)layoutFrame {
    self = [super init];
    if (self) {
        self.nameArray = [[NSArray alloc] init];
        self.valueArray = [[NSArray alloc] init];
        self.colorArray = [[NSArray alloc] init];
        
        self.vColor = [UIColor blackColor];
        self.vFontSize = [NSNumber numberWithFloat:11.f];
        
        self.chartView = [[PieChartView alloc] init];
        ((PieChartView *)self.chartView).drawSlicesUnderHoleEnabled = NO;
        ((PieChartView *)self.chartView).transparentCircleRadiusPercent = 0.61;
        self.chartView.chartDescription.enabled = NO;
        [self.chartView  setExtraOffsetsWithLeft:5.f top:10.f right:5.f bottom:5.f];
        //Center Text
        ((PieChartView *)self.chartView).drawCenterTextEnabled = NO;
        
        NSMutableParagraphStyle *paragraphStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
        paragraphStyle.lineBreakMode = NSLineBreakByTruncatingTail;
        paragraphStyle.alignment = NSTextAlignmentCenter;
        
        ((PieChartView *)self.chartView).drawHoleEnabled = YES;
        
        
        self.chartView.delegate = self;
        
        ((PieChartView *)self.chartView).holeColor = UIColor.whiteColor;
        ((PieChartView *)self.chartView).transparentCircleColor = [UIColor.whiteColor colorWithAlphaComponent:0.43];
        ((PieChartView *)self.chartView).holeRadiusPercent = 0.58;
        ((PieChartView *)self.chartView).rotationEnabled = YES;
        ((PieChartView *)self.chartView).highlightPerTapEnabled = YES;
        
        ((PieChartView *)self.chartView).maxAngle = 360.0; // Half chart
        ((PieChartView *)self.chartView).rotationAngle = 360.0; // Rotate to make the half on the upper side
        ((PieChartView *)self.chartView).centerTextOffset = CGPointMake(0.0, -20.0);
        
        ChartLegend *la = self.chartView.legend;
        la.horizontalAlignment = ChartLegendHorizontalAlignmentCenter;
        la.verticalAlignment = ChartLegendVerticalAlignmentTop;
        la.orientation = ChartLegendOrientationHorizontal;
        la.drawInside = NO;
        la.xEntrySpace = 7.0;
        la.yEntrySpace = 0.0;
        la.yOffset = 0.0;
        
        // entry label styling
        ((PieChartView *)self.chartView).entryLabelColor = UIColor.whiteColor;
        ((PieChartView *)self.chartView).entryLabelFont = [UIFont fontWithName:@"HelveticaNeue-Light" size:12.f];
        
        [self addSubview:self.chartView];
        
        
    }
    return self;
}
    
- (void)updateChartData
    {
        if (self.chartView.data)
        {
            self.chartView.data = nil;
            //return;
        }
        
        [self setDataPie];
    }
    
    
- (void)setDataPie
    {
        
        NSMutableArray *values = [[NSMutableArray alloc] init];
        
        for (int i = 0; i < [self.nameArray count]; i++)
        {
            
            [values addObject:[[PieChartDataEntry alloc] initWithValue:[[self.valueArray objectAtIndex:i] floatValue] label:[self.nameArray objectAtIndex:i]]];
        }
        
        PieChartDataSet *dataSet = [[PieChartDataSet alloc] initWithValues:values label:@""];
        dataSet.sliceSpace = 3.0;
        dataSet.selectionShift = 5.0;
        if ([self.colorArray count] == [self.nameArray count]) {
            dataSet.colors = self.colorArray;
        }else{
            dataSet.colors = ChartColorTemplates.material;
        }
        
        
        PieChartData *data = [[PieChartData alloc] initWithDataSet:dataSet];
        
        NSNumberFormatter *pFormatter = [[NSNumberFormatter alloc] init];
        pFormatter.numberStyle = NSNumberFormatterDecimalStyle;
        pFormatter.maximumFractionDigits = 1;
        pFormatter.multiplier = @1.f;
        [data setValueFormatter:[[ChartDefaultValueFormatter alloc] initWithFormatter:pFormatter]];
        
        [data setValueFont:[UIFont fontWithName:@"HelveticaNeue-Light" size:[self.vFontSize floatValue]]];
        [data setValueTextColor:self.vColor];
        
        self.chartView.data = data;
        
        [self.chartView animateWithXAxisDuration:1.4 easingOption:ChartEasingOptionEaseOutBack];
        
        [self.chartView setNeedsDisplay];
    }
    
-(void)chartValueSelected:(ChartViewBase *)chartView entry:(ChartDataEntry *)entry highlight:(ChartHighlight *)highlight{
    NSNumber *value =  [NSNumber numberWithDouble:highlight.y];
    NSNumber *index =  [NSNumber numberWithDouble:highlight.x];
    NSString *name = [self.nameArray objectAtIndex:[index intValue]];
    
    NSDictionary *selected = [[NSDictionary alloc] initWithObjectsAndKeys:
                              index,@"index" ,
                              name,@"text",
                              value,@"value",
                              nil];
    
    [self.onItemSelected callWithArguments:@[selected]];
}
    
-(void)setData:(JSValue *)valueArray
              :(JSValue *)nameArray{
    dispatch_async(dispatch_get_main_queue(), ^{
        
        if ([nameArray toArray].count != [valueArray toArray].count) {
            
            NSDictionary *error = [[NSDictionary alloc] initWithObjectsAndKeys:
                                   @"Data and label array length must be same",@"errorText" ,
                                   @"1002",@"errorCode",
                                   nil];
            
            [self.onError callWithArguments:@[error]];
            return ;
            
        }
        
        self.nameArray = [nameArray toArray];
        self.valueArray = [valueArray toArray];
        self.colorArray = [NSArray new];
        
        [self updateChartData];
        
    });
}
    
-(void)setDataWithColors:(JSValue *)valueArray
                        :(JSValue *)nameArray
                        :(JSValue *)colorArray{
    
    dispatch_async(dispatch_get_main_queue(), ^{
        if ([nameArray toArray].count != [valueArray toArray].count || [nameArray toArray].count != [colorArray toArray].count) {
            
            NSDictionary *error = [[NSDictionary alloc] initWithObjectsAndKeys:
                                   @"Data, label and color array length must be same",@"errorText" ,
                                   @"1003",@"errorCode",
                                   nil];
            
            [self.onError callWithArguments:@[error]];
            return ;
            
        }
        
        self.nameArray = [nameArray toArray];
        self.valueArray = [valueArray toArray];
        self.colorArray = [[colorArray toArray] stringArrayToColorArray];
        
        [self updateChartData];
        
    });
    
}
    
-(void)setRotationEnabled:(JSValue *)boolValue{
    ((PieChartView *)self.chartView).rotationEnabled = [boolValue toBool];
}
    
@end
