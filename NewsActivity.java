package com.example.daniel.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private NewsAdapter mAdapter;
    private LoaderManager mLoader;
    private SwipeRefreshLayout mSwipe;
    private TextView mEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView listView = findViewById(R.id.list_view);

        mAdapter = new NewsAdapter(this);
        listView.setAdapter(mAdapter);

        mEmpty = findViewById(R.id.empty_view);
        listView.setEmptyView(mEmpty);

        mSwipe = findViewById(R.id.swipe_refresh);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = mAdapter.getItem(i);
                String url = news.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        android.support.v4.app.LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mLoader == null)
                    loadNews();
                else
                    updateNews();
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mSwipe.setRefreshing(false);

        ProgressBar progressBar = findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.INVISIBLE);

        TextView empty = findViewById(R.id.empty_view);
        empty.setText(R.string.empty);

        mAdapter.clear();

        if (news != null) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    private void loadNews() {
        if (isNetworkAvailable()) {
            mLoader = android.support.v4.app.LoaderManager.getInstance(this);
            mLoader.initLoader(1, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(GONE);
            mEmpty.setText(R.string.no_internet);
        }
    }

    private void updateNews() {
        if (isNetworkAvailable()) {
            mLoader.restartLoader(1, null, NewsActivity.this);
        } else {
            mSwipe.setRefreshing(false);
            mEmpty.setText(R.string.no_internet);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}
