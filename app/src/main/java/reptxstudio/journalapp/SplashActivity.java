package reptxstudio.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import reptxstudio.journalapp.application.JournalApplication;

public class SplashActivity extends AppCompatActivity {


    ActionBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        bar = getSupportActionBar();
        bar.hide();

        gotosignin();
    }

    private void resolvedependency() {
        ((JournalApplication) getApplication()).getComponent().inject(SplashActivity.this);
    }

    private  void gotosignin(){
        Intent go = new Intent(this,SignInActivity.class);
        startActivity(go);
    }
}
