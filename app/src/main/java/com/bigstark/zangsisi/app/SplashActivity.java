package com.bigstark.zangsisi.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.app.comic.ComicListActivity;
import com.bigstark.zangsisi.db.ComicDatabase;
import com.bigstark.zangsisi.model.ComicModel;
import com.bigstark.zangsisi.service.ZangsisiClient;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ZangsisiClient.getInstance().getComics(new ZangsisiClient.ZangsisiCallback<ComicModel>() {
            @Override
            public void onSuccess(List<ComicModel> items) {
                if (isFinishing()) {
                    return;
                }

                ComicDatabase.getInstance().addOrUpdateComics(items);

                Intent intent = new Intent(SplashActivity.this, ComicListActivity.class);
                startActivity(intent);
                finish();
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
