package com.project.android_swipes;

public class RemoteCard {
    private byte[] mImageBase64;
    private String mHeading;
    private String mBodyText;
    private String mDate;

    public RemoteCard(byte[] imageBase64, String heading, String bodyText, String date) {
        mImageBase64 = imageBase64;
        mHeading = heading;
        mBodyText = bodyText;
        mDate = date;
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
}
