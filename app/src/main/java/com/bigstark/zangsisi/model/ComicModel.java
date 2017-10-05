package com.bigstark.zangsisi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by bigstark on 2017. 9. 20..
 */
@Entity(tableName = "comic")
public class ComicModel {


    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "comic_id")
    private long comicId;

    @ColumnInfo(name = "title")
    private String title;


    @ColumnInfo(name = "is_finished")
    private boolean isFinished;


    public ComicModel(long comicId, String title, boolean isFinished) {
        this.comicId = comicId;
        this.title = title;
        this.isFinished = isFinished;
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComicModel that = (ComicModel) o;

        if (comicId != that.comicId) return false;
        if (isFinished != that.isFinished) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (comicId ^ (comicId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (isFinished ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ComicModel{" +
                "id=" + comicId +
                ", title='" + title + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
