package com.fractureof.demos.location;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.fractureof.demos.location.backend.Pickup;
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
        private static float arrowHeadSideSize = 5.0f;
        public static float arrowPadding = 2.0f;
        public static String distanceUnit = "K";
        public static String distanceTitleFMT = "%3.2f %s";
        public static float drawTextonPathV = 14;
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
    }
    private void initialize() {
        mPaintHangoutMark = new Paint();
        mPaintHangoutMark.setColor(Color.parseColor("#9124B2"));
        mPaintHangoutMark.setTextSize(scaledDimensions.textSize);
        mPaintDistanceBetweenUs = new Paint();
        mPaintDistanceBetweenUs.setColor(Color.BLACK);
        mPaintDistanceHangoutMe = new Paint();
        mPaintDistanceHangoutMe.setColor(Color.BLACK);
        mPaintDistanceHangoutMe.setTextSize(14.0f);
        mPaintDistanceHangoutMe.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintDistanceHangoutMe.setStrokeWidth(1.0f);
        mPaintDistanceHangoutPartner =  new Paint();
        mPaintDistanceHangoutPartner.setColor(Color.BLACK);
        mPaintDistanceHangoutPartner.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintDistanceHangoutPartner.setTextSize(14.0f);
        mPaintDistanceHangoutPartner.setStrokeWidth(1.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //elevation - depends if need be drawn about pickup or meetup
        float height = getHeight();
        float hangoutEmojiBottom = height/2 + scaledDimensions.textSize/2;

        if(pickup.mePickingUp() || pickup.partnerPickingUp() || pickup.areMeetingInMeetupPoint()) {
            hangoutEmojiBottom =  height/3 + scaledDimensions.textSize/2;
        }

        //X of the hangout emoji
        float width = getWidth();
        float hangoutEmojiLeft = (width - scaledDimensions.textSize)/2;

        String hangoutEmoticonStr = String.format("%s", Values.drinkEmoticon);
        float emojiStrWidth = this.mPaintHangoutMark.measureText(hangoutEmoticonStr);

        if (pickup.dateLocationSet()) {
            //measure distance from me
            float distanceMe = (float) DistanceCalculator.distance(
                    pickup.meLatLng,
                    pickup.dateLatLng,
                    Values.distanceUnit);
            float distancePartner = (float) DistanceCalculator.distance(
                    pickup.partnerLatLng,
                    pickup.dateLatLng,
                    Values.distanceUnit );
            String distHangPartnerTitle = String.format(Values.distanceTitleFMT,distancePartner,Values.distanceUnit);
            String distHangMeTitle = String.format(Values.distanceTitleFMT,distanceMe,Values.distanceUnit);

            hangoutEmojiLeft = width / ( distanceMe + distancePartner ) * distancePartner;

            //arrow from partner to hangout
            float arPartStartX = 0 + Values.arrowPadding;
            float arPartStartY = height/2 + scaledDimensions.textSize/2;
            
            float arPartEndX = hangoutEmojiLeft - Values.arrowPadding;
            float arPartEndY = hangoutEmojiBottom;

            float arPartHeadX = arPartEndX - Values.arrowHeadSideSize;
            float arPartHeadY1 = arPartEndY + Values.arrowHeadSideSize;
            float arPartHeadY2 = arPartEndY - Values.arrowHeadSideSize;

            Path arPartPath = new Path();
            arPartPath.moveTo(arPartStartX, arPartStartY);
            arPartPath.lineTo(arPartEndX, arPartEndY);
            arPartPath.moveTo(arPartEndX, arPartEndY);
            arPartPath.lineTo(arPartHeadX, arPartHeadY1);
            arPartPath.moveTo(arPartEndX, arPartEndY);
            arPartPath.lineTo(arPartHeadX, arPartHeadY2);

            canvas.drawPath(arPartPath,this.mPaintDistanceHangoutPartner);
            canvas.drawTextOnPath(distHangPartnerTitle,arPartPath,0,Values.drawTextonPathV,this.mPaintDistanceHangoutPartner);

            float arMeStartX = getWidth() - Values.arrowPadding;
            float arMeStartY = arPartStartY;

            float arMeEndX = hangoutEmojiLeft + emojiStrWidth + Values.arrowPadding;
            float arMeEndY = hangoutEmojiBottom;

            float arMeHeadX = arMeEndX + Values.arrowHeadSideSize;
            float arMeHeadY1 = arMeEndY + Values.arrowHeadSideSize;
            float arMeHeadY2 = arMeEndY - Values.arrowHeadSideSize;

            Path arMePath = new Path();
            arMePath.moveTo(arMeEndX, arMeEndY);
            arMePath.lineTo(arMeStartX, arMeStartY);
            arMePath.moveTo(arMeEndX, arMeEndY);
            arMePath.lineTo(arMeHeadX, arMeHeadY1);
            arMePath.moveTo(arMeEndX, arMeEndY);
            arMePath.lineTo(arMeHeadX, arMeHeadY2);

            canvas.drawPath(arMePath, this.mPaintDistanceHangoutMe);
            canvas.drawTextOnPath(distHangMeTitle, arMePath, 0, Values.drawTextonPathV, this.mPaintDistanceHangoutMe);
        }

        canvas.drawText( hangoutEmoticonStr, hangoutEmojiLeft, hangoutEmojiBottom, this.mPaintHangoutMark);
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
