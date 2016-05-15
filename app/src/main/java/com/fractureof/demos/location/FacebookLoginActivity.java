package com.fractureof.demos.location;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fractureof.demos.location.backend.UserProfile;
import com.squareup.picasso.Picasso;
import com.syncano.library.Syncano;
import com.syncano.library.api.Response;
import com.syncano.library.data.SyncanoObject;
import com.syncano.library.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class FacebookLoginActivity extends AppCompatActivity {


    static class Consts {
        static String FACEBOOK_GENDER_ATTR = "gender";
    }

    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
//        /loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//
//        }
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        // If using in a fragment
//        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //SplashActivity.fb_acc_token =  loginResult.getAccessToken();
                SplashActivity.facebookProfile = Profile.getCurrentProfile();

                //check if the user has a FDP profile. if not, create it
                //launch a code box with the facebook id parameter.. but i can do this directly in code - the check and the creation



                new RetrieveInfoTask().execute(null, null, null);

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    class RetrieveInfoTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String facebookId = SplashActivity.facebookProfile.getId();
            Response<List<UserProfile>> result =
            SplashActivity.syncano.please(UserProfile.class)
                .where().eq(UserProfile.FIELD_FACEBOOK_ID, facebookId).get();
            if (result.isSuccess()) {
                List<UserProfile> data = result.getData();
                if (data.size() == 1) {
                    SplashActivity.fdp_user_profile = data.get(0);
                    SplashActivity.fdp_user_profile.profilePictureUri = SplashActivity.facebookProfile.getProfilePictureUri(160, 160).toString();

                } else if (data.size() == 0) {
                    //create new profile for this FB id

                    String uuidString = UUID.randomUUID().toString();
                    User newUser = new User(
                            String.format("%s %s",
                            SplashActivity.facebookProfile.getName(), uuidString.substring(uuidString.length()-7))
                            , "12e45S");
                    Syncano syncano_user_create = new Syncano("47cf12b867de776e9a0a099c65324c1ae1fb1bbb","polished-night-6282");
                    Response<User> responseCreateUser = syncano_user_create.registerUser(newUser).send();
                    if (responseCreateUser.isSuccess()) {
                        Log.d("FacebookLoginActivity", String.format(
                                "new user created for facebook id : %s", facebookId));
                        Log.d("FacebookLoginActivity", "Setting user profile with the facebook id...");
//                        User user = responseCreateUser.getData();
                        Integer newProfileId = responseCreateUser.getData().getProfile().getId();
                        Response<List<UserProfile>> responseGetNewProfileObject =
                                SplashActivity.syncano.please(UserProfile.class).
                                    where().eq("id", newProfileId).get();
                        if (responseGetNewProfileObject.isSuccess()) {
                            List<UserProfile> newProfileData = responseGetNewProfileObject.getData();
                            int sz = newProfileData.size();
                            if (sz == 1) {
                                SplashActivity.fdp_user_profile =newProfileData.get(0);
                                SplashActivity.fdp_user_profile.facebookId = facebookId;
                                SplashActivity.fdp_user_profile.name = SplashActivity.facebookProfile.getName();
                                SplashActivity.fdp_user_profile.firstName= SplashActivity.facebookProfile.getFirstName();
                                SplashActivity.fdp_user_profile.middleName = SplashActivity.facebookProfile.getMiddleName();
                                SplashActivity.fdp_user_profile.lastName = SplashActivity.facebookProfile.getLastName();
                                SplashActivity.fdp_user_profile.profilePictureUri = SplashActivity.facebookProfile.getProfilePictureUri(160, 160).toString();

                                //get gender
                                String gender = getGenderFromFacebook();

                                //workaround TODO
                                if (gender=="") gender="male";

                                SplashActivity.fdp_user_profile.gender = gender;

                                Response<UserProfile> setProfileResponse= SplashActivity.fdp_user_profile.save();
                                if (setProfileResponse.isSuccess()) {
                                    Log.d("FacebookLoginActivity", "Succesfully set facebook id in user profile");
                                } else {
                                    Log.e("FacebookLoginActivity",
                                            String.format(
                                                    "Failed to set facebook id in user profile. Error: %s",setProfileResponse.getError()));
                                }

                            } else if (sz==0){
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
                                String.format("failed to create user. Error: %s.",responseCreateUser.getError()));
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(getApplicationContext(), PartnerEntryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * @throws
     * @return
     */
    private String getGenderFromFacebook() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),null);
        Bundle parameters = new Bundle();
        parameters.putString("fields",FacebookLoginActivity.Consts.FACEBOOK_GENDER_ATTR);
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
                SplashActivity.avatarBitmap = Picasso.with(getApplicationContext()).load(
                        SplashActivity.fdp_user_profile.profilePictureUri
                ).get();
            } catch (IOException ex) {
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
