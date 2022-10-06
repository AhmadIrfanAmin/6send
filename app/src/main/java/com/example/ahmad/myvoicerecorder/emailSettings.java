package com.example.noman.myvoicerecorder;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.noman.myvoicerecorder.Utils.Helper;

import java.util.Locale;

public class emailSettings extends AppCompatActivity {

    TextView textRecipients, textSubject, textFaq, textInfo;
    DataManager dataManager;
    private CheckBox cb_mode;

    EditText et_subject, et_content, et_filename;
    CheckBox cb_subject, cb_content, cb_email_programm;
    LinearLayout mini_settngs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_settings);
        dataManager = new DataManager(this);
        cb_mode = findViewById(R.id.cb_mode);
        cb_subject = findViewById(R.id.cb_show_subject);
        cb_content = findViewById(R.id.cb_show_content);
        cb_email_programm = findViewById(R.id.cb_send_via_mail);
        et_content = findViewById(R.id.et_mini_content);
        et_filename = findViewById(R.id.et_mini_file_name);
        et_subject = findViewById(R.id.et_mini_subject);
        mini_settngs = findViewById(R.id.mini_settngs);

        cb_mode.setChecked(Helper.IS_MINIMALISTIC);
        if(Helper.IS_MINIMALISTIC){
            InitMinimalistic(true);
            mini_settngs.setVisibility(View.VISIBLE);
        }else{
            InitMinimalistic(false);
            mini_settngs.setVisibility(View.GONE);
        }


        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                Helper.default_content = s.toString();
                getSharedPreferences(getPackageName(),MODE_PRIVATE).edit().putString(Helper.PREFS_DEFAULT_CONTENT,s.toString()).apply();
            }
        });
        et_subject.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        Helper.default_subject = s.toString();
                        getSharedPreferences(getPackageName(),MODE_PRIVATE).edit().putString(Helper.PREFS_DEFAULT_SUBJECT,s.toString()).apply();
                    }
                });
        et_filename.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        Helper.default_file_name = s.toString();
                        getSharedPreferences(getPackageName(),MODE_PRIVATE).edit().putString(Helper.PREFS_CB_FILE_NAME,s.toString()).apply();
                    }
                });


        cb_email_programm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Helper.cb_email_default = b;
                getSharedPreferences(getPackageName(),MODE_PRIVATE).edit().putBoolean(Helper.PREFS_DEFAULT_EMAIL,b).apply();
            }
        });
        cb_subject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Helper.cb_subject_default = b;
                getSharedPreferences(getPackageName(),MODE_PRIVATE).edit().putBoolean(Helper.PREFS_CB_SUBJECT,b).apply();
            }
        });
        cb_content.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Helper.cb_content_default = b;
                getSharedPreferences(getPackageName(),MODE_PRIVATE).edit().putBoolean(Helper.PREFS_CB_CONTENT,b).apply();
            }
        });

        cb_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Helper.IS_MINIMALISTIC = b;
                if(Helper.IS_MINIMALISTIC) {
                    Helper.default_subject = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString(Helper.PREFS_DEFAULT_SUBJECT, "");
                    Helper.default_content = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString(Helper.PREFS_DEFAULT_CONTENT, "");
                    Helper.default_file_name = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString(Helper.PREFS_CB_FILE_NAME, "audiomessage");
                    Helper.cb_content_default = getSharedPreferences(getPackageName(), MODE_PRIVATE).getBoolean(Helper.PREFS_CB_CONTENT, false);
                    Helper.cb_subject_default = getSharedPreferences(getPackageName(), MODE_PRIVATE).getBoolean(Helper.PREFS_CB_SUBJECT, false);
                    Helper.cb_email_default = getSharedPreferences(getPackageName(), MODE_PRIVATE).getBoolean(Helper.PREFS_DEFAULT_EMAIL, false);
                    mini_settngs.setVisibility(View.VISIBLE);
                }
                else
                    mini_settngs.setVisibility(View.GONE);
                getSharedPreferences(getPackageName(),MODE_PRIVATE).edit().putBoolean(Helper.PREFS_MODE,b).apply();
            }
        });
        textRecipients = findViewById(R.id.txt_Recipients);
        textRecipients.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(emailSettings.this,Recipients.class);
                startActivity(intent);
                finish();
            }
        });

        textSubject = findViewById(R.id.txt_subjects);
        textSubject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(emailSettings.this,AddSubject.class);
                startActivity(intent);
                finish();
            }
        });
        final Spinner spinner = findViewById(R.id.sp_languages);
        if(Helper.CURRENT_LANGUAGE.isEmpty()) {
            if (Locale.getDefault().getLanguage().equalsIgnoreCase("en"))
                Helper.CURRENT_LANGUAGE = "English";
            else if (Locale.getDefault().getLanguage().equalsIgnoreCase("de"))
                Helper.CURRENT_LANGUAGE = "Deutsch";
        }else {
            if (Helper.CURRENT_LANGUAGE.equalsIgnoreCase("English")) {
                spinner.setSelection(0, true);
            } else if (Helper.CURRENT_LANGUAGE.equalsIgnoreCase("Deutsch")) {
                spinner.setSelection(1, true);
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = spinner.getItemAtPosition(i).toString();
                if(!Helper.CURRENT_LANGUAGE.equalsIgnoreCase(value)){
                    if(value.equalsIgnoreCase("English")){
                        askPermission("en",value);
                    }else if(value.equalsIgnoreCase("Deutsch")){
                        askPermission("de",value);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void InitMinimalistic(boolean b) {
        cb_email_programm.setChecked(Helper.cb_email_default);
        cb_subject.setChecked(Helper.cb_subject_default);
        cb_content.setChecked(Helper.cb_content_default);
        et_filename.setText(Helper.default_file_name);
        et_subject.setText(Helper.default_subject);
        et_content.setText(Helper.default_content);

    }

    public void askPermission(final String lang,final String name) {
        setLocale(lang,name);
//        mDialog = new AlertDialog.Builder(this)
//                .setTitle("Restart")
//                .setMessage("You need to restart the app in order make changes take effect.")
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        setLocale(lang,name);
//                        mDialog.dismiss();
//                    }})
//                .setNegativeButton(android.R.string.no, null).show();

    }



    public void setLocale(final String lang,final String name) {
        Locale myLocale;
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        Helper.CURRENT_LANGUAGE = name;
        dataManager.saveString("LANG",Helper.CURRENT_LANGUAGE);
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finishAffinity();
        startActivity(refresh);
    }
    public void goBack(View view) {
        finish();
    }

    public void startImprint(View view) {
        startActivity(new Intent(this,ImprintActivity.class));
    }
}
