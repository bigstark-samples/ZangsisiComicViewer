package com.bigstark.zangsisi.app.comic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.db.ComicDatabase;
import com.bigstark.zangsisi.model.ComicModel;

import java.util.List;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class ComicListActivity extends AppCompatActivity {


    private ComicAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);

        AppCompatSpinner acsComic = findViewById(R.id.acs_comic);
        acsComic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<ComicModel> comics;
                switch (i) {
                    case 1:
                        comics = ComicDatabase.getInstance().getComicDao().getSerialComics();
                        break;

                    case 2:
                        comics = ComicDatabase.getInstance().getComicDao().getFinishedComics();
                        break;

                    default:
                        comics = ComicDatabase.getInstance().getComicDao().getComics();
                }
                adapter.setItem(comics);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RecyclerView rvComic = findViewById(R.id.rv_comic);
        rvComic.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ComicAdapter();
        rvComic.setAdapter(adapter);
    }
}
