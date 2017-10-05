package com.bigstark.zangsisi.app.episode;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.ZangsisiApplication;
import com.bigstark.zangsisi.app.content.ContentsActivity;
import com.bigstark.zangsisi.db.ComicDatabase;
import com.bigstark.zangsisi.model.EpisodeHistoryModel;
import com.bigstark.zangsisi.model.EpisodeModel;
import com.bigstark.zangsisi.util.Defines;

import java.util.Date;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class EpisodeViewHolder extends RecyclerView.ViewHolder {

    public static EpisodeViewHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_item, parent, false);
        return new EpisodeViewHolder(itemView);
    }

    private EpisodeModel episode;

    private View viewBackground;
    private TextView tvTitle;


    EpisodeViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (episode == null) {
                    return;
                }


                ComicDatabase.getInstance().getComicDao().insertEpisodeHistory(new EpisodeHistoryModel(episode.getEpisodeId(), new Date(System.currentTimeMillis())));


                Intent intent = new Intent(view.getContext(), ContentsActivity.class);
                intent.putExtra(Defines.KEY_EPISODE_ID, episode.getEpisodeId());
                view.getContext().startActivity(intent);
            }
        });
        tvTitle = itemView.findViewById(R.id.tv_title);
        viewBackground = itemView.findViewById(R.id.background);
    }

    public void bindItem(EpisodeModel episode, boolean existHistory) {
        this.episode = episode;

        Context context = ZangsisiApplication.getInstance();
        tvTitle.setText(episode.getTitle());
        tvTitle.setTextColor(
                existHistory ?
                        ContextCompat.getColor(context, R.color.colorTextSelected) :
                        ContextCompat.getColor(context, android.R.color.black)
        );

        viewBackground.setBackgroundColor(
                existHistory ?
                        ContextCompat.getColor(context, R.color.colorSelected) :
                        ContextCompat.getColor(context, android.R.color.white)
        );
    }

}
