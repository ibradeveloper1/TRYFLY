package com.e.tryfly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        /*to edit this contactfragment.java i had to create a variable and return the variable instead of the whole line of code*/
         View v = inflater.inflate(R.layout.fragment_contact, container, false);

         /*making the text view an onclick method and setting is a phone number*/
         TextView tv_phone = (TextView) v.findViewById(R.id.tv_phone);
            tv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:123456789"));
                    startActivity(intent);
                }
            });


            /*This is for the contact form*/
        final EditText et_fullname = (EditText) v.findViewById(R.id.et_fullname);
        final EditText et_email = (EditText) v.findViewById(R.id.et_email);
        final EditText et_subject = (EditText) v.findViewById(R.id.et_subject);
        final EditText et_message = (EditText) v.findViewById(R.id.et_message);
        Button btn_send = (Button) v.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getting the input from the fields and assigning it to the variables*/
                String fullname = et_fullname.getText().toString();
                String email = et_email.getText().toString();
                String subject = et_subject.getText().toString();
                String message = et_message.getText().toString();

                if(TextUtils.isEmpty(fullname)){
                    et_fullname.setError("Enter Full-Name");
                    et_fullname.requestFocus();
                    return;
                }

                /*using isValidEmail function in the if statement*/
                Boolean onError = false;
                if (!isValidEmail(email)) {
                    onError = true;
                    et_email.setError("Invalid Email");
                    return;
                }

                if (TextUtils.isEmpty(subject)){
                    et_subject.setError("Enter subject");
                    et_subject.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(message)){
                    et_message.setError("Enter some message");
                    et_message.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(Intent.ACTION_SEND);

                /* Fill it with Data */
                sendEmail.setType("plain/text");
                sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"umarsyed080597@gmail.com"});
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
                sendEmail.putExtra(Intent.EXTRA_TEXT,
                        "name:"+fullname+'\n'+"Email:"+email+'\n'+"Message:"+'\n'+message);

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(sendEmail, "Send email..."));

            }
        });

         return v;
    }

    // validating email
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}


