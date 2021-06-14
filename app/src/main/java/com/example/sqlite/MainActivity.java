package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText etId,etName, etNumber, etcgpa;
    StudentsDB studentsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        etName=findViewById(R.id.etName);
        etNumber=findViewById(R.id.etNumber);
        etId=findViewById(R.id.etId);
        etcgpa=findViewById(R.id.etcgpa);
        studentsDB =new StudentsDB(this);

    }
    public void clear(){
        etName.setText("");
        etNumber.setText("");
        etId.setText("");
        etcgpa.setText("");
    }
    public void btnSubmit(View v){
        String Id=etId.getText().toString().trim();
        String name=etName.getText().toString().trim();
        String cell=etNumber.getText().toString().trim();
        String cgpa=etcgpa.getText().toString().trim();

        if(name.isEmpty() || cell.isEmpty() ||Id.isEmpty() || cgpa.isEmpty())
        {
            Toast.makeText(this, "Enter missing entities", Toast.LENGTH_SHORT).show();
        }
        else {
            studentsDB.open();
            studentsDB.CreateEntry(Id, name, cell, cgpa);
            clear();
            studentsDB.close();
        }
    }
    public void btnShowData(View v){
        Intent intent=new Intent(MainActivity.this, ShowData.class);
        startActivity(intent);
    }
    public void btnEditData(View view){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.dialogue_editdata,null);

        EditText etId=v.findViewById(R.id.etId);
        EditText etName=v.findViewById(R.id.etName);
        EditText etCell=v.findViewById(R.id.etNumber);
        EditText etCgpa=v.findViewById(R.id.etcgpa);
        Button btnSubmit=v.findViewById(R.id.btnSubmit);
        Button btnCancel=v.findViewById(R.id.btnCancel);


        dialog.setView(v);
        final AlertDialog  dialog1=dialog.create();
        dialog1.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id=etId.getText().toString().trim();
                String Name=etName.getText().toString().trim();
                String Cell=etCell.getText().toString().trim();
                String Cgpa=etCgpa.getText().toString().trim();

                studentsDB.open();
                long totalUpdatedRecords= studentsDB.UpdateEntry(Id, Name, Cell, Cgpa);
                studentsDB.close();
                etId.setText("");
                etName.setText("");
                etCell.setText("");
                etCgpa.setText("");

            }
        });

    }
    public void btnDelData(View view){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.dialogue_del,null);

        EditText etId=v.findViewById(R.id.etDel);
        Button btnDel=v.findViewById(R.id.btnDel);
        Button btnCancel= v.findViewById(R.id.btnCancel);


        dialog.setView(v);
        final AlertDialog  dialog1=dialog.create();
        dialog1.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id=etId.getText().toString().trim();
                studentsDB.open();
                studentsDB.DeleteData(Id);
                studentsDB.close();
                etId.setText("");

            }
        });

    }

}