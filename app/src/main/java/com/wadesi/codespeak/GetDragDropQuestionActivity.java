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

public class GetDragDropQuestionActivity extends AsyncTask<String, Void, String>
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
    public final static String STATEMENT_ONE = "com.wadesi.codespeak.STATEMENT_ONE";
    public final static String STATEMENT_ONE_ORDER = "com.wadesi.codespeak.STATEMENT_ONE_ORDER";
    public final static String STATEMENT_TWO = "com.wadesi.codespeak.STATEMENT_TWO";
    public final static String STATEMENT_TWO_ORDER = "com.wadesi.codespeak.STATEMENT_TWO_ORDER";
    public final static String STATEMENT_THREE = "com.wadesi.codespeak.STATEMENT_THREE";
    public final static String STATEMENT_THREE_ORDER = "com.wadesi.codespeak.STATEMENT_THREE_ORDER";
    public final static String STATEMENT_FOUR = "com.wadesi.codespeak.STATEMENT_FOUR";
    public final static String STATEMENT_FOUR_ORDER = "com.wadesi.codespeak.STATEMENT_FOUR_ORDER";

    public final static String SCORE = "com.wadesi.codespeak.SCORE";
    public final static String QUESTION_COUNT = "com.wadesi.codespeak.QUESTION_COUNT";

    public GetDragDropQuestionActivity(Context context, int score, int question_count)
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

            hyperlink = "http://teacherportal.netau.net/get_drag_drop_question.php" + arguments;
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
        String statement_one_answer;
        String statement_two_answer;
        String statement_three_answer;
        String statement_four_answer;

        String statement_one_order;
        String statement_two_order;
        String statement_three_order;
        String statement_four_order;

        if (response != null)            // If we've received a response that isn't null
        {
            try
            {
                List<String> resultsList = Arrays.asList(response.split((",[ ]*")));

                question = resultsList.get(0);
                statement_one_answer = resultsList.get(1);
                statement_one_order = resultsList.get(2);

                statement_two_answer = resultsList.get(3);
                statement_two_order = resultsList.get(4);

                statement_three_answer = resultsList.get(5);
                statement_three_order = resultsList.get(6);

                statement_four_answer = resultsList.get(7);
                statement_four_order = resultsList.get(8);

                question = question.replace("\"", "");
                question = question.replaceAll("\\[", "").replaceAll("\\]", "");
                question = question.trim();

                statement_one_answer = statement_one_answer.replace("\"", "");
                statement_one_order = statement_one_order.replace("\"", "");

                statement_two_answer = statement_two_answer.replace("\"", "");
                statement_two_order = statement_two_order.replace("\"", "");

                statement_three_answer = statement_three_answer.replace("\"", "");
                statement_three_order = statement_three_order.replace("\"", "");

                statement_four_answer = statement_four_answer.replace("\"", "");
                statement_four_order = statement_four_order.replace("\"", "").replaceAll("\\]", "");;

                Intent i = new Intent(context, DragDropQuestionActivity.class);

                i.putExtra(USERNAME, username);
                i.putExtra(TOPIC, topic);
                i.putExtra(QUESTION, question);
                i.putExtra(STATEMENT_ONE, statement_one_answer);
                i.putExtra(STATEMENT_ONE_ORDER, statement_one_order);
                i.putExtra(STATEMENT_TWO, statement_two_answer);
                i.putExtra(STATEMENT_TWO_ORDER, statement_two_order);
                i.putExtra(STATEMENT_THREE, statement_three_answer);
                i.putExtra(STATEMENT_THREE_ORDER, statement_three_order);
                i.putExtra(STATEMENT_FOUR, statement_four_answer);
                i.putExtra(STATEMENT_FOUR_ORDER, statement_four_order);
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
