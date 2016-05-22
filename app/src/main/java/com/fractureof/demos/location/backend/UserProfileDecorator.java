package com.fractureof.demos.location.backend;

import android.graphics.Bitmap;

/**
 * Created by tyler on 19/05/2016.
 */
public class UserProfileDecorator  extends UserProfile{
    public UserProfileDecorator(UserProfile mUserProfile) {
        this.mUserProfile = mUserProfile;
    }
    public UserProfile mUserProfile;
    public Bitmap mAvatarBitmap;
}
