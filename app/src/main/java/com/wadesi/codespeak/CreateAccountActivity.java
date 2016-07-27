package com.wadesi.codespeak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity
{
    private EditText et_uname;
    private EditText et_pass;
    private EditText et_forename;
    private EditText et_surname;
    private EditText et_class;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Intent i = getIntent();

        et_uname = (EditText)findViewById(R.id.etUname);
        et_pass = (EditText)findViewById(R.id.etPword);
        et_forename = (EditText)findViewById(R.id.etForename);
        et_surname = (EditText)findViewById(R.id.etSurname);
        et_class = (EditText)findViewById(R.id.etClass);
    }

    public void register(View v)
    {
        String username = et_uname.getText().toString();
        String password = et_pass.getText().toString();
        String forename = et_forename.getText().toString();
        String surname = et_surname.getText().toString();
        String class_name = et_class.getText().toString();

        // Add field validation

        new RegisterActivity(this).execute(username, password, forename, surname, class_name);
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
