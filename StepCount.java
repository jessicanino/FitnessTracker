package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class StepCount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);
        GraphView graph = (GraphView) findViewById(R.id.stepCountGraph);
        BarGraphSeries<DataPoint> stepCountGraph = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(stepCountGraph);
        stepCountGraph.setColor(Color.BLUE);
        stepCountGraph.setSpacing(20);
        stepCountGraph.setDataWidth(8);
    }
}