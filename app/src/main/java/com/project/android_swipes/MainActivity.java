package com.project.android_swipes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private float startX, endX, startY, endY;
    private static final int MIN_SWIPE_DISTANCE = 50;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetector = new GestureDetector(this,this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();

                float directionX = endX - startX;
                float directionY = endY - startY;

                if (Math.abs(directionX) >= MIN_SWIPE_DISTANCE) {
                    if (endX != startX){
                        doImageLoading();
                    }
                }else if(Math.abs(directionY) >= MIN_SWIPE_DISTANCE) {

                }
        }
        return true;
    }

    private void doImageLoading() {
        Random randomNumberGenerator = new Random();
        int index = randomNumberGenerator.nextInt(imagesName.size());
        int imageId = 0;
        if (index < imagesName.size() && index >= 0 ){
            final String imageName = imagesName.get(index);
            imageId = getResources().getIdentifier(imageName, "drawable",getPackageName());
        }
        ImageView cardImage = findViewById(R.id.images);
        Glide.with(this).load(imageId).into(cardImage);
    }
    static ArrayList<String> imagesName = new ArrayList<>();
    static {
        imagesName.add(0,"photo1");
        imagesName.add(1,"photo2");
        imagesName.add(2,"photo3");
        imagesName.add(3,"photo4");
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
