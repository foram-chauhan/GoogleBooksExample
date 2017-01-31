package com.example.dell.googlebooksexample;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>, View.OnClickListener {

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /* Button to search for a book*/
    private Button btnSearch;

    /*EditText to collect the query*/
    private EditText searchBook;

    /*String query global variable*/
    private String query;
    /*ListView global variable*/
    private ListView bookListView;
    /*BookAdapter global variable*/
    private BookAdapter bookAdapter;
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * URL for Book data from the Google dataset
     */
    private String GOOGLE_BOOKS_REQUEST_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        bookListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of books
        bookAdapter = new BookAdapter(
                this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);
        // Find a reference to the {@link Button} in the layout
        btnSearch = (Button)findViewById(R.id.btnSearch);
        //Set listener for the click event
        btnSearch.setOnClickListener(this);


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.v("Loader State","on Create Loader");
        return new BookLoader(this, GOOGLE_BOOKS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous book data
        bookAdapter.clear();
        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            bookAdapter.addAll(books);
            bookAdapter.notifyDataSetChanged();
        }

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_books);
        Log.v("Loader State","on Load Finished");
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        bookAdapter.clear();
        Log.v("Loader State","on Loader Reset");
    }

    @Override
    public void onClick(View view) {

        searchBook = (EditText)findViewById(R.id.searchBook);
        query = searchBook.getText().toString();

        GOOGLE_BOOKS_REQUEST_URL  = "https://www.googleapis.com/books/v1/volumes?q="+query+"&maxResults=3";
        Log.v("url",GOOGLE_BOOKS_REQUEST_URL);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }else{
            //Otherwise,display error
            //First hide loading indicatorso error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookAdapter.clear();
        //bookListView.invalidateViews();
        bookListView.setAdapter(bookAdapter);
        //((BookAdapter)bookListView.getAdapter()).notifyDataSetChanged();
        bookListView.setEmptyView(mEmptyStateTextView);


    }

}
