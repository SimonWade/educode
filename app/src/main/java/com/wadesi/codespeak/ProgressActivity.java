package com.wadesi.codespeak;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity
{
    Context context = this;
    String username;

    String average_score;
    String average_boolean_score;
    String average_iteration_score;
    String average_teacher_score;

    MediaPlayer mp;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String AVERAGE_SCORE = "com.wadesi.codespeak.AVERAGE_SCORE";
    public final static String AVERAGE_ITE_SCORE = "com.wadesi.codespeak.AVERAGE_SCORE";
    public final static String AVERAGE_BOOLEAN_SCORE = "com.wadesi.codespeak.AVERAGE_BOOLEAN_SCORE";
    public final static String AVERAGE_TEACHER_SCORE = "com.wadesi.codespeak.AVERAGE_TEACHER_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        mp = MediaPlayer.create(context, R.raw.click);

        Intent i = getIntent();

        username = i.getStringExtra(GetProgressActivity.USERNAME);
        average_score = i.getStringExtra(GetProgressActivity.AVERAGE_SCORE);
        average_iteration_score = i.getStringExtra(GetProgressActivity.AVERAGE_ITE_SCORE);
        average_boolean_score = i.getStringExtra(GetProgressActivity.AVERAGE_BOOLEAN_SCORE);
        average_teacher_score = i.getStringExtra(GetProgressActivity.AVERAGE_TEACHER_SCORE);

        if(!average_score.contains("No Score Found"))
        {
            average_score = average_score.replace("\"", "");
            float avg_score = Float.parseFloat(average_score);

            float avg_percentage = avg_score * 100/5;
            int avg = (int)avg_percentage;

            TextView Tv_score = (TextView)findViewById(R.id.tv_score);
            Tv_score.setText(""+ avg + "%", TextView.BufferType.EDITABLE);
        }
        else
        {
            TextView Tv_score = (TextView)findViewById(R.id.tv_score);
            Tv_score.setText("N/A", TextView.BufferType.EDITABLE);
        }

        if(!average_boolean_score.contains("No Score Found"))
        {
            average_boolean_score = average_boolean_score.replace("\"", "");
            float avg_bool_score = Float.parseFloat(average_boolean_score);

            float avg_bool_percentage = avg_bool_score * 100/5;
            int avg_bool = (int)avg_bool_percentage;

            TextView Tv_bool_score = (TextView)findViewById(R.id.tv_boolean_score);
            Tv_bool_score.append(" " + avg_bool + "%");
        }
        else
        {
            TextView Tv_bool_score = (TextView)findViewById(R.id.tv_boolean_score);
            Tv_bool_score.append(" N/A");
        }

        if(!average_iteration_score.contains("No Score Found"))
        {
            average_iteration_score = average_iteration_score.replace("\"", "");
            float avg_var_score = Float.parseFloat(average_iteration_score);

            float avg_var_percentage = avg_var_score * 100/5;
            int avg_var = (int)avg_var_percentage;

            TextView Tv_var_score = (TextView)findViewById(R.id.tv_iteration_score);
            Tv_var_score.append(" " + avg_var + "%");
        }
        else
        {
            TextView Tv_var_score = (TextView)findViewById(R.id.tv_iteration_score);
            Tv_var_score.append(" N/A");
        }

        if(!average_teacher_score.contains("No Score Found"))
        {
            average_teacher_score = average_teacher_score.replace("\"", "");
            float avg_teacher_score = Float.parseFloat(average_teacher_score);

            float avg_teacher_percentage = avg_teacher_score * 100 / 5;
            int avg_teacher = (int) avg_teacher_percentage;

            TextView Tv_teacher_score = (TextView)findViewById(R.id.tv_teacher_score);
            Tv_teacher_score.append(" " + avg_teacher + "%");
        }
        else
        {
            TextView Tv_teacher_score = (TextView)findViewById(R.id.tv_teacher_score);
            Tv_teacher_score.append(" N/A");
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_progress, menu);
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
