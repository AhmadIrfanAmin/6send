package com.example.noman.myvoicerecorder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditName extends AppCompatActivity {


    DBHealper dbHealper = null;
    CustomAdapterRecipients myCustomAdapter = null;
    ArrayList<emailData> emailDatalist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);


        dbHealper = new DBHealper(this);
        emailDatalist = dbHealper.getData();


        String name = getIntent().getExtras().getString("name");
        String email = getIntent().getExtras().getString("email");
        String id = getIntent().getExtras().getString("id");


        LayoutInflater inflater = LayoutInflater.from(EditName.this);
        View subView = inflater.inflate(R.layout.edittextusername, null);
        final EditText nameEditText = (EditText) subView.findViewById(R.id.editname);
        final EditText emailEditText = (EditText) subView.findViewById(R.id.editemail);
        final TextView txtId = (TextView) subView.findViewById(R.id.txt_id);

        nameEditText.setText(name);
        emailEditText.setText(email);
        txtId.setText(id);


        AlertDialog.Builder builder = new AlertDialog.Builder(EditName.this);
        builder.setTitle("Edit Record");
        builder.setMessage("Enter Name and Email to Update");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String personName = nameEditText.getText().toString();
                String personEmail = emailEditText.getText().toString();
//                        int personid = Integer.parseInt(String.valueOf(txtId));
                int personid = Integer.valueOf(txtId.getText().toString());

                
                editEmailData(personName, personEmail, personid);
                finish();


                Toast.makeText(EditName.this, "data edited successfully", Toast.LENGTH_LONG).show();
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


    public void editEmailData(String name, String emailAddress, int id) {
        boolean insertData = dbHealper.updatePersonInfo(name, emailAddress, id);

    }

}
