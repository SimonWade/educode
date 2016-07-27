package com.wadesi.codespeak;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UploadScoreActivity extends AsyncTask<String, Void, String>
{
    Context context;
    String username;
    String topic;
    int score;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String SCORE = "com.wadesi.codespeak.SCORE";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";

    public UploadScoreActivity(Context context, int score)
    {
        this.context = context;
        this.score = score;
    }

    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected String doInBackground(String...arg0)
    {
        username = (String)arg0[0];
        topic = (String)arg0[1];

        if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
        {
            topic = "Miscellaneous";
        }

        String hyperlink, arguments, result;
        BufferedReader rdr;                                                      // Declare a BufferedReader to read the response from the PHP page

        try
        {
            arguments = "?username=" + URLEncoder.encode(username, "UTF-8");         // Encode with UTF-8
            arguments += "&score=" + URLEncoder.encode("" + score, "UTF-8");
            arguments += "&topic=" + URLEncoder.encode(topic, "UTF-8");

            hyperlink = "http://teacherportal.netau.net/upload_score.php" + arguments;
            URL url = new URL(hyperlink);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();       // Connect to the PHP login page

            rdr = new BufferedReader(new InputStreamReader(con.getInputStream()));  // Use an InputStreamReader to read in the bytes and convert them to characters
            result = rdr.readLine();                                                // Read each line of data and store in string result
            return result;
        }
        catch(Exception e)
        {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        if (result != null)            // If we've received a response that isn't null
        {
            try
            {
                JSONObject jsonObj = new JSONObject(result);

                String response = jsonObj.getString("response");

                if (response.equals("Successful"))
                {
                    Toast.makeText(context, "Uploading score...", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("Unsuccessful"))
                {
                    Toast.makeText(context, "There's been an error uploading your score. Sorry!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Cannot connect to database.", Toast.LENGTH_SHORT).show();
                }
            }
            catch (JSONException e)
            {
                Toast.makeText(context, "Cannot parse the JSON data", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(context, "No JSON data found", Toast.LENGTH_SHORT).show();
        }
    }
}
