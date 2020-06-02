package com.project.android_swipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.project.android_swipes.Utility.JsonUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<RemoteCard> mRemoteCards;

    /**
     *  It parses the json and converts it into List of RemoteCard object
     *  and provides this to ViewPager to load the content to display
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.pager);
        mRemoteCards = new ArrayList<>();

        final String jsonContents = JsonUtility.getJsonFileContentsAsString(this);
        if (jsonContents != null) {
            mRemoteCards = JsonUtility.getRemoteCardsFromJson(jsonContents);
            ImageViewAdapter imageViewAdapter = new ImageViewAdapter(mRemoteCards, this);
            viewPager.setAdapter(imageViewAdapter);
        }else {
            Toast.makeText(this, "Error Converting Json File to String ", Toast.LENGTH_SHORT).show();
        }
    }
}
