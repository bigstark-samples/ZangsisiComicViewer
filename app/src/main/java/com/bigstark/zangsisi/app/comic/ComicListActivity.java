package com.bigstark.zangsisi.app.comic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private RecyclerView rvComic;
    private ComicAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);

        rvComic = findViewById(R.id.rv_comic);
        rvComic.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ComicAdapter();
        rvComic.setAdapter(adapter);


        AppCompatSpinner acsComic = findViewById(R.id.acs_comic);
        acsComic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<ComicModel> comicHistories = ComicDatabase.getInstance().getComicDao().getComicHistory();
                List<ComicModel> comics;
                switch (i) {
                    case 0:
                        comics = ComicDatabase.getInstance().getComicDao().getComics();
                        break;

                    case 1:
                        comics = comicHistories;
                        break;

                    case 2:
                        comics = ComicDatabase.getInstance().getComicDao().getSerialComics();
                        break;

                    case 3:
                        comics = ComicDatabase.getInstance().getComicDao().getFinishedComics();
                        break;

                    default:
                        comics = ComicDatabase.getInstance().getComicDao().getComics();
                }

                adapter.setItem(comics, comicHistories);
                rvComic.smoothScrollToPosition(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.updateHistory(ComicDatabase.getInstance().getComicDao().getComicHistory());
    }
}
