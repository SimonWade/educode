package com.wadesi.codespeak;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    private EditText et_user;
    private EditText et_pass;

    Context context = this;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_user = (EditText)findViewById(R.id.etUsername);
        et_pass = (EditText)findViewById(R.id.etPassword);
        mp = MediaPlayer.create(context, R.raw.click);
    }

    public void login(View v)
    {
        String username = et_user.getText().toString();
        String password = et_pass.getText().toString();

        new LoginActivity(this).execute(username, password);
        new HomeActivity();

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

    public void createAccount(View v)
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

        Intent i = new Intent(this, CreateAccountActivity.class);
        this.startActivity(i);
    }

    public void skipLogin(View v)
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
        this.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

