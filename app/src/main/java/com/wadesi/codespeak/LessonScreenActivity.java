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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class LessonScreenActivity extends AppCompatActivity
{
    Context context = this;
    MediaPlayer mp;
    int counter;

    String username;
    String topic = "Boolean";

    public final static String COUNTER = "com.wadesi.codespeak.COUNTER";
    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_screen);

        Intent i = getIntent();

        username = i.getStringExtra(LessonActivity.USERNAME);
        counter = i.getIntExtra(COUNTER, counter);

        if(username == null)
            username = "blank";

        mp = MediaPlayer.create(context, R.raw.click);

        TextView TvText = (TextView)findViewById(R.id.tv_text);
        TextView TvHeader = (TextView)findViewById(R.id.tv_header);
        ImageView IvTable = (ImageView)findViewById(R.id.table);
        LinearLayout space = (LinearLayout)findViewById(R.id.space);

        String header_one = "Boolean type";
        String screen_one = "The Boolean data type is always either TRUE or FALSE.\n\nTRUE is equivalent to 1 while FALSE is equivalent to 0.\n\nWe can use these expressions for many purposes, such as checking a test score or a person's age.";

        String header_two = "Boolean operators";
        String screen_two = "Boolean operators include NOT, AND and OR. We can use Boolean variables to control the flow of a program.\n\nWe can can change the flow of the program depending on whether variable a is TRUE or FALSE.";

        String header_three = "AND operator";
        String screen_three = "When using AND in a condition, such as 'if a and b' both a AND b must be TRUE for the condition to be met. If just one is FALSE, it will fail.\n\nAND can be used with 'and' in Python or '&&' in other languages, like C.";

        String header_four = "OR operator";
        String screen_four = "When using OR in a condition, such as 'if a or b' the condition will be met if either are TRUE.\n\nAlternatively, if both are TRUE, it will also be TRUE. OR can be used with 'or' in Python or '||' in other languages, such as C.";

        String header_five = "NOT operator";
        String screen_five = "When using NOT in a condition, such as '!a' it inverts the value of the variable.\n\nFor example, if a is TRUE, using NOT will invert it, making it FALSE. If it is FALSE, using NOT will change it to TRUE.";

        String header_six = "Operators";
        String screen_six = "Other comparison operators can also be used when evaluating a condition, such as equal to, greater than or less than.\n\nUsing these operators we can determine whether an expression is TRUE or FALSE.";

        String header_seven = "Equal To";
        String screen_seven = "We can use 'equal to' in an expression to find is the condition is TRUE or FALSE.\n\nFor example 'if a == 5' will be TRUE if a is indeed equal to 5. Otherwise, the condition will be FALSE and the code will not be executed.";

        String header_eight = "Not Equal To";
        String screen_eight = "We can use 'not equal to' in a similar way, such as comparing two separate variables in a program.\n\nThe if statement 'if 'a != b' will be TRUE if a is not equal to b. Otherwise, the condition will be FALSE.";

        String header_nine = "Greater Than";
        String screen_nine = "Greater than operators can also be used in programming.\n\nA 'greater than' expression can be used to compare the value of a variable against a certain number. For example, we can check if a test score has exceeded the pass level.";

        String header_ten = "Less Than";
        String screen_ten = "Less than operators also have a use in programming. A 'less than' expression can be used similarly to 'greater than'.\n\nThe expression will be TRUE if the condition is met, such as when the variable a is less than 20.";

        switch(counter)
        {
            case 0:
                TvHeader.setText(header_one, TextView.BufferType.EDITABLE);
                TvText.setText(screen_one, TextView.BufferType.EDITABLE);
                break;
            case 1:
                TvHeader.setText(header_two, TextView.BufferType.EDITABLE);
                TvText.setText(screen_two, TextView.BufferType.EDITABLE);
                IvTable.setVisibility(View.VISIBLE);
                break;
            case 2:
                TvHeader.setText(header_three, TextView.BufferType.EDITABLE);
                TvText.setText(screen_three, TextView.BufferType.EDITABLE);
                IvTable.setVisibility(View.VISIBLE);
                IvTable.setImageResource(R.drawable.andtable);
                break;
            case 3:
                TvHeader.setText(header_four, TextView.BufferType.EDITABLE);
                TvText.setText(screen_four, TextView.BufferType.EDITABLE);
                IvTable.setVisibility(View.VISIBLE);
                IvTable.setImageResource(R.drawable.ortable);
                break;
            case 4:
                TvHeader.setText(header_five, TextView.BufferType.EDITABLE);
                TvText.setText(screen_five, TextView.BufferType.EDITABLE);
                space.setVisibility(View.GONE);
                IvTable.setVisibility(View.VISIBLE);
                IvTable.setImageResource(R.drawable.nottable);
                break;
            case 5:
                TvHeader.setText(header_six, TextView.BufferType.EDITABLE);
                TvText.setText(screen_six, TextView.BufferType.EDITABLE);
                space.setVisibility(View.GONE);
                break;
            case 6:
                TvHeader.setText(header_seven, TextView.BufferType.EDITABLE);
                TvText.setText(screen_seven, TextView.BufferType.EDITABLE);
                IvTable.setVisibility(View.VISIBLE);
                IvTable.setImageResource(R.drawable.equalcode);
                break;
            case 7:
                TvHeader.setText(header_eight, TextView.BufferType.EDITABLE);
                TvText.setText(screen_eight, TextView.BufferType.EDITABLE);
                IvTable.setVisibility(View.VISIBLE);
                IvTable.setImageResource(R.drawable.notequalcode);
                break;
            case 8:
                TvHeader.setText(header_nine, TextView.BufferType.EDITABLE);
                TvText.setText(screen_nine, TextView.BufferType.EDITABLE);
                IvTable.setVisibility(View.VISIBLE);
                IvTable.setImageResource(R.drawable.greaterthantable);
                break;
            case 9:
                TvHeader.setText(header_ten, TextView.BufferType.EDITABLE);
                TvText.setText(screen_ten, TextView.BufferType.EDITABLE);
                IvTable.setVisibility(View.VISIBLE);
                IvTable.setImageResource(R.drawable.lessthantable);
                break;
            case 10:
                try
                {
                    Intent in = new Intent(this, TestActivity.class);
                    in.putExtra(USERNAME, username);
                    in.putExtra(TOPIC, topic);
                    this.startActivity(in);
                }
                catch(Exception ex)
                {
                    Toast.makeText(this, "You could not be taken to a relevant test.", Toast.LENGTH_SHORT);

                    Intent in = new Intent(this, LessonsActivity.class);
                    in.putExtra(USERNAME, username);
                    this.startActivity(in);
                }

                break;
            default:
                Intent in = new Intent(this, LessonsActivity.class);
                in.putExtra(USERNAME, username);
                this.startActivity(in);
                break;
        }
    }

    public void nextQuestion(View v)
    {
        counter++;

        Intent i = new Intent(this, LessonScreenActivity.class);
        i.putExtra(USERNAME, username);
        i.putExtra(COUNTER, counter);
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

    public void exitLesson(View v)
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
        build.setMessage("Are you sure you want to leave the lesson?");

        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Leave test
                Intent i = new Intent(context, LessonsActivity.class);
                i.putExtra(USERNAME, username);
                context.startActivity(i);
                finish();
                dialog.dismiss();
            }
        });

        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        counter--;

        Intent i = new Intent(this, LessonScreenActivity.class);
        i.putExtra(USERNAME, username);
        i.putExtra(COUNTER, counter);
        this.startActivity(i);

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lesson_screen, menu);
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
