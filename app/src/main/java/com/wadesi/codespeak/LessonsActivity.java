package com.wadesi.codespeak;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LessonsActivity extends AppCompatActivity
{
    String username;
    String topic;
    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";

    Context context = this;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        Intent i = getIntent();
        username = i.getStringExtra(LoginActivity.USERNAME);
        mp = MediaPlayer.create(context, R.raw.click);
    }

    public void onClickLesson(View view)
    {
        Button b = (Button)view;
        topic = b.getText().toString();

        Intent i = new Intent(this, LessonActivity.class);
        i.putExtra(USERNAME, username);
        i.putExtra(TOPIC, topic);
        this.startActivity(i);

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

    public void returnToHome(View v)
    {
        Intent i = new Intent(this,HomeActivity .class);
        i.putExtra(USERNAME, username);
        this.startActivity(i);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lessons, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
