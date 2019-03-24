package com.example.mp_wortspiel;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import java.text.SimpleDateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    sqlData database= new sqlData(this);
    private Button button3;
    Date c;
    SimpleDateFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        button3 = (Button) findViewById(R.id.button_signuppage);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = (EditText) findViewById(R.id.user_signup);
                EditText password = (EditText) findViewById(R.id.password_signup);
                EditText password2 = (EditText) findViewById(R.id.password_again_signup);
                EditText emailid = (EditText) findViewById(R.id.email_signup);
                String userstr = user.getText().toString();
                String passwordstr = password.getText().toString();
                String password2str = password2.getText().toString();
                String emailidstr = emailid.getText().toString();
               // database.addScoreandDate(userstr,0,formattedDate);
                if ((database.userexist(userstr))) {
                    Toast userm = Toast.makeText(SignUp.this, "User already exists!", Toast.LENGTH_SHORT);
                    userm.show();
                }
                else if ((isvaliduser(userstr))) {
                    Toast userv = Toast.makeText(SignUp.this, "Username is not valid!", Toast.LENGTH_SHORT);
                    userv.show();
                }
                else if (passwordstr.equals("")) {
                    Toast passw = Toast.makeText(SignUp.this, "Password is not given!", Toast.LENGTH_SHORT);
                    passw.show();
                }
                else if (emailidstr.equals("")) {
                    Toast emailw = Toast.makeText(SignUp.this, "Email is empty!", Toast.LENGTH_SHORT);
                    emailw.show();
                }
                else if (!(isvalidemail(emailidstr))) {
                    Toast emailw = Toast.makeText(SignUp.this, "Please provide a valid email", Toast.LENGTH_SHORT);
                    emailw.show();
                }
                else if (!(passwordstr.equals(password2str))){
                    Toast passme = Toast.makeText(SignUp.this, "The Passwords don't match!", Toast.LENGTH_SHORT);
                    passme.show();
                }
                else if (passwordstr.equals(password2str)) {
                    if (passwordstr.length() >= 8) {
                        UserDetails details = new UserDetails();
                        details.setEmailid(emailidstr);
                        details.setPassword(passwordstr);
                        details.setUser(userstr);
                        database.addUser(details);
                        signup();
                    } else {
                        Toast messagep = Toast.makeText(SignUp.this, "Password is too short!", Toast.LENGTH_SHORT);
                        messagep.show();
                    }

                }
            }
        });
    }
    public boolean isvalidemail(String email)
    {
        Pattern pattern;

        Matcher matcher;
        final String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean isvaliduser(String user)
    {
        Pattern pattern;

        Matcher matcher;
        final String User_PATTERN = "[a-zA-Z]";
        pattern = Pattern.compile(User_PATTERN);
        matcher = pattern.matcher(user);
        return matcher.matches();
    }
    public void signup() {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
    }
}
