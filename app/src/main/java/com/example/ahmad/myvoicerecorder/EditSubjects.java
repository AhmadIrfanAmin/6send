package com.example.noman.myvoicerecorder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditSubjects extends AppCompatActivity {

    DBHealper dbHealper = null;
    CustomAdapterSubjects myCustomAdapter = null;
    ArrayList<emailSubjectData> emailSubjectData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subjects);


        dbHealper = new DBHealper(this);
        emailSubjectData = dbHealper.getDataSubject();



        String subject = getIntent().getExtras().getString("subject");
        String id = getIntent().getExtras().getString("id");




        LayoutInflater inflater = LayoutInflater.from(EditSubjects.this);
        View subView = inflater.inflate(R.layout.editusername, null);
        final EditText subjectEditText = (EditText)subView.findViewById(R.id.editSubject);
        final TextView txtId = (TextView) subView.findViewById(R.id.txt_idSubject);

        subjectEditText.setText(subject);
        txtId.setText(id);


        AlertDialog.Builder builder = new AlertDialog.Builder(EditSubjects.this);
        builder.setTitle("Edit Record");
        builder.setMessage("Enter Subject to Update");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String subject = subjectEditText.getText().toString();
//                        int personid = Integer.parseInt(String.valueOf(txtId));
                int personid = Integer.valueOf(txtId.getText().toString());


                editSubjectData(subject, personid);
                finish();


                Toast.makeText(EditSubjects.this, "data edited successfully", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(Recipients.this, "Cancel", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        builder.show();
    }



    public void editSubjectData(String subject, int id) {
        boolean insertData = dbHealper.updateSubjectInfo(subject, id);
    }


}

