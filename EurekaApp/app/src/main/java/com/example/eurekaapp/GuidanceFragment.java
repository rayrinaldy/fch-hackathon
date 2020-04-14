package com.example.eurekaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class GuidanceFragment extends Fragment implements View.OnClickListener {

    Button button;
    SharedPreferences sp;

    final String url = "http://128.199.175.144:1337/users";

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_guidance, container, false);

        button = (Button) rootView.findViewById(R.id.guidanceButton);

        sp = this.getActivity().getSharedPreferences("eureka", Context.MODE_PRIVATE);
        String id = sp.getString("id", "null");
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            // input API parameters
            object.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url+"/"+id, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean isUnderObservation = (Boolean) response.get("underObservation");
                            Boolean isCovidPositive = (Boolean) response.get("isCovidPositive");
                            if(isCovidPositive) {
                                Toast.makeText(getActivity(), "You have been declared positive of COVID-19", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "You have NOT been declared positive of COVID-19", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
                Toast.makeText(getActivity(), "Invalid attempt. Try again", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);

        button = (Button) rootView.findViewById(R.id.guidanceButton);
        button.setOnClickListener(this);

        return inflater.inflate(R.layout.fragment_guidance,container,false);
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.guidanceButton:
//                switchFragment(HelpFragment.TAG);
                Toast.makeText(getActivity(), "Changing status", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
