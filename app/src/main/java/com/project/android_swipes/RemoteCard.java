package com.project.android_swipes;

public class RemoteCard {
    private byte[] mImageBase64;
    private String mImageUrl;
    private String mHeading;
    private String mBodyText;
    private String mDate;

    public RemoteCard(String heading, String bodyText, String date){
        mHeading = heading;
        mBodyText = bodyText;
        mDate = date;
    }
    public RemoteCard(byte[] imageBase64, String heading, String bodyText, String date) {
        this(heading,bodyText,date);
        mImageBase64 = imageBase64;
    }
    public RemoteCard(String imageUrl, String heading, String bodyText, String date) {
        this(heading,bodyText,date);
        mImageUrl = imageUrl;
    }

    public byte[] getImageBase64() {
        return mImageBase64;
    }

    public String getHeading() {
        return mHeading;
    }

    public String getBodyText() {
        return mBodyText;
    }

    public String getDate() {
        return mDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
