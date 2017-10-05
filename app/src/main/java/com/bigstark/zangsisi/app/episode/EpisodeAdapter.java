package com.bigstark.zangsisi.app.episode;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.bigstark.zangsisi.model.EpisodeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {


    private List<EpisodeModel> episodes = new ArrayList<>();
    private List<EpisodeModel> episodeHistories = new ArrayList<>();


    public void setItems(List<EpisodeModel> episodes, List<EpisodeModel> episodeHistories) {
        this.episodes.clear();
        this.episodes.addAll(episodes);

        Collections.sort(this.episodes, new Comparator<EpisodeModel>() {
            @Override
            public int compare(EpisodeModel episodeModel, EpisodeModel t1) {
                return (int) (episodeModel.getEpisodeId() - t1.getEpisodeId());
            }
        });

        this.episodeHistories.clear();
        this.episodeHistories.addAll(episodeHistories);
        notifyDataSetChanged();
    }


    public void updateHistory(List<EpisodeModel> episodeHistories) {
        this.episodeHistories.clear();
        this.episodeHistories.addAll(episodeHistories);
        notifyDataSetChanged();
    }



    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EpisodeViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
        holder.bindItem(episodes.get(position), episodeHistories.contains(episodes.get(position)));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }
}
