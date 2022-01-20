package com.ism.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ism.bmicalculator.utils.VolleyWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog; //Progress dialog that tells us to wait...
    private TextView bmiTextView; // Displays the calculated BMI value
    private TextView bmiDescriptionTextView; //Displays the description message of the BMI




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the UI elements we want to use...
        Button calculateButton = findViewById(R.id.calculateButton);
        EditText heightEditText = findViewById(R.id.heightEditText);
        EditText weightEditText = findViewById(R.id.weightEditText);
        bmiDescriptionTextView = findViewById(R.id.resultsDescription);
        bmiTextView = findViewById(R.id.resultBMI);

        //Initialize the ProgressDialog
        progressDialog = new ProgressDialog(this);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = heightEditText.getText().toString();
                String weight = weightEditText.getText().toString();

                if(weight.isEmpty() || height.isEmpty())
                {
                    //TODO:: Task 3.1 Implement a dialog box to tell the user they must enter height and weight

                    //call the created function
                    alert("Height and Weight should not be Empty");

                }
                else
                {
                    sendRequestToCalculateBMI(height, weight);
                }
            }
        });
    }
    // Creating a function for dialog box...............
    private void alert(String warning){

        //creating a dialogue box by using an AlertDialog manager....
        AlertDialog dlg = new AlertDialog.Builder(MainActivity.this)
            .setTitle("Message")
            .setMessage(warning)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
        dlg.show();
    }

    private void sendRequestToCalculateBMI(String height, String weight){

        //Set a few values for our progress dialog like the title and message...
        progressDialog.setTitle("Sending request");
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        // Instantiate the RequestQueue.
        RequestQueue queue = VolleyWrapper.getInstance(getApplicationContext())
                .getRequestQueue();

        //The IP of our API, this depends on the connection between your phone and dev machine
        String url = "http://192.168.43.62/bmi_api/store_bmi_data.php?height="+height+"&weight="+weight;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //When you receive a response dismiss the progress dialog
                        progressDialog.dismiss();
                        //Since we receive a JSON string response, we are going to convert it to an JSONObject as follows
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            bmiTextView.setText(jsonObject.getString("bmi"));
                            bmiDescriptionTextView.setText(jsonObject.getString("information"));
                        }
                        catch (JSONException err)
                        {
                            //TODO:: Task 3.2 Implement a dialog box to display a user friendly error message

                            //call the created function
                            alert("Error In Configurations");


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO:: Task 3.3 Implement a dialog box to display a user friendly error message

                        //call the created function
                        alert("API does not respond try again Later");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}