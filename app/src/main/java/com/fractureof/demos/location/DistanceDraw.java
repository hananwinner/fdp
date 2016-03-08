package com.fractureof.demos.location;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.fractureof.demos.location.interactions.DistanceDrawInteraction;

/**
 * Created by hanan on 29/02/2016.
 */
public class DistanceDraw extends View {
    DistanceDrawInteraction interaction;
    Paint mPaintDistanceBetweenUs;
    Paint mPaintDistanceHangoutMe;
    Paint mPaintDistanceHangoutPartner;
    Paint mPaintHangoutMark;
    public DistanceDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }
    public DistanceDraw(Context context, AttributeSet attrs, DistanceDrawInteraction interaction) {
        super(context, attrs);
        this.interaction = interaction;
        initialize();
    }
    private void initialize() {
        mPaintHangoutMark = new Paint();
        mPaintHangoutMark.setColor(Color.RED);
        mPaintDistanceBetweenUs = new Paint();
        mPaintDistanceBetweenUs.setColor(Color.BLACK);
        mPaintDistanceHangoutMe = new Paint();
        mPaintDistanceHangoutMe.setColor(Color.GREEN);
        mPaintDistanceHangoutPartner =  new Paint();
        mPaintDistanceHangoutPartner.setColor(Color.RED);
    }
}
