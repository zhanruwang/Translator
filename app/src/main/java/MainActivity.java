package nothingwillbewrong.putyourgrasseson.translation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;


import org.json.*;
import java.util.*;



public final class MainActivity extends AppCompatActivity {
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);
        // Load the main layout for our activity
        setContentView(R.layout.activity_main);

        // Attach the handler to our UI button

        final TextView textField = findViewById(R.id.editText);

        final Button toChinese  = findViewById(R.id.Chinese);
        final Button toFrench  = findViewById(R.id.French);
        final Button toSpanish  = findViewById(R.id.Spanish);



        toChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String input = textField.getText().toString();
                getTranslateResult(input, "zh-CN");
            }
        });

        toFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String input = textField.getText().toString();
                getTranslateResult(input, "fr");
            }

        });
        toSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String input = textField.getText().toString();
                getTranslateResult(input, "es");
            }

        });
        // Make sure that our progress bar isn't spinning and style it a bit

    }

    /**
     * Make an API call.
     * @param inputText the input.
     */
    void getTranslateResult(final String inputText, final String langCode) {
        try {
            Map<String, String> map = new HashMap<String, String>();

            map.put("q", inputText);
            map.put("target", langCode);

            map.put("source", "en");
            map.put("format", "text");

            JSONObject js = new JSONObject(map);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "https://translation.googleapis.com/language/translate/v2?key=AIzaSyDXep9nE6_eoIPuJH-pmukPHm2w5q0xO7Y",
                    js,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try{
                                final TextView translationResult = findViewById(R.id.textView3);
                                JSONArray translatedResults = (JSONArray) ((JSONObject) response.get("data")).getJSONArray("translations");
                                String result = ((JSONObject) translatedResults.get(0)).getString("translatedText");
                                translationResult.setText(result);
                            } catch (JSONException ex) {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                }
            }
            );
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
