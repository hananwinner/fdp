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
import com.fractureof.demos.location.backend.UserProfileDecorator;
import com.fractureof.demos.location.logic.profile.SocialProfile;
import com.fractureof.demos.location.logic.profile.SocialProfileLoader;
import com.fractureof.demos.location.logic.profile.SocialProfileRealMock;
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


    public static class Consts {
        public static String FACEBOOK_GENDER_ATTR = "gender";
    }

    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        // If using in a fragment
        //loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                SplashActivity.facebookProfile = Profile.getCurrentProfile();
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
            Context context = getApplicationContext();
            final Profile facebookProfile = SplashActivity.facebookProfile;
            SocialProfileRealMock socialProfileMock = new SocialProfileRealMock(facebookProfile);
            SplashActivity.loadUserProfileFromFacebook(socialProfileMock, context);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(getApplicationContext(), PartnerNameEntryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
