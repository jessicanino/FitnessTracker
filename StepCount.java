package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class StepCount extends AppCompatActivity {
    //declare variable to display current step count and distance traveled
   TextView stepcount,distancedtraveled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set activity_step_count.xml for layout
        setContentView(R.layout.activity_step_count);
        GraphView graph = (GraphView) findViewById(R.id.stepCountGraph);
        BarGraphSeries<DataPoint> stepCountGraph = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5780),


        });
        graph.addSeries(stepCountGraph);
        stepCountGraph.setColor(Color.BLUE);
       stepCountGraph.setSpacing(10);
        stepCountGraph.setDataWidth(0.4);




        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(10000);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.setTitle("My Step Count");
        graph.setTitleColor(R.color.black);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Day of the Week");
        graph.getGridLabelRenderer().setVerticalAxisTitle(" Number of Steps");

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"});

        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        GridLabelRenderer renderer = graph.getGridLabelRenderer();
        renderer.setHorizontalLabelsAngle(160);
        //  graph()
        graph.getGridLabelRenderer().setTextSize(31f);
        graph.getViewport().setScrollable(true);

        stepcount=(TextView)findViewById(R.id.sc);
        String usersc="5780";
       stepcount.setText(usersc);

        distancedtraveled=(TextView)findViewById(R.id.dt);

        String userdc="1.95 miles";
       distancedtraveled.setText(userdc);

    }
}