package reptxstudio.journalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reptxstudio.journalapp.application.JournalApplication;
import reptxstudio.journalapp.mvp.presenter.SignInPresenter;
import reptxstudio.journalapp.mvp.view.SignInView;

public class SignInActivity extends AppCompatActivity implements SignInView {

    SignInPresenter signInPresenter;

    public static final String TAG = SignInActivity.class.getSimpleName();

    public static final String User_Email="useremail";
    public static final String User_Cellphone="userphone";
    public static final String User_Name="username";
    public static final String FirstLogin="firstlogin";

    @BindView(R.id.signin_btn)
    SignInButton mGooglebtn;




    @Inject
    SharedPreferences pref;


    boolean firstlogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInPresenter = new SignInPresenter(this,TAG);
        ButterKnife.bind(this);
        resolvedependency();
        signInPresenter.initAuth(this);
    }


    private void resolvedependency() {
        ((JournalApplication) getApplication()).getComponent().inject(SignInActivity.this);
    }


    @OnClick(R.id.signin_btn)
    public void SignInButtonClicked(View view){
        signInPresenter.signInUser();
    }



    @Override
    protected void onStart() {
        super.onStart();
        //TODO Got to the next screen 1
        //presenter listening to auth
        signInPresenter.startAuthListener();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //presenter removes auth
        signInPresenter.removeAuthListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        signInPresenter.onActivityResult(requestCode,resultCode,data,SignInActivity.this,pref);
    }


    @Override
    public void SignInBtnClicked(int IntentRefIdResult,GoogleApiClient mGoogleApiclient) {
        Intent signinIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiclient);
        startActivityForResult(signinIntent,IntentRefIdResult);
    }

    @Override
    public void SignedIn() {
        Log.d("Sigined In","got signed in");
        Intent GotoMainPage = new Intent(this,ArticlesActivity.class);
          GotoMainPage.putExtra(FirstLogin,firstlogin);
        startActivity(GotoMainPage);
//        finish();
    }

    @Override
    public void FirstLogin() {
        firstlogin = true;
    }



}
