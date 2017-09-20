package com.bigstark.zangsisi.app.episode;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bigstark.zangsisi.model.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {


    private List<EpisodeModel> episodes = new ArrayList<>();


    public void setItems(List<EpisodeModel> episodes) {
        this.episodes.clear();
        this.episodes.addAll(episodes);
        notifyDataSetChanged();
    }


    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EpisodeViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
        holder.bindItem(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }
}
