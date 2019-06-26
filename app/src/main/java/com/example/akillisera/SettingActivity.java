package com.example.akillisera;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingActivity extends AppCompatActivity {

    Button kac;
    Button kak;
    Button sac;
    Button sak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("İŞLEMLER");


        kac = findViewById(R.id.buttonHavaAc);
        kak = findViewById(R.id.buttonHavaKapa);
        sac = findViewById(R.id.buttonSuAc);
        sak = findViewById(R.id.buttonSuKapa);

        kac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    sendCommand sendC = new sendCommand();

                    String url = "https://api.thingspeak.com/update?api_key=7Q8I0DS8KOW12OE1&field1=1";
                    sendC.execute(url);
                    Toast.makeText(getApplicationContext(), "HAVALANDIRMA ETKİN", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR" + e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

        kak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand sendC = new sendCommand();

                String url = "https://api.thingspeak.com/update?api_key=7Q8I0DS8KOW12OE1&field1=0";
                sendC.execute(url);
                Toast.makeText(getApplicationContext(), "HAVALANDIRMA PASİF", Toast.LENGTH_SHORT).show();

            }
        });

        sac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    sendCommand sendC = new sendCommand();

                    String url = "https://api.thingspeak.com/update?api_key=7Q8I0DS8KOW12OE1&field2=1";
                    sendC.execute(url);
                    Toast.makeText(getApplicationContext(), "SULAMA ETKİN", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR" + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });
        sak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand sendC = new sendCommand();

                String url = "https://api.thingspeak.com/update?api_key=7Q8I0DS8KOW12OE1&field2=0";
                sendC.execute(url);
                Toast.makeText(getApplicationContext(), "SULAMA PASİF", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private class sendCommand extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            URL url;

            HttpURLConnection httpURLConnection;

            try {
                url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();
                }
                return result;

            } catch (Exception e) {
                return null;

            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPreExecute();

            try {


            } catch (Exception e) {


            }
        }
    }




}
