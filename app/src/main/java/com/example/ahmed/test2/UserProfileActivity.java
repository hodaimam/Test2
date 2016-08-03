package com.example.ahmed.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;


import com.example.ahmed.test2.helper.SQLiteHandler;
import com.example.ahmed.test2.helper.SessionManager;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by ahmed on 8/1/2016.
 */
public class UserProfileActivity extends BaseActivity {

    /* UI */

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private TextView Pass;
    private TextView m;


    private SQLiteHandler db;
    private SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
       // Pass = (TextView) findViewById(R.id.text1);
        //m = (TextView) findViewById(R.id.text2);
        // setup common views
// reference views
        //ButterKnife.bind(this);

        // setup common views
        //setupNavigationBar(this, toolbar);
        //toolbar.setTitle("Dashboard");

        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
        db.addUser(Constants.EMAIL, "mEmailview", "pass", "phone","mobile","type");
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        //Pass.setText(Constants.EMAIL);
        //m.setText(email);

    }
}