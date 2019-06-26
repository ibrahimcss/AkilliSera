package com.example.akillisera;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView st;
    TextView nt;
    TextView tnt;
    private static final String TAG = "UsingThingspeakAPI";
    Map<String, String> params = new HashMap<String, String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("DEĞERLER");


        st = findViewById(R.id.sicaklikView);
        nt = findViewById(R.id.nemView);
        tnt = findViewById(R.id.toprakNemView);

    }

    public void setting(View view) {

        Intent i = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(i);


    }


    public void fetchData(View view) {


        try {

            st.setText("");

            nt.setText("");

            tnt.setText("");


            new FetchThingspeakTask().execute();


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }

    class FetchThingspeakTask extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {

        }

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL("https://api.thingspeak.com/channels/769812/feeds/last?fields/Q0KIKHX24RH7WDHS&results=2fields/1.json?api_key");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {


                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                Toast.makeText(MainActivity.this, "Beklenmedik Bir Arıza İle Karşılandı..!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject channel = (JSONObject) new JSONTokener(response).nextValue();
                String sicaklikDegeri = channel.getString("field1");
                String nemDegeri = channel.getString("field2");
                String toprakNemDegeri = channel.getString("field3");



                if (sicaklikDegeri == "null" && nemDegeri == "null" && toprakNemDegeri == "null") {

                    Toast.makeText(MainActivity.this, "LÜTFEN SİSTEMİ KONTROL EDİNİZ..!!!", Toast.LENGTH_LONG).show();

                    st.setText("--");
                    nt.setText("--");
                    tnt.setText("--");

                }else{

                    st.setText(sicaklikDegeri+"°");
                    nt.setText("%"+nemDegeri);
                    tnt.setText("%"+toprakNemDegeri);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
