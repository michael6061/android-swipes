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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
        ImageView cardImageView = parent.findViewById(R.id.card_image);
        TextView cardTitleTextView = parent.findViewById(R.id.card_title_text);
        TextView cardBodyTextView = parent.findViewById(R.id.card_body_text);
        TextView cardViewSeenTextView = parent.findViewById(R.id.card_seen_time);
        final RemoteCard remoteCard = mRemoteCards.get(position);

            if (remoteCard.getImageBase64() != null) {
                cardImageView.setVisibility(View.VISIBLE);
//                Glide.with(mContext).asBitmap().load(remoteCard.getImageBase64()).placeholder(R.drawable.ic_launcher_background).into(cardImageView);
                Glide.with(mContext).load(remoteCard.getImageBase64()).placeholder(R.drawable.ic_launcher_background).into(cardImageView);
            }
            if (remoteCard.getImageUrl() != null) {
                cardImageView.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(remoteCard.getImageUrl()).into(cardImageView);
            }
            if (remoteCard.getDate() != null) {
                cardViewSeenTextView.setVisibility(View.VISIBLE);
                final String dateString = remoteCard.getDate();
                StringBuilder date = new StringBuilder();
                for (int i = 0; i < dateString.length(); i++) {
                    final char codePoint = dateString.charAt(i);
                    if (Character.isAlphabetic(codePoint)) {
                        break;
                    }
                    date.append(codePoint);
                }
                final Date date1 = Date.valueOf(date.toString());
                SimpleDateFormat formatDate = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());
                cardViewSeenTextView.setText(formatDate.format(date1));
            }
            if (remoteCard.getHeading() != null) {
                cardTitleTextView.setVisibility(View.VISIBLE);
                cardTitleTextView.setText(remoteCard.getHeading());
            }
            if (remoteCard.getBodyText() != null) {
                cardBodyTextView.setVisibility(View.VISIBLE);
                cardBodyTextView.setText(remoteCard.getBodyText());
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
