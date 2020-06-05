package com.project.android_swipes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.project.android_swipes.Utility.JsonUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String GAMES_NEWS_CATEGORY = "games";
    public static final String FIN_TECH_NEWS_CATEGORY = "fintech";
    public static final String STARTUP_NEWS_CATEGORY = "Startup";
    public static final String ACQUISITION_NEWS_CATEGORY = "Acquisition";
    public static final String TECHNOLOGY_NEWS_CATEGORY = "technology";
    private HashMap<String,List<RemoteCard>> mRemoteCardCategories;
    private static final String TAG = "MainActivity";
    public static final int INTERNET_PERMISSION_REQUEST_CODE = 1;
    public static final String A_98_C_6_E_04_F_41827_DEC_1_E_42_CE_5699 = "33942a98c6e04f41827dec1e42ce5699";
    private List<RemoteCard> mRemoteCards;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private boolean mCompleteFetchingNotesFromApi;
    private boolean mFailedToFetchCardsFromApi;
    private ImageViewAdapter mImageViewAdapter;
    private ProgressBar mProgressBar;
    private String mKeyWord = GAMES_NEWS_CATEGORY;

    /**
     *  It parses the json and converts it into List of RemoteCard object
     *  and provides this to ViewPager to load the content to display
     * @param savedInstanceState
     */
    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main);
        InitViews();
        List<String> categories = createCategories();
        setUpMap(categories);

        final List<RemoteCard> remoteCards = mRemoteCardCategories.get(mKeyWord);
        if (remoteCards == null) {
            Log.d(TAG, "onCreate: Unknown category fetched");
            return;
        }
        mRemoteCards = remoteCards;
        makeCallToApi(mKeyWord,remoteCards);
        while (!isPermissionsGranted()){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_REQUEST_CODE);
        }
    }

    private List<String> createCategories() {
        List<String> categories = new ArrayList<>();
        categories.add(GAMES_NEWS_CATEGORY);
        categories.add(FIN_TECH_NEWS_CATEGORY);
        categories.add(STARTUP_NEWS_CATEGORY);
        categories.add(ACQUISITION_NEWS_CATEGORY);
        categories.add(TECHNOLOGY_NEWS_CATEGORY);
        return categories;
    }

    private void setUpMap(List<String> listOfCategories) {
        mRemoteCardCategories = new HashMap<>();
        for (String category : listOfCategories) {
            mRemoteCardCategories.put(category,new ArrayList<>());
        }
    }

    private boolean isPermissionsGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == (PackageManager.PERMISSION_GRANTED);
    }

    public void makeCallToApi(String keyWord,List<RemoteCard> remoteCards) {
        Call<ResponseBody> newsLoad = RetrofitClient.getInstance().getApi().getNews(keyWord,A_98_C_6_E_04_F_41827_DEC_1_E_42_CE_5699);
        newsLoad.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String jsonContentAsString = null;
                    try {
                        jsonContentAsString = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onResponse: \n" + jsonContentAsString + "\n");
                    //TODO: enable user to fetch cards when current card finishes
                    final List<RemoteCard> remoteCardsFromJson =
                            JsonUtility.getRemoteCardsFromJson(
                                    jsonContentAsString,
                                    20,
                                    mRemoteCards.size()
                            );
                    if (remoteCardsFromJson != null) {
                        remoteCards.addAll(remoteCardsFromJson);
                        mImageViewAdapter = new ImageViewAdapter(remoteCards,MainActivity.this);
                        mViewPager.setAdapter(mImageViewAdapter);
                    }else{
                        Toast.makeText(MainActivity.this, "You have Watched All Cards Available", Toast.LENGTH_SHORT).show();
                    }
                }
                mCompleteFetchingNotesFromApi = true;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                mFailedToFetchCardsFromApi = true;
            }
        });
    }

    private void InitViews() {
        mViewPager = findViewById(R.id.pager);
        mRemoteCards = new ArrayList<>();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.opendrawer,
                R.string.closedrawer
        );
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setCheckedItem(R.id.gaming_nav_menu);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }else{
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        mRemoteCards = new ArrayList<>();
        if (id == R.id.gaming_nav_menu) {
            mKeyWord = GAMES_NEWS_CATEGORY;
        }else if (id == R.id.fintech_nav_menu) {
            mKeyWord = FIN_TECH_NEWS_CATEGORY;
        }else if (id == R.id.acquire_nav_menu) {
            mKeyWord = ACQUISITION_NEWS_CATEGORY;
        }else if (id == R.id.startup_nav_menu) {
            mKeyWord = STARTUP_NEWS_CATEGORY;
        }else {
            mKeyWord = TECHNOLOGY_NEWS_CATEGORY;
        }
        mRemoteCards = mRemoteCardCategories.get(mKeyWord);
        makeCallToApi(mKeyWord,mRemoteCards);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
