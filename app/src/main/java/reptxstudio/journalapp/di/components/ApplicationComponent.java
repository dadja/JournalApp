package reptxstudio.journalapp.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import reptxstudio.journalapp.ArticleDetailActivity;
import reptxstudio.journalapp.ArticlesActivity;
import reptxstudio.journalapp.EditArticleActivity;
import reptxstudio.journalapp.SignInActivity;
import reptxstudio.journalapp.SplashActivity;
import reptxstudio.journalapp.api.JournalApiService;
import reptxstudio.journalapp.application.JournalApplication;
import reptxstudio.journalapp.di.modules.NetWorkModule;
import retrofit2.Retrofit;


/**
 * Created by DadjaBASSOU on 10/10/16.
 *
 * Ici nous decrivons ou peuvent se derouler nos injections de dependance
 */

@Singleton
@Component(modules = {NetWorkModule.class})
public interface ApplicationComponent {


       //here you define where you wanna inject some values that are linked to your component and the compoenent itself...
    void inject(JournalApplication application);


    //By defining  methods and type return here
    //you do expose your object to be used where ever this component is called
      Retrofit exposeRetrofit();
      Context exposeContext();
      JournalApiService exposeHeadApiService();
//      JournalApplication exposeAppContext();

    void inject(SplashActivity activity);
    void inject(ArticleDetailActivity activity);
    void inject(SignInActivity activity);
    void inject(ArticlesActivity activity);
    void inject(EditArticleActivity activity);

}
