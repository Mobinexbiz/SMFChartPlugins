# Smartface Chart Plugin

### Version

1.0.0

### Installation & Usage

#### iOS Screenshots

<img width=250 src="https://github.com/Mobinexbiz/SMFChartPlugins/blob/master/Screenshot/barChart.PNG">
<img width=250 src="https://github.com/Mobinexbiz/SMFChartPlugins/blob/master/Screenshot/lineChart.PNG">
<img width=250 src="https://github.com/Mobinexbiz/SMFChartPlugins/blob/master/Screenshot/pieChart.PNG">

#### Android Screenshots

<img width=250 src="https://github.com/Mobinexbiz/SMFChartPlugins/blob/master/Screenshot/androidBarChart.png">
<img width=250 src="https://github.com/Mobinexbiz/SMFChartPlugins/blob/master/Screenshot/androidLineChart.png">
<img width=250 src="https://github.com/Mobinexbiz/SMFChartPlugins/blob/master/Screenshot/androidPieChart.png">

#### Requirements
iOS 7+ and Android Api 14+

To start using Chart plugin you need to follow below steps:

 - Download plugin file in PluginZip folder.
 - Copy plugin file to Smartface plugin folder.
 - Set the Chart plugin in plugin.json.
 

### USAGE BAR CHART & LINE CHART

#### Create New Bar Chart View 
Parameters	:  
- 1.Parameter : Paramater must be "Landroid/app/Activity;" 

``` Javascript
var barChart = new SMFBarChart("Landroid/app/Activity;");
var lineChart = new SMFLineChart("Landroid/app/Activity;");
```

- Other functions are same for the LineChart

#### Set Position Bar Chart View 
Parameters	: 
- 1.Parameter : Paramater must be json like that 
 - {"left" : "0%","top" : "0%","width" : "100%","height" : "100%"} //Percent
 - {"left" : 10, "top" : 500, "width" : 800, height" : "1000"} //Pixel

``` Javascript
barChart.setPosition({
		"left" : "0%",
		"top" : "20%",
		"width" : "100%",
		"height" : "50%"
});
```

#### Set onError Bar Chart View
Return Values	: 
- e.errorText  = Error Description Text
- e.errorCode  = Error Code

Error Codes:
- Error Code : 1001 
- Error Text : Please check your position values
- Error Code : 1002 
- Error Text : X and Y data array length must be same
- Error Code : 1003 
- Error Text : Please check your R-G-B value (Must be between 0-255)

``` Javascript
barChart.onError = function (e) {
 alert("Error Text : " + e.errorText);
 alert("Error Code : " + e.errorCode);
};
```
		
#### Set onItemSelected Bar Chart View
Return Values	: 
- e.index	 = Selected Item Array Index
- e.x		    = Selected Item X Value
- e.y	  	  = Selected Item Y Value

``` Javascript
barChart.onItemSelected = function (e) {
		alert("Index : " + e.index);
		alert("xValue : " + e.x);
		alert("yValue : " + e.y);
};
```

#### Set onItemSelected Bar Chart View
Return Values	: 
- e.index	 = Selected Item Array Index
- e.x		    = Selected Item X Value
- e.y	  	  = Selected Item Y Value

``` Javascript
barChart.onItemSelected = function (e) {
		alert("Index : " + e.index);
		alert("xValue : " + e.x);
		alert("yValue : " + e.y);
};
```

#### Set Data Bar Chart View 
Parameters	: 	 
- 1. Parameter : X			"Array"
- 2. Parameter : Y			"Array"
- 3. Parameter : Data Information	"String"

``` Javascript
var xArray = [2001,2002,2003,2004,2005,2006,2007,2008,2009,2010];
var yArray = [56,35.5,22.12,45.15,20.35,6.12,2.12,3.5,45.15,20.35];

barChart.setData(xArray,yArray);
```

#### Set Color Bar Chart View
Parameters	:	 
- 1. Parameter : Color Name must be Upper Case 	"String" 
- Color Names :
 - RED, BLACK, BLUE, CYAN, GRAY, GREEN, MAGENTA, WHITE, YELLOW

``` Javascript
barChart.setColor("Blue");
```

#### Set RGB Color  Bar Chart View
Parameters	:	
- 1. Parameter : Color RED Value (Must be between0-255)	   	 "int" 
- 2. Parameter : Color GREEN Value (Must be between0-255)	 "int" 
- 3. Parameter : Color BLUE Value (Must be between0-255) 	 "int" 

``` Javascript
barChart.setColorRGB(0,0,255);
```

#### Set Value Color Bar Chart View
Parameters	:	 
- 1. Parameter : Color Name must be Upper Case 	"String" 
- Color Names :
 - RED, BLACK, BLUE, CYAN, GRAY, GREEN, MAGENTA, WHITE, YELLOW

``` Javascript
barChart.setValueColor("Blue");
```

#### Set Value Font Size Bar Chart View
Parameters	:	 
- 1. Parameter : Font Size 			"Float" 

``` Javascript
barChart.setValueFontSize(12);
```

#### Set XAxis Font Size Bar Chart View
Parameters	:	 
- 1. Parameter : Font Size 			"Float" 

``` Javascript
barChart.setXAxisFontSize(12);
```

#### Set YAxis Font Size Bar Chart View
Parameters	:	 
- 1. Parameter : Font Size 			"Float" 

``` Javascript
barChart.setYAxisFontSize(12);
```

#### Bar Chart View add to Page
``` Javascript
page1.add(barChart);
```




### USAGE PIE CHART

#### Create New Pie Chart View 
Parameters	:  
- 1.Parameter : Paramater must be "Landroid/app/Activity;" 

``` Javascript
var pieChart = new SMFPieChart("Landroid/app/Activity;");
```

#### Set Position Pie Chart View
Parameters	: 
- 1.Parameter : Paramater must be json like that 
 - {"left" : "0%","top" : "0%","width" : "100%","height" : "100%"} // Percent
 - {"left" : 10, "top" : 500, "width" : 800, height" : "1000"}  // Pixel

``` Javascript
pieChart.setPosition({
		"left" : "0%",
		"top" : "20%",
		"width" : "100%",
		"height" : "50%"
});
```

#### Set onError Pie Chart View
Return Values	: 
- e.errorText  = Error Description Text
- e.errorCode  = Error Code

Error Codes:
- Error Code : 1001 
- Error Text : Please check your position values
- Error Code : 1002 
- Error Text : Data and label array length must be same
- Error Code : 1003 
- Error Text : Data, label and color array length must be same

``` Javascript
pieChart.onError = function (e) {
 alert("Error Text : " + e.errorText);
 alert("Error Code : " + e.errorCode);
};		
```

#### Set onItemSelected Pie Chart View
Return Values	: 
- e.index	  	 Selected Item Array Index
- e.text		 	 Selected Item Text Value
- e.value	  	 Selected Item Value

``` Javascript
pieChart.onItemSelected = function (e) {
		alert("Index : " + e.index);
		alert("text : " + e.text);
		alert("value : " + e.value);
};
```

#### Set Data Pie Chart View
Parameters	: 	 
- 1. Parameter : Datas			"Array"
- 2. Parameter : Labels			"Array"
 
``` Javascript
pieChart.onItemSelected = function (e) {
 var Datas = [2001,2002,2003];
 var Labels = ["Test1","Test2","Test3"];
 pieChart.setData(Datas,Labels);
};
```

#### Set Data With Colors Pie Chart View
Parameters	: 	 
- 1. Parameter : Datas			"Array"
- 2. Parameter : Labels			"Array"
- 3. Parameter : Colors			"Array"
- Color Names :
 - RED, BLACK, BLUE, CYAN, GRAY, GREEN, MAGENTA, WHITE, YELLOW

``` Javascript
var Datas = [2001,2002,2003];
var Labels = ["Test1","Test2","Test3"];
var Colors = ["RED","GRAY","CYAN"]

pieChart.setDataWithColors(Datas,Labels,Colors);
```

#### Set Value Color Pie Chart View
Parameters	:	
- 1. Parameter : Color Name must be Upper Case 	"String" 
- Color Names :
 - RED, BLACK, BLUE, CYAN, GRAY, GREEN, MAGENTA, WHITE, YELLOW

``` Javascript
barChart.setValueColor("Blue");
```

#### Set Value Font Size Pie Chart View
Parameters	:	 
- 1. Parameter : Font Size 			"Float" 

``` Javascript
barChart.setValueFontSize(12);
```

#### Set Rotation Enable Pie Chart View
Parameters	: 	 
- 1. Parameter : Enabled			"Boolean"

``` Javascript
// Default true
pieChart.setRotationEnabled(false);
```


### Contact Information
- Project Owner : Smartface Inc
- Project Website : [smartface.io](https://smartface.io)

### Code of Conduct 
"We, as the community behind this project, are committed to making participation in this project a harassment-free experience for everyone, regardless of the level of expertise, gender, gender identity and expression, sexual orientation, disability, personal appearance, body size, race, ethnicity, age, religion or nationality."

### License
Smartface Chart Plugins is licensed under the Apache License, Version 2.0. See the LICENSE file for more info.

### ChangeLog
Read the [CHANGELOG.md](https://github.com/Mobinexbiz/SMFChartPlugins/blob/master/CHANGELOG.md) file.
