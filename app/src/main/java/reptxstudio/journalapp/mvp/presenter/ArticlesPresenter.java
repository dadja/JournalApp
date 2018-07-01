package reptxstudio.journalapp.mvp.presenter;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import reptxstudio.journalapp.ArticlesActivity;
import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.mvp.model.ArticlesModel;
import reptxstudio.journalapp.mvp.view.ArticlesView;
import reptxstudio.journalapp.repository.ArticlesRepository;
import reptxstudio.journalapp.repository.local.ArticleLocalDataSource;
import reptxstudio.journalapp.repository.remote.ArticleRemoteDataSource;

public class ArticlesPresenter implements ArticlesModel.State{

    ArticlesView articlesView;
    ArticlesRepository articlesRepository;
    ArticlesModel articlesModel;

    public ArticlesPresenter(ArticlesView articlesView, ArticleDao articleDao){
        this.articlesView=articlesView;
        articlesRepository = new ArticlesRepository(new ArticleLocalDataSource(articleDao),new ArticleRemoteDataSource());
        articlesModel= new ArticlesModel(articlesRepository,this);

    }


    /**
     * Updates view after loading data is completed successfully.
     */
//    private void handleReturnedData(ArrayList<Article> list) {
//        articlesView.stopLoadingIndicator();
//        if (list != null && !list.isEmpty()) {
//            articlesView.loadArticles(Flowable.just(list));
//        } else {
//            articlesView.showNoDataMessage();
//        }
//    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private void handleError(Throwable error) {
        articlesView.stopLoadingIndicator();
        articlesView.showErrorMessage(error.getLocalizedMessage());
    }

    public void CreateNewNote() {
        articlesView.createNewNote();
    }

    public void showArticleDetail(Article article) {
        articlesView.showArticleDetail(article);
    }

    //look for data..
    public void GetArticles(boolean Remotely,String usermail) {
        articlesModel.GetArticles(Remotely,usermail);

    }

    @Override
    public void loadArticles(List<Article> articles) {
        articlesView.loadArticles(articles);
    }

    @Override
    public void deletioncomplete() {
        articlesView.SignOut();
    }


    public void SignOut(SharedPreferences prefs, ArticleDao articleDao, FragmentActivity context) {
        articlesModel.DeleteData(prefs,articleDao,context);
    }


    public void InitGoogleClient(ArticlesActivity articlesActivity) {
        articlesModel.InitGoogleClient(articlesActivity);
    }
}
