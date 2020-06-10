package com.example.myapplication;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayer extends AppCompatActivity {
    private VideoView videoView;
    private ImageView imageView;
    private ValueAnimator valueAnimator;
    private int width,height;
    private RelativeLayout rlLove;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer);

        //接受Url参数
        String url = getIntent().getStringExtra("url");
        Uri uri = Uri.parse(url);
        videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        rlLove = findViewById(R.id.rl_love);
        imageView = findViewById(R.id.iv_easy_love);
        imageView.getBackground().setAlpha(0);

        //点击暂停
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                } else {
                    videoView.start();
                }
                return false;
            }
        });

        //双击点赞
        rlLove.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                move();
            }
        }));

    }
    @SuppressLint("NewApi")
    private void move() {
        imageView.setVisibility(View.VISIBLE);
        imageView.getBackground().setAlpha(255);
        width = imageView.getRight();
        height = imageView.getBottom();
        valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(imageView.getLeft(), imageView.getBottom() - imageView.getHeight()),
                new PointF(width, 0));
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                if ((Math.round(pointF.y)) < 150) {
                    imageView.getBackground().setAlpha(Math.round(pointF.y));
                }

            }
        });
        valueAnimator.setTarget(imageView);
        valueAnimator.setRepeatCount(0);
        valueAnimator.start();
    }

    @SuppressLint("NewApi")
    class BezierEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue,
                               PointF endValue) {
            float oneMinusT = 1.0f - fraction;
            PointF point = new PointF();
            PointF point0 =  startValue;
            PointF point1 = new PointF();
            point1.set(width, 0);

            PointF point2 = new PointF();
            point2.set(0, height);

            PointF point3 = endValue;

            point.x = oneMinusT * oneMinusT * oneMinusT * (point0.x) + 4
                    * oneMinusT * oneMinusT * fraction * (point0.x) + 2
                    * oneMinusT * fraction * fraction * (point0.x) + fraction
                    * fraction * fraction * (point0.x);

            point.y = oneMinusT * oneMinusT * oneMinusT * (point0.y)
                    + oneMinusT * oneMinusT * fraction * (point0.y) + 2
                    * oneMinusT * fraction * fraction * (point2.y) + fraction
                    * fraction * fraction * (point3.y);

            return point;
        }
    }
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        String result;
        if(intent!=null){
            result = intent.getStringExtra("url");
            setIntent(intent);
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        if(intent!=null){
            String result = intent.getStringExtra("url");
        }
    }
}
