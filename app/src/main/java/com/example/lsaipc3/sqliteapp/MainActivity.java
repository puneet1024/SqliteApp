package com.example.lsaipc3.sqliteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editName,editEmail,editTVShow,editID;
    Button bAdd, bView, bUpdate, bDelete;
    private static final String TAG = "PUNEET";
    public static final String TABLE_NAME = "FavoriteTV_table";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

        editID = findViewById(R.id.editText4);
        editName = findViewById(R.id.editText1);
        editEmail = findViewById(R.id.editText2);
        editTVShow = findViewById(R.id.editText3);

        bAdd = findViewById(R.id.button1);
        bView = findViewById(R.id.button2);
        bUpdate = findViewById(R.id.button3);
        bDelete = findViewById(R.id.button4);


        addData();
        viewData();
        updateData();
        deleteData();

    }

    public void addData() {
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String favTvShow = editTVShow.getText().toString().trim();


                if(name.length()==0) {
                    Toast.makeText(getApplicationContext(), "Name Field Empty", Toast.LENGTH_LONG).show();
                }
                else if(email.length()==0)
                    Toast.makeText(getApplicationContext(),"Email Field Empty", Toast.LENGTH_LONG).show();
                else if(favTvShow.length()==0)
                    Toast.makeText(getApplicationContext(),"TVShow Field Empty", Toast.LENGTH_LONG).show();

                boolean insertData = db.addData(name,email,favTvShow);

                if (insertData && name.length()!=0 && email.length()!=0 && favTvShow.length()!=0) {
                    Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                    editName.setText("");
                    editEmail.setText("");
                    editTVShow.setText("");
                }
                 else
                    Toast.makeText(getApplicationContext(),"Not Inserted Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewData() {
        bView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data  = db.viewData();
                if (data.getCount() == 0){
                    display("Error", "No Data Found");
                    Toast.makeText(getApplicationContext(),"Nothing to show",Toast.LENGTH_LONG).show();
                }
                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID: " + data.getString(0)+ "\n");
                    buffer.append("Name: " + data.getString(1)+ "\n");
                    buffer.append("Email: " + data.getString(2) + "\n");
                    buffer.append("Favorite TV Show: " + data.getString(3)+ "\n");
                    Log.d(TAG, "onClick : View ");

                    display("All Stored Data",buffer.toString());

                }
            }
        });
    }

    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData() {
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tID = editID.getText().toString().trim().length();
                if(tID > 0 && editName.getText().toString().trim().length()> 0 && editEmail.getText().toString().trim().length() > 0 && editTVShow.getText().toString().trim().length() > 0) {
                    boolean update = db.updateData(editID.getText().toString().trim(),
                            editName.getText().toString().trim(),editEmail.getText().toString().trim(),
                            editTVShow.getText().toString().trim());
                    if(update){
                        Toast.makeText(MainActivity.this,"Successfully Updated Data",Toast.LENGTH_LONG).show();
                        editID.setText("");
                        editName.setText("");
                        editEmail.setText("");
                        editTVShow.setText("");
                    } else {
                        Toast.makeText(MainActivity.this,"Update Not Successful",Toast.LENGTH_LONG).show();
                    }
                } else if(tID == 0){
                    Toast.makeText(MainActivity.this,"You must enter an ID to update",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,"You must enter fields to update",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void deleteData(){
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tID = editID.getText().toString().trim().length();
                if(tID > 0) {
                    Integer delete = db.deleteData(editID.getText().toString().trim());
                    if(delete > 0){
                        Toast.makeText(MainActivity.this,"Successfully Deleted The Data with ID = "+ tID,Toast.LENGTH_LONG).show();
                        editID.setText("");
                    } else {
                        Toast.makeText(MainActivity.this,"Delete Not Successful",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,"You must enter an ID to delete",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
