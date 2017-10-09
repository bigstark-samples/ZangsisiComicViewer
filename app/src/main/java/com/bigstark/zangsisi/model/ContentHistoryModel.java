package com.bigstark.zangsisi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.Date;

/**
 * Created by gangdaegyu on 2017. 10. 9..
 */
@Entity(
        tableName = "content_history",
        foreignKeys = @ForeignKey(
                entity = ContentModel.class,
                parentColumns = "content_id",
                childColumns = "content_id"
        ),
        primaryKeys = {"content_id", "date"}
)
public class ContentHistoryModel {

    @ColumnInfo(name = "content_id")
    private long contentId;


    @ColumnInfo(name = "date")
    private Date date;

    public ContentHistoryModel(long contentId, Date date) {
        this.contentId = contentId;
        this.date = date;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
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

        ContentHistoryModel that = (ContentHistoryModel) o;

        if (contentId != that.contentId) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (contentId ^ (contentId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContentHistoryModel{" +
                "contentId=" + contentId +
                ", date=" + date +
                '}';
    }
}
