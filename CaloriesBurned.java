package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;


public class CaloriesBurned extends AppCompatActivity {

    //declare variable to display current calories burned
    TextView caloriesburned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set activity_calories_burned.xml for layout
        setContentView(R.layout.activity_calories_burned);
        //find caloriesBurnedGraph in activity_calories_burned.xml
        GraphView graph = (GraphView)findViewById(R.id.caloriesBurnedGraph);
        //create bar graph where x-label will be day of the week y-label will be calories burned
        BarGraphSeries<DataPoint> caloriesBurnedGraph = new BarGraphSeries<>(new DataPoint[] {
                //set data point
                new DataPoint(0, 120),

        });
        //display bar graph
        graph.addSeries(caloriesBurnedGraph);
        //set color of bars to blue
        caloriesBurnedGraph.setColor(Color.BLUE);
        //set spacing between bars to 10
        caloriesBurnedGraph.setSpacing(10);
        //set the width of the bars to 0.4
        caloriesBurnedGraph.setDataWidth(0.4);


        //set the min of y-axis(calories burned) to 0
        graph.getViewport().setMinY(0);
        //set the max of y-axis(calories burned) to 2000
        graph.getViewport().setMaxY(2000);
        //set bounds above
        graph.getViewport().setYAxisBoundsManual(true);
        //title of bar graph
        graph.setTitle("My Calories Burned");
        //set title color to black
        graph.setTitleColor(R.color.black);
        //set x-axis title to day of the week
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Day of the Week");
        //set y-axis title to calories burned
        graph.getGridLabelRenderer().setVerticalAxisTitle(" Calories Burned(kg)");

        //declare new format for bar graph
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        //x-axis will display Monday-Sunday [0-6]
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"});

        //set format
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        //set the x-axis labels at an angle of 160
        GridLabelRenderer renderer = graph.getGridLabelRenderer();
        renderer.setHorizontalLabelsAngle(160);
      //set the
        graph.getGridLabelRenderer().setTextSize(31f);
        graph.getViewport().setScrollable(true);

        //set variable equal to the text view on activity_calories_burned.xml
        caloriesburned=(TextView)findViewById(R.id.cb);

        //set a random number as the number of calories burned of now this will be grabbed from shared preferences once bluetooth is functioning
        String usersc="120";
        caloriesburned.setText(usersc);

    }
}










