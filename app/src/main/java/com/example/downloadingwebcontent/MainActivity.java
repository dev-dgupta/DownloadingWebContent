package com.example.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    /*
     * String: we pass the string
     * Void: progress of the functions
     * String: we send back the string
     * */
    public class DownloadTask extends AsyncTask<String, Void, String> {

        /*
         * the task you want to do in background        *
         * */
        @Override
        protected String doInBackground(String... urls) {
//            Log.i("URL::", urls[0]);
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                result = "Failed";
            } catch (IOException e) {
                e.printStackTrace();
                result = "Failed";
            }


            return result;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask downloadTask = new DownloadTask();
        String result = null;
        try {
            result = downloadTask.execute("https://zappycode.com/").get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("Result::", result);
    }
}