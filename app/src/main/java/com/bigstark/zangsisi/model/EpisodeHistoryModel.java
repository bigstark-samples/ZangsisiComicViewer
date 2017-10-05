package com.bigstark.zangsisi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.Date;

/**
 * Created by gangdaegyu on 2017. 10. 5..
 */
@Entity(
        tableName = "episode_history",
        foreignKeys = @ForeignKey(
                entity = EpisodeModel.class,
                parentColumns = "episode_id",
                childColumns = "episode_id"
        ),
        primaryKeys = {"episode_id", "date"}
)
public class EpisodeHistoryModel {


    @ColumnInfo(name = "episode_id")
    private long episodeId;


    @ColumnInfo(name = "date")
    private Date date;


    public EpisodeHistoryModel(long episodeId, Date date) {
        this.episodeId = episodeId;
        this.date = date;
    }


    public long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpisodeHistoryModel that = (EpisodeHistoryModel) o;

        if (episodeId != that.episodeId) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (episodeId ^ (episodeId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EpisodeHistoryModel{" +
                "episodeId=" + episodeId +
                ", date=" + date +
                '}';
    }
}
