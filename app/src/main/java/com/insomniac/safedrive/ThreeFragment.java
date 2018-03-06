package com.insomniac.safedrive;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sanjeev on 3/6/2018.
 */

public class ThreeFragment extends Fragment {

    double mLatitude;
    double mLongitude;

    GPSTracker mGPSTracker;
    String mAddress,mCity,mState,mCountry,mPostalAddress,mKnownName,nAddress;
    Button mSearchRideButton;
    Button mSearchGoogleButton;
    Button mEmergencyButton;

    View mView;
    TextView mNoteTextView;
    WebView mWebView;

    int a;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_3,container,false);

        mSearchRideButton =  (Button)mView.findViewById(R.id.bt_ride);
        mNoteTextView = (TextView) mView.findViewById(R.id.tv_note);
        mSearchGoogleButton = (Button)mView.findViewById(R.id.bt_searchCab);
        mEmergencyButton = (Button)mView.findViewById(R.id.bt_emergencyButton);

        mSearchRideButton.setText("Find cab / taxi in my location.");
        a=0;

        if(!isInternetOn()){
            mSearchRideButton.setText("Internet connection not found!");
            a=1;
        }

        mWebView = (WebView)mView.findViewById(R.id.wv_myWebview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.getJavaScriptEnabled();
        mWebView.setWebViewClient(new customWebView());

        mSearchRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (a == 0)
                {
                    mSearchRideButton.setText("Finding...");
                    Toast.makeText(getActivity(), "Finding your location ...", Toast.LENGTH_SHORT).show();

                    
                    mGPSTracker = new GPSTracker(getActivity());

                    
                    if (mGPSTracker.canGetLocation()) {
                        mLatitude = mGPSTracker.getLatitude();
                        mLongitude = mGPSTracker.getLongitude();
                    } else {      
                        mGPSTracker.showSettingsAlert();
                    }

                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                Geocoder geocoder;
                                List<Address> addresses;
                                geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                                try {
                                    addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
                                    mAddress = addresses.get(0).getAddressLine(1);
                                    nAddress = addresses.get(0).getAddressLine(0);
                                    mCity = addresses.get(0).getLocality();
                                    mState = addresses.get(0).getAdminArea();
                                    mCountry = addresses.get(0).getCountryName();
                                    mPostalAddress = addresses.get(0).getPostalCode();
                                    mKnownName = addresses.get(0).getFeatureName();
                                } catch (IOException e) {
                                    //
                                }
                            } finally {

                            }
                        }
                    };
                    timer.start();

                    try {
                        timer.join();
                        Toast.makeText(getActivity(), "Lat " + mLatitude + " Lon " + mLongitude + "\nAddress: " + mAddress + "\nCity: " + mCity + "\nState: " + mState + "\nCountry: " + mCountry + "\nPostal Address: " + mPostalAddress + " " + mKnownName + "\n" + nAddress, Toast.LENGTH_LONG).show();

                        mWebView.loadUrl("https://www.google.co.in/search?q=" + "book taxi or cab online " + nAddress + " " + mCity);

                        mWebView.setVisibility(View.VISIBLE);
                        mSearchRideButton.setVisibility(View.INVISIBLE);
                        mSearchGoogleButton.setVisibility(View.INVISIBLE);
                        mNoteTextView.setVisibility(View.INVISIBLE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mEmergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), EmergencyNumbersActivity.class);
                startActivity(i);
            }
        });

        return mView;
    }
    public final boolean isInternetOn(){
        ConnectivityManager connec = (ConnectivityManager)getActivity().getSystemService(getActivity().getBaseContext().CONNECTIVITY_SERVICE);
        if(connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            return true;
        }
        else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            return false;
        }
        return false;
    }

    private class customWebView extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}


