package com.bigstark.zangsisi.app.content;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewParent;
import android.view.WindowManager;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.db.ComicDatabase;
import com.bigstark.zangsisi.model.ContentModel;
import com.bigstark.zangsisi.service.ZangsisiClient;
import com.bigstark.zangsisi.util.Defines;

import java.util.List;

public class ContentsActivity extends AppCompatActivity {

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
        getSupportActionBar().hide();

        vpContent = findViewById(R.id.vp_content);
        vpContent.setOffscreenPageLimit(3);

        adapter = new ContentAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);

        long episodeId = getIntent().getLongExtra(Defines.KEY_EPISODE_ID, 0);
        if (episodeId == 0) {
            finish();
            return;
        }

        List<ContentModel> contents = ComicDatabase.getInstance().getComicDao().getContents(episodeId);
        if (contents.size() == 0) {
            getContents(episodeId);
        } else {
            adapter.setItems(contents);
        }

        int currentPosition = 0;
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(Defines.KEY_CURRENT_POSITION);
        }


        vpContent.setCurrentItem(currentPosition);
    }

    private void getContents(long episodeId) {
        ZangsisiClient.getInstance().getContents(episodeId, new ZangsisiClient.ZangsisiCallback<ContentModel>() {
            @Override
            public void onSuccess(List<ContentModel> items) {
                if (isFinishing()) {
                    return;
                }

                ComicDatabase.getInstance().getComicDao().insertContents(items);
                adapter.setItems(items);
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
}
