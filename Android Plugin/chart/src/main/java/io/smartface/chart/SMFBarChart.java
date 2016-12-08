package io.smartface.chart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;

import io.smartface.plugin.SMFJSObject;


/**
 * Chart Plugin.
 *
 * @author Metehan Semiz
 */
public class SMFBarChart extends RelativeLayout {

    public SMFJSObject smfJSObject = null;


    private Activity currentActivity;
    private float[] DataX;
    private float[] DataY;
    private BarChart mChart;
    private BarDataSet dataset;
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


    public SMFBarChart(Activity activity) {
        super (activity);
        currentActivity = activity;

        mChart = new BarChart(activity);

        // CHART Setting
        mChart.getDescription().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);
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
                    int index = findIndex(e.getX(), e.getY());

                    try {

                        JSONObject obj = new JSONObject();
                        obj.put("x", e.getX());
                        obj.put("y", e.getY());
                        obj.put("index", index);
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


    public void setPosition (SMFJSObject jsonObj) {


        double leftVal = 1 ;
        double topVal = 1;
        double heightVal = 1 ;
        double widthVal = 1 ;
        int Measuredwidth = 0;
        int Measuredheight = 0;

        Point size = new Point();
        WindowManager w = currentActivity.getWindowManager();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
            w.getDefaultDisplay().getSize(size);
            Measuredwidth = size.x;
            Measuredheight = size.y;
        }else{
            Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();
        }


        try{
            String Left = jsonObj.getProperty("left").toString();
            if(!Left.contains("%")){ // pixel
                leftVal = Double.parseDouble(Left);
            }else{
                leftVal = Double.parseDouble(Left.replace("%",""))/100 ;
                leftVal =  (int)(Measuredwidth*leftVal);
            }

            String Top = jsonObj.getProperty("top").toString();
            if(!Top.contains("%")){ // pixel
                topVal = Double.parseDouble(Top) ;
            }else{
                topVal = Double.parseDouble(Top.replace("%",""))/100 ;
                topVal =  (int)(Measuredheight*topVal);
            }

            String Height = jsonObj.getProperty("height").toString();
            if(!Height.contains("%")){ // pixel
                heightVal = Double.parseDouble(Height) ;
            }else{
                heightVal = Double.parseDouble(Height.replace("%",""))/100 ;
                heightVal =  (int)(Measuredheight*heightVal);
            }

            String Width = jsonObj.getProperty("width").toString();
            if(!Width.contains("%")){ // pixel
                widthVal = Double.parseDouble(Width) ;
            }else{
                widthVal = Double.parseDouble(Width.replace("%",""))/100 ;
                widthVal =  (int)(Measuredwidth*widthVal);
            }

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


    public void setData(float[] dataX, float[] dataY){

        ArrayList<BarEntry> entries = new ArrayList<>();
        DataX = dataX;
        DataY = dataY;

        if(DataX.length == DataY.length){

            float sum = 0;
            for(int i = 0 ; i < DataX.length ; i++){
                entries.add(new BarEntry(i, DataY[i]));

                sum += DataX[i];

            }

            dataset = new BarDataSet(entries, "");

            // Bar color change
            if(selectedColor.equals("Custom")){
                setColorRGB(R,G,B);
            }else{
                setColor(selectedColor);
            }
            // Bar value color change
            setValueColor(selectedValueColor);
            // Var value text font size
            setValueFontSize(selectedValueFontSize);


            // Create data
            BarData data = new BarData(dataset);


            data.setBarWidth(0.8f);


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
                mChart.invalidate();
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

            onError("Please check your R-G-B value (Must be between 0-255)",ErrorCodes.RGB_VALUE_ERROR);
            return ;
        }

    }

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



    private boolean isInRange(int num, int min, int max){

        if(num < min || num > max ){
            return false;
        }

        return true;

    }

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



    private int findIndex(float x, float y){

        for(int i = 0 ; i < DataX.length ; i++){

            if(x == DataX[i] && y == DataY[i]){

                    return i;
            }
        }
        return -1;
    }




}
