package com.insomniac.safedrive;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Sanjeev on 3/6/2018.
 */

public class OneFragment extends Fragment{

    View mView;
    SessionManager mSessionManager;
    String name,number;
    Button mSMSButton;
    Button mCallButton;
    Button mEmergencyButton;

    double lat;
    double lon;

    GPSTracker mGPSTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_1,container,false);

        mSessionManager = new SessionManager(getActivity().getApplicationContext());
        mSessionManager.checkLogin();
        HashMap<String,String> user = mSessionManager.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        number = user.get(SessionManager.KEY_EMAIL);

        mSMSButton = (Button)mView.findViewById(R.id.bt_smsHelp);
        mCallButton = (Button)mView.findViewById(R.id.bt_callHelp);
        mEmergencyButton =  (Button)mView.findViewById(R.id.bt_emergencyButton_one);

        mSMSButton.setText("Send Help SMS to : " + number);
        mCallButton.setText("Make a Call to : " + number);
        mEmergencyButton.setText("Emergency Numbers");

        mGPSTracker = new GPSTracker(getActivity());

        if(mGPSTracker.canGetLocation()){
            lat=mGPSTracker.getLatitude();
            lon=mGPSTracker.getLongitude();
        }

        mSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mGPSTracker.canGetLocation()) {
                        //
                    } else {
                        //
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Send Help SMS?");
                    alertDialog.setMessage("\nSend SMS to " + number + " ?\n\n\nContent of SMS : \n\n" + "Contact " + name + ", he/she is not in a condition to drive & needs your help to get back home from location : \'Google Maps URL of your location\' \n\n");
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SmsManager smsManager = SmsManager.getDefault();
                            String smsBody;
                            smsBody = "http://maps.google.com/?q=" + lat + "," + lon;
                            smsManager.sendTextMessage(number, null, "Contact " + name + ", he/she is not in a condition to drive & needs your help to get back home from location : " + smsBody, null, null);
                        }
                    });
                    alertDialog.show();
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Are you sure ?");
                alertDialog.setMessage("Make a call to " + number + " ?");
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                });

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+number));
                        startActivity(callIntent);
                    }
                });
                alertDialog.show();
            }
        });

        mEmergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EmergencyNumbersActivity.class);
                startActivity(i);
            }
        });

        return mView;
    }
}

