package com.example.ahmad.myvoicerecorder.Utils;

import com.example.ahmad.myvoicerecorder.emailData;
import com.example.ahmad.myvoicerecorder.emailSubjectData;

import java.io.File;

/**
 * Created by noman on 1/8/2018.
 */

public class Helper {

    public static final String PREFS_MODE = "prefs_mode";
    public static final String PREFS_DEFAULT_EMAIL = "prefs_d_em";
    public static final String PREFS_DEFAULT_SUBJECT = "prefs_d_sub";
    public static final String PREFS_DEFAULT_CONTENT = "prefs_d_con";
    public static final String PREFS_CB_CONTENT = "prefs_cb_con";
    public static final String PREFS_CB_SUBJECT = "prefs_cb_subj";
    public static final String PREFS_CB_FILE_NAME = "prefs_cb_name";




    public static String userName;
    public static String email;
    public static String subject;

    public static boolean IS_MINIMALISTIC = false;
    public static boolean IS_RECORDING = false;
    public static emailSubjectData subjectToEdit = null;
    public static emailData recipientToEdit = null;

    public static File mp3File = null;
    public static boolean RESET = false;
    public static boolean IS_FOR_RECIP_SELECT = false;
    public static boolean IS_FAST_BACK = false;
    public static String CURRENT_LANGUAGE = "English";
    public static boolean cb_email_default = false;
    public static boolean cb_subject_default = false;
    public static boolean cb_content_default = false;
    public static String default_file_name = "audiomessage";
    public static String default_subject = "";
    public static String default_content = "";
}
