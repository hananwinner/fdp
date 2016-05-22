package com.fractureof.demos.location.logic.profile;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.internal.Validate;

/**
 * Created by tyler on 19/05/2016.
 */
public class SocialProfileMock implements SocialProfile{

    private final String id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String name;
    /**
     * Contructor.
     * @param id         The id of the profile.
     * @param firstName  The first name of the profile. Can be null.
     * @param middleName The middle name of the profile. Can be null.
     * @param lastName   The last name of the profile. Can be null.
     * @param name       The name of the profile. Can be null.
     */
    public SocialProfileMock(
            final String id,
            @Nullable
            final String firstName,
            @Nullable
            final String middleName,
            @Nullable
            final String lastName,
            @Nullable
            final String name) {
        Validate.notNullOrEmpty(id, "id");

        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.name = name;
    }

    /**
     * Getter for the id of the profile.
     * @return id of the profile.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the first name of the profile.
     * @return the first name of the profile.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for the middle name of the profile.
     * @return the middle name of the profile.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Getter for the last name of the profile.
     * @return the last name of the profile.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for the name of the profile.
     * @return the name of the profile.
     */
    public String getName() {
        return name;
    }



    @Override
    public Uri getProfilePictureUri(int width, int height) {
        return Uri.parse("android.resource://com.fractureof.demos.location/drawable/my_avatar");
    }
}
