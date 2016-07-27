package com.wadesi.codespeak;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity
{
    String username;
    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";
    public final static String QUESTION_COUNT = "com.wadesi.codespeak.QUESTION_COUNT";

    Context context = this;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        username = i.getStringExtra(LoginActivity.USERNAME);
        mp = MediaPlayer.create(context, R.raw.click);
    }

    public void startLessons(View v)
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

        Intent i = new Intent(this, LessonsActivity.class);
        i.putExtra(USERNAME, username);
        this.startActivity(i);

    }

    public void startTests(View v)
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

    public void logout(View v)
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

        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
    }

    public void startGlossary(View v)
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
    }

    public void startProgress(View v)
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
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
