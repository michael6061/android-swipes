package com.project.android_swipes.Utility;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.project.android_swipes.R;
import com.project.android_swipes.RemoteCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtility {
    private static final String TAG = "JsonUtility";
    public static final String JSON_IMAGE = "image";
    public static final String JSON_TITLE = "title";
    public static final String JSON_TEXT = "text";
    public static final String JSON_DATE = "date";

    /**
     * this function accepts content of json file as String and fetches each RemoteCard
     *  Object from it and add it to a list of RemoteCard
     * @param jsonContents String representation of the json file contents
     * @return List of the RemoteCards from the json file
     */
    public static List<RemoteCard> getRemoteCardsFromJson(String jsonContents) {
        List<RemoteCard> remoteCards = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonContents);
            String cardsArrayName = jsonObject.keys().next();
            JSONObject jsonArrayObject = jsonObject.getJSONObject(cardsArrayName);

            final Iterator<String> keys = jsonArrayObject.keys();
            while (keys.hasNext()){
                final JSONObject object = jsonArrayObject.getJSONObject(keys.next());
                String image64 = object.getString(JSON_IMAGE);
                String title = object.getString(JSON_TITLE);
                String text = object.getString(JSON_TEXT);
                String date = object.getString(JSON_DATE);
                byte[] imageDecoded = Base64.decode(image64,Base64.DEFAULT);
                RemoteCard card = new RemoteCard(imageDecoded,title,text,date);
                remoteCards.add(card);
            }
            return remoteCards;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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
