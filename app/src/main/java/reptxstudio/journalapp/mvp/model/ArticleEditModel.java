package reptxstudio.journalapp.mvp.model;

import android.os.AsyncTask;

import reptxstudio.journalapp.ArticlesActivity;
import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.repository.ArticlesRepository;

public class ArticleEditModel {

       State status;
       ArticlesRepository articlesRepository;

       public ArticleEditModel(ArticlesRepository articlesRepository,State status){
           this.status =status;
           this.articlesRepository=articlesRepository;
       }

     public interface State{
         void ArticleSaved(String message);
     }


    public void SaveArticleLocally(Article article,ArticleDao articleDao,int actionmode){

        ArticleTask task= new ArticleTask(articleDao,actionmode);
        task.execute(article);
    }


    public class ArticleTask extends AsyncTask<Article,Void,Article> {

        ArticleDao articleDao;
        int actionmode;

        public ArticleTask(ArticleDao articleDao,int actionmode)
        {
            this.articleDao=articleDao;
            this.actionmode=actionmode;
        }

        @Override
        protected Article doInBackground(Article... articles) {
            if(actionmode == ArticlesActivity.NewArticleMode)
            {
                articleDao.insert(articles);
            }
            else {
                articleDao.update(articles);
            }

            return articles[0];
        }

        //TODO push article from here to firebase
        @Override
        protected void onPostExecute(Article article) {
            super.onPostExecute(article);
            if(actionmode == ArticlesActivity.NewArticleMode)
                status.ArticleSaved("Article has been well inserted ");
            else
                status.ArticleSaved("Article has been well updated ");
             //pushit online
            articlesRepository.addArticle(article);
        }
    }
}
