package com.example.noman.myvoicerecorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.noman.myvoicerecorder.Utils.Helper;

import java.util.ArrayList;

/**
 * Created by noman on 1/7/2018.
 */

public class CustomAdapterSelectSubject extends ArrayAdapter {
    private Context context;
    private ArrayList<emailSubjectData> emailSubjectData;

    public CustomAdapterSelectSubject(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);

        this.context = context;
        emailSubjectData = objects;

    }

    private class ViewHolder {
        TextView id;
        TextView subject;
        TextView txtcolor;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomAdapterSelectSubject.ViewHolder holder = null;


        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.layoutsubjects, null);

            holder = new CustomAdapterSelectSubject.ViewHolder();
            holder.subject = (TextView) convertView.findViewById(R.id.txt_subject);
            holder.id = (TextView) convertView.findViewById(R.id.txt_idSubject);
            holder.txtcolor = (TextView) convertView.findViewById(R.id.txt_color);





            convertView.setTag(holder);



        } else {
            holder = (CustomAdapterSelectSubject.ViewHolder) convertView.getTag();
        }
        emailSubjectData user = (emailSubjectData) getItem(position);
        holder.subject.setText(user.getSubject());
        holder.id.setText(user.getId());
        holder.subject.setTag(position);
        holder.txtcolor.setTag(position);

        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSubjectData data = emailSubjectData.get((int)v.getTag());
                Helper.subject = data.getSubject();
                ((SelectSubject)context).selectItem();
            }
        });


        holder.txtcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSubjectData data = emailSubjectData.get((int)v.getTag());
                Helper.subject = data.getSubject();
                ((SelectSubject)context).selectItem();
            }
        });





        if (position % 2 == 0) {
            holder.txtcolor.setBackground(context.getResources().getDrawable(R.drawable.bgtextgray));
        } else if (position % 2 == 1) {
            holder.txtcolor.setBackground(context.getResources().getDrawable(R.drawable.bgtextgray_dark));
        }

        return convertView;

    }


}


