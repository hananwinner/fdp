package com.fractureof.demos.location;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by hanan on 23/02/2016.
 */
public class RoundedAvatarImageView extends ImageView {
    RoundedAvatarDrawable mRoundedAvatarDrawable;

    public RoundedAvatarImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }
    public RoundedAvatarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        setWillNotDraw(false);
        mRoundedAvatarDrawable =
                 new RoundedAvatarDrawable(
                         SplashActivity.avatarBitmap
                         );
    }
//    @Override
//    public void setImageBitmap (Bitmap bm) {
//        //mRoundedAvatarDrawable = new RoundedAvatarDrawable(bm);
//
//    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRoundedAvatarDrawable.setBounds(0, 0, w, h);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        mRoundedAvatarDrawable.draw(canvas);
    }
}
