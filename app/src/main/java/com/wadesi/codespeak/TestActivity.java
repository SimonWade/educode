package com.wadesi.codespeak;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TestActivity extends AppCompatActivity
{
    String username;
    String topic;
    int score = 0;
    int question_count = 5;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";

    Context context = this;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent i = getIntent();
        username = i.getStringExtra(TestsActivity.USERNAME);
        topic = i.getStringExtra(TestsActivity.TOPIC);

        mp = MediaPlayer.create(context, R.raw.click);
    }

    public void startTest(View v)
    {
        new GetQuestionActivity(this, score, question_count).execute(username, topic);

        try
        {
            if (mp.isPlaying())
            {
                mp.stop();
            }

            mp.reset();
            mp.release();
            mp = MediaPlayer.create(context, R.raw.click);
            mp.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void exitTest(View v)
    {
        Intent in = new Intent(this, LessonsActivity.class);
        in.putExtra(USERNAME, username);
        this.startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boolean_test, menu);
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
