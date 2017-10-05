package com.bigstark.zangsisi.app.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.bigstark.zangsisi.model.ContentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigstark on 2017. 9. 21..
 */

public class ContentAdapter extends FragmentStatePagerAdapter {

    private List<ContentModel> contents = new ArrayList<>();


    public ContentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setItems(List<ContentModel> contents) {
        this.contents.clear();
        this.contents.addAll(contents);
        Log.v("TAG", "contents : " + contents);
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return ContentFragment.newInstance(contents.get(position).getImageUrl());
    }

    @Override
    public int getCount() {
        return contents.size();
    }
}
