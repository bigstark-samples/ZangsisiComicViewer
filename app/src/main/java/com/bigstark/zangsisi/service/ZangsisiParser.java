package com.bigstark.zangsisi.service;

import android.net.Uri;
import android.text.TextUtils;

import com.bigstark.zangsisi.model.ComicModel;
import com.bigstark.zangsisi.model.ContentModel;
import com.bigstark.zangsisi.model.EpisodeModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class ZangsisiParser {
    public static final String HOST_ZANGSISI = "zangsisi.net";

    public static final String CSS_QUERY_SERIAL_COMIC = "#manga-list a";
    public static final String CSS_QUERY_FINISHED_COMIC = ".tx-link";
    public static final String CSS_QUERY_EPISODES = "#post a";
    public static final String CSS_QUERY_CONTENTS = ".contents img";

    public static final String ATTR_HREF = "href";
    public static final String ATTR_SRC = "src";

    public static final String QUERY_P = "p";
    public static final String QUERY_PAGE_ID = "page_id";




    public static List<ComicModel> parseSerialComics(String html) {
        return parseComics(html, CSS_QUERY_SERIAL_COMIC, false);
    }

    public static List<ComicModel> parseFinishedComics(String html) {
        return parseComics(html, CSS_QUERY_FINISHED_COMIC, true);
    }


    private static List<ComicModel> parseComics(String html, String cssQuery, boolean isFinished) {
        Document doc = Jsoup.parse(html);
        Elements comicElements = doc.select(cssQuery);
        List<ComicModel> comics = new ArrayList<>(comicElements.size());
        for (Element element : comicElements) {
            String title = element.text();
            String href = element.attr(ATTR_HREF);
            Uri uri = Uri.parse(href);
            if (uri.getHost() != null && !uri.getHost().contains(HOST_ZANGSISI)) {
                continue;
            }

            long id = getId(uri);

            ComicModel comic = new ComicModel(id, title, isFinished);
            comics.add(comic);
        }

        return comics;
    }


    public static List<EpisodeModel> parseEpisodes(long comicId, String html) {
        Document doc = Jsoup.parse(html);
        Elements episodeElements = doc.select(CSS_QUERY_EPISODES);
        List<EpisodeModel> episodes = new ArrayList<>(episodeElements.size());
        for (Element element : episodeElements) {
            String title = element.text();
            String href = element.attr(ATTR_HREF);
            Uri uri = Uri.parse(href);
            if (uri.getHost() != null && !uri.getHost().contains(HOST_ZANGSISI)) {
                continue;
            }

            long id = getId(uri);

            EpisodeModel episode = new EpisodeModel(id, comicId, title);
            episodes.add(episode);
        }

        return episodes;
    }


    public static List<ContentModel> parseContents(long episodeId, String html) {
        Document doc = Jsoup.parse(html);
        Elements contentElements = doc.select(CSS_QUERY_CONTENTS);
        List<ContentModel> contents = new ArrayList<>(contentElements.size());
        for (Element element : contentElements) {
            String imageUrl = element.attr(ATTR_SRC);
            ContentModel content = new ContentModel(imageUrl, episodeId);
            contents.add(content);
        }

        return contents;
    }



    private static long getId(Uri uri) {
        String idString = uri.getQueryParameter(QUERY_P);
        if (TextUtils.isEmpty(idString)) {
            idString = uri.getQueryParameter(QUERY_PAGE_ID);
        }

        return Long.parseLong(idString);
    }

}
