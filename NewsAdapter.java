package com.example.daniel.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsAdapter extends ArrayAdapter<News>{


    public NewsAdapter(Context context){
        super(context, -1, new ArrayList<News>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News current = getItem(position);
        TextView title = (TextView) view.findViewById(R.id.title_text_view);
        title.setText(current.getTitle());
        TextView author = (TextView) view.findViewById(R.id.author_text_view);
        author.setText(current.getAuthor());
        TextView section = (TextView) view.findViewById(R.id.section_text_view);
        section.setText(current.getSection());
        TextView date = (TextView) view.findViewById(R.id.date_text_view);
        date.setText(current.getDate());

        return view;
    }
}