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

public class GetTeacherQuestionActivity extends AsyncTask<String, Void, String>
{
    Context context;

    String username;
    String test_name;

    int score = 0;
    int question_count = 5;
    String question;
    String topic = "";

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
    public final static String ANSWER_ONE = "com.wadesi.codespeak.ANSWER_ONE";
    public final static String ANSWER_TWO = "com.wadesi.codespeak.ANSWER_TWO";
    public final static String ANSWER_THREE = "com.wadesi.codespeak.ANSWER_THREE";
    public final static String ANSWER_FOUR = "com.wadesi.codespeak.ANSWER_FOUR";

    public GetTeacherQuestionActivity(Context context, int score, int question_count)
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
        test_name= (String)arg0[1];

        String hyperlink, arguments;
        BufferedReader rdr;

        try
        {
            arguments = "?test_name=" + URLEncoder.encode(test_name, "UTF-8");                          // Encode with UTF-8

            hyperlink = "http://teacherportal.netau.net/get_teacher_question.php" + arguments;
            URL url = new URL(hyperlink);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();                           // Connect to the PHP login page

            rdr = new BufferedReader(new InputStreamReader(con.getInputStream()));                      // Use an InputStreamReader to read in the bytes and convert them to characters
            String response = rdr.readLine();                                                           // Read each line of data and store in string result
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

        String question_type;

        if (response != null)            // If we've received a response that isn't null
        {
            try
            {
                List<String> resultsList = Arrays.asList(response.split((",[ ]*")));

                switch(question_count)
                {
                    case 5:
                        question = resultsList.get(0);
                        statement_one_answer = resultsList.get(1);
                        statement_two_answer = resultsList.get(2);
                        statement_three_answer = resultsList.get(3);
                        statement_four_answer = resultsList.get(4);
                        question_type = resultsList.get(5);

                        question = question.replace("\"", "");
                        question = question.replaceAll("\\[", "").replaceAll("\\]", "");
                        question = question.trim();

                        statement_one_answer = statement_one_answer.replace("\"", "");
                        statement_two_answer = statement_two_answer.replace("\"", "");
                        statement_three_answer = statement_three_answer.replace("\"", "");
                        statement_four_answer = statement_four_answer.replace("\"", "");
                        question_type = question_type.replace("\"", "").replaceAll("\\]", "");

                        if (question_type.equals("rearrange"))
                        {
                            Intent i = new Intent(context, DragDropQuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(STATEMENT_ONE, statement_one_answer);
                            i.putExtra(STATEMENT_TWO, statement_two_answer);
                            i.putExtra(STATEMENT_THREE, statement_three_answer);
                            i.putExtra(STATEMENT_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);
                        }
                        else if (question_type.equals("multiple"))
                        {
                            Intent i = new Intent(context, QuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(ANSWER_ONE, statement_one_answer);
                            i.putExtra(ANSWER_TWO, statement_two_answer);
                            i.putExtra(ANSWER_THREE, statement_three_answer);
                            i.putExtra(ANSWER_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);

                        }
                        break;
                    case 4:
                        question = resultsList.get(6);
                        statement_one_answer = resultsList.get(7);
                        statement_two_answer = resultsList.get(8);
                        statement_three_answer = resultsList.get(9);
                        statement_four_answer = resultsList.get(10);
                        question_type = resultsList.get(11);

                        question = question.replace("\"", "");
                        question = question.replaceAll("\\[", "").replaceAll("\\]", "");
                        question = question.trim();

                        statement_one_answer = statement_one_answer.replace("\"", "");
                        statement_two_answer = statement_two_answer.replace("\"", "");
                        statement_three_answer = statement_three_answer.replace("\"", "");
                        statement_four_answer = statement_four_answer.replace("\"", "");
                        question_type = question_type.replace("\"", "").replaceAll("\\]", "");

                        if (question_type.equals("rearrange"))
                        {
                            Intent i = new Intent(context, DragDropQuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(STATEMENT_ONE, statement_one_answer);
                            i.putExtra(STATEMENT_TWO, statement_two_answer);
                            i.putExtra(STATEMENT_THREE, statement_three_answer);
                            i.putExtra(STATEMENT_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);
                        }
                        else if (question_type.equals("multiple"))
                        {
                            Intent i = new Intent(context, QuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(ANSWER_ONE, statement_one_answer);
                            i.putExtra(ANSWER_TWO, statement_two_answer);
                            i.putExtra(ANSWER_THREE, statement_three_answer);
                            i.putExtra(ANSWER_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);

                        }

                        break;
                    case 3:
                        question = resultsList.get(12);
                        statement_one_answer = resultsList.get(13);
                        statement_two_answer = resultsList.get(14);
                        statement_three_answer = resultsList.get(15);
                        statement_four_answer = resultsList.get(16);
                        question_type = resultsList.get(17);

                        question = question.replace("\"", "");
                        question = question.replaceAll("\\[", "").replaceAll("\\]", "");
                        question = question.trim();

                        statement_one_answer = statement_one_answer.replace("\"", "");
                        statement_two_answer = statement_two_answer.replace("\"", "");
                        statement_three_answer = statement_three_answer.replace("\"", "");
                        statement_four_answer = statement_four_answer.replace("\"", "");
                        question_type = question_type.replace("\"", "").replaceAll("\\]", "");

                        if (question_type.equals("rearrange"))
                        {
                            Intent i = new Intent(context, DragDropQuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(STATEMENT_ONE, statement_one_answer);
                            i.putExtra(STATEMENT_TWO, statement_two_answer);
                            i.putExtra(STATEMENT_THREE, statement_three_answer);
                            i.putExtra(STATEMENT_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);
                        }
                        else if (question_type.equals("multiple"))
                        {
                            Intent i = new Intent(context, QuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(ANSWER_ONE, statement_one_answer);
                            i.putExtra(ANSWER_TWO, statement_two_answer);
                            i.putExtra(ANSWER_THREE, statement_three_answer);
                            i.putExtra(ANSWER_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);

                        }

                        break;
                    case 2:
                        question = resultsList.get(18);
                        statement_one_answer = resultsList.get(19);
                        statement_two_answer = resultsList.get(20);
                        statement_three_answer = resultsList.get(21);
                        statement_four_answer = resultsList.get(22);
                        question_type = resultsList.get(23);

                        question = question.replace("\"", "");
                        question = question.replaceAll("\\[", "").replaceAll("\\]", "");
                        question = question.trim();

                        statement_one_answer = statement_one_answer.replace("\"", "");
                        statement_two_answer = statement_two_answer.replace("\"", "");
                        statement_three_answer = statement_three_answer.replace("\"", "");
                        statement_four_answer = statement_four_answer.replace("\"", "");
                        question_type = question_type.replace("\"", "").replaceAll("\\]", "");

                        if (question_type.equals("rearrange"))
                        {
                            Intent i = new Intent(context, DragDropQuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(STATEMENT_ONE, statement_one_answer);
                            i.putExtra(STATEMENT_TWO, statement_two_answer);
                            i.putExtra(STATEMENT_THREE, statement_three_answer);
                            i.putExtra(STATEMENT_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);
                        }
                        else if (question_type.equals("multiple"))
                        {
                            Intent i = new Intent(context, QuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(ANSWER_ONE, statement_one_answer);
                            i.putExtra(ANSWER_TWO, statement_two_answer);
                            i.putExtra(ANSWER_THREE, statement_three_answer);
                            i.putExtra(ANSWER_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);

                        }

                        break;

                    case 1:
                        question = resultsList.get(24);
                        statement_one_answer = resultsList.get(25);
                        statement_two_answer = resultsList.get(26);
                        statement_three_answer = resultsList.get(27);
                        statement_four_answer = resultsList.get(28);
                        question_type = resultsList.get(29);

                        question = question.replace("\"", "");
                        question = question.replaceAll("\\[", "").replaceAll("\\]", "");
                        question = question.trim();

                        statement_one_answer = statement_one_answer.replace("\"", "");
                        statement_two_answer = statement_two_answer.replace("\"", "");
                        statement_three_answer = statement_three_answer.replace("\"", "");
                        statement_four_answer = statement_four_answer.replace("\"", "");
                        question_type = question_type.replace("\"", "").replaceAll("\\]", "");

                        if (question_type.equals("rearrange"))
                        {
                            Intent i = new Intent(context, DragDropQuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(STATEMENT_ONE, statement_one_answer);
                            i.putExtra(STATEMENT_TWO, statement_two_answer);
                            i.putExtra(STATEMENT_THREE, statement_three_answer);
                            i.putExtra(STATEMENT_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);
                        }
                        else if (question_type.equals("multiple"))
                        {
                            Intent i = new Intent(context, QuestionActivity.class);

                            i.putExtra(USERNAME, username);
                            i.putExtra(TOPIC, test_name);
                            i.putExtra(QUESTION, question);
                            i.putExtra(ANSWER_ONE, statement_one_answer);
                            i.putExtra(ANSWER_TWO, statement_two_answer);
                            i.putExtra(ANSWER_THREE, statement_three_answer);
                            i.putExtra(ANSWER_FOUR, statement_four_answer);
                            i.putExtra(SCORE, score);
                            i.putExtra(QUESTION_COUNT, question_count);

                            context.startActivity(i);

                        }

                        break;

                    case 0:
                        Intent j = new Intent(this.context, ScoreActivity.class);
                        j.putExtra(SCORE, score);
                        j.putExtra(USERNAME, username);
                        j.putExtra(TOPIC, topic);
                        this.context.startActivity(j);

                        break;
                }
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
