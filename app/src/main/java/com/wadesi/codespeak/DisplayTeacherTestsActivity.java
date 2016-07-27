package com.wadesi.codespeak;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayTeacherTestsActivity extends AppCompatActivity
{
    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TEST_NAME = "com.wadesi.codespeak.TEST_NAME";

    String username;
    String test_names[];
    MediaPlayer mp;

    int score = 0;
    int question_count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_teacher_tests);

        Intent i = getIntent();

        username = i.getStringExtra(GetTeacherTestsActivity.USERNAME);
        test_names = i.getStringArrayExtra(GetTeacherTestsActivity.TESTS);

        mp = MediaPlayer.create(this, R.raw.click);

        Button buttons[] = new Button[10];

        buttons[0] = (Button)findViewById(R.id.test_one);
        buttons[1] = (Button)findViewById(R.id.test_two);
        buttons[2] = (Button)findViewById(R.id.test_three);
        buttons[3] = (Button)findViewById(R.id.test_four);

        int size = test_names.length;

        switch(size)        // Depending on number of tests created by the teacher display the equivalent number of buttons for them
        {
            case 1:
                buttons[0].setVisibility(View.VISIBLE);
                buttons[0].setText(test_names[0].toString());
                break;
            case 2:
                buttons[0].setVisibility(View.VISIBLE);
                buttons[0].setText(test_names[0].toString());

                buttons[1].setVisibility(View.VISIBLE);
                buttons[1].setText(test_names[1].toString());
                break;
            case 3:
                buttons[0].setVisibility(View.VISIBLE);
                buttons[0].setText(test_names[0].toString());

                buttons[1].setVisibility(View.VISIBLE);
                buttons[1].setText(test_names[1].toString());

                buttons[2].setVisibility(View.VISIBLE);
                buttons[2].setText(test_names[2].toString());
                break;
            case 4:
                buttons[0].setVisibility(View.VISIBLE);
                buttons[0].setText(test_names[0].toString());

                buttons[1].setVisibility(View.VISIBLE);
                buttons[1].setText(test_names[1].toString());

                buttons[2].setVisibility(View.VISIBLE);
                buttons[2].setText(test_names[2].toString());

                buttons[3].setVisibility(View.VISIBLE);
                buttons[3].setText(test_names[3].toString());
                break;
            default:
                break;
        }
    }

    public void onClick(View view)
    {
        Button b = (Button)view;
        String test_name = b.getText().toString();

        new GetTeacherQuestionActivity(this, score, question_count).execute(username, test_name);

        try
        {
            if (mp.isPlaying())
            {
                mp.stop();
                mp.release();
                mp = MediaPlayer.create(this, R.raw.click);
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
        getMenuInflater().inflate(R.menu.menu_display_teacher_tests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
