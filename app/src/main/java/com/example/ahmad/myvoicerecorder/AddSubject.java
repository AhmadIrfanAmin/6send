package com.example.noman.myvoicerecorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noman.myvoicerecorder.Utils.Helper;

import java.util.ArrayList;

public class AddSubject extends AppCompatActivity {

    DBHealper dbHealper = null;
    CustomAdapterSubjects myCustomAdapter = null;
    ArrayList<emailSubjectData> emailSubjectDatalist = null;
    private static final int RECPIENT_SELECTION_REQUEST = 1001;
    private LinearLayout add_recip_container;
    private ListView listView;
    private Animation slide_up, slide_down;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);


        dbHealper = new DBHealper(this);
        emailSubjectDatalist = dbHealper.getDataSubject();

        myCustomAdapter= new CustomAdapterSubjects(this,R.layout.layoutitemssubjects,emailSubjectDatalist);
        listView = (ListView) findViewById(R.id.subjects_list);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        add_recip_container = findViewById(R.id.add_recip_container);
        listView.setAdapter(myCustomAdapter);
        nameEditText = (EditText)findViewById(R.id.et_name);
        final Button btn_cancel = findViewById(R.id.btn_cancel);
        final Button btn_add = findViewById(R.id.btn_dialog_add);

//        editName = findViewById(R.id.edit_name);
//        editId = findViewById(R.id.edit_id);
//        editEmail = findViewById(R.id.edit_email);


        ImageButton btn_addButton = findViewById(R.id.btn_addButton);
        btn_addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openDialogPerson();
                if(add_recip_container.getVisibility() == View.VISIBLE)
                    return;
                slideAddDialogUp();

            }
        });


        ImageButton btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                String testsubject = ((TextView) view.findViewById(R.id.txt_subject)).getText().toString();


                Helper.subject = testsubject;

                Intent myIntent = new Intent(view.getContext(), SendEmail.class);

                startActivity(myIntent);


            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String personName = nameEditText.getText().toString();

                for (emailSubjectData obj : emailSubjectDatalist){
                    if(obj.getSubject() == null)
                        continue;
                    if(obj.getSubject().equalsIgnoreCase(personName)) {
                        nameEditText.setError(getString(R.string.subject_already));
                        return;
                    }
                }
                nameEditText.setText("");
                editSubjectData(personName,Integer.valueOf(Helper.subjectToEdit.getId()));
                updateListview();
                Toast.makeText(AddSubject.this, R.string.saved, Toast.LENGTH_LONG).show();
                slideAddDialogDown();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideAddDialogDown();
            }
        });


    }
    public void slideAddDialogUp() {
        if(add_recip_container.getVisibility() == View.VISIBLE)
            return;

        nameEditText.setText(Helper.subjectToEdit.getSubject());
        nameEditText.requestFocus();
        showSoftKeyboard(nameEditText);
        add_recip_container.startAnimation(slide_down);
        add_recip_container.setVisibility(View.VISIBLE);
    }

    private void showSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftkeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void slideAddDialogDown() {
        hideSoftkeyboard(nameEditText);
        add_recip_container.startAnimation(slide_up);
        add_recip_container.setVisibility(View.GONE);
    }

    public void addSubjectData(String subject) {
        boolean insertData = dbHealper.addemailSubjectData(subject);
    }
    public void editEmailData(String subject, int id) {
        boolean insertData = dbHealper.updateSubjectInfo(subject, id);
    }
    public void editSubjectData(String subject, int id) {
        boolean insertData = dbHealper.updateSubjectInfo(subject, id);
    }

    public void updateListview(){

        dbHealper = new DBHealper(AddSubject.this);
        emailSubjectDatalist = dbHealper.getDataSubject();
        CustomAdapterSubjects.emailSubjectData.clear();
        CustomAdapterSubjects.emailSubjectData.addAll(emailSubjectDatalist);
        myCustomAdapter.notifyDataSetChanged();

    }
    @Override
    public void onResume()
    {
        super.onResume();
        updateListview();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void selectItem() {
        if(getCallingActivity() != null) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "OK");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }






}

