package net.qiujuer.italker.push;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;

import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.italker.common.app.Activity;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.push.activities.MainActivity;
import net.qiujuer.italker.push.frags.assist.PermissionsFragment;

public class LaunchActivity extends Activity {
    private ColorDrawable mBgDrawable;

    private Property<LaunchActivity,Object> property = new Property<LaunchActivity, Object>(Object.class,"color") {
        @Override
        public void set(LaunchActivity object, Object value) {

            object.mBgDrawable.setColor((Integer) value);

        }

        @Override
        public Object get(LaunchActivity object) {
            return object.mBgDrawable.getColor();
        }
    };

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //拿到根布局
        View root   = findViewById(R.id.activity_launch);
        int  color  = UiCompat.getColor(getResources(),R.color.colorPrimary);
        ColorDrawable drawable  = new ColorDrawable(color);
        //给根布局设置背景
        root.setBackground(drawable);
        //想要让背景有渐变的效果
        mBgDrawable = drawable;
    }

    @Override
    protected void initData() {
        super.initData();

        startAnim(0.5f, new Runnable() {
            @Override
            public void run() {
                waitPushReceiverId();
            }
        });

    }

    /**
     * 等待个推框架对我们的PushID设置好值
     */
    private void waitPushReceiverId(){
        if(!TextUtils.isEmpty(Account.getPushId())){
            skip();
            return;
        }

        getWindow().getDecorView()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitPushReceiverId();
                    }
                },500);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void skip(){
        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                reallySkip();
            }
        });

    }

    private void reallySkip(){
        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }

    private void startAnim(float endProgress, final Runnable endCallback){
        int finalColor = Resource.Color.WHITE;
        ArgbEvaluator argbEvaluator  =new ArgbEvaluator();
        int endColor =(int) argbEvaluator.evaluate(endProgress,mBgDrawable.getColor(),finalColor);

        ValueAnimator valueAnimator = ObjectAnimator.ofObject(this,property,argbEvaluator,endColor);
        valueAnimator.setDuration(1500);
        valueAnimator.setIntValues(mBgDrawable.getColor(),endColor);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              endCallback.run();
            }
        });

        valueAnimator.start();

    }




}
