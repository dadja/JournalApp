package reptxstudio.journalapp.mvp.presenter;

import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.mvp.model.ArticleDetailModel;
import reptxstudio.journalapp.mvp.view.ArticleDetailView;

public class ArticleDetailPresenter {

    ArticleDetailView articleDetailView;
    ArticleDetailModel articleDetailModel;

    public ArticleDetailPresenter(ArticleDetailView articleDetailView){
        this.articleDetailView=articleDetailView;
    }


    public void EditArticle(Article article){
        articleDetailView.editArticle(article);
    }
}
