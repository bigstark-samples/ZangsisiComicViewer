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
    private long id;

    @ColumnInfo(name = "title")
    private String title;


    @ColumnInfo(name = "is_finished")
    private boolean isFinished;


    public ComicModel(long id, String title, boolean isFinished) {
        this.id = id;
        this.title = title;
        this.isFinished = isFinished;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

        if (id != that.id) return false;
        if (isFinished != that.isFinished) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (isFinished ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ComicModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
