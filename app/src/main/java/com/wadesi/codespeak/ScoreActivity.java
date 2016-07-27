package com.wadesi.codespeak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity
{
    Context context = this;
    String username;
    String topic;

    int score;
    private TextView Tv_score;
    private TextView Tv_feedback;
    MediaPlayer mp;

    public final static String SCORE = "com.wadesi.codespeak.SCORE";
    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent i = getIntent();
        username = i.getStringExtra(QuestionActivity.USERNAME);
        topic = i.getStringExtra(QuestionActivity.TOPIC);
        score = i.getIntExtra(SCORE, score);

        Tv_score = (TextView)findViewById(R.id.tv_score);
        Tv_score.setText("" + score, TextView.BufferType.EDITABLE);

        mp = MediaPlayer.create(context, R.raw.click);

        Tv_feedback = (TextView)findViewById(R.id.tv_feedback);
        Tv_feedback.setText("Feedback", TextView.BufferType.EDITABLE);

        if(score == 5)
            Tv_feedback.setText("Excellent " + username + "! Full marks.", TextView.BufferType.EDITABLE);
        else if(score > 2 && score < 5)
            Tv_feedback.setText("Well done " + username + "! Go for full marks next time.", TextView.BufferType.EDITABLE);
        else if(score > 0 && score < 3)
            Tv_feedback.setText("Unlucky " + username + "! Try another test.", TextView.BufferType.EDITABLE);

        new UploadScoreActivity(this, score).execute(username, topic);
    }

    public void exitToTests(View v)
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

        Intent i = new Intent(this, TestsActivity.class);
        i.putExtra(USERNAME, username);
        this.startActivity(i);
    }

    public void exitToHome(View v)
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

        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra(USERNAME, username);
        this.startActivity(i);
    }

    public void checkProgress(View v)
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

        new GetProgressActivity(this).execute(username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
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
