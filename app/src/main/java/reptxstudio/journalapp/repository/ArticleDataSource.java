package reptxstudio.journalapp.repository;

import java.util.List;

import reptxstudio.journalapp.database.model.Article;

public interface ArticleDataSource {

    List<Article> loadArticles(boolean forceRemote,String UserEmail);

    void addArticle(Article article);

    void clearData(String articleuseremail);


}
