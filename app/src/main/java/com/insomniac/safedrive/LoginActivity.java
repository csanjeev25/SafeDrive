package com.insomniac.safedrive;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Sanjeev on 3/6/2018.
 */

public class LoginActivity extends AppCompatActivity{

    private EditText mNameEdittext;
    private EditText mNumberEditText;
    private Button mNextButton;
    private TextView mMessageTextView;
    private TextView mMessageTextView1;
    private TextView mMessageTextView2;


    private SessionManager mSessionManager;

    String name,number;
    int count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_login);

        mNameEdittext = (EditText)findViewById(R.id.et_login_name);
        mNumberEditText = (EditText)findViewById(R.id.et_login_number);
        mMessageTextView = (TextView)findViewById(R.id.tv_welcome);
        mMessageTextView1 = (TextView)findViewById(R.id.tv_tell_us);
        mMessageTextView2 = (TextView)findViewById(R.id.tv_friends_number_message);
        mNextButton = (Button)findViewById(R.id.bt_login_next);

        count = 0;
        mSessionManager = new SessionManager(getApplicationContext());

        mSessionManager.checkLogin();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mNameEdittext.getText().toString();
                number = mNumberEditText.getText().toString();

                if(!name.equals(""))
                    count++;

                if(count==0){
                    Toast.makeText(LoginActivity.this,"Please Enter name",Toast.LENGTH_SHORT).show();
                }else {
                    mNameEdittext.setVisibility(View.GONE);
                    mNumberEditText.setVisibility(View.VISIBLE);
                    mMessageTextView.setText("Now");
                    mMessageTextView1.setText("Please Enter Your Emergency Contact Number");
                    mNextButton.setText("Done");
                    mMessageTextView2.setText("This number (of your friend) will get the emergency help SMS showing your location.\nSMS will not be sent until you push the\n\'Text sms to friend\' button.");
                    number = mNumberEditText.getText().toString();
                }
                if(!number.equals(""))
                    count++;
                if(count==1)
                    Toast.makeText(LoginActivity.this,"Please Enter Number",Toast.LENGTH_SHORT).show();
                else{
                    mSessionManager.createLoginSession(name,number);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
