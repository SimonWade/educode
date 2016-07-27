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
import android.widget.TextView;
import android.content.ClipData;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DragDropQuestionActivity extends AppCompatActivity
{
    String username;
    String topic;

    String question;
    String statement_one_answer;
    String statement_two_answer;
    String statement_three_answer;
    String statement_four_answer;

    String statement_one_order;
    String statement_two_order;
    String statement_three_order;
    String statement_four_order;

    int score;
    int question_count;

    public final static String USERNAME = "com.wadesi.codespeak.USERNAME";
    public final static String TOPIC = "com.wadesi.codespeak.TOPIC";
    public final static String SCORE = "com.wadesi.codespeak.SCORE";
    public final static String QUESTION_COUNT = "com.wadesi.codespeak.QUESTION_COUNT";

    Context context = this;
    MediaPlayer mp;
    MediaPlayer mp_correct;
    MediaPlayer mp_incorrect;

    private TextView question_tv, position_one, position_two, position_three, position_four;
    private TextView statement_one, statement_two, statement_three, statement_four;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop_question);

        Intent i = getIntent();

        username = i.getStringExtra(GetQuestionActivity.USERNAME);
        topic = i.getStringExtra(GetQuestionActivity.TOPIC);

        question = i.getStringExtra(GetDragDropQuestionActivity.QUESTION);

        statement_one_answer = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_ONE);                             // Always the right answer - need to randomise the order so it's not the same every time
        statement_one_order = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_ONE_ORDER);

        statement_two_answer = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_TWO);
        statement_two_order = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_TWO_ORDER);

        statement_three_answer = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_THREE);
        statement_three_order = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_THREE_ORDER);

        statement_four_answer = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_FOUR);
        statement_four_order = i.getStringExtra(GetDragDropQuestionActivity.STATEMENT_FOUR_ORDER);

        score = i.getIntExtra(SCORE, score);
        question_count = i.getIntExtra(QUESTION_COUNT, question_count);

        statement_one = (TextView)findViewById(R.id.statement_one);             // Define the statement text views we want to be able to move around
        statement_one.setOnTouchListener(new StatementTouchListener());         // Set the line text views with a listener so that we can detect when the user has touched one

        statement_two = (TextView)findViewById(R.id.statement_two);
        statement_two.setOnTouchListener(new StatementTouchListener());

        statement_three = (TextView)findViewById(R.id.statement_three);
        statement_three.setOnTouchListener(new StatementTouchListener());

        statement_four = (TextView)findViewById(R.id.statement_four);
        statement_four.setOnTouchListener(new StatementTouchListener());

        position_one = (TextView)findViewById(R.id.position_one);
        position_one.setOnDragListener(new PositionDragListener());

        position_two = (TextView)findViewById(R.id.position_two);
        position_two.setOnDragListener(new PositionDragListener());

        position_three = (TextView)findViewById(R.id.position_three);
        position_three.setOnDragListener(new PositionDragListener());

        position_four = (TextView)findViewById(R.id.position_four);
        position_four.setOnDragListener(new PositionDragListener());

        question_tv = (TextView)findViewById(R.id.tv_question);
        question_tv.setText(question);

        List<String> statements = Arrays.asList(statement_one_answer, statement_two_answer, statement_three_answer, statement_four_answer);     // Put answers in string array

        Collections.shuffle(statements);        // Shuffle the string list

        statement_one.setText(statements.get(0));
        statement_two.setText(statements.get(1));
        statement_three.setText(statements.get(2));
        statement_four.setText(statements.get(3));

        if(question_count == 0)
        {
            if(username.equals("blank"))
            {
                Toast.makeText(this, "You are not logged in. Your score cannot be saved.", Toast.LENGTH_SHORT).show();

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

        mp = MediaPlayer.create(context, R.raw.click);
        mp_correct = MediaPlayer.create(context, R.raw.correct);
        mp_incorrect = MediaPlayer.create(context, R.raw.incorrect);
    }

    private final class StatementTouchListener implements OnTouchListener
    {
        public boolean onTouch(View v, MotionEvent me)
        {
            if (me.getAction() == me.ACTION_DOWN)
            {
                ClipData data = ClipData.newPlainText("", "");                          // Set a clipdata to store the text on the view
                DragShadowBuilder shadow = new View.DragShadowBuilder(v);               // Build a shadow builder for the drag

                // Start the drag
                v.startDrag(data, shadow, v, 0);

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    private class PositionDragListener implements OnDragListener
    {
        @Override
        public boolean onDrag(View v, DragEvent de)
        {
            switch (de.getAction())
            {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:                         // User has dropped the drag shadow

                    View view = (View) de.getLocalState();
                    view.setVisibility(View.INVISIBLE);             // Set the view as invisible when it's dropped on the target

                    TextView stayOnTarget = (TextView) v;
                    TextView droppedTv = (TextView) view;
                    stayOnTarget.setText(droppedTv.getText());      // Set the text of the target as the text of the dropped text
                    droppedTv.setText(stayOnTarget.getText());

                    Object tag = stayOnTarget.getTag();             // Create a tag if a view has already been dropped

                    droppedTv.setVisibility(View.INVISIBLE);
                    droppedTv.setVisibility(View.GONE);

                    if(tag != null)
                    {
                        int existingID = (Integer)tag;                              // Tag is the view id that's already been dropped here
                        findViewById(existingID).setVisibility(View.VISIBLE);       // Set the view as visible again
                    }

                    stayOnTarget.setTag(stayOnTarget.getId());      // Tag in target view should be set to the ID of the dropped view

                    try
                    {
                        if (mp.isPlaying())
                        {
                            mp.stop();
                            mp.reset();
                            mp.release();
                            mp = MediaPlayer.create(context, R.raw.click);
                        }

                        mp.start();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:


                    break;
                default:
                    break;
            }

            return true;
        }
    }

    public void submitQuestion(View v)
    {
        Boolean position_one_correct = position_one.getText().toString().equals(statement_one_answer);
        Boolean position_two_correct = position_two.getText().toString().equals(statement_two_answer);
        Boolean position_three_correct = position_three.getText().toString().equals(statement_three_answer);
        Boolean position_four_correct = position_four.getText().toString().equals(statement_four_answer);

        // if((position_one.getText().toString().equals(statement_one_answer)) && (position_two.getText().toString().equals(statement_two_answer))
        // && (position_three.getText().toString().equals(statement_three_answer)) && (position_four.getText().toString().equals(statement_four_answer)))

        if(position_one_correct && position_two_correct && position_three_correct && position_four_correct)
        {
            score++;
            question_count--;

            try
            {
                if (mp.isPlaying())
                {
                    mp.stop();
                    mp.reset();
                    mp.release();
                    mp = MediaPlayer.create(context, R.raw.correct);
                }

                mp.start();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            Toast.makeText(context, "Correct! Score: " + score, Toast.LENGTH_SHORT).show();

            if(!topic.equals("Boolean") && !topic.equals("Selection") && !topic.equals("Iteration"))
            {
                new GetTeacherQuestionActivity(this, score, question_count).execute(username, topic);
            }
            else
            {
                new GetQuestionActivity(this, score, question_count).execute(username, topic);                      // Go to the next question
            }
        }
        else
        {
            question_count--;
            Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT).show();

            try
            {
                if (mp.isPlaying())
                {
                    mp.stop();
                    mp.reset();
                    mp.release();
                    mp = MediaPlayer.create(context, R.raw.incorrect);
                }

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
                new GetQuestionActivity(this, score, question_count).execute(username, topic);                      // Go to the next question
            }
        }
    }

    public void resetQuestion(View v)
    {
        statement_one.setVisibility(TextView.VISIBLE);
        statement_two.setVisibility(TextView.VISIBLE);
        statement_three.setVisibility(TextView.VISIBLE);
        statement_four.setVisibility(TextView.VISIBLE);

        position_one.setText("Put");
        position_two.setText("the statements");
        position_three.setText("in");
        position_four.setText("order");

        statement_one.setTag(null);
        statement_two.setTag(null);
        statement_three.setTag(null);
        statement_four.setTag(null);

        statement_one.setOnTouchListener(new StatementTouchListener());
        statement_two.setOnTouchListener(new StatementTouchListener());
        statement_three.setOnTouchListener(new StatementTouchListener());
        statement_four.setOnTouchListener(new StatementTouchListener());
    }

    public void exitTest(View v)
    {
        try
        {
            if (mp.isPlaying())
            {
                mp.stop();
                mp.reset();
                mp.release();
                mp = MediaPlayer.create(context, R.raw.click);
            }

            mp.start();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("Confirm");
        build.setMessage("Are you sure you want to leave the test? Your progress will be lost.");

        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
