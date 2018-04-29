package com.example.hp.databaseread;

import java.util.List;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText name;
    EditText surname;
    EditText marks;
    EditText id;
    Button add;
    Button view;
    Button update;
    Button delete;


    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.editText_name);
        surname = (EditText)findViewById(R.id.editText_surname);
        marks = (EditText)findViewById(R.id.editText_Marks);
        add = (Button)findViewById(R.id.button_add);
        view = (Button) findViewById(R.id.button_view);
        update = (Button) findViewById(R.id.button_update);
        id = (EditText) findViewById(R.id.editText_id);
        delete = (Button) findViewById(R.id.button_delete);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isinserted = myDb.insertdata(name.getText().toString(),surname.getText().toString(),marks.getText().toString());
                if(isinserted == true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getalldata();
                if(res.getCount() == 0){
                    //show message
                    showmessage("Error","No Data Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id: "+ res.getString(0)+ "\n");
                    buffer.append("Name: "+ res.getString(1)+ "\n");
                    buffer.append("Surname: "+ res.getString(2)+ "\n");
                    buffer.append("Marks: "+ res.getString(3)+ "\n"+"\n");

                }

                showmessage("Date",buffer.toString());

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isupdate = myDb.updatedata(id.getText().toString(),name.getText().toString(),surname.getText().toString(),marks.getText().toString());

                if(isupdate == true){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedrows = myDb.deletedata(id.getText().toString());

                if(deletedrows > 0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    public void showmessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}