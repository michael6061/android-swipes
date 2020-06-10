package com.project.android_swipes.Utility;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.android_swipes.R;
import com.project.android_swipes.RemoteCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtility {
    private static final String TAG = "JsonUtility";
    public static final String JSON_STATUS = "status";
    public static final String JSON_TOTAL_RESULTS = "totalResults";
    public static final String JSON_ARTICLES = "articles";
    public static final String JSON_TITLE = "title";
    public static final String JSON_DESCRIPTION = "description";
    public static final String JSON_PUBLISHED_AT = "publishedAt";
    public static final String JSON_URL_TO_IMAGE = "urlToImage";

    public static List<RemoteCard> getRemoteCardsFromJson(String jsonContents,int numberOfCardsToFetch , int numberOfCardsAlreadyFetched) {
        List<RemoteCard> remoteCards = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonContents);
            final String status = jsonObject.getString(JSON_STATUS);
            if (!status.equals("ok")) return null;
            final int totalResults = jsonObject.getInt(JSON_TOTAL_RESULTS);
            if (totalResults == 0) return null;
            final JSONArray articles = jsonObject.getJSONArray(JSON_ARTICLES);

            for (int i = numberOfCardsAlreadyFetched ; i < Math.min(numberOfCardsToFetch,totalResults); i++){
                final JSONObject article = articles.getJSONObject(i);
                final String title = article.getString(JSON_TITLE);
                final String content = article.getString(JSON_DESCRIPTION);
                final String date = article.getString(JSON_PUBLISHED_AT);
                final String urlToImage = article.getString(JSON_URL_TO_IMAGE);
                RemoteCard remoteCard = new RemoteCard(urlToImage,title,content,date);
                remoteCards.add(remoteCard);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remoteCards;
    }
    /**
     * get the input Stream of the json file read it and add its contents to the string
     * and return the produced string
     * @param context accepts context object to get the information regarding invoking activity
     * @return return String representation of the json file
     */
    public static String getJsonFileContentsAsString(Context context) {
        StringBuilder sb = new StringBuilder();
        try(BufferedInputStream inputStream = new BufferedInputStream(
                context.getResources().openRawResource(R.raw.sample)) ){
            byte[] buffer = new byte[1000];
            int read = -1;
            while ( (read = inputStream.read(buffer)) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[100];
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "getJsonFileContents: " + e.getMessage());
        }
        return null;
    }
}
