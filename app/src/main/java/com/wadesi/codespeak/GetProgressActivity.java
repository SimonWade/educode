package com.wadesi.codespeak;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class GetProgressActivity extends AsyncTask<String, Void, String>
{
    Context context;
    String username;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String AVERAGE_SCORE = "com.wadesi.codespeak.AVERAGE_SCORE";
    public final static String AVERAGE_ITE_SCORE = "com.wadesi.codespeak.AVERAGE_ITE_SCORE";
    public final static String AVERAGE_BOOLEAN_SCORE = "com.wadesi.codespeak.AVERAGE_BOOLEAN_SCORE";
    public final static String AVERAGE_TEACHER_SCORE = "com.wadesi.codespeak.AVERAGE_TEACHER_SCORE";

    public GetProgressActivity(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String...arg0)
    {
        username = (String)arg0[0];

        String hyperlink, arguments;
        BufferedReader rdr;

        try
        {
            arguments = "?username=" + URLEncoder.encode(username, "UTF-8");         // Encode with UTF-8

            hyperlink = "http://teacherportal.netau.net/get_progress.php" + arguments;
            URL url = new URL(hyperlink);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();       // Connect to the PHP login page

            rdr = new BufferedReader(new InputStreamReader(con.getInputStream()));  // Use an InputStreamReader to read in the bytes and convert them to characters
            String response = rdr.readLine();                                                // Read each line of data and store in string result
            return response;
        }
        catch(Exception e)
        {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String resp)
    {
        String average_score_string;
        String average_ite_string;
        String average_boolean_string;
        String average_teacher_string;

        if (resp != null)            // If we've received a response that isn't null
        {
            try
            {
                List<String> resultsList = Arrays.asList(resp.split((",[ ]*")));       // Split by commas

                average_score_string = resultsList.get(0);
                average_ite_string = resultsList.get(1);
                average_boolean_string = resultsList.get(2);
                average_teacher_string = resultsList.get(3);

                average_score_string = average_score_string.replace("\"", "");
                average_score_string = average_score_string.replaceAll("\\[", "").replaceAll("\\]", "");
                average_score_string = average_score_string.trim();

                average_boolean_string = average_boolean_string.replace("\"", "").replaceAll("\\[", "").replaceAll("\\]", "");
                average_ite_string = average_ite_string.replace("\"", "");
                average_teacher_string = average_teacher_string.replace("\"", "").replaceAll("\\]","");

                Intent i = new Intent(context, ProgressActivity.class);
                i.putExtra(USERNAME, username);
                i.putExtra(AVERAGE_SCORE, average_score_string);
                i.putExtra(AVERAGE_ITE_SCORE, average_ite_string);
                i.putExtra(AVERAGE_BOOLEAN_SCORE, average_boolean_string);
                i.putExtra(AVERAGE_TEACHER_SCORE, average_teacher_string);
                context.startActivity(i);
            }
            catch(Exception e)
            {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(context, "No JSON data found", Toast.LENGTH_SHORT).show();
        }
    }
}
