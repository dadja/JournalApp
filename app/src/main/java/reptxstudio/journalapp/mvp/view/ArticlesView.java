package reptxstudio.journalapp.mvp.view;

import java.util.List;

import reptxstudio.journalapp.database.model.Article;

public interface ArticlesView {

//    public void loadArticles(Flowable<List<Article>> articles);
    public void loadArticles(List<Article> articles);

    void clearArticles();

    void showNoDataMessage();

    void showErrorMessage(String error);

    void showArticleDetail(Article article);

    void stopLoadingIndicator();

    void showEmptySearchResult();


    void createNewNote();

    void SignOut();


}
