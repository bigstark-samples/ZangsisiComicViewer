package com.bigstark.zangsisi.app.comic;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.app.episode.EpisodeActivity;
import com.bigstark.zangsisi.model.ComicModel;
import com.bigstark.zangsisi.util.Defines;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public class ComicViewHolder extends RecyclerView.ViewHolder {

    public static ComicViewHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_item, parent, false);
        return new ComicViewHolder(itemView);
    }

    private ComicModel comic;
    private TextView tvTitle;


    ComicViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EpisodeActivity.class);
                intent.putExtra(Defines.KEY_COMIC_ID, comic.getId());
                view.getContext().startActivity(intent);
            }
        });
        tvTitle = itemView.findViewById(R.id.tv_title);
    }


    public void bindItem(ComicModel comic) {
        this.comic = comic;
        tvTitle.setText(comic.getTitle());
    }
}
