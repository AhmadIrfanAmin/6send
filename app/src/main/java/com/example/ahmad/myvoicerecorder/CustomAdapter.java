package com.example.noman.myvoicerecorder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by noman on 1/2/2018.
 */

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<emailData> emailArray;
    public CustomAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context = context;
        emailArray = objects;

    }

    private class ViewHolder
    {
        TextView id;
        TextView name;
        TextView emailAddrress;
        TextView txtcolor;


        public RelativeLayout item_container;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;


        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layoutitems, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.emailAddrress = (TextView) convertView.findViewById(R.id.txt_email);
            holder.id = (TextView) convertView.findViewById(R.id.txt_id);
            holder.txtcolor = (TextView) convertView.findViewById(R.id.txt_color);
            holder.item_container =  convertView.findViewById(R.id.item_container);


            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        emailData user = (emailData) getItem(position);
        holder.name.setText(user.getName());
        holder.emailAddrress.setText(user.getEmailAddress());
        holder.id.setText(user.getId());
        holder.name.setTag(position);
        holder.txtcolor.setTag(position);
        holder.emailAddrress.setTag(position);


//        holder.name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                emailData data = emailArray.get((int)v.getTag());
//                Helper.userName = data.getName();
//                Helper.email = data.getEmailAddress();
//                ((Recipients)context).selectItem();
//            }
//        });
//
//        holder.emailAddrress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                emailData data = emailArray.get((int)v.getTag());
//                Helper.userName = data.getName();
//                Helper.email = data.getEmailAddress();
//                ((Recipients)context).selectItem();
//
//            }
//        });
//        holder.txtcolor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                emailData data = emailArray.get((int)v.getTag());
//                Helper.userName = data.getName();
//                Helper.email = data.getEmailAddress();
//                ((Recipients)context).selectItem();
//            }
//        });



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
