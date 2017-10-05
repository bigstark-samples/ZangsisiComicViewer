package com.bigstark.zangsisi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

/**
 * Created by bigstark on 2017. 9. 20..
 */
@Entity(tableName = "episode",
        foreignKeys = @ForeignKey(
            entity = ComicModel.class,
            parentColumns = "comic_id",
            childColumns = "comic_id"
        )
)
public class EpisodeModel {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "episode_id")
    private long episodeId;

    @ColumnInfo(name = "comic_id")
    private long comicId;

    @ColumnInfo(name = "episode")
    private String title;


    public EpisodeModel(long episodeId, long comicId, String title) {
        this.episodeId = episodeId;
        this.comicId = comicId;
        this.title = title;
    }


    public long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    public long getComicId() {
        return comicId;
    }

    public void setComicId(long comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpisodeModel that = (EpisodeModel) o;

        if (episodeId != that.episodeId) return false;
        if (comicId != that.comicId) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (episodeId ^ (episodeId >>> 32));
        result = 31 * result + (int) (comicId ^ (comicId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EpisodeModel{" +
                "id=" + episodeId +
                ", comicId=" + comicId +
                ", title='" + title + '\'' +
                '}';
    }
}
