package com.bigstark.zangsisi.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bigstark.zangsisi.model.ComicModel;
import com.bigstark.zangsisi.model.ContentModel;
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


    @Query("SELECT * FROM comic WHERE id = :id")
    ComicModel getComic(long id);


    @Query("SELECT * FROM episode WHERE comic_id = :comicId")
    List<EpisodeModel> getEpisodes(long comicId);


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
    void insertContents(List<ContentModel> contents);
}
