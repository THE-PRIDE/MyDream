package com.dream.lmy.mydream.viewStudy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.dream.lmy.mydream.R;
import com.dream.lmy.mydream.ccc.JniTest;

import java.util.Date;
import java.util.List;


public class ViewStudyActivity extends AppCompatActivity {


    private View view;

    private MyView myView;
    private TextView textView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study);
        textView = findViewById(R.id.textView);
        Debug.startMethodTracing();
        String string = JniTest.get();
        Log.e("TEST", string);



//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("TEST","TEST");
//                }
//            }
//        });
//        thread.setName("lmy_test_thread");
//        thread.start();


//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                textView.scrollTo(15,15);
//
////                ObjectAnimator.ofFloat(textView,"translationX",0,100).setDuration(100).start();
//
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
//                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                layoutParams.height = 100;
//                layoutParams.leftMargin +=100;
//                textView.requestLayout();
//
//            }
//        });

//        myView = findViewById(R.id.myview);
//
//        myView.smoothScrollTo(100,200);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int mLastX = 0;
                int mLastY = 0;
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ViewHelper.setTranslationX(v, (int) event.getRawX());
                        ViewHelper.setTranslationY(v, (int) event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                mLastX = x;
                mLastY = y;

                return true;
            }
        });


    }





    static class ViewHelper {

        public static int getTranslationX(View view) {
            return (int) view.getTranslationX();
        }

        public static int getTranslationY(View view) {
            return (int) view.getTranslationY();
        }

        public static void setTranslationX(View view, int translationX) {
            view.setTranslationX(translationX);
        }

        public static void setTranslationY(View view, int translationY) {
            view.setTranslationY(translationY);
        }
    }
}
