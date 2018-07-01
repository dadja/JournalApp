package reptxstudio.journalapp.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import reptxstudio.journalapp.R;
import reptxstudio.journalapp.mvp.model.SignInModel;
import reptxstudio.journalapp.mvp.view.SignInView;

public class SignInPresenter /*implements LifecycleObserver*/{

    SignInView signInView;
    SignInModel signInModel;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private GoogleApiClient mGoogleApiclient;
    private String TAG;

    private static final int RC_SIGN_IN=20;


    public SignInPresenter(SignInView signInView,String classname){
       this.signInView=signInView;
       TAG=classname;

        signInModel = new SignInModel();
        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
//        if (signInView instanceof LifecycleOwner) {
//            ((LifecycleOwner) signInView).getLifecycle().addObserver(this);
//        }

    }

    public void initAuth(final FragmentActivity context) {

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    //User Signed In
                    Log.d(TAG,"User is In ");
                    signInView.SignedIn();
                }
                else{
                    signInView.FirstLogin();
                    //User is Signed out..
                    //init google SignIn Stuff
                    InitGoogleMaterials(context);
                    Log.d(TAG,"User is Out ");
                }
            }
        };
    }



    public void InitGoogleMaterials(final FragmentActivity context){

        Log.d(TAG,"InitGoogleMaterials started ");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

       mGoogleApiclient =new  GoogleApiClient.Builder(context)
                .enableAutoManage(context, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(context,"",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        Log.d(TAG,"InitGoogleMaterials Over ");
    }

    /*@OnLifecycleEvent(Lifecycle.Event.ON_RESUME) */public void startAuthListener() {
        Log.d(TAG,"authStateListener RESUME ");
        mAuth.addAuthStateListener(authStateListener);
    }

     /*@OnLifecycleEvent(Lifecycle.Event.ON_STOP) */public void removeAuthListener(FragmentActivity context) {
         Log.d(TAG,"authStateListener STOP ");
        if(authStateListener != null){
            Log.d(TAG,"authStateListener Off ");
            mAuth.removeAuthStateListener(authStateListener);

        }

         if (mGoogleApiclient != null && mGoogleApiclient.isConnected())
         {
             mGoogleApiclient.stopAutoManage(context);
             mGoogleApiclient.disconnect();
         }
    }


    public void shutdownGoogleApiClient(final FragmentActivity context){

         if (mGoogleApiclient != null && mGoogleApiclient.isConnected())
         {
             mGoogleApiclient.stopAutoManage(context);
             mGoogleApiclient.disconnect();
         }
    }


    public void firebaseAuthWithGoogle(GoogleSignInAccount acct, Activity context, final SharedPreferences prefs) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                //TODO we removed the context here if it doesnt work lets bring it back
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println("\n username : "+user.getDisplayName()+
                                    "\n email : "+user.getEmail()+
                                    "\n phone : "+user.getPhoneNumber());

                            signInModel.SaveUser(user.getDisplayName(),
                                                 user.getEmail(),
                                                 user.getPhoneNumber(),
                                                 prefs);

                            signInView.SignedIn();
                        } else {
                            //TODO Send a feedback saying login failed...
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void signInUser() {
        if(mGoogleApiclient != null) {
            Log.d(TAG,"mGoogleApiclient IN  ");
            signInView.SignInBtnClicked(RC_SIGN_IN, mGoogleApiclient);
        }
        else{
            Log.d(TAG,"mGoogleApiclient OUT  ");
        }
    }

    public void signOutUser() {
        Auth.GoogleSignInApi.signOut(mGoogleApiclient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
//                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity, SharedPreferences prefs) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account,activity,prefs);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
}
