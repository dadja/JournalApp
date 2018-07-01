package reptxstudio.journalapp.mvp.view;

import com.google.android.gms.common.api.GoogleApiClient;

public interface SignInView {

    void SignInBtnClicked(int IntentRefIdResult,GoogleApiClient mGoogleApiclient);

    void SignedIn();

    void FirstLogin();
}
