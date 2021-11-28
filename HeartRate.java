package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HeartRate extends AppCompatActivity {

    SimpleDateFormat sdf= new SimpleDateFormat("mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        GraphView graph = (GraphView) findViewById(R.id.heartRateGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {



                new DataPoint(new Date().getTime(), 1),
                new DataPoint(new Date().getTime(), 2),
                new DataPoint(new Date().getTime(), 3),
                new DataPoint(new Date().getTime(), 4),
                new DataPoint(new Date().getTime(), 5),
               /* new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)*/
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

    }

}