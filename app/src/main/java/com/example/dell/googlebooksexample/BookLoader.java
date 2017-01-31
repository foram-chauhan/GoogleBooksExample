package com.example.dell.googlebooksexample;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by DELL on 26-01-2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    /** Tag for log messages */
    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public BookLoader(Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v("Loader State","Loader starts loading");
        super.onStartLoading();
    }

    @Override
    public List<Book> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        //perform network request, parse the response andextract a list of books.
        List<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;

    }

}
