package reptxstudio.journalapp.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import reptxstudio.journalapp.api.JournalApiService;
import reptxstudio.journalapp.application.JournalApplication;
import reptxstudio.journalapp.database.JournalDatabase;
import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.repository.ArticleDataSource;
import reptxstudio.journalapp.repository.local.ArticleLocalDataSource;
import reptxstudio.journalapp.repository.remote.ArticleRemoteDataSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DadjaBASSOU on 10/10/16.
 *
 * Comme son nom l'indique toutes les dependances gerees ici sont liees a la gestion de tous les protocoles necessitant ou passant par internet
 */

@Module
@Singleton
public class NetWorkModule {


    private String mBaseUrl="";
    private final JournalApplication headMasterApplication;


    public NetWorkModule(String baseurl,JournalApplication headMasterApplication)
    {
        mBaseUrl=baseurl;
        this.headMasterApplication=headMasterApplication;
    }


    //Application context part..
    @Singleton
    @Provides
    Context provideApplicationcvontext()
    {
        return  headMasterApplication;
    }

    //Application context part..
    @Singleton
    @Provides
    SharedPreferences providesPreferences(Context app)
    {
        return  app.getSharedPreferences("journalapp",Context.MODE_PRIVATE);
    }


    @Singleton
    @Provides
    JournalDatabase providesRoomDataBase(Context app)
    {
        return JournalDatabase.getInstance(app);
    }

//    @Singleton
//    @Provides
//    UserDao providesuserdao(JournalDatabase db)
//    {
//        return db.getUserDao();
//    }

    @Singleton
    @Provides
    ArticleDao providesarticlesdao(JournalDatabase db)
    {
        return db.getArticleDao();
    }




    @Provides
    @Singleton
    public ArticleDataSource provideLocalDataSource(ArticleLocalDataSource articleLocalDataSource) {
        return articleLocalDataSource;
    }

    @Provides
    @Singleton
    public ArticleDataSource provideRemoteDataSource(ArticleRemoteDataSource articleRemoteDataSource) {
        return articleRemoteDataSource;
    }





    @Singleton
    @Provides
    Gson provideGson()
    {
        return new GsonBuilder()
                .setLenient()
                .create();
    }


    @Singleton
    @Provides
    RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory()
    {
        return  RxJavaCallAdapterFactory.create();
    }



    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory(Gson gson)
    {
        return  GsonConverterFactory.create(gson);
    }


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient()
    {
        return new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Response response = chain.proceed(chain.request());
//                        Log.w("Retrofit@Response", response.body().string());
//                        return response;
//                    }
//                })
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client,GsonConverterFactory converterFactory,RxJavaCallAdapterFactory madapterfactory)
    {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(madapterfactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    JournalApiService provideHeadApiService(Retrofit retrofit)
    {
        return  retrofit.create(JournalApiService.class);
    }

    @Provides
    @Singleton
    Resources provideresource()
    {
        return headMasterApplication.getResources();
    }
}
