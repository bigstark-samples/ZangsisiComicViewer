package com.bigstark.zangsisi.app.comic;

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

    private View viewBackground;
    private TextView tvTitle;


    ComicViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EpisodeActivity.class);
                intent.putExtra(Defines.KEY_COMIC_ID, comic.getComicId());
                view.getContext().startActivity(intent);
            }
        });
        tvTitle = itemView.findViewById(R.id.tv_title);
        viewBackground = itemView.findViewById(R.id.background);
    }


    public void bindItem(ComicModel comic, boolean existHistory) {
        this.comic = comic;

        Context context = ZangsisiApplication.getInstance();
        tvTitle.setText(comic.getTitle());
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
