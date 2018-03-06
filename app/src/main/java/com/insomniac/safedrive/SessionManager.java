package com.insomniac.safedrive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Sanjeev on 3/6/2018.
 */

public class SessionManager {

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Login_Session";
    public static final String KEY_NAME = "name";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "email";

    public SessionManager(Context context){
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public void createLoginSession(String name,String email){
        mEditor.putBoolean(IS_LOGIN,true);
        mEditor.putString(KEY_EMAIL,name);
        mEditor.putString(KEY_EMAIL,email);
        mEditor.commit();
    }

    public void checkLogin(){
        if(!isLoggedIn()){
            Intent intent = new Intent(mContext,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public HashMap<String,String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, mSharedPreferences.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, mSharedPreferences.getString(KEY_EMAIL, null));
        return user;
    }

    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();

        Intent intent = new Intent(mContext,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public boolean isLoggedIn(){
        return mSharedPreferences.getBoolean(IS_LOGIN,false);
    }

}
