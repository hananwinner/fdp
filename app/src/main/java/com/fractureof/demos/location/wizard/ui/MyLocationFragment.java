package com.fractureof.demos.location.wizard.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fractureof.demos.location.R;
import com.fractureof.demos.location.SplashActivity;
import com.fractureof.demos.location.util.MapUtils;
import com.fractureof.demos.location.wizard.model.MyLocationPage;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MyLocationFragment extends Fragment
        implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener, PlaceSelectionListener {

    private static final String ARG_KEY = "key";
    private String mKey;
    private MyLocationPage mPage;
    private PageFragmentCallbacks mCallbacks;
    private GoogleMap mMap;
    private com.google.android.gms.maps.model.Marker mMyMarker;
    private PlaceAutocompleteFragment mAutocompleteFragment;

    public MyLocationFragment() {
        // Required empty public constructor
    }

    public static MyLocationFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY,key);
        MyLocationFragment fragment = new MyLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (getArguments() != null) {
            mKey = args.getString(ARG_KEY);
            mPage = (MyLocationPage) mCallbacks.onGetPage(mKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_my_location, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(
                                R.id.map_my_location
                        );
        mapFragment.getMapAsync(this);
        mAutocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mAutocompleteFragment.setOnPlaceSelectedListener(this);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());
        updateAutocompleteText();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PageFragmentCallbacks) {
            mCallbacks = (PageFragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PageFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private LatLng myLatLng() {
        return new LatLng(
                mPage.getData().getDouble(MyLocationPage.MY_LOCATION_LAT_DATA_KEY),
                mPage.getData().getDouble(MyLocationPage.MY_LOCATION_LNG_DATA_KEY)
        );
    }

    /*OnPlaceSelectedListener*/
    @Override
    public void onPlaceSelected(Place place) {
        updatePage(place);
        setMyLocationOnMap();

    }

    /*OnPlaceSelectedListener*/
    @Override
    public void onError(Status status) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        setMyLocationOnMap();
    }

    private void setMyLocationOnMap() {
        LatLng latLng = myLatLng();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                12.0f));
        if (mMyMarker == null) {
            BitmapDescriptor bitmapDescMe = BitmapDescriptorFactory.fromBitmap(SplashActivity.meMarkerBitmap);
            MarkerOptions optsMe = new MarkerOptions().position(latLng)
                    .icon(bitmapDescMe);
            mMyMarker =  mMap.addMarker(optsMe);
        } else {
            mMyMarker.setPosition(latLng);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMyMarker.setPosition(latLng);
        String addressDescription =
                MapUtils.geocodeAddressDescription(getContext(), latLng);
        updatePage(latLng,"", addressDescription);
        updateAutocompleteText(addressDescription);
    }

    private void updateAutocompleteText(String address) {
        mAutocompleteFragment.setText(address);
    }

    private void updateAutocompleteText() {
        String address = mPage.getData().getString(MyLocationPage.MY_LOCATION_ADDRESS_DESC_DATA_KEY,"");
        if (address != "") {
            mAutocompleteFragment.setText(address);
        }
    }

    private void updatePage(LatLng latLng, String geoId, String addressDescription) {
        mPage.getData().putString(MyLocationPage.MY_LOCATION_ADDRESS_DESC_DATA_KEY, addressDescription);
        updatePage(latLng, geoId);
    }

    private void updatePage(Place place) {
        updatePage(place.getLatLng(),place.getId());
    }

    private void updatePage(LatLng latLng, String geo_id) {
        mPage.getData().putString(MyLocationPage.MY_LOCATION_GEO_ID_DATA_KEY,geo_id);
        mPage.getData().putDouble(
                MyLocationPage.MY_LOCATION_LAT_DATA_KEY,
                latLng.latitude
        );
        mPage.getData().putDouble(
                MyLocationPage.MY_LOCATION_LNG_DATA_KEY,
                latLng.longitude
        );
        mPage.notifyDataChanged();
    }

}
