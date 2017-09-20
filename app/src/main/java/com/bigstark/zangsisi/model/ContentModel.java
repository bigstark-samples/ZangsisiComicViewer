package com.bigstark.zangsisi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by bigstark on 2017. 9. 20..
 */
@Entity(
        tableName = "content",
        foreignKeys = @ForeignKey(
                entity = EpisodeModel.class,
                parentColumns = "id",
                childColumns = "episode_id"
        )
)
public class ContentModel {

    @PrimaryKey(autoGenerate = true)
    private long id;


    @ColumnInfo(name = "image_url")
    private String imageUrl;


    @ColumnInfo(name = "episode_id")
    private long episodeId;


    public ContentModel(String imageUrl, long episodeId) {
        this.imageUrl = imageUrl;
        this.episodeId = episodeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(long episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public String toString() {
        return "ContentModel{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", episodeId=" + episodeId +
                '}';
    }
}
