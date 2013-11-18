package com.example.lesson9;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CityEditActivity extends Activity
{
    private long id_subject = 0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cityedit);

        id_subject = getIntent().getLongExtra("ID_CITY", 0);

        if (id_subject != 0)
        {
            TextView name = (TextView)findViewById(R.id.editSubjectName);
            name.setText(City.findById(id_subject).name);
            ((Button)findViewById(R.id.deleteButton)).setEnabled(true);
        }
        else
        {
            ((Button)findViewById(R.id.deleteButton)).setEnabled(false);
        }
    }

    /*public void onSaveButtonClicked(View v)
    {
        EditText name = (EditText)findViewById(R.id.editSubjectName);

        Cursor c = Database.gi().query("select * from subjects where id_subject = "+ id_subject);
        if (c.getCount() == 0)
        {
            Database.gi().exec("insert into subjects values(null,'" + (name.getText().toString()) + "')");
            Toast t = Toast.makeText(this, "New subject is added", 3000);
            t.show();
            c.close();
            finish();
        }
        else
        {
            Database.gi().exec("update subjects set name = '"+(name.getText().toString())+"' where id_subject = "+ id_subject);
            Toast t = Toast.makeText(this, "Subject is updated", 3000);
            t.show();
            c.close();
        }
    } */

    public void onDeleteButtonClicked(View v)
    {
        City.findById(id_subject).chosen = false;
        City.findById(id_subject).flush();
        Toast.makeText(this, "City has been deleted", 3000).show();
        finish();
    }
}
