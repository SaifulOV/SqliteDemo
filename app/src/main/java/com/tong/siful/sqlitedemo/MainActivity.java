package com.tong.siful.sqlitedemo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyDatabaseHelper myDatabaseHelper;
    private EditText nameEditText, ageEditText, genderEditText;
    private Button saveButton, showButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        nameEditText = findViewById(R.id.nameEdId);
        ageEditText = findViewById(R.id.ageEdId);
        genderEditText = findViewById(R.id.genderEdId);
        saveButton = findViewById(R.id.saveBtnId);
        showButton = findViewById(R.id.showBtnId);
        saveButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        System.out.println("--------------------------------");
        System.out.println(name);
        System.out.println(age);
        System.out.println(gender);
        if (view.getId() == R.id.saveBtnId) {
            if (name.length() == 0 && age.length() == 0 && gender.length() == 0) {
                Toast.makeText(MainActivity.this, "FIELD CANNOT BE EMPTY", Toast.LENGTH_LONG).show();
            } else {
                long rowId = myDatabaseHelper.insertData(name, age, gender);
                if (rowId == -1) {
                    Toast.makeText(getApplicationContext(), "unsuccessful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Row " + rowId + " is Successfully Insert", Toast.LENGTH_LONG).show();
                    nameEditText.setText("");
                    ageEditText.setText("");
                    genderEditText.setText("");
                }
            }
        }
        if (view.getId() == R.id.showBtnId) {
            SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
            Cursor resultSet = myDatabaseHelper.displayData();
            if (resultSet.getCount() == 0) {
                //if there is no data
                showData("Error", "No Data Found");
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (resultSet.moveToNext()) {
                stringBuffer.append("Id     : " + resultSet.getString(0) + "\n");
                stringBuffer.append("Name   : " + resultSet.getString(1) + "\n");
                stringBuffer.append("Age    : " + resultSet.getString(2) + "\n");
                stringBuffer.append("Gender : " + resultSet.getString(3) + "\n\n\n");
            }
            showData("ResultSet", stringBuffer.toString());
        }
    }
    public void showData(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}