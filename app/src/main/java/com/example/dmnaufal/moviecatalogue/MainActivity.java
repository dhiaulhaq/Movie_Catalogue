package com.example.dmnaufal.moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>{

    ImageView ivPoster;
    ListView listView;
    MovieAdapter adapter;
    EditText etTitle;
    Button btnSearch;


    static final String EXTRAS_FILM = "EXTRAS_FILM";


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MovieAdapter(this);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MovieItem item = (MovieItem)parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);

                intent.putExtra(MovieDetailActivity.EXTRA_TITLE, item.getMov_title());
                intent.putExtra(MovieDetailActivity.EXTRA_OVERVIEW, item.getMov_synopsis());
                intent.putExtra(MovieDetailActivity.EXTRA_POSTER_JPG, item.getMov_poster());
                intent.putExtra(MovieDetailActivity.EXTRA_RATE, item.getMov_rate());
                intent.putExtra(MovieDetailActivity.EXTRA_RATE_COUNT, item.getMov_rate_count());
                intent.putExtra(MovieDetailActivity.EXTRA_RELEASE_DATE, item.getMov_releasedate());


                startActivity(intent);


            }
        });

        etTitle   = (EditText)findViewById(R.id.et_cari_film);
        ivPoster  = (ImageView)findViewById(R.id.poster_mov);
        btnSearch = (Button)findViewById(R.id.btn_cari_film);
        btnSearch.setOnClickListener(myListener);

        String title = etTitle.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM,title);

        getLoaderManager().initLoader(0,bundle,this);

    }


    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {

        String titleMovie = "";
        if (args != null){
            titleMovie = args.getString(EXTRAS_FILM);
        }

        return new MovieAsyncTaskLoader(this, titleMovie);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            String titleMovie = etTitle.getText().toString();

            if (TextUtils.isEmpty(titleMovie))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_FILM, titleMovie);
            getLoaderManager().restartLoader(0, bundle,MainActivity.this);
        }
    };
}
