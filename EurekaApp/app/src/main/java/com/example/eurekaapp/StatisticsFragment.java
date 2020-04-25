package com.example.eurekaapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class StatisticsFragment extends Fragment {
    LineGraphSeries<DataPoint> series;
    EditText score;

    String dataURL = "https://corona-api.com/timeline";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        double y,x;
        x = -5.0;
        View rootView =  inflater.inflate(R.layout.fragment_statistics,container,false);
        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        final EditText score = rootView.findViewById(R.id.scoreText);
        score.setEnabled(false);
        EditText confirmedEditText = (EditText)rootView.findViewById(R.id.editText);
        EditText activeEditText = (EditText)rootView.findViewById(R.id.editText2);
        EditText deathsEditText = (EditText)rootView.findViewById(R.id.editText3);

        getLatestData(confirmedEditText, "confirmed");
        getLatestData(activeEditText, "active");
        getLatestData(deathsEditText, "deaths");

        getGraphData(graph);
//        ArrayList<Integer> confirmedCasesData = graphData.get(1);

//        makeGraph(graph, confirmedCasesData);

//        Button button = (Button) rootView.findViewById(R.id.scoreButton);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                // do something
//                score.setText("12321");
//            }
//        });

        return rootView;
    }

    private void getLatestData(EditText confirmedEditText, String attribute) {
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        JSONObject object = new JSONObject();
        try {
            // input API parameters
            object.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, dataURL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = (JSONArray) response.get("data");
                            JSONObject soloData = data.getJSONObject(0);
                            Integer latestData = (Integer) soloData.get(attribute);
                            updateData(confirmedEditText, latestData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Invalid attempt. Try again", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);
    }

    private void updateData(EditText editText, Integer latestData) {
        editText.setText(Integer.toString(latestData));
    }

    public void onClick(View view){


    }
    private ArrayList<ArrayList> getGraphData(GraphView graph) {
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<Integer> confirmedCases = new ArrayList<Integer>();
        JSONObject object = new JSONObject();
        try {
            // input API parameters
            object.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, dataURL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = (JSONArray) response.get("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject soloData = data.getJSONObject(i);
                                String date = (String) soloData.get("updated_at");
                                Integer confirmed = (Integer) soloData.get("confirmed");
                                dates.add(date);
                                confirmedCases.add(confirmed);
                            }
                            Collections.reverse(confirmedCases);
                            makeGraph(graph, confirmedCases);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Invalid attempt. Try again", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);
        ArrayList<ArrayList> response = new ArrayList<ArrayList>();
        response.add(dates);
        response.add(confirmedCases);
        return(response);
    }

    private void makeGraph(GraphView graph, ArrayList<Integer> confirmedCasesData) {

        graph.setTitle("Total Confirmed Cases");
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Days");
//        gridLabel.setVerticalAxisTitle("Number of Cases");
//        graph.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
//        graph.getViewport().setScrollable(true);  // activate horizontal scrolling
//        graph.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
//        graph.getViewport().setScrollableY(true);  // activate vertical scrolling
//        graph.setPadding(50, 0, 50, 0);

        DataPoint[] dataPoints = new DataPoint[confirmedCasesData.size()]; // declare an array of DataPoint objects with the same size as your list
        for (int i = 0; i < confirmedCasesData.size(); i++) {
            // add new DataPoint object to the array for each of your list entries
            dataPoints[i] = new DataPoint(i, confirmedCasesData.get(i)); // not sure but I think the second argument should be of type double
            Log.d("debug Time", Integer.toString(confirmedCasesData.get(i)));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);

    }
}
