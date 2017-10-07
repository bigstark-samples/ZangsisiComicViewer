package com.bigstark.zangsisi.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bigstark.zangsisi.model.ComicModel;
import com.bigstark.zangsisi.model.ContentModel;
import com.bigstark.zangsisi.model.EpisodeHistoryModel;
import com.bigstark.zangsisi.model.EpisodeModel;

import java.util.List;

/**
 * Created by bigstark on 2017. 9. 20..
 */
@Dao
public interface ComicDao {

    @Query("SELECT * FROM comic")
    List<ComicModel> getComics();


    @Query("SELECT * FROM comic WHERE is_finished = 1")
    List<ComicModel> getFinishedComics();


    @Query("SELECT * FROM comic WHERE is_finished = 0")
    List<ComicModel> getSerialComics();


    @Query("SELECT * FROM comic WHERE comic_id = :id")
    ComicModel getComic(long id);


    @Query("SELECT * FROM episode WHERE comic_id = :comicId")
    List<EpisodeModel> getEpisodes(long comicId);



    @Query("SELECT * FROM episode WHERE episode_id = :episodeId")
    EpisodeModel getEpisode(long episodeId);



    @Query("SELECT * FROM "
            + "(SELECT * FROM episode INNER JOIN episode_history ON episode_history.episode_id = episode.episode_id) as e "
            + "WHERE e.comic_id = :comicId ORDER BY e.date DESC LIMIT 1")
    EpisodeModel getLastViewedEpisode(long comicId);


    @Query("SELECT DISTINCT episode_id, comic_id, episode FROM (SELECT * FROM episode INNER JOIN episode_history ON episode_history.episode_id = episode.episode_id)")
    List<EpisodeModel> getEpisodeHistory();



    @Query("SELECT DISTINCT comic_id, title, is_finished FROM (SELECT * FROM comic as c "
            + "INNER JOIN "
            +   "(SELECT * FROM episode "
            +       "INNER JOIN episode_history as h "
            +       "ON h.episode_id = episode.episode_id) "
            +   "as e "
            + "ON e.comic_id = c.comic_id)")
    List<ComicModel> getComicHistory();



    @Query("SELECT * FROM content WHERE episode_id = :episodeId")
    List<ContentModel> getContents(long episodeId);


    @Insert
    void insertComics(List<ComicModel> comics);


    @Update
    void updateComics(List<ComicModel> comics);


    @Delete
    void deleteComics(List<ComicModel> comics);


    @Insert
    void insertEpisodes(List<EpisodeModel> episodes);



    @Update
    void updateEpisodes(List<EpisodeModel> episode);


    @Delete
    void deleteEpisodes(List<EpisodeModel> episode);



    @Insert
    void insertEpisodeHistory(EpisodeHistoryModel history);


    @Insert
    void insertContents(List<ContentModel> contents);
}
