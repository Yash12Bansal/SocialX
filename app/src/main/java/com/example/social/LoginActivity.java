package com.example.social;

import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button signIn;
    TextView forgotPassword,registerText;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private static final String TAG = "LOGIN";
    BeginSignInRequest signInRequest;
    BeginSignInRequest signUpRequest;

    private boolean showOneTapUI = true;
    private SignInClient oneTapClient;
    private SignInButton signInButton;
    // ...
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

//////////////////////////////////
    //////////////FACEBOOK
//    private FirebaseAuth mAuth;
    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword=findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
        /////////////////////////FACEBOOK//////////////////////////////////////////////
        LoginButton loginButton = findViewById(R.id.facebook_sign_in);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(LoginActivity.this,Facebook.class);
//                startActivity(i);
//                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//                mAuth.signInWithCredential(credential)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithCredential:success");
//                                    Log.e(TAG, "signInWithCredential:successzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
//
//
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
//                                    Toast.makeText(Facebook.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
//                                }
//                            }
//                        });


                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });

        TextView register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


        email=findViewById(R.id.email_sign_in);
        password=findViewById(R.id.password_sign_in);
        signIn=findViewById(R.id.sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EmailPasswordActivity e=new EmailPasswordActivity();
//                e.userLogin();

//                mAuth = FirebaseAuth.getInstance();

                String e=email.getText().toString().trim();
                String p=password.getText().toString().trim();
                if(e.isEmpty()){
                    email.setError("Full name Required");
                    email.requestFocus();
                    return;
                }
                if(p.isEmpty()){
                    password.setError("Full name Required");
                    password.requestFocus();
                    return;
                }


                if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
                    email.setError("Invalid email");
                    email.requestFocus();
                    return;
                }


                mAuth.signInWithEmailAndPassword(e,p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"singin failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });


// Initialize Facebook Login button
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = findViewById(R.id.facebook_sign_in);
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//            }
//        });
//// ...
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            // Pass the activity result back to the Facebook SDK
//            mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        }



/////////////////////////////////////////////////////////////////////////////////////////////////////

       //////////////////////////// GOOGLE
        signInButton=findViewById(R.id.google_sign_in);
        oneTapClient= Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
         signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();
        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                oneTapClient.beginSignIn(signInRequest);
                displaySignIn();
                Log.w(TAG, "onClick: dfdfdfdfdfdfdfdfdfdffffffffffffthissisisntos the sactiit");
//                Intent signInIntent=mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInRequest);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();


                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        //        SignInClient oneTapClient;
//                        SignInCredential googleCredential = null;
//                        googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
//                        String idToken = googleCredential.getGoogleIdToken();
//                        if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                        mAuth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithCredential:success");
                                            Log.e(TAG, "shoe gaya assign g id fnsdfoto tot ot totd tfdfffdffaaaa");

                                            FirebaseUser user = mAuth.getCurrentUser();

                                            updateUI(user);

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                                            updateUI(null);
                                        }
                                    }
                                });

//                    }

                        Log.d(TAG, "Got ID token.");
                    }
                } catch (ApiException e) {
                    // ...
                }
                break;
        }



    }
    private void displaySignIn(){
        Log.e(TAG, "shoe gaya assign g id fnsdfoto tot ot totd tfdfffdffaaaa");

        oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        startIntentSenderForResult(
                                result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                null, 0, 0, 0);


                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, e.getLocalizedMessage());
                    }
                });    }
    private void updateUI(FirebaseUser currentUser) {
        Log.e(TAG, "updateUIiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii:"+currentUser.getEmail() );
        startActivity(new Intent(LoginActivity.this,HomeActivity.class));


    }


}







//    private static final String TAG = "MainActivity";
//    private SignInButton signInButton;
//    private GoogleApiClient googleApiClient;
//    private static final int RC_SIGN_IN = 1;
//    String name, email;
//    String idToken;
//    private FirebaseAuth firebaseAuth;
//    private FirebaseAuth.AuthStateListener authStateListener;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
//        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
//        authStateListener = new FirebaseAuth.AuthStateListener(){
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                // Get signedIn user
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                //if user is signed in, we call a helper method to save the user details to Firebase
//                if (user != null) {
//                    // User is signed in
//                    // you could place other firebase code
//                    //logic to save the user details to Firebase
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//            }
//        };
//
//        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))//you can also use R.string.default_web_client_id
//                .requestEmail()
//                .build();
//        googleApiClient=new GoogleApiClient.Builder(this)
////                .enableAutoManage(this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();
//
//        signInButton = findViewById(R.id.google_sign_in);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                startActivityForResult(intent,RC_SIGN_IN);
//            }
//        });
//    }
//
//    @Override
////    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
////
////    }
//
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==RC_SIGN_IN){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result){
//        if(result.isSuccess()){
//            GoogleSignInAccount account = result.getSignInAccount();
//            idToken = account.getIdToken();
//            name = account.getDisplayName();
//            email = account.getEmail();
//            // you can store user data to SharedPreference
//            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//            firebaseAuthWithGoogle(credential);
//        }else{
//            // Google Sign In failed, update UI appropriately
//            Log.e(TAG, "Login Unsuccessful. "+result);
//            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void firebaseAuthWithGoogle(AuthCredential credential){
//
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//                        if(task.isSuccessful()){
//                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                            gotoProfile();
//                        }else{
//                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
//                            task.getException().printStackTrace();
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//    }
//
//
//
//
//    private void gotoProfile(){
//        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (authStateListener != null){
//            FirebaseAuth.getInstance().signOut();
//        }
//        firebaseAuth.addAuthStateListener(authStateListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener != null){
//            firebaseAuth.removeAuthStateListener(authStateListener);
//        }
//    }
//
//}

