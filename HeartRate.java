package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HeartRate extends AppCompatActivity {

    TextView heartrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        GraphView graph = (GraphView) findViewById(R.id.heartRateGraph);
        LineGraphSeries<DataPoint> heartrategraph = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 98),
                new DataPoint(1, 98),
                new DataPoint(2, 97),
                new DataPoint(7, 123),
                new DataPoint(8, 124)
        });
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(170);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.setTitle("My Heart Rate");
        graph.setTitleColor(R.color.black);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time of the day(hour)");
        graph.getGridLabelRenderer().setVerticalAxisTitle(" Heart Rate(bpm)");
        graph.addSeries(heartrategraph);
        heartrategraph.setColor(Color.BLUE);


        heartrate = (TextView) findViewById(R.id.hr);
        String userhr = "98 bpm";
        heartrate.setText(userhr);


    }
}






   /* EditText inputTextY;
    GraphView graphView;

    DatabaseHandler databaseHandler;
    SQLiteDatabase sqLiteDatabase;


    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);


    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        inputTextY = (EditText) findViewById(R.id.inputTextY);
        graphView = (GraphView) findViewById(R.id.heartRateGraph);

        databaseHandler = new DatabaseHandler(this);
        sqLiteDatabase = databaseHandler.getWritableDatabase();

        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(10);
        insertData();

    }

    public void insertData() {

        long xValue = new Date().getTime();
        int yValue = Integer.parseInt(String.valueOf(inputTextY.getText()));

        databaseHandler.insertToData(xValue, yValue);
      //  series.resetData(grabData());

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }

        });

    }



    private DataPoint[] grabData() {
        String [] column = {"xValue","yValue"};
        Cursor cursor = sqLiteDatabase.query("Table1",column,null, null,null,null,null);
        DataPoint [] dataPoints = new DataPoint[cursor.getCount()];
        for (int i=0; i<cursor.getCount();i++){
            cursor.moveToNext();
            dataPoints[i] =new DataPoint(cursor.getLong(0),cursor.getInt(1));
        }
        return dataPoints;
    }


  /*  LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {



                new DataPoint(new Date().getTime(), 1),
                new DataPoint(new Date().getTime(), 2),
                new DataPoint(new Date().getTime(), 3),
                new DataPoint(new Date().getTime(), 4),
                new DataPoint(new Date().getTime(), 5),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }

        });
        graph.addSeries(series);
        series.setColor(Color.BLUE);
        series.setThickness(8);
        //series.setDataPointRadius(10);


        private static float harrisBenedictRmr(int gender, float weightKg, float age, float heightCm) {
        if (gender == "F") {
            return 655.0955f + (1.8496f * heightCm) + (9.5634f * weightKg) - (4.6756f * age);
        } else {
            return 66.4730f + (5.0033f * heightCm) + (13.7516f * weightKg) - (6.7550f * age);
        }

    }

    }*/

