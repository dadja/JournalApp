package reptxstudio.journalapp.mvp.presenter;

import android.text.TextUtils;

import reptxstudio.journalapp.ArticlesActivity;
import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.mvp.model.ArticleEditModel;
import reptxstudio.journalapp.mvp.view.ArticleEditView;
import reptxstudio.journalapp.repository.ArticlesRepository;
import reptxstudio.journalapp.repository.local.ArticleLocalDataSource;
import reptxstudio.journalapp.repository.remote.ArticleRemoteDataSource;

public class ArticleEditPresenter implements ArticleEditModel.State {

    ArticleEditView articleEditView;

    ArticleEditModel articleEditModel;

    ArticlesRepository articlesRepository;


    public  ArticleEditPresenter(ArticleEditView articleEditView, ArticleDao articleDao){

         this.articleEditView=articleEditView;
         articlesRepository = new ArticlesRepository(new ArticleLocalDataSource(articleDao),new ArticleRemoteDataSource());
         articleEditModel = new ArticleEditModel(articlesRepository,this);
    }


    public void validatedataandinsertorupdate(Article updatearticle,String title, String content, String actualdate, String useremail, ArticleDao articleDao,int actionMode){

        if(!TextUtils.isEmpty(content)){

            if(!TextUtils.isEmpty(title)){

                if(!TextUtils.isEmpty(actualdate))
                {

                    Article article;
                    //valid email and actualdate and then process to save data
                    if(actionMode == ArticlesActivity.NewArticleMode)
                    {
                        article = new Article();
                    }
                    else
                    {
                        article=updatearticle;
                    }

                    article.setContenu(content);
                    article.setTitle(title);
                    article.setUserEmail(useremail);
                    article.setDate(actualdate);
                    article.setType("CommonType");

                    //TODO launch this inside an asynctask ou asyntaskloader
                      articleEditModel.SaveArticleLocally(article,articleDao,actionMode);

                }
                else
                    {
                       articleEditView.dateError("There is no Date");
                    }


            }
            else{
                articleEditView.errorTitle("Titre Vide");
            }

        }
        else{
            articleEditView.errorContent("Contenu Vide");
        }

    }


    public void ThrowErrortoView(String message){
            articleEditView.error(message);
    }

    @Override
    public void ArticleSaved(String message) {
        articleEditView.ArticlehasbeenInserted(message);
    }


}
