package reptxstudio.journalapp.repository.local;

import java.util.List;

import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.repository.ArticleDataSource;

public class ArticleLocalDataSource implements ArticleDataSource {


    private ArticleDao articleDao;

    public ArticleLocalDataSource(ArticleDao articleDao){
        this.articleDao=articleDao;
    }

    @Override
    public List<Article> loadArticles(boolean forceRemote,String UserEmail) {
        return  articleDao.getAllArticles();
    }

    //no required for local processing we already doing this in another class :)
    @Override
    public void addArticle(Article article) {
         articleDao.insert(article);
    }

    @Override
    public void clearData(String useremail) {
        articleDao.deleteAll();
    }


}
