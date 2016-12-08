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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;

import io.smartface.plugin.SMFJSObject;

/**
 * Created by metehansemiz on 30/11/16.
 */

public class SMFPieChart extends RelativeLayout {

    public SMFJSObject smfJSObject = null;


    private Activity currentActivity;
    private float[] DataX;
    private String[] Labels;
    private PieChart mChart;
    private PieDataSet dataset;
    private int R = 0,G = 0,B = 255;

    boolean gastureSingleTap = false;
    int chartAnimationTime = 1400;

    private String selectedValueColor = "WHITE";
    private float selectedValueFontSize = 11;

    private class ErrorCodes {
        public static final int POSITION_ERROR = 1001;
        public static final int ARRAY_LENGTH_ERROR = 1002;
        public static final int ARRAY_LENGTH_WITH_COLOR_ERROR = 1003;
    }


    public SMFPieChart(Activity activity) {
        super (activity);
        currentActivity = activity;

        mChart = new PieChart(activity);

        // CHART Setting
        mChart.getDescription().setEnabled(false);


        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(false);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateX(chartAnimationTime);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);



        // Selected Listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(gastureSingleTap){

                    gastureSingleTap = false;


                    try {

                        JSONObject obj = new JSONObject();
                        obj.put("text", Labels[(int)h.getX()]);
                        obj.put("value", h.getY());
                        obj.put("index", h.getX());
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

    public void setData(float[] dataX, String[] labels){

        setDataWithColors(dataX,labels,null);


    }

    public void setDataWithColors(float[] dataX, String[] labels , String[] colors){


        ArrayList<Integer> colorList = new ArrayList<>();
        ArrayList<PieEntry> entries = new ArrayList<>();
        DataX = dataX;
        Labels = labels;

        if((dataX.length == labels.length) && (colors == null || dataX.length == colors.length) ){

            this.removeAllViews();

            for(int i = 0 ; i < DataX.length ; i++){
                entries.add(new PieEntry(DataX[i], labels[i]));

                if( colors != null){
                    colorList.add(getColor(colors[i]));

                }

            }


            dataset = new PieDataSet(entries, "");
            if(colors == null){
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);

            }else{

                dataset.setColors(colorList);
            }


            PieData datas = new PieData(dataset);
            datas.setValueTextSize(selectedValueFontSize);
            datas.setValueTextColor(getColor(selectedValueColor));

            mChart.setEntryLabelColor(getColor(selectedValueColor));
            mChart.setEntryLabelTextSize(selectedValueFontSize);



            mChart.setData(datas);
            mChart.animateX(chartAnimationTime);
            mChart.invalidate();




            this.addView(mChart,new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        }
        else{
            if(colors == null){
                onError("Data and label array length must be same", ErrorCodes.ARRAY_LENGTH_WITH_COLOR_ERROR);
            }else{
                onError("Data, label and color array length must be same", ErrorCodes.ARRAY_LENGTH_ERROR);

            }

        }

    }


    private int getColor(String colorName){

        try{
            return Color.parseColor(colorName);

        }catch (Exception e ){
            return Color.BLUE;

        }

    }



    public void setValueFontSize(float size){

        if(dataset != null){
            dataset.setValueTextSize(size);
            mChart.setEntryLabelTextSize(size);
            mChart.invalidate();
        }

        selectedValueFontSize = size;

    }

    public void setValueColor(String color){

        if(dataset!=null){

            try{
                dataset.setValueTextColor(Color.parseColor(color));
                mChart.setEntryLabelColor(Color.parseColor(color));
                mChart.invalidate();
            }catch (Exception e ){
                dataset.setValueTextColor(Color.BLACK);
                mChart.setEntryLabelColor(Color.BLACK);
                mChart.invalidate();
            }

        }

        selectedValueColor = color;

    }

    public void setRotationEnabled(boolean status){
        mChart.setRotationEnabled(status);
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



}
