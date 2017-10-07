package com.bigstark.zangsisi.app.episode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.app.content.ContentsActivity;
import com.bigstark.zangsisi.db.ComicDatabase;
import com.bigstark.zangsisi.model.ComicModel;
import com.bigstark.zangsisi.model.EpisodeModel;
import com.bigstark.zangsisi.service.ZangsisiClient;
import com.bigstark.zangsisi.util.Defines;
import com.bigstark.zangsisi.util.SharedPreferenceUtil;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ComicModel comic = ComicDatabase.getInstance().getComicDao().getComic(comicId);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.ctl_episode);
        toolbarLayout.setTitle(comic.getTitle());


        Switch switchDirection = findViewById(R.id.switch_episode_direction);
        boolean isLeftDirection = SharedPreferenceUtil.get(Defines.KEY_PREF_DIRECTION + comicId, false);
        switchDirection.setChecked(isLeftDirection);
        switchDirection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferenceUtil.put(Defines.KEY_PREF_DIRECTION + comicId, isChecked);
            }
        });


        TextView btnLastEpisode = findViewById(R.id.btn_episode_last);
        btnLastEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EpisodeModel lastViewedEpisode = ComicDatabase.getInstance().getComicDao().getLastViewedEpisode(comicId);
                if (lastViewedEpisode == null) {
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), ContentsActivity.class);
                intent.putExtra(Defines.KEY_EPISODE_ID, lastViewedEpisode.getEpisodeId());
                startActivity(intent);
            }
        });


        RecyclerView rvEpisode = findViewById(R.id.rv_episode);
        rvEpisode.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EpisodeAdapter();
        rvEpisode.setAdapter(adapter);

        List<EpisodeModel> episodes = ComicDatabase.getInstance().getComicDao().getEpisodes(comicId);
        List<EpisodeModel> episodeHistories = ComicDatabase.getInstance().getComicDao().getEpisodeHistory();
        adapter.setItems(episodes, episodeHistories);

        ZangsisiClient.getInstance().getEpisodes(comicId, new ZangsisiClient.ZangsisiCallback<EpisodeModel>() {
            @Override
            public void onSuccess(List<EpisodeModel> items) {
                if (isFinishing()) {
                    return;
                }

                ComicDatabase.getInstance().addOrUpdateEpisodes(comicId, items);

                List<EpisodeModel> episodes = ComicDatabase.getInstance().getComicDao().getEpisodes(comicId);
                List<EpisodeModel> episodeHistories = ComicDatabase.getInstance().getComicDao().getEpisodeHistory();

                adapter.setItems(episodes, episodeHistories);
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

    @Override
    protected void onStart() {
        super.onStart();
        adapter.updateHistory(ComicDatabase.getInstance().getComicDao().getEpisodeHistory());
    }
}
