package com.example.noman.myvoicerecorder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noman.myvoicerecorder.Utils.Helper;

import java.util.ArrayList;



public class SelectSubject extends AppCompatActivity {

    private static final int MINIMALISTIC_CODE = 012;
    DBHealper dbHealper = null;
    CustomAdapterSelectSubject myCustomAdapter = null;
    ArrayList<emailSubjectData> emailSubDatalist = null;
    TextView namePerson, emailPerson;
    MainActivity mainAtv= new MainActivity();
    boolean mStartPlaying = true;


    ImageButton btn_settings, btn_back,btn_openRecipient, btn_play, btn_replay ,btn_delete;

    private static final int RECPIENT_SELECTION_REQUEST = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);


        dbHealper = new DBHealper(this);
        emailSubDatalist = dbHealper.getDataSubject();

        myCustomAdapter= new CustomAdapterSelectSubject(this,R.layout.layoutsubjects,emailSubDatalist);
        final ListView listView = (ListView) findViewById(R.id.selectsubject_list);

        listView.setAdapter(myCustomAdapter);


//        String name = getIntent().getExtras().getString("name");
//        String email = getIntent().getExtras().getString("email");

        namePerson = findViewById(R.id.txt_name);
        emailPerson = findViewById(R.id.txt_email);
        namePerson.setText(Helper.userName);
        emailPerson.setText(Helper.email);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String subject = ((TextView) view.findViewById(R.id.txt_subject)).getText().toString();
                Helper.subject = subject;
                if(Helper.subject.isEmpty()) {
                    Toast.makeText(SelectSubject.this, R.string.empty_subject, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Helper.IS_MINIMALISTIC) {
                    if(Helper.cb_content_default && Helper.default_content.isEmpty()) {
                        Intent myIntent = new Intent(view.getContext(), SendEmail.class);
                        if(!Helper.default_subject.isEmpty())
                            Helper.subject = Helper.default_subject;
                        startActivityForResult(myIntent,MINIMALISTIC_CODE);
                    }else if(Helper.cb_email_default ) {
                        Intent myIntent = new Intent(view.getContext(), SendEmail.class);
                        if(!Helper.default_content.isEmpty())
                            myIntent.putExtra("BODY",Helper.default_content);
                        startActivityForResult(myIntent,MINIMALISTIC_CODE);
                    }else{
                        Intent data2 = new Intent();
                        data2.putExtra("RESULT","SENT");
                        setResult(RESULT_OK,data2);
                        finish();
                    }
                }else {
                    Intent myIntent = new Intent(view.getContext(), SendEmail.class);
                    startActivityForResult(myIntent,MINIMALISTIC_CODE);
                }

            }
        });

        btn_play = findViewById(R.id.btn_play);

        btn_play.setOnClickListener(new View.OnClickListener() {
            boolean mStartPlaying = true;

            @Override
            public void onClick(View v) {
                int icon = 0;
                if (mStartPlaying) {
                    btn_delete.setEnabled(false);

                    mainAtv.startPlaying(btn_play);
                    icon = R.drawable.ic_pause_circle_outline_black_24dp;
                } else {
                    btn_delete.setEnabled(true);

                    mainAtv.stopPlaying();
                    icon = R.drawable.ic_play_circle_outline_black_24dp;
                }
                mStartPlaying = !mStartPlaying;



                btn_play.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));

            }
        });



        btn_replay = findViewById(R.id.btn_replay);

        btn_replay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                int icon = 0;
//                if (mStartPlaying) {
//                    return;
//                } else {
//
//                    mainAtv.stopPlaying();
//                    icon = R.drawable.ic_play_circle_outline_black_24dp;
//                }
//                mStartPlaying = !mStartPlaying;
//
//
//                btn_play.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
                finish();

            }
        });

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(SelectSubject.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(SelectSubject.this);
                }
                builder.setTitle(R.string.delete_audio)
                        .setMessage(R.string.are_you_sure_to_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int icon = R.drawable.ic_play_circle_outline_black_24dp;
                                btn_play.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
                                stopAndDelete();
                                mStartPlaying = !mStartPlaying;
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });


        btn_settings = findViewById(R.id.btn_settings);

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectSubject.this, emailSettings.class);
                startActivity(intent);
            }
        });

        btn_openRecipient = findViewById(R.id.btn_openRecipient);

        btn_openRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Helper.IS_FOR_RECIP_SELECT = false;
//                Intent intent = new Intent(SelectSubject.this, MainActivity.class);
//                startActivityForResult(intent,RECPIENT_SELECTION_REQUEST);
                finish();
            }
        });
        refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RECPIENT_SELECTION_REQUEST){
            if(resultCode == RESULT_OK && data != null){
                refresh();
            }
        }else if(requestCode == MINIMALISTIC_CODE){
            Intent data2 = new Intent();
            data2.putExtra("RESULT","SENT");
            setResult(RESULT_OK,data2);
            finish();
        }
    }

    private void refresh() {
        if (namePerson != null)
            namePerson.setText(Helper.userName);
        if (emailPerson != null)
            emailPerson.setText(Helper.email);
        if (emailPerson != null)
            emailPerson.setText(Helper.email);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        refresh();
    }

    protected void stopAndDelete(){

        mainAtv.stopPlaying();
        if(Helper.mp3File != null)
            Helper.mp3File.delete();
        Helper.RESET = true;
        MainActivity.CURRENT_COUNT = 0;
        Toast.makeText(SelectSubject.this, R.string.audio_deleted, Toast.LENGTH_SHORT).show();
        finish();
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




    @Override
    public void onResume()
    {
        super.onResume();
        if(Helper.IS_FAST_BACK){
            Helper.IS_FAST_BACK = false;
            finish();
        }

        dbHealper = new DBHealper(this);
        emailSubDatalist = dbHealper.getDataSubject();
        myCustomAdapter = new CustomAdapterSelectSubject(this, R.layout.layoutsubjects, emailSubDatalist);
        final ListView listView = (ListView) findViewById(R.id.selectsubject_list);
        listView.setAdapter(myCustomAdapter);
    }


    public void goBack(View view) {
        finish();
    }
}
