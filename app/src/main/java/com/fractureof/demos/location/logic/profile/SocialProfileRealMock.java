package com.fractureof.demos.location.logic.profile;

import android.net.Uri;

import com.facebook.Profile;

/**
 * Created by tyler on 19/05/2016.
 */
public class SocialProfileRealMock implements SocialProfile {
    public SocialProfileRealMock(Profile mFacebookProfile) {
        this.mFacebookProfile = mFacebookProfile;
    }

    Profile mFacebookProfile;
    @Override
    public String getId() {
        return mFacebookProfile.getId();
    }

    @Override
    public String getFirstName() {
        return mFacebookProfile.getFirstName();
    }

    @Override
    public String getMiddleName() {
        return mFacebookProfile.getMiddleName();
    }

    @Override
    public String getLastName() {
        return mFacebookProfile.getLastName();
    }

    @Override
    public String getName() {
        return mFacebookProfile.getName();
    }

    @Override
    public Uri getProfilePictureUri(int width, int height) {
        return mFacebookProfile.getProfilePictureUri(width,height);
    }
}
