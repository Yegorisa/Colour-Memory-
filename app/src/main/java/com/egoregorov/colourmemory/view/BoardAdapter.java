package com.egoregorov.colourmemory.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.egoregorov.colourmemory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 04.12.2017.
 */

public class BoardAdapter extends BaseAdapter {
    private Context mContext;
    private List<ImageView> mCardViews;

    public BoardAdapter(Context c) {
        mContext = c;
        mCardViews = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            ImageView imageView;
            // if it's not recycled, initialize some attributes
            int imageHeight = (365);
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, imageHeight));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(4, 4, 4, 4);

            imageView.setImageResource(R.drawable.card_bg);
            mCardViews.add(imageView);
        }
    }

    public int getCount() {
        return mCardViews.size();
    }

    public Object getItem(int position) {
        return mCardViews.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mCardViews.get(position);
    }
}
