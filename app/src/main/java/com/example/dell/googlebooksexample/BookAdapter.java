package com.example.dell.googlebooksexample;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DELL on 26-01-2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context,0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //check if the existing view is being reused , otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list,parent,false);
        }

        Book currentBook =getItem(position);

        //Find the Textview in the list.
        TextView title = (TextView)listItemView.findViewById(R.id.titleView);
        String titleForView = currentBook.getTitle();
        title.setText(titleForView);

        //Find the Textview in the list.
        TextView subTitle = (TextView)listItemView.findViewById(R.id.subtitleView);
        String subtitleForView = currentBook.getPublisher();
        subTitle.setText(subtitleForView);

        //Find the Textview in the list.
        TextView pagecount = (TextView)listItemView.findViewById(R.id.pageView);
        String pageForView = currentBook.getPageCount();
        pagecount.setText(pageForView);

        //Find the Textview in the list.
        TextView authors = (TextView)listItemView.findViewById(R.id.authorView);
        String authorForView = currentBook.getAuthors();
        authors.setText(authorForView);


        return listItemView;
    }
}
