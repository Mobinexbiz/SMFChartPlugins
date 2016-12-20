
package io.smartface.chart;


import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.widget.AbsoluteLayout;


/**
 * Test activity.
 *
 * @author Metehan Semiz
 */
public class MainActivity extends AppCompatActivity {




    float[] testData = {200,1000,2500};
    String[] DataLabels = {"Test1","Test2","Test3"};
    String[] Colors = {"RED","GRAY","CYAN"};

    float[] testData2 = {200,123,300,500};
    String[] DataLabels2 = {"Test1","Test2","Test3","Test4"};

    SMFPieChart testPie;
    SMFBarChart testBar;
    SMFLineChart testLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final AbsoluteLayout layout = (AbsoluteLayout) findViewById(R.id.main_layout);

/*
        //BAR TEST
        //////////////////////////////////

        float[] testDataX = {2001,2009,2010};
        float[] testDataY = {12,33,43};



        testBar = new SMFBarChart(this);
        testBar.setValueFontSize(10);
        testBar.setData(testDataX, testDataY);

        testBar.setXAxisFontSize(12);

        testBar.setYAxisFontSize(17);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testBar.setValueFontSize(17);
                testBar.setYAxisFontSize(11);
                testBar.setValueColor("RED");
            }
        }, 5000);



        layout.addView(testBar);

*/
/*
        //PIE TEST
        //////////////////////////////////

        testPie = new SMFPieChart(this);


        testPie.setDataWithColors(testData, DataLabels, Colors);
        testPie.setValueColor("YELLOW");
        testPie.setValueFontSize(12);
        testPie.setRotationEnabled(false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testPie.setValueColor("BLUE");
                testPie.setValueFontSize(18);
                testPie.setData(testData2, DataLabels2);
            }
        }, 5000);


        //testPie.setPosition(10,10,800,1000);
        //testPie.setPositionTEST();

        layout.addView(testPie);

 */
        //LINE TEST
        //////////////////////////////////

        testLine = new SMFLineChart(this);
        float[] testDataX = {2001,2009,2010};
        float[] testDataY = {12,33,43};

        final float[] testDataX2 = {1,2,3,4};
        final float[] testDataY2 = {32,123,43,54};

        testLine.setData(testDataX, testDataY);

        testLine.setXAxisFontSize(12);

        testLine.setYAxisFontSize(17);

        testLine.setColor("RED");


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testLine.setValueFontSize(17);
                testLine.setYAxisFontSize(11);
                testLine.setValueColor("RED");

                testLine.setData(testDataX2, testDataY2);

                testLine.setColor("YELLOW");
            }
        }, 5000);



        layout.addView(testLine);



    }


}
