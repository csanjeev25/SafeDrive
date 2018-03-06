package com.insomniac.safedrive;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by Sanjeev on 3/6/2018.
 */

public class TwoFragment extends Fragment
{
    View mView;
    EditText mWeightEditText,mHoursEditText,mBeerEditText,mSpiritEditText,mWineEditText;
    Button mSubmitButton;
    RadioButton kgRadioButton,poundRadioButton,maleRadioButton,femaleRadioButton;
    double W,A,r,H,B;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_2, container, false);

        mWeightEditText = (EditText)mView.findViewById(R.id.et_bac_weight);
        mHoursEditText = (EditText)mView.findViewById(R.id.et_bac_hours);
        mBeerEditText = (EditText)mView.findViewById(R.id.et_bac_beer);
        mSpiritEditText = (EditText)mView.findViewById(R.id.et_bac_spirit);
        mWineEditText = (EditText)mView.findViewById(R.id.et_bac_wine);
        kgRadioButton = (RadioButton)mView.findViewById(R.id.rb_bac_kg);
        poundRadioButton = (RadioButton)mView.findViewById(R.id.rb_bac_pounds);
        maleRadioButton = (RadioButton)mView.findViewById(R.id.rb_bac_male);
        femaleRadioButton = (RadioButton)mView.findViewById(R.id.rb_bac_female);
        mSubmitButton = (Button)mView.findViewById(R.id.bt_bac_submit);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kgRadioButton.isChecked()) {
                    try {
                        W = Double.parseDouble(mWeightEditText.getText().toString());
                        W = W * 2.2;
                    }
                    catch (NumberFormatException e){
                        W = 0;
                    }
                }
                else if (poundRadioButton.isChecked()) {
                    try {
                        W = Double.parseDouble(mWeightEditText.getText().toString());
                    }
                    catch (NumberFormatException e){
                        W = 0;
                    }
                }

                if (maleRadioButton.isChecked()) {
                    r = 0.73;
                }
                else if (femaleRadioButton.isChecked()) {
                    r = 0.66;
                }

                try {
                    A = (Double.parseDouble(mBeerEditText.getText().toString()) * 0.60) + (Double.parseDouble(mSpiritEditText.getText().toString()) * 0.62) + (Double.parseDouble(mWineEditText.getText().toString()) * 0.58);
                    H = Double.parseDouble(mHoursEditText.getText().toString());

                }catch (NumberFormatException e) {
                    A = 0;
                    H = 0;
                }

                B = ( (A * 5.14) / (W + r) ) - (H * 0.015);
                String msg = "";

                if(B < 0.01) {
                    msg = "You might not be feeling the effects of alcohol right now.";
                }
                else if(B < 0.03) {
                    msg = "The typical depressant effects of alcohol are not affecting you at this stage and you probably haven’t lost any of your coordination.";
                }
                else if(B < 0.06) {
                    msg = "You're probably feeling some impairment of reasoning and memory and since your level of caution is lower, it's a bad idea to drive.";
                }
                else if(B < 0.09) {
                    msg = "In reality, with a BAC of 0.07 – 0.09 you'll be experiencing a slight impairment of speech, balance and hearing and your reaction time is reduced. Your caution, reasoning and memory are impaired, and your judgment and level of self-control are reduced. \n" +
                            "Note: Many countries have laws that forbid people to drive if they have a BAC of 0.05 or more. Most countries have laws that forbid people to drive if they have a BAC of 0.08+. No matter where you live or where you are, definitely don’t drive – take a cab or plan to stay the night.";
                }
                else if(B < 0.125) {
                    msg = "If your BAC is between 0.10 and 0.125 your speech will be slurred and your balance, vision, reactions time and hearing will be impaired. Your motor coordination will also be significantly impaired and you'll have a loss of good judgment. \n" +
                            "Note: No matter where you live, it's illegal to drive. In fact, it'd be extremely irresponsible to drive anything at this point: including a boat, snowmobile, off-road vehicle – even a riding lawnmower. Not a good idea. Don't even think about it. ";
                }
                else if(B < 0.15) {
                    msg = "Right now, you'd be feeling gross motor impairment and a lack of physical control. It'd probably be hard for you to type on the keyboard and your monitor or mobile phone screen would appear blurry.\nIf your BAC is 0.20, you'll probably need help if you want to walk properly. If you fall down you probably won't feel a lot of pain – even if you hurt yourself. At this level of BAC, some people begin to vomit. At the very least, you'll feel dazed, confused and disoriented. ";
                }
                else if(B < 0.20) {
                    msg = "If you were awake at this point you'd have very little comprehension as to where you were or what you were doing. You might pass out suddenly and it'd be hard to wake you up.YOU ARE IN A BIG TROUBLE";
                }
                else if(B < 0.30) {
                    msg = "It's possible that you might fall into a coma. You might die due to the paralysis of your diaphragm, the collapse of your lungs, or heart attack (any form of respiratory arrest).  ";
                }


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("BAC Calculator");
                alertDialog.setMessage("Your BAC: " + B + "g/100 ml \n\n" + msg);


                alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });
        return mView;
    }
}
