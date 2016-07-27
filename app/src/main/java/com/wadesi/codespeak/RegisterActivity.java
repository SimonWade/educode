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

public class RegisterActivity extends AsyncTask<String, Void, String>
{
    Context context;

    public RegisterActivity(Context context)
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
        String username = (String)arg0[0];
        String password = (String)arg0[1];
        String forename = (String)arg0[2];
        String surname = (String)arg0[3];
        String class_name = (String)arg0[4];

        String hyperlink, arguments, result;
        BufferedReader rdr;                                                      // Declare a BufferedReader to read the response from the PHP page

        try
        {
            arguments = "?username=" + URLEncoder.encode(username, "UTF-8");         // Encode with UTF-8
            arguments += "&password=" + URLEncoder.encode(password, "UTF-8");
            arguments += "&forename=" + URLEncoder.encode(forename, "UTF-8");
            arguments += "&surname=" + URLEncoder.encode(surname, "UTF-8");
            arguments += "&class_name=" + URLEncoder.encode(class_name, "UTF-8");

            hyperlink = "http://teacherportal.netau.net/android_create_account.php" + arguments;
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

                switch (response)
                {
                    case "Successful":
                        Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, MainActivity.class);
                        context.startActivity(i);
                        break;
                    case "Unsuccessful":
                        Toast.makeText(context, "Invalid account details. Please try again.",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "Cannot connect to database.",
                                Toast.LENGTH_SHORT).show();
                        break;
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
