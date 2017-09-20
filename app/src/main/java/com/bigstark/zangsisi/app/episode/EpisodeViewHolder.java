package com.bigstark.zangsisi.app.episode;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.app.content.ContentsActivity;
import com.bigstark.zangsisi.model.EpisodeModel;
import com.bigstark.zangsisi.util.Defines;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class EpisodeViewHolder extends RecyclerView.ViewHolder {

    public static EpisodeViewHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_item, parent, false);
        return new EpisodeViewHolder(itemView);
    }

    private EpisodeModel episode;
    private TextView tvTitle;


    EpisodeViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ContentsActivity.class);
                intent.putExtra(Defines.KEY_EPISODE_ID, episode.getId());
                view.getContext().startActivity(intent);
            }
        });
        tvTitle = itemView.findViewById(R.id.tv_title);
    }

    public void bindItem(EpisodeModel episode) {
        this.episode = episode;
        tvTitle.setText(episode.getTitle());
    }

}
