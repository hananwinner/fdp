package com.fractureof.demos.location.logic.profile;

import android.net.Uri;

/**
 * Created by tyler on 19/05/2016.
 */
public interface SocialProfile {
    /**
     * Getter for the id of the profile.
     * @return id of the profile.
     */
    public String getId();

    /**
     * Getter for the first name of the profile.
     * @return the first name of the profile.
     */
    public String getFirstName();

    /**
     * Getter for the middle name of the profile.
     * @return the middle name of the profile.
     */
    public String getMiddleName();

    /**
     * Getter for the last name of the profile.
     * @return the last name of the profile.
     */
    public String getLastName();

    /**
     * Getter for the name of the profile.
     * @return the name of the profile.
     */
    public String getName();
    public Uri getProfilePictureUri(
            int width,
            int height);
}
