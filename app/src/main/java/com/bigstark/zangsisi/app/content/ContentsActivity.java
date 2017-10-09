package com.bigstark.zangsisi.app.content;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.db.ComicDatabase;
import com.bigstark.zangsisi.model.ContentHistoryModel;
import com.bigstark.zangsisi.model.ContentModel;
import com.bigstark.zangsisi.model.EpisodeModel;
import com.bigstark.zangsisi.service.ZangsisiClient;
import com.bigstark.zangsisi.util.Defines;
import com.bigstark.zangsisi.util.SharedPreferenceUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ContentsActivity extends AppCompatActivity {

    private ImageButton btnRotate;
    private ViewPager vpContent;
    private ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_contents);

        btnRotate = findViewById(R.id.btn_rotate);
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(
                        getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ?
                                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                );
            }
        });

        vpContent = findViewById(R.id.vp_content);
        vpContent.setOffscreenPageLimit(3);

        adapter = new ContentAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);

        long episodeId = getIntent().getLongExtra(Defines.KEY_EPISODE_ID, 0);
        if (episodeId == 0) {
            finish();
            return;
        }
        EpisodeModel episode = ComicDatabase.getInstance().getComicDao().getEpisode(episodeId);
        List<ContentModel> contents = ComicDatabase.getInstance().getComicDao().getContents(episodeId);

        boolean isLeftDirection = SharedPreferenceUtil.get(Defines.KEY_PREF_DIRECTION + episode.getComicId(), false);
        if (isLeftDirection) {
            Collections.reverse(contents);
        }

        if (contents.size() == 0) {
            getContents(episodeId);
        } else {
            adapter.setItems(contents);
        }

        int currentPosition = isLeftDirection ? contents.size() - 1 : 0;
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(Defines.KEY_CURRENT_POSITION);
        }

        vpContent.setCurrentItem(currentPosition);


        ContentModel lastViewed = ComicDatabase.getInstance().getComicDao().getLastViewedContent(episodeId);
        if (lastViewed == null || savedInstanceState != null) {
            return;
        }

        final int lastViewedPosition = adapter.getItems().indexOf(lastViewed);

        new AlertDialog.Builder(this)
                .setMessage("최근 본 장면으로 이동하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vpContent.setCurrentItem(lastViewedPosition);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void getContents(final long episodeId) {
        ZangsisiClient.getInstance().getContents(episodeId, new ZangsisiClient.ZangsisiCallback<ContentModel>() {
            @Override
            public void onSuccess(List<ContentModel> items) {
                if (isFinishing()) {
                    return;
                }

                ComicDatabase.getInstance().getComicDao().insertContents(items);

                EpisodeModel episode = ComicDatabase.getInstance().getComicDao().getEpisode(episodeId);
                boolean isLeftDirection = SharedPreferenceUtil.get(Defines.KEY_PREF_DIRECTION + episode.getComicId(), false);
                if (isLeftDirection) {
                    Collections.reverse(items);
                }

                adapter.setItems(items);

                int currentPosition = isLeftDirection ? items.size() - 1 : 0;
                vpContent.setCurrentItem(currentPosition);
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Defines.KEY_CURRENT_POSITION, vpContent.getCurrentItem());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        ContentModel lastViewed = adapter.getItems().get(vpContent.getCurrentItem());
        ContentHistoryModel history = new ContentHistoryModel(lastViewed.getContentId(), new Date(System.currentTimeMillis()));
        ComicDatabase.getInstance().getComicDao().insertContentHistory(history);
        super.onDestroy();
    }
}
