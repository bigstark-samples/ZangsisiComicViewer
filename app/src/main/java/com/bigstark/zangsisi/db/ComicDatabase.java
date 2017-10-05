package com.bigstark.zangsisi.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.util.Log;

import com.bigstark.zangsisi.ZangsisiApplication;
import com.bigstark.zangsisi.model.ComicModel;
import com.bigstark.zangsisi.model.ContentModel;
import com.bigstark.zangsisi.model.EpisodeHistoryModel;
import com.bigstark.zangsisi.model.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2017. 9. 20..
 */

@Database(
        entities = {
                ComicModel.class,
                EpisodeModel.class,
                ContentModel.class,
                EpisodeHistoryModel.class
        },
        version = 2
)
@TypeConverters({Converters.class})
public abstract class ComicDatabase extends RoomDatabase {

    private static final String DB_NAME = "zangsisi";
    private volatile static ComicDatabase instance;

    public static ComicDatabase getInstance() {
        if (instance == null) {
            synchronized (ComicDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            ZangsisiApplication.getInstance(),
                            ComicDatabase.class,
                            DB_NAME
                    ).allowMainThreadQueries().build();
                }
            }
        }

        return instance;
    }


    public abstract ComicDao getComicDao();


    public void addOrUpdateComics(List<ComicModel> comics) {
        List<ComicModel> previous = getComicDao().getComics();

        // deletes
        List<ComicModel> deletes = new ArrayList<>();
        deletes.addAll(previous);
        deletes.removeAll(comics);


        // inserts and updates
        List<ComicModel> updates = new ArrayList<>();
        List<ComicModel> inserts = new ArrayList<>();
        comics.removeAll(previous);
        for (ComicModel comic : comics) {
            if (getComicDao().getComic(comic.getComicId()) == null) {
                if (inserts.contains(comic)) {
                    continue;
                }

                inserts.add(comic);
            } else {
                updates.add(comic);
            }
        }

        getComicDao().deleteComics(deletes);
        getComicDao().insertComics(inserts);
        getComicDao().updateComics(updates);
    }


    public void addOrUpdateEpisodes(long comicId, List<EpisodeModel> episodes) {
        List<EpisodeModel> previous = getComicDao().getEpisodes(comicId);

        // deletes
        List<EpisodeModel> deletes = new ArrayList<>();
        deletes.addAll(previous);
        deletes.removeAll(episodes);


        // inserts and updates
        List<EpisodeModel> updates = new ArrayList<>();
        List<EpisodeModel> inserts = new ArrayList<>();
        episodes.removeAll(previous);
        for (EpisodeModel episode : episodes) {
            if (getComicDao().getComic(episode.getEpisodeId()) == null) {
                if (inserts.contains(episode)) {
                    continue;
                }

                inserts.add(episode);
            } else {
                updates.add(episode);
            }
        }



        getComicDao().deleteEpisodes(deletes);
        getComicDao().insertEpisodes(inserts);
        getComicDao().updateEpisodes(updates);
    }


}
