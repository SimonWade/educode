package com.wadesi.codespeak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity
{
    String username;
    String topic;
    int score;
    int question_count;

    String question;
    String answer_one;
    String answer_two;
    String answer_three;
    String answer_four;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String SCORE = "com.wadesi.codespeak.SCORE";
    public final static String QUESTION_COUNT = "com.wadesi.codespeak.QUESTION_COUNT";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";

    Context context = this;
    MediaPlayer mp;
    MediaPlayer mp_correct;
    MediaPlayer mp_incorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent i = getIntent();

        username = i.getStringExtra(GetQuestionActivity.USERNAME);
        topic = i.getStringExtra(GetQuestionActivity.TOPIC);

        question = i.getStringExtra(GetQuestionActivity.QUESTION);
        answer_one = i.getStringExtra(GetQuestionActivity.ANSWER_ONE);                             // Always the right answer - need to randomise the order so it's not the same every time
        answer_two = i.getStringExtra(GetQuestionActivity.ANSWER_TWO);
        answer_three = i.getStringExtra(GetQuestionActivity.ANSWER_THREE);
        answer_four = i.getStringExtra(GetQuestionActivity.ANSWER_FOUR);
        score = i.getIntExtra(SCORE, score);
        question_count = i.getIntExtra(QUESTION_COUNT, question_count);

        if(question_count == 0)
        {
            if(username.equals("blank"))
            {
                Toast.makeText(this, "You are not logged in. Your score cannot be saved.", Toast.LENGTH_LONG).show();

                Intent k = new Intent(this.context, LessonsActivity.class);
                k.putExtra(USERNAME, username);
                k.putExtra(TOPIC, topic);
                this.context.startActivity(k);
            }
            else
            {
                Intent j = new Intent(this.context, ScoreActivity.class);
                j.putExtra(SCORE, score);
                j.putExtra(USERNAME, username);
                j.putExtra(TOPIC, topic);
                this.context.startActivity(j);
            }
        }

        List <String> answers = Arrays.asList(answer_one, answer_two, answer_three, answer_four);
        Collections.shuffle(answers);                                                              // Shuffle the order of the answers

        String ans[] = answers.toArray(new String[answers.size()]);                                // Convert to an array so buttons can be associated with answers

        Button buttons[] = new Button[4];                                                          // Assign existing button IDs to button array that can be accessed here
        buttons[0] = (Button)findViewById(R.id.btnAnswerOne);
        buttons[1] = (Button)findViewById(R.id.btnAnswerTwo);
        buttons[2] = (Button)findViewById(R.id.btnAnswerThree);
        buttons[3] = (Button)findViewById(R.id.btnAnswerFour);

        for(int j = 0; j < 4; j++)
        {
            buttons[j].setText(ans[j]);                                                            // Assign an answer to each button
        }

        mp = MediaPlayer.create(context, R.raw.click);
        mp_correct = MediaPlayer.create(context, R.raw.correct);
        mp_incorrect = MediaPlayer.create(context, R.raw.incorrect);

        TextView TvQuestion = (TextView)findViewById(R.id.tv_question);
        TvQuestion.setText(question, TextView.BufferType.EDITABLE);
    }

    public void ButtonOnClick(View v)
    {
        Button btnOne = (Button)findViewById(R.id.btnAnswerOne);                                            // Initialise buttons so their text can be accessed in switch statement
        Button btnTwo = (Button)findViewById(R.id.btnAnswerTwo);
        Button btnThree = (Button)findViewById(R.id.btnAnswerThree);
        Button btnFour = (Button)findViewById(R.id.btnAnswerFour);

        switch(v.getId())
        {
            case R.id.btnAnswerOne:
                if(answer_one.equals(btnOne.getText()))                                                     // If the button contains the answer_one string then it is the correct answer
                {
                    score++;                                                                                // Increment the score
                    question_count--;                                                                       // Decrement the question coumt

                    Toast.makeText(this, "Correct! Score: " + score, Toast.LENGTH_SHORT).show();

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.correct);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                }
                else
                {
                    question_count--;
                    Toast.makeText(this, "Incorrect! The correct answer is " + answer_one, Toast.LENGTH_SHORT).show();

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.incorrect);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                }
                break;

            case R.id.btnAnswerTwo:
                if(answer_one.equals(btnTwo.getText()))
                {
                    score++;
                    question_count--;
                    Toast.makeText(this, "Correct! Score: " + score, Toast.LENGTH_SHORT).show();

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.correct);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    question_count--;
                    Toast.makeText(this, "Incorrect! The correct answer is " + answer_one, Toast.LENGTH_SHORT).show();

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.incorrect);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btnAnswerThree:

                if(answer_one.equals(btnThree.getText()))
                {
                    score++;
                    question_count--;
                    Toast.makeText(this, "Correct! Score: " + score, Toast.LENGTH_SHORT).show();

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.correct);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    question_count--;
                    Toast.makeText(this, "Incorrect! The correct answer is " + answer_one, Toast.LENGTH_SHORT).show();

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.incorrect);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btnAnswerFour:

                if(answer_one.equals(btnFour.getText()))
                {
                    score++;
                    question_count--;
                    Toast.makeText(this, "Correct! Score: " + score, Toast.LENGTH_SHORT).show();

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.correct);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    question_count--;
                    Toast.makeText(this, "Incorrect! The correct answer is " + answer_one, Toast.LENGTH_SHORT).show();

                    if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
                    {
                        new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
                    }
                    else
                    {
                        new GetDragDropQuestionActivity(this, score, question_count).execute(username, topic);
                    }

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                        }

                        mp.reset();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.incorrect);
                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void exitTest(View v)
    {
        try
        {
            if (mp.isPlaying())
            {
                mp.stop();
                mp.release();
                mp = MediaPlayer.create(context, R.raw.click);
            }

            mp.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("Confirm");
        build.setMessage("Are you sure you want to leave the test? Your progress will be lost.");

        build.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Leave test
                Intent i = new Intent(context, TestsActivity.class);
                i.putExtra(USERNAME, username);
                context.startActivity(i);
                finish();
                dialog.dismiss();
            }
        });

        build.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Stay at test
                dialog.dismiss();
            }
        });

        AlertDialog alert = build.create();
        alert.show();

    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
