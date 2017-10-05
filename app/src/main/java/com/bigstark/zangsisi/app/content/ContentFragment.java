package com.bigstark.zangsisi.app.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigstark.zangsisi.GlideApp;
import com.bigstark.zangsisi.R;
import com.bigstark.zangsisi.util.Defines;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by bigstark on 2017. 9. 21..
 */

public class ContentFragment extends Fragment {

    public static Fragment newInstance(String imageUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(Defines.KEY_IMAGE_URL, imageUrl);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PhotoView ivContent = view.findViewById(R.id.iv_content);

        String imageUrl = getArguments().getString(Defines.KEY_IMAGE_URL);

        GlideApp.with(this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(ivContent);
    }
}
