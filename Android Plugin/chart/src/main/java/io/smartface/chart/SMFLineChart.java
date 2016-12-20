package io.smartface.chart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONObject;

import java.util.ArrayList;

import io.smartface.plugin.SMFJSObject;

/**
 * Created by metehansemiz on 07/12/16.
 */

public class SMFLineChart extends RelativeLayout {

    public SMFJSObject smfJSObject = null;


    private Activity currentActivity;
    private float[] DataX;
    private float[] DataY;
    private LineChart mChart;
    private LineDataSet dataset;
    private String selectedColor = "BLUE";
    private int R = 0,G = 0,B = 255;

    private String selectedValueColor = "BLACK";
    private float selectedValueFontSize = 13;

    boolean gastureSingleTap = false;
    int chartAnimationTime = 1400;

    private class ErrorCodes {
        public static final int POSITION_ERROR = 1001;
        public static final int ARRAY_LENGTH_ERROR = 1002;
        public static final int RGB_VALUE_ERROR = 1003;
    }


    public SMFLineChart(final Activity activity) {
        super (activity);
        currentActivity = activity;

        mChart = new LineChart(activity);

        // CHART Setting
        mChart.getDescription().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setDragEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setDrawGridBackground(false);
        mChart.animateY(chartAnimationTime);



        Legend l = mChart.getLegend();
        l.setEnabled(false);



        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);




        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf(DataX[(int) value]);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        mChart.fitScreen();


        // Selected Listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(gastureSingleTap){



                    gastureSingleTap = false;



                    try {

                        JSONObject obj = new JSONObject();
                        obj.put("x", DataX[(int)e.getX()]);
                        obj.put("y", e.getY());
                        obj.put("index", e.getX());
                        sendToMessageSMF("onItemSelected",obj);

                    } catch (Exception e1) {

                        e1.printStackTrace();
                    }

                }

            }

            @Override
            public void onNothingSelected() {



            }
        });


        //Chart gasture listener
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                gastureSingleTap = true;
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

        this.removeAllViews();
        this.addView(mChart,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }

    // Set Position
    public void setPosition (SMFJSObject jsonObj) {


        double leftVal = 1 ;
        double topVal = 1;
        double heightVal = 1 ;
        double widthVal = 1 ;
        int Measuredwidth = 0;
        int Measuredheight = 0;

        Point size = new Point();
        WindowManager w = currentActivity.getWindowManager();

        // Device screen size
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
            w.getDefaultDisplay().getSize(size);
            Measuredwidth = size.x;
            Measuredheight = size.y;
        }else{
            Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();
        }


        // Position calculate
        try{

            String Left = jsonObj.getProperty("left").toString();
            leftVal =  calculatePosition(Left, Measuredwidth);

            String Top = jsonObj.getProperty("top").toString();
            topVal =  calculatePosition(Top, Measuredheight);

            String Height = jsonObj.getProperty("height").toString();
            heightVal =  calculatePosition(Height, Measuredheight);

            String Width = jsonObj.getProperty("width").toString();
            widthVal =  calculatePosition(Width, Measuredwidth);

        }catch (Exception e){
            onError("Please check your position values", ErrorCodes.POSITION_ERROR);
            return;
        }


        setLayoutParams( new AbsoluteLayout.LayoutParams(
                        (int)widthVal,  // Width
                        (int)heightVal,   // height
                        (int)leftVal,   // left
                        (int)topVal     // top
                )
        );

    }

    private double calculatePosition(String number, int referanceNumber){
        if(!number.contains("%")){ // pixel
            return Double.parseDouble(number);
        }else{
            double val = Double.parseDouble(number.replace("%",""))/100 ;
            val =  (int)(referanceNumber*val);
            return val;
        }

    }

    // Set Data
    public void setData(float[] dataX, float[] dataY){

        ArrayList<Entry> entries = new ArrayList<>();
        DataX = dataX;
        DataY = dataY;

        if(DataX.length == DataY.length){

            float sum = 0;
            for(int i = 0 ; i < DataX.length ; i++){
                entries.add(new Entry(i, DataY[i]));

                sum += DataX[i];

            }

            dataset = new LineDataSet(entries,"");

            // Bar color change
            if(selectedColor.equals("Custom")){
                setColorRGB(R,G,B);
            }else{
                setColor(selectedColor);
            }
            // Bar value color change
            setValueColor(selectedValueColor);
            // Var value text font size
            dataset.setValueTextSize(selectedValueFontSize);

            dataset.setDrawCircles(false);

            // Create data
            LineData data = new LineData(dataset);


            mChart.setData(data);

            mChart.invalidate();
            mChart.animateY(chartAnimationTime);
        }
        else{
            onError("X and Y data array length must be same", ErrorCodes.ARRAY_LENGTH_ERROR);
        }

    }

    public void setColor(String colorName){
        if(dataset!=null){

            try{
                dataset.setColor(Color.parseColor(colorName));
                mChart.invalidate();
            }catch (Exception e ){
                dataset.setColor(Color.BLUE);

            }

        }

        selectedColor = colorName;

    }

    public void setColorRGB(int R, int G, int B){


        if(isInRange(R,0,255) && isInRange(G,0,255) && isInRange(B,0,255) ){

            if(dataset!=null){
                dataset.setColor(Color.rgb(R,G,B));
                mChart.invalidate();
            }

            this.R = R;
            this.G = G;
            this.B = B;

            selectedColor = "Custom";

        }else{

            onError("Please check your R-G-B value (Must be between 0-255)", ErrorCodes.RGB_VALUE_ERROR);
            return ;
        }

    }

    // Font size set
    public void setXAxisFontSize(float size){
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(size);
        mChart.invalidate();
    }

    public void setYAxisFontSize(float size){
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextSize(size);
        mChart.invalidate();
    }

    public void setValueFontSize(float size){

        if(dataset != null){
            dataset.setValueTextSize(size);
            mChart.invalidate();
        }

        selectedValueFontSize = size;

    }

    // Value color set
    public void setValueColor(String color){

        if(dataset!=null){

            try{
                dataset.setValueTextColor(Color.parseColor(color));
                mChart.invalidate();
            }catch (Exception e ){
                dataset.setValueTextColor(Color.BLACK);

            }

        }

        selectedValueColor = color;

    }

    // Range control
    private boolean isInRange(int num, int min, int max){

        if(num < min || num > max ){
            return false;
        }

        return true;

    }

    // onError callback
    private void onError(String errorMsg, int errorCode)
    {
        try {

            SMFJSObject func = smfJSObject.getProperty("onError");

            SMFJSObject[] params = new SMFJSObject[1];
            JSONObject obj = new JSONObject();
            obj.put("errorText", errorMsg);
            obj.put("errorCode", errorCode);
            params[0] = new SMFJSObject(obj);
            func.callAsFunction(smfJSObject, params);

        } catch (Exception e1) {

            e1.printStackTrace();
        }
    }

    // Smartface callback function
    private void sendToMessageSMF(String eventName, JSONObject obj){

        try {

            SMFJSObject func = smfJSObject.getProperty(eventName);

            SMFJSObject[] params = new SMFJSObject[1];
            params[0] = new SMFJSObject(obj);
            func.callAsFunction(smfJSObject, params);

        } catch (Exception e1) {

            e1.printStackTrace();
        }


    }


}
