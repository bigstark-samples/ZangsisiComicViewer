package com.bigstark.zangsisi.app.episode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.db.ComicDatabase;
import com.bigstark.zangsisi.model.EpisodeModel;
import com.bigstark.zangsisi.service.ZangsisiClient;
import com.bigstark.zangsisi.util.Defines;

import java.util.List;

public class EpisodeActivity extends AppCompatActivity {

    private EpisodeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        final long comicId = getIntent().getLongExtra(Defines.KEY_COMIC_ID, 0);
        if (comicId == 0) {
            finish();
            return;
        }

        RecyclerView rvEpisode = findViewById(R.id.rv_episode);
        rvEpisode.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EpisodeAdapter();
        rvEpisode.setAdapter(adapter);

        List<EpisodeModel> episodes = ComicDatabase.getInstance().getComicDao().getEpisodes(comicId);
        adapter.setItems(episodes);

        ZangsisiClient.getInstance().getEpisodes(comicId, new ZangsisiClient.ZangsisiCallback<EpisodeModel>() {
            @Override
            public void onSuccess(List<EpisodeModel> items) {
                if (isFinishing()) {
                    return;
                }

                ComicDatabase.getInstance().addOrUpdateEpisodes(comicId, items);
                adapter.setItems(ComicDatabase.getInstance().getComicDao().getEpisodes(comicId));
            }

            @Override
            public void onFail() {
                if (isFinishing()) {
                    return;
                }

                finish();
            }
        });
    }


}
