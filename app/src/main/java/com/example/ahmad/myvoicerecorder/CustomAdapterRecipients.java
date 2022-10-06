package com.example.noman.myvoicerecorder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.noman.myvoicerecorder.Utils.Helper;

import java.util.ArrayList;

/**
 * Created by noman on 1/7/2018.
 */

public class CustomAdapterRecipients extends ArrayAdapter {
    private Context context;
    public static ArrayList<emailData> emailArray;

    public CustomAdapterRecipients(Context context, int textViewResourceId, ArrayList objects) {

        super(context, textViewResourceId, objects);
        this.context = context;
        emailArray = objects;
    }

    private class ViewHolder {
        TextView id;
        TextView name;
        TextView emailAddrress;
        TextView txtcolor;
        ImageButton imgName, imgEmail;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomAdapterRecipients.ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layoutitemsrecipients, null);
            holder = new CustomAdapterRecipients.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.emailAddrress = (TextView) convertView.findViewById(R.id.txt_email);
            holder.id = (TextView) convertView.findViewById(R.id.txt_idMain);
            holder.txtcolor = (TextView) convertView.findViewById(R.id.txt_color);
            holder.imgName = (ImageButton) convertView.findViewById(R.id.img_name);
            holder.imgEmail = (ImageButton) convertView.findViewById(R.id.img_email);
            convertView.setTag(holder);
        } else {
            holder = (CustomAdapterRecipients.ViewHolder) convertView.getTag();
        }
        emailData user = (emailData) getItem(position);
        holder.name.setText(user.getName());
        holder.emailAddrress.setText(user.getEmailAddress());
        holder.id.setText(user.getId());
        holder.name.setTag(position);
        holder.txtcolor.setTag(position);
        holder.emailAddrress.setTag(position);
        holder.imgEmail.setTag(position);
        holder.imgName.setTag(position);


        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailData data = emailArray.get((int)v.getTag());
                Helper.userName = data.getName();
                Helper.email = data.getEmailAddress();
                ((Recipients)context).selectItem();
            }
        });

        holder.emailAddrress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailData data = emailArray.get((int)v.getTag());
                Helper.userName = data.getName();
                Helper.email = data.getEmailAddress();
                ((Recipients)context).selectItem();

            }
        });
        holder.txtcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailData data = emailArray.get((int)v.getTag());
                Helper.userName = data.getName();
                Helper.email = data.getEmailAddress();
                ((Recipients)context).selectItem();
            }
        });



     final String testname = holder.name.getText().toString();
       final String testemail = holder.emailAddrress.getText().toString();
        final String testID = holder.id.getText().toString();
        holder.imgName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int index = (int) v.getTag();
                Helper.recipientToEdit = emailArray.get(index);
                ((Recipients)context).slideUpdateDialogUp();
            }
        });
        holder.imgEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int index = (int) v.getTag();
                Helper.recipientToEdit = emailArray.get(index);
                ((Recipients)context).slideUpdateDialogUp();

            }
        });


        if (position%6 == 0){
            holder.txtcolor.setBackgroundColor(Color.parseColor("#CE0005"));
        } else if (position%6 == 1){
            holder.txtcolor.setBackgroundColor(Color.parseColor("#E99034"));
        } else if (position%6 == 2){
            holder.txtcolor.setBackgroundColor(Color.parseColor("#F1C332"));
        } else if (position%6 == 3){
            holder.txtcolor.setBackgroundColor(Color.parseColor("#67A751"));
        } else if (position%6 == 4){
            holder.txtcolor.setBackgroundColor(Color.parseColor("#587FAA"));
        } else if (position%6 == 5){
            holder.txtcolor.setBackgroundColor(Color.parseColor("#A44F79"));
        }



        return convertView;

    }


}


