package com.fractureof.demos.location;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fractureof.demos.location.backend.UserProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.syncano.library.Syncano;
import com.syncano.library.api.Response;
import com.syncano.library.callbacks.SyncanoCallback;

public class SplashActivity extends AppCompatActivity {

    public static Syncano syncano =
            Syncano.init("83d5b6d706b3584108ac8c543a0b4809c9a4a8a5", "polished-night-6282");
    public static Bitmap avatarBitmap = null;
//    public static Bitmap scaledAvatarBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load the bitmap from the web

        SyncanoCallback<UserProfile> callback = new SyncanoCallback<UserProfile>() {
            @Override
            public void success(Response<UserProfile> response, UserProfile result) {
                Uri url1 = Uri.parse(result.avatar.getLink());
                Picasso.with(getApplicationContext()).load(url1).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        avatarBitmap = bitmap;
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
            @Override
            public void failure(Response<UserProfile> response) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        };
        syncano.getObject(UserProfile.class, Integer.decode("11").intValue()).sendAsync(callback);
//        Response<UserProfile> response =  syncano.getObject(UserProfile.class, Integer.decode("11").intValue()).send();
//        if(response.isSuccess()) {
//            UserProfile.getData();
//            Uri url1 = Uri.parse(result.avatar.getLink());
//        }

    }
}
