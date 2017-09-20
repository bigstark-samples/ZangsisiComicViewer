package com.bigstark.zangsisi.service;

import android.net.Uri;
import android.text.TextUtils;

import com.bigstark.zangsisi.model.ContentModel;
import com.bigstark.zangsisi.model.EpisodeModel;
import com.bigstark.zangsisi.util.Defines;
import com.bigstark.zangsisi.model.ComicModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class ZangsisiClient {

    private static final String SERVER_URL = "http://zangsisi.net/";

    private volatile static ZangsisiClient instance;

    public static ZangsisiClient getInstance() {
        if (instance == null) {
            synchronized (ZangsisiClient.class) {
                if (instance == null) {
                    instance = new ZangsisiClient();
                }
            }
        }

        return instance;
    }


    private ZangsisiService service;
    public ZangsisiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .build();

        service = retrofit.create(ZangsisiService.class);
    }


    public void getComics(final ZangsisiCallback<ComicModel> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback must not be null");
        }

        Call<ResponseBody> call = service.getHtml("");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String html = "";
                    try {
                        html = response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFail();
                    }

                    List<ComicModel> comics = new ArrayList<>();
                    comics.addAll(ZangsisiParser.parseSerialComics(html));
                    comics.addAll(ZangsisiParser.parseFinishedComics(html));
                    callback.onSuccess(comics);
                    return;
                }

                callback.onFail();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFail();
            }
        });
    }





    public void getEpisodes(final long comicId, final ZangsisiCallback<EpisodeModel> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback must not be null");
        }

        Call<ResponseBody> call = service.getHtml(Long.toString(comicId));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String html = "";
                    try {
                        html = response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFail();
                    }

                    List<EpisodeModel> episodes = new ArrayList<>();
                    episodes.addAll(ZangsisiParser.parseEpisodes(comicId, html));
                    callback.onSuccess(episodes);
                    return;
                }

                callback.onFail();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFail();
            }
        });

    }


    public void getContents(final long episodeId, final ZangsisiCallback<ContentModel> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback must not be null");
        }

        Call<ResponseBody> call = service.getHtml(Long.toString(episodeId));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String html = "";
                    try {
                        html = response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFail();
                    }

                    List<ContentModel> contents = new ArrayList<>();
                    contents.addAll(ZangsisiParser.parseContents(episodeId, html));
                    callback.onSuccess(contents);
                    return;
                }

                callback.onFail();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFail();
            }
        });
    }



    public interface ZangsisiCallback<T> {

        public void onSuccess(List<T> items);


        public void onFail();

    }


}
