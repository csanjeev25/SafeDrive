package com.insomniac.safedrive;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sanjeev on 3/6/2018.
 */

public class EmergencyNumbersActivity extends AppCompatActivity {

    Button mEmergencyNumberButton1;
    Button mEmergencyNumberButton2;
    Button mEmergencyNumberButton3;
    Button mEmergencyNumberButton4;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.emergency_numbers_layout);

        mEmergencyNumberButton1=(Button) findViewById(R.id.bt_number_1);
        mEmergencyNumberButton2=(Button) findViewById(R.id.bt_number_2);
        mEmergencyNumberButton3=(Button) findViewById(R.id.bt_number_3);
        mEmergencyNumberButton4=(Button) findViewById(R.id.bt_number_4);

        mEmergencyNumberButton1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + 100));
                if (ActivityCompat.checkSelfPermission(EmergencyNumbersActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
                startActivity(intent);
            }
        });

        mEmergencyNumberButton2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + 102));
                if (ActivityCompat.checkSelfPermission(EmergencyNumbersActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
                startActivity(intent);
            }
        });

        mEmergencyNumberButton3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + 112));
                if (ActivityCompat.checkSelfPermission(EmergencyNumbersActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
                startActivity(intent);
            }
        });

        mEmergencyNumberButton4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + 181));
                if (ActivityCompat.checkSelfPermission(EmergencyNumbersActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    return;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
