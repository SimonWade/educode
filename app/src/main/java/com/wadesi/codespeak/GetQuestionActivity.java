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

public class GetQuestionActivity extends AsyncTask<String, Void, String>
{
    Context context;

    String username;
    String topic;

    int score;
    int question_count;
    String question;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";
    public final static String QUESTION = "com.wadesi.codespeak.QUESTION";
    public final static String ANSWER_ONE = "com.wadesi.codespeak.ANSWER_ONE";
    public final static String ANSWER_TWO = "com.wadesi.codespeak.ANSWER_TWO";
    public final static String ANSWER_THREE = "com.wadesi.codespeak.ANSWER_THREE";
    public final static String ANSWER_FOUR = "com.wadesi.codespeak.ANSWER_FOUR";
    public final static String SCORE = "com.wadesi.codespeak.SCORE";
    public final static String QUESTION_COUNT = "com.wadesi.codespeak.QUESTION_COUNT";

    public GetQuestionActivity(Context context, int score, int question_count)
    {
        this.context = context;
        this.score = score;
        this.question_count = question_count;
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

        String hyperlink, arguments;
        BufferedReader rdr;

        try
        {
            arguments = "?topic=" + URLEncoder.encode(topic, "UTF-8");         // Encode with UTF-8

            hyperlink = "http://teacherportal.netau.net/get_question.php" + arguments;
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
    protected void onPostExecute(String response)
    {
        String quest_component;
        String answer_one;
        String answer_two;
        String answer_three;
        String answer_four;

        if (response != null)            // If we've received a response that isn't null
        {
            try
            {
                List<String> resultsList = Arrays.asList(response.split((",[ ]*")));

                question = resultsList.get(0);
                quest_component = resultsList.get(1);
                answer_one = resultsList.get(2);
                answer_two = resultsList.get(3);
                answer_three = resultsList.get(4);
                answer_four = resultsList.get(5);

                question = question.replace("\"", "");
                question = question.replaceAll("\\[", "").replaceAll("\\]","");
                question = question.trim();
                quest_component = quest_component.replace("\"", "");

                question = question + " " + quest_component + "\n\nChoose the correct answer.";

                answer_one = answer_one.replace("\"", "");
                answer_two = answer_two.replace("\"", "");
                answer_three = answer_three.replace("\"", "");
                answer_four = answer_four.replace("\"", "").replaceAll("\\]","");

                Intent i = new Intent(context, QuestionActivity.class);

                i.putExtra(USERNAME, username);
                i.putExtra(TOPIC, topic);
                i.putExtra(QUESTION, question);
                i.putExtra(ANSWER_ONE, answer_one);
                i.putExtra(ANSWER_TWO, answer_two);
                i.putExtra(ANSWER_THREE, answer_three);
                i.putExtra(ANSWER_FOUR, answer_four);
                i.putExtra(SCORE, score);
                i.putExtra(QUESTION_COUNT, question_count);
                i.putExtra(TOPIC, topic);

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
