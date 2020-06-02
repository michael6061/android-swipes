package com.project.android_swipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageViewAdapter extends PagerAdapter {
    private List<Integer> mImageId;
    private List<String> mCardTitle;
    private List<String> mCardText;
    private Context mContext;

    public ImageViewAdapter(List<Integer> imageId, List<String> cardTitle,List<String> cardText , Context context) {
        mImageId = imageId;
        mCardTitle = cardTitle;
        mCardText = cardText;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageId.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_pager_item, container, false);
        final Integer imageId = mImageId.get(position);
        final String title = mCardTitle.get(position);
        final String text = mCardText.get(position);
        setViews(view,imageId, title, text);
        container.addView(view);
        return view;
    }

    private void setViews(View parent, Integer imageId, String title, String text) {
        ImageView view1 = parent.findViewById(R.id.card_image);
        TextView view2 = parent.findViewById(R.id.card_title_text);
        TextView view3 = parent.findViewById(R.id.card_body_text);

        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
//        if (text == null && imageId == null) {
//            view2.setVisibility(View.VISIBLE);
//            view2.setText("No Text or Image Found");
//        }
//        else {
            if (imageId != null) {
                view1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(imageId).into(view1);
            }
            if (title != null) {
                view2.setVisibility(View.VISIBLE);
                view2.setText(title);
            }
            if (text != null) {
                view3.setVisibility(View.VISIBLE);
                view3.setText(text);
            }
//        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
