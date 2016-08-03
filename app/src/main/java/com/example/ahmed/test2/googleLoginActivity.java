package com.example.ahmed.test2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class googleLoginActivity extends BaseActivity
{

    /* constants */
    private static final int GOOGLE_SIGN_IN = 42;
    public String idToken;
    /* UI */

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.content)
    LinearLayout content;


    /* fields */
    //CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // set an exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Transition exitTrans = new Fade();
            getWindow().setExitTransition(exitTrans);
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_googlelogin);

        // reference views
        ButterKnife.bind(this);


        initGoogleLogin();




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN)
        {
            // google login
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                GoogleSignInAccount acct = result.getSignInAccount();
               Constants.EMAIL = acct.getEmail();
                Constants.FIRST_NAME= acct.getDisplayName();
                Constants.LAST_NAME= acct.getFamilyName();
                // open the main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        }
    }


    /* listeners */


    @OnClick(R.id.buttonGoogleSignIn)
    void onButtonGoogleClicked()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }





    /* methods */

    /**
     * prepares the google client for login
     */
    private void initGoogleLogin()
    {
        // init google client
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener()
                {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult)
                    {
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * prepares facebook callbacks for login
     */

    /**
     * called after we receive the token from google SDK
     * sends the google token to the backend for authentication
     */
    private void onGoogleLogin(String token)
    {
        progressBar.setVisibility(View.VISIBLE);

        Log.e("Game", "google token " + token);
        // login from backend
       // AuthenticationAPIController controller = new AuthenticationAPIController(this);
        //controller.loginByGmail(token, new LoginCallback());
    }




    /**
     * called after google or facebook or normal login
     * opens the dashboard activity then finishes this one
     */
    class LoginCallback implements  com.example.ahmed.test2.LoginCallback
    {
        @Override
        public void success()
        {
            // show succes
            progressBar.setVisibility(View.GONE);

            // open the main activity
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        @Override
        public void fail(String message)
        {
            // show error
            progressBar.setVisibility(View.GONE);
            Snackbar.make(content, message, Snackbar.LENGTH_SHORT).show();
        }
    }


}
