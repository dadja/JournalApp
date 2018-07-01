package reptxstudio.journalapp.repository;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import reptxstudio.journalapp.database.model.Article;



public  class ArticlesRepository implements ArticleDataSource {


    private ArticleDataSource remoteDataSource;
    private ArticleDataSource localDataSource;

    @VisibleForTesting  List<Article> caches;



    public ArticlesRepository(ArticleDataSource localDataSource,
                               ArticleDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;

        caches = new ArrayList<>();
    }


    @Override
    public List<Article> loadArticles(boolean forceRemote,String UserEmail) {


        if (forceRemote) {
            return refreshData(UserEmail);
        } else {
            if (caches.size() > 0) {
                // if cache is available, return it immediately
                return caches;
            } else {
                // else return data from local storage

                return   localDataSource.loadArticles(false,UserEmail);
                        //If local data is empty, fetch from remote {FireStore Instead} source instead.
//                         .switchIfEmpty(refreshData());

            }
        }

    }


      List<Article> refreshData(String UserEmail) {
          Log.d("Articles","Refreshing from firebase");
        return remoteDataSource.loadArticles(true,UserEmail);
    }

    // we using this only to push data online we could have also been using it to save data locally
    //which means we should have need to verify if its a local or remote action
    @Override
    public void addArticle(Article article) {

        remoteDataSource.addArticle(article);

    }

    @Override
    public void clearData(String useremail) {
          remoteDataSource.clearData(useremail);
    }



}
