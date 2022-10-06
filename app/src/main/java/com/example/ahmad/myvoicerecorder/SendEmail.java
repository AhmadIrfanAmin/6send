package com.example.noman.myvoicerecorder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.noman.myvoicerecorder.Utils.Helper;


public class SendEmail extends AppCompatActivity {

    TextView txtSubject, namePerson, emailPerson;
    ImageButton btn_settings, btn_openAddSub, btn_openRecipient, btn_play, btn_replay, btn_delete;
    EditText etMailContent;
    public static String  mailBody;
    public static String subject;
    boolean mStartPlaying = true;
    ProgressBar progressBar;
    MainActivity mainAtv= new MainActivity();


    private static final int RECPIENT_SELECTION_REQUEST = 1001;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.containsKey("BODY")){
                subject = Helper.subject;
                if(subject.isEmpty()) {
                    String language = "1";
                    if(getLocale().equalsIgnoreCase("en")
                            || getLocale().equalsIgnoreCase("English")
                            ){
                        subject = "";

                    }else {
                        subject = "";
                    }
                }
                mailBody = bundle.getString("BODY","");
                email(SendEmail.this,Helper.email,subject,mailBody);                // send email

            }
        }

        setContentView(R.layout.activity_send_email);
        txtSubject = findViewById(R.id.txt_subjectTitle);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        txtSubject.setText(Helper.subject);
        subject = txtSubject.getText().toString();
        namePerson = findViewById(R.id.txt_name);
        emailPerson = findViewById(R.id.txt_email);
        namePerson.setText(Helper.userName);
        emailPerson.setText(Helper.email);
        btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendEmail.this, emailSettings.class);
                startActivity(intent);
            }
        });
        btn_openAddSub = findViewById(R.id.btn_openAddSub);
        btn_openAddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SendEmail.this, AddSubject.class);
//                startActivityForResult(intent,RECPIENT_SELECTION_REQUEST);
                finish();
            }
        });
        btn_openRecipient = findViewById(R.id.btn_openRecipient);
        btn_openRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMainActivity= new Intent(SendEmail.this, MainActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity, 0);
            }
        });
        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                int icon = R.drawable.ic_play_circle_outline_black_24dp;
//                btn_play.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
//
//                stopAndDelete();
//                mStartPlaying = !mStartPlaying;
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(SendEmail.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(SendEmail.this);
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
        btn_play = findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {

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
//                   return;
//                } else {
//
//                    mainAtv.stopPlaying();
//                    icon = R.drawable.ic_play_circle_outline_black_24dp;
//                }
//                mStartPlaying = !mStartPlaying;
//
//
//                btn_play.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), icon));
                Helper.IS_FAST_BACK = true;
                finish();
            }
        });
    }
    public String getLocale() {

        return getResources().getConfiguration().locale.getDisplayName();


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(etMailContent != null)
            outState.putString("BODY",etMailContent.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(etMailContent != null)
            etMailContent.setText(mailBody = savedInstanceState.getString("BODY"));
        if(namePerson != null)
            namePerson.setText(Helper.userName);
        if(emailPerson != null)
            emailPerson.setText(Helper.email);
        if(txtSubject != null)
            txtSubject.setText(Helper.subject);
    }

    public void email(Context context, String emailTo, String subject, String mailBody)
    {
        try {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailTo});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mailBody);
        //has to be an ArrayList
//      ArrayList<Uri> uris = new ArrayList<Uri>();
    //convert from paths to Android friendly Parcelable Uri's
//      for (String file : filePaths)
//      {
//          File fileIn = new File(file);
//          Uri u = Uri.fromFile(fileIn);
//          uris.add(u);
//      }
//      File fileIn = new File(filePath);

        Uri u = Uri.fromFile(Helper.mp3File);
        emailIntent.putExtra(Intent.EXTRA_STREAM, u);
        startActivityForResult(emailIntent,RECPIENT_SELECTION_REQUEST);

        } catch(Exception e) {
            System.out.println("is exception raises during sending mail" + e);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RECPIENT_SELECTION_REQUEST){
            if(resultCode == RESULT_OK && data != null){
//                Intent data2 = new Intent();
//                data2.putExtra("RESULT","SENT");
//                setResult(RESULT_OK,data2);
                refresh();
            }
        }
    }

    private void refresh() {
        try {
            if (namePerson != null)
                namePerson.setText(Helper.userName);
            if (emailPerson != null)
                emailPerson.setText(Helper.email);
            if (txtSubject != null)
                txtSubject.setText(Helper.subject);
            MainActivity.CURRENT_COUNT = 0;
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Intent data2 = new Intent();
            data2.putExtra("RESULT","SENT");
            setResult(RESULT_OK,data2);
            finish();
        }catch (IllegalStateException ignore){}
    }


    protected void stopAndDelete(){
        //File file = new File(mFileName);
        mainAtv.stopPlaying();
        if(Helper.mp3File != null)
            Helper.mp3File.delete();
        Helper.RESET =true;
        MainActivity.CURRENT_COUNT = 0;
        Intent intent =new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void sendEmail(View view) {
        etMailContent = findViewById(R.id.et_emailContent);
        Helper.subject = subject;
        mailBody = etMailContent.getText().toString();
        email(SendEmail.this,Helper.email,subject,mailBody);


    }


}



































































