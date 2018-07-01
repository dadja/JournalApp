package reptxstudio.journalapp.application;

import android.app.Application;

import reptxstudio.journalapp.di.components.ApplicationComponent;
import reptxstudio.journalapp.di.components.DaggerApplicationComponent;
import reptxstudio.journalapp.di.modules.NetWorkModule;
import reptxstudio.journalapp.utils.Config;


/**
 * Created by DadjaBASSOU on 12/28/16.
 */

public class JournalApplication extends Application {

    ApplicationComponent component;
//    ConnectivityReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();


        initializeApplicationComponent();
//        MultiDex.install(this);
//        receiver = new ConnectivityReceiver();
//        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }



    private void initializeApplicationComponent() {
        component = DaggerApplicationComponent
                .builder()
                .netWorkModule(new NetWorkModule(Config.BASE_URL,this))
                .build();
        component.inject(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onTerminate() {
//        unregisterReceiver(receiver);
        super.onTerminate();
    }
}
