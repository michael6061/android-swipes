package com.project.android_swipes;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
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
    private Context mContext;
    private List<RemoteCard> mRemoteCards;
    public ImageViewAdapter(List<RemoteCard> remoteCards , Context context) {
        mRemoteCards = remoteCards;
        mContext = context;
    }

    /**
     *
     * @return return the number of cards in out dataSource(viz. List of RemoteCard)
     */
    @Override
    public int getCount() {
        return mRemoteCards.size();
    }

    /**
     * tells if we are getting view from object
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    /**
     * get called when views needed to be displayed
     * it initialize the content of a card
     * gets called for multiple card multiple times
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_pager_item, container, false);
        setViews(view,position);
        container.addView(view);
        return view;
    }

    /**
     * Determine's if a View should be displayed or not
     * @param parent get the parent View reference
     * @param position accepts the position of card
     */
    private void setViews(View parent, int position) {
        ImageView view1 = parent.findViewById(R.id.card_image);
        TextView view2 = parent.findViewById(R.id.card_title_text);
        TextView view3 = parent.findViewById(R.id.card_body_text);
        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);

        final RemoteCard remoteCard = mRemoteCards.get(position);

            if (remoteCard.getImageBase64() != null) {
                view1.setVisibility(View.VISIBLE);
                Glide.with(mContext).asBitmap().load(remoteCard.getImageBase64()).placeholder(R.drawable.ic_launcher_background).into(view1);
            }
            if (remoteCard.getHeading() != null) {
                view2.setVisibility(View.VISIBLE);
                view2.setText(remoteCard.getHeading());
            }
            if (remoteCard.getBodyText() != null) {
                view3.setVisibility(View.VISIBLE);
                view3.setText(remoteCard.getBodyText());
            }
    }

    /**
     * get automatically called when view is not used
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
