package com.fractureof.demos.location.logic.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.fractureof.demos.location.FacebookLoginActivity;
import com.fractureof.demos.location.backend.UserProfile;
import com.fractureof.demos.location.backend.UserProfileDecorator;
import com.squareup.picasso.Picasso;
import com.syncano.library.Syncano;
import com.syncano.library.api.Response;
import com.syncano.library.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by tyler on 19/05/2016.
 */
public class SocialProfileLoader {
    SocialProfile mSocialProfile;
    UserProfile fdp_user_profile;
    Syncano mBackend;
    private Bitmap mAvatarBitmap;
    Context mContext;


    public SocialProfileLoader(Context mContext, Syncano mBackend,
                               SocialProfile mSocialProfile) {
        this.mContext = mContext;
        this.mBackend = mBackend;
        this.mSocialProfile = mSocialProfile;
    }

    public UserProfileDecorator load() {
        String facebookId = mSocialProfile.getId();
        Response<List<UserProfile>> result =
                mBackend.please(UserProfile.class)
                        .where().eq(UserProfile.FIELD_FACEBOOK_ID, facebookId).get();
        if (result.isSuccess()) {
            List<UserProfile> data = result.getData();
            if (data.size() == 1) {
                fdp_user_profile = data.get(0);
                fdp_user_profile.profilePictureUri = mSocialProfile.getProfilePictureUri(160, 160).toString();

            } else if (data.size() == 0) {
                //create new profile for this FB id

                String uuidString = UUID.randomUUID().toString();
                User newUser = new User(
                        String.format("%s %s",
                                mSocialProfile.getName(), uuidString.substring(uuidString.length() - 7))
                        , "12e45S");
                Syncano syncano_user_create = new Syncano("47cf12b867de776e9a0a099c65324c1ae1fb1bbb", "polished-night-6282");
                Response<User> responseCreateUser = syncano_user_create.registerUser(newUser).send();
                if (responseCreateUser.isSuccess()) {
                    Log.d("FacebookLoginActivity", String.format(
                            "new user created for facebook id : %s", facebookId));
                    Log.d("FacebookLoginActivity", "Setting user profile with the facebook id...");
//                        User user = responseCreateUser.getData();
                    Integer newProfileId = responseCreateUser.getData().getProfile().getId();
                    Response<List<UserProfile>> responseGetNewProfileObject =
                            mBackend.please(UserProfile.class).
                                    where().eq("id", newProfileId).get();
                    if (responseGetNewProfileObject.isSuccess()) {
                        List<UserProfile> newProfileData = responseGetNewProfileObject.getData();
                        int sz = newProfileData.size();
                        if (sz == 1) {

                            fdp_user_profile = newProfileData.get(0);
                            fdp_user_profile.facebookId = facebookId;
                            fdp_user_profile.name = mSocialProfile.getName();
                            fdp_user_profile.firstName = mSocialProfile.getFirstName();
                            fdp_user_profile.middleName = mSocialProfile.getMiddleName();
                            fdp_user_profile.lastName = mSocialProfile.getLastName();
                            fdp_user_profile.profilePictureUri = mSocialProfile.getProfilePictureUri(160, 160).toString();

                            //get gender
                            String gender = getGenderFromFacebook();

                            //workaround TODO
                            if (gender == "") gender = "male";

                            fdp_user_profile.gender = gender;

                            Response<UserProfile> setProfileResponse = fdp_user_profile.save();
                            if (setProfileResponse.isSuccess()) {
                                Log.d("FacebookLoginActivity", "Succesfully set facebook id in user profile");
                            } else {
                                Log.e("FacebookLoginActivity",
                                        String.format(
                                                "Failed to set facebook id in user profile. Error: %s", setProfileResponse.getError()));
                            }

                        } else if (sz == 0) {
                            Log.e("FacebookLoginActivity", "no new profile found");
                        } else {
                            Log.e("FacebookLoginActivity", "multiple new profile");

                        }
                    } else {
                        Log.d("FacebookLoginActivity",
                                String.format("Failed to get the created profile with id : %s",
                                        newProfileId));
                    }

                } else {
                    Log.e("FacebookLoginActivity",
                            String.format("failed to create user. Error: %s.", responseCreateUser.getError()));
                }


//                    UserProfile newProfile = new UserProfile();
//                    newProfile.facebook_id = facebookId;
//                    Response<UserProfile> createFdpProfileResponse = newProfile.save();
//                    if (createFdpProfileResponse.isSuccess()) {
//                        SplashActivity.fdp_user_profile = newProfile;
//                        int newId = newProfile.getId();
//                        Log.i("FacebookLoginActivity", String.format("app profile created with id : %d", newId));
//                    } else {
//                        Log.e("FacebookLoginActivity", String.format("failed to create app profile. Error: %s",createFdpProfileResponse.getError()));
//                    }
            } else {
                Log.e("FacebookLoginActivity", "error multiple user profile for the same FB account.");
            }
        } else {
            Log.e("FacebookLoginActivity", "failed to get user profile");
        }
        retrieveProfilePhoto();
        UserProfileDecorator userProfileDecorator = new UserProfileDecorator(fdp_user_profile);
        userProfileDecorator.mAvatarBitmap = mAvatarBitmap;
        return userProfileDecorator;
    }

    /**
     * @return
     * @throws
     */
    private String getGenderFromFacebook() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(), null);
        Bundle parameters = new Bundle();
        parameters.putString("fields", FacebookLoginActivity.Consts.FACEBOOK_GENDER_ATTR);
        graphRequest.setParameters(parameters);
        GraphResponse response = graphRequest.executeAndWait();
        FacebookRequestError err = response.getError();
        if (err != null) {
            Log.e("FacebookLoginActivity",
                    String.format("Failed to retrieve user gender from facebook. Error: %s", err.toString())
            );
        } else {
            JSONObject jsonProfileResponse = response.getJSONObject();
            try {
                String gender =
                        jsonProfileResponse.getString(FacebookLoginActivity.Consts.FACEBOOK_GENDER_ATTR);
                Log.d("FacebookLoginActivity",
                        String.format("getGender returned %s", gender));
                return gender;
            } catch (JSONException ex) {
                Log.e("FacebookLoginActivity",
                        String.format("Missing %s attribute in user data. Error: %s",
                                FacebookLoginActivity.Consts.FACEBOOK_GENDER_ATTR, ex.toString())
                );
            }
        }
        Log.d("FacebookLoginActivity", "getGender returned empty");
        return "";
    }

    private void retrieveProfilePhoto() {
//            if (SplashActivity.fdp_user_profile.profile_picture_uri == null) {
//                SplashActivity.fdp_user_profile.profile_picture_uri = new android.net.Uri(SplashActivity.fdp_user_profile.profile_picture_uri_path);
//            }
        try {
            mAvatarBitmap = Picasso.with(mContext).load(
                    fdp_user_profile.profilePictureUri
            ).get();
        } catch (IOException ex) {
        }
    }
}