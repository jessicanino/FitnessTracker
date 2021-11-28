package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class CaloriesBurned extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_burned);
        GraphView graph = (GraphView) findViewById(R.id.caloriesBurnedGraph);
        BarGraphSeries<DataPoint> caloriesBurnedGraph = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(caloriesBurnedGraph);
        caloriesBurnedGraph.setColor(Color.BLUE);
        caloriesBurnedGraph.setSpacing(20);
        //series.setThickness(8);
    }
}