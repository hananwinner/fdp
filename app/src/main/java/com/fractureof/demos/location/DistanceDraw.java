package com.fractureof.demos.location;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.fractureof.demos.location.backend.Pickup;
import com.fractureof.demos.location.interactions.DistanceDrawInteraction;
import com.fractureof.demos.location.logic.DistanceCalculator;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hanan on 29/02/2016.
 */
public class DistanceDraw extends View {


    static class UnscaledDimensions {
        private static int textSize = 14;
        private static float avatarSize = 160.0f;
    }
    class ScaledDimesnsions {
        float textSize= UnscaledDimensions.textSize;
        float avatarSize = UnscaledDimensions.avatarSize;

        public ScaledDimesnsions(float scaleFactor) {
            this.textSize *= scaleFactor;
            this.avatarSize *= scaleFactor;
        }
    }

    static class Values {
        private static String drinkEmoticon =  "\ud83c\udf78";
    }
    Paint mPaintDistanceBetweenUs;
    Paint mPaintDistanceHangoutMe;
    Paint mPaintDistanceHangoutPartner;
    Paint mPaintHangoutMark;

    private Pickup pickup;


    float scaleFactor;
    ScaledDimesnsions scaledDimensions;

    private void initScaleFactor() {
        scaleFactor = getResources().getDisplayMetrics().density;
    }

    public DistanceDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        initScaleFactor();
        scaledDimensions = new ScaledDimesnsions(scaleFactor);
        initialize();
    }
    public DistanceDraw(Context context, AttributeSet attrs, Pickup pickup) {
        super(context, attrs);
        setWillNotDraw(false);
        this.pickup = pickup;
        initScaleFactor();
        scaledDimensions = new ScaledDimesnsions(scaleFactor);
        initialize();
//        invalidate();
    }
    private void initialize() {

        mPaintHangoutMark = new Paint();
        mPaintHangoutMark.setColor(Color.parseColor("#9124B2"));
        mPaintHangoutMark.setTextSize(scaledDimensions.textSize);
        mPaintDistanceBetweenUs = new Paint();
        mPaintDistanceBetweenUs.setColor(Color.BLACK);
        mPaintDistanceHangoutMe = new Paint();
        mPaintDistanceHangoutMe.setColor(Color.BLACK);
        mPaintDistanceHangoutPartner =  new Paint();
        mPaintDistanceHangoutPartner.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //where to draw the emoji ?
        //elevation - depends if need be drawn about pickup or meetup
        float height = getHeight();
        float hangoutEmojiBottom = height/2 + scaledDimensions.textSize/2;

        if(pickup.mePickingUp() || pickup.partnerPickingUp() || pickup.areMeetingInMeetupPoint()) {
            hangoutEmojiBottom =  height/3 + scaledDimensions.textSize/2;
        }

        //X of the hangout emoji
        float width = getWidth();
        float hangoutEmojiLeft = (width - scaledDimensions.textSize)/2;
        if (pickup.dateLocationSet()) {
            //measure distance from me
            float distanceMe = (float) DistanceCalculator.distance( pickup.meLatLng, pickup.dateLatLng, "K" );
            float distancePartner = (float) DistanceCalculator.distance( pickup.partnerLatLng, pickup.dateLatLng, "K" );
            hangoutEmojiLeft = width/( distanceMe+distancePartner ) * distancePartner;
        }
        canvas.drawText( String.format("%s", Values.drinkEmoticon), hangoutEmojiLeft, hangoutEmojiBottom, this.mPaintHangoutMark);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min((int) (scaledDimensions.avatarSize+1), heightSize);
        } else {
            height = (int) (scaledDimensions.avatarSize+1);
        }
        setMeasuredDimension(widthSize, height);
    }
    public void setPickupData(Pickup pickup) {
        this.pickup = pickup;
        invalidate();
    }

    public void setDateLatLng(LatLng dateLatLng) {
        this.pickup.dateLatLng = dateLatLng;
        invalidate();
    }
}
