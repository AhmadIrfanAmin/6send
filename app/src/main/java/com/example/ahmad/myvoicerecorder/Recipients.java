package com.example.noman.myvoicerecorder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.noman.myvoicerecorder.Utils.Helper;

import java.util.ArrayList;

public class Recipients extends AppCompatActivity {

    DBHealper dbHealper = null;
    CustomAdapterRecipients myCustomAdapter = null;
    ArrayList<emailData> emailDatalist = null;
    ImageButton addPerson, btn_close;
    private LinearLayout add_recip_container,update_recip_container;
    private ListView listView;
    private Animation slide_up, slide_down;
    private EditText nameEditText, nameEditText2;
//    TextView editName;
//    TextView editEmail;
//    TextView editId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);

        dbHealper = new DBHealper(this);
        emailDatalist = dbHealper.getData();

        myCustomAdapter= new CustomAdapterRecipients(this,R.layout.layoutitemsrecipients,emailDatalist);
        listView = (ListView) findViewById(R.id.recipients_list);
        add_recip_container = findViewById(R.id.add_recip_container);
        update_recip_container = findViewById(R.id.update_recip_container);
        listView.setAdapter(myCustomAdapter);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);




        nameEditText = (EditText)findViewById(R.id.et_name);
        final EditText emailEditText = (EditText)findViewById(R.id.et_email);
        final Button btn_cancel = findViewById(R.id.btn_cancel);
        final Button btn_add = findViewById(R.id.btn_dialog_add);

        nameEditText2 = (EditText)findViewById(R.id.et_update_name);
        final EditText emailEditText2 = (EditText)findViewById(R.id.et_update_email);
        final Button btn_cancel2 = findViewById(R.id.btn_update_cancel);
        final Button btn_add2 = findViewById(R.id.btn_dialog_update);
//        editName = findViewById(R.id.edit_name);
//        editId = findViewById(R.id.edit_id);
//        editEmail = findViewById(R.id.edit_email);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String personName = nameEditText.getText().toString();
                String personEmail = emailEditText.getText().toString();
                if(!isValidEmail(personEmail)) {
                    emailEditText.setError("Email is not Valid");
                    return;
                }

                for (emailData obj : emailDatalist){
                    if(obj.getEmailAddress().equalsIgnoreCase(personEmail)) {
                        emailEditText.setError("Email already Exists");
                        return;
                    }
                }
                nameEditText.setText("");
                emailEditText.setText("");
                addData(personName, personEmail);
                updateListview();
                Toast.makeText(Recipients.this, R.string.saved, Toast.LENGTH_LONG).show();
                slideAddDialogDown();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideAddDialogDown();
            }
        });
        btn_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String personName = nameEditText2.getText().toString();
                String personEmail = emailEditText2.getText().toString();
                if(!isValidEmail(personEmail)) {
                    emailEditText2.setError(getString(R.string.email_not_valid));
                    return;
                }

                for (emailData obj : emailDatalist){
                    if(obj.getEmailAddress() != null)
                        if(obj.getEmailAddress().equalsIgnoreCase(personEmail)) {
                            emailEditText2.setError(getString(R.string.email_already_exists));
                            return;
                        }
                }
                nameEditText2.setText("");
                emailEditText2.setText("");
                editEmailData(personName, personEmail,Integer.valueOf(Helper.recipientToEdit.getId()));
                updateListview();
                Toast.makeText(Recipients.this, R.string.saved, Toast.LENGTH_LONG).show();
                slideUpdateDialogDown();
            }
        });

        btn_cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideUpdateDialogDown();
            }
        });

        addPerson = findViewById(R.id.btn_addButton);
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                openDialogPerson();
                if(add_recip_container.getVisibility() == View.VISIBLE)
                    return;
                slideAddDialogUp();
            }
        });


        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void slideUpdateDialogUp() {
        nameEditText2.requestFocus();
        showSoftKeyboard(nameEditText2);
        update_recip_container.startAnimation(slide_down);
        update_recip_container.setVisibility(View.VISIBLE);
    }
    private void slideUpdateDialogDown() {
        hideSoftkeyboard(nameEditText2);
        update_recip_container.requestFocus();
        update_recip_container.startAnimation(slide_up);
        update_recip_container.setVisibility(View.GONE);
    }
    private void slideAddDialogUp() {
        nameEditText.requestFocus();
        showSoftKeyboard(nameEditText);
        add_recip_container.startAnimation(slide_down);
        add_recip_container.setVisibility(View.VISIBLE);
    }
    private void slideAddDialogDown() {
        hideSoftkeyboard(nameEditText);
        add_recip_container.startAnimation(slide_up);
        add_recip_container.setVisibility(View.GONE);
    }

    private void showSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftkeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void addData(String name, String emailAddress) {
        boolean insertData = dbHealper.addemailAddressData(name, emailAddress);
    }
    public void editEmailData(String name, String emailAddress, int id) {
        boolean insertData = dbHealper.updatePersonInfo(name, emailAddress, id);
    }


    private void openDialogPerson(){
        final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_add_recipient);
        final EditText nameEditText = (EditText)dialog.findViewById(R.id.et_name);
        final EditText emailEditText = (EditText)dialog.findViewById(R.id.et_email);
        final Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        final Button btn_add = dialog.findViewById(R.id.btn_dialog_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String personName = nameEditText.getText().toString();
                String personEmail = emailEditText.getText().toString();

                addData(personName, personEmail);
                updateListview();
                Toast.makeText(Recipients.this, "data added into database", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
    public void updateListview(){
        dbHealper = new DBHealper(Recipients.this);
        emailDatalist = dbHealper.getData();
        CustomAdapterRecipients.emailArray.clear();
        CustomAdapterRecipients.emailArray.addAll(emailDatalist);
        myCustomAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
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
