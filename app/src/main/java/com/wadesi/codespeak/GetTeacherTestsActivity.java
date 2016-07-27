package com.wadesi.codespeak;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class GetTeacherTestsActivity extends AsyncTask<String, Void, String>
{
    Context context;

    String username;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TESTS = "com.wadesi.codespeak.TESTS";

    public GetTeacherTestsActivity(Context context)
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
            arguments = "?username=" + URLEncoder.encode(username, "UTF-8");                // Encode with UTF-8

            hyperlink = "http://teacherportal.netau.net/get_teacher_test.php" + arguments;
            URL url = new URL(hyperlink);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();               // Connect to the PHP login page

            rdr = new BufferedReader(new InputStreamReader(con.getInputStream()));          // Use an InputStreamReader to read in the bytes and convert them to characters
            String response = rdr.readLine();                                               // Read each line of data and store in string result
            return response;
        }
        catch(Exception e)
        {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String response)
    {
        if (response != null)            // If we've received a response that isn't null
        {
            try
            {
                List<String> resultsList = Arrays.asList(response.split((",[ ]*")));

                int arraySize = resultsList.size();
                String tests[] = new String[resultsList.size()];

                for(int i = 0; i < arraySize; i++)
                {
                    //resultsList.set(i, resultsList.get(i).replaceAll("\\[", "").replaceAll("\\]", "").replace("\"", ""));
                    tests[i] = resultsList.get(i).replaceAll("\\[", "").replaceAll("\\]", "").replace("\"", "");
                }

                Bundle b = new Bundle();
                b.putSerializable(TESTS, tests);

                Intent i = new Intent(context, DisplayTeacherTestsActivity.class);
                i.putExtra(USERNAME, username);
                i.putExtras(b);

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
