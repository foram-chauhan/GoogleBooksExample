package com.example.dell.googlebooksexample;

/**
 * Created by DELL on 26-01-2017.
 */

public class Book {
    //Title of the Book
    public final String title;

    //Publisher of the Book
    public final String publisher;

    //pages of the book
    public final String pageCount;

    //authors of the book
    public final String authors;

    //public constructor
    public Book(String mTitle, String mPublisher, String mPageCount, String mAuthors) {
        title = mTitle;
        publisher = mPublisher;
        pageCount = mPageCount;
        authors =mAuthors;
    }

    //getter methods
    public String getPublisher()
    {
        return publisher;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getTitle() {
        return title;

    }
}
