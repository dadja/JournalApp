package reptxstudio.journalapp.mvp.model;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import reptxstudio.journalapp.R;
import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.repository.ArticlesRepository;


//here we go check if we need to access data from internet or locally to show Articles..

public class ArticlesModel {

    ArticlesRepository articlesRepository;
    State status;
    GoogleApiClient  mGoogleApiclient;

    public ArticlesModel(ArticlesRepository articlesRepository,State status)
    {
        this.articlesRepository=articlesRepository;
        this.status=status;
    }

    public void GetArticles(boolean Remotely,String usermail){
        GetArticleTask task = new GetArticleTask(usermail);
        task.execute(Remotely);
    }


    public void DeleteData(SharedPreferences prefs, ArticleDao articleDao,FragmentActivity context){
        DeleteArticlesLocally task = new DeleteArticlesLocally(prefs,articleDao,context);
        task.execute();
    }


    public void InitGoogleClient(final FragmentActivity context){

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
        mGoogleApiclient.connect();

    }





    class DeleteArticlesLocally extends AsyncTask<Void,Void,Void> {

        SharedPreferences prefs;
        ArticleDao articleDao;
        FragmentActivity context;

        DeleteArticlesLocally(SharedPreferences prefs,ArticleDao articleDao,final FragmentActivity context){
            this.prefs=prefs;
            this.articleDao=articleDao;
            this.context = context;
        }


        @Override
        protected Void doInBackground(Void... voids) {


            if(mGoogleApiclient != null && mGoogleApiclient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiclient).setResultCallback(
                        new ResultCallback<com.google.android.gms.common.api.Status>() {
                            @Override
                            public void onResult(com.google.android.gms.common.api.Status status) {
                                // [START_EXCLUDE]
//                        updateUI(false);
                                // [END_EXCLUDE]
                            }
                        });
            }

            prefs.edit().clear().commit();
            articleDao.deleteAll();
            FirebaseAuth.getInstance().signOut();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            status.deletioncomplete();
        }
    }


     class GetArticleTask extends AsyncTask<Boolean,Void,List<Article>> {

        String usermail;

        public GetArticleTask(String usermail){
          this.usermail=usermail;
        }

        @Override
        protected List<Article> doInBackground(Boolean... IsRemotely) {

            List<Article> articles = articlesRepository.loadArticles(IsRemotely[0],usermail);
            return articles;
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            super.onPostExecute(articles);
            status.loadArticles(articles);
        }
    }


    public interface State{
        void loadArticles(List<Article> articles);
        void deletioncomplete();
    }
}
