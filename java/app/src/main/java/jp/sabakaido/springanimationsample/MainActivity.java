package jp.sabakaido.springanimationsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import jp.sabakaido.springanimationsample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    private SpringAnimation mAnimX;
    private SpringAnimation mAnimY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.imageView.setOnTouchListener(new DragListener());

        mAnimX = new SpringAnimation(mBinding.imageView, DynamicAnimation.X, 0);
        mAnimX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        mAnimY = new SpringAnimation(mBinding.imageView, DynamicAnimation.Y, 0);
        mAnimY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // imageViewの位置がView表示後じゃないと取得できないのでonWindowFocusChangedでセット
        mAnimX.getSpring().setFinalPosition(mBinding.imageView.getLeft());
        mAnimY.getSpring().setFinalPosition(mBinding.imageView.getTop());
    }

    /**
     * ドラッグイベントのリスナー
     */
    private class DragListener implements View.OnTouchListener {
        private int oldx;
        private int oldy;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    int left = v.getLeft() + (x - oldx);
                    int top = v.getTop() + (y - oldy);

                    v.layout(left, top, left + v.getWidth(), top + v.getHeight());
                    break;
                case MotionEvent.ACTION_UP:
                    mAnimX.start();
                    mAnimY.start();
                    break;
            }

            oldx = x;
            oldy = y;

            return true;
        }
    }
}
