package com.bigstark.zangsisi.app.comic;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bigstark.zangsisi.model.ComicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicViewHolder> {

    private List<ComicModel> comics = new ArrayList<>();
    private List<ComicModel> comicHistories = new ArrayList<>();


    public void setItem(List<ComicModel> comics, List<ComicModel> comicHistories) {
        this.comics.clear();
        this.comics.addAll(comics);

        this.comicHistories.clear();
        this.comicHistories.addAll(comicHistories);

        notifyDataSetChanged();
    }


    public void updateHistory(List<ComicModel> comicHistories) {
        this.comicHistories.clear();
        this.comicHistories.addAll(comicHistories);

        notifyDataSetChanged();
    }


    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ComicViewHolder.newInstance(parent);
    }


    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        holder.bindItem(comics.get(position), comicHistories.contains(comics.get(position)));
    }


    @Override
    public int getItemCount() {
        return comics.size();
    }
}
