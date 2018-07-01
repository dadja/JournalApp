package reptxstudio.journalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import reptxstudio.journalapp.application.JournalApplication;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.mvp.presenter.ArticleDetailPresenter;
import reptxstudio.journalapp.mvp.view.ArticleDetailView;

public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailView {


    @BindView(R.id.notedetailtitle)
    TextView Title;

    @BindView(R.id.contenttextdetail)
    TextView Content;

    @BindView(R.id.detailbtn)
    TextView Update;


    ArticleDetailPresenter articleDetailPresenter;
    Article article= new Article();

    @Inject
    SharedPreferences  prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        resolvedependency();
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Detail Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articleDetailPresenter = new ArticleDetailPresenter(this);
        article = getIntent().getParcelableExtra(ArticlesActivity.ArticleDetail);

        if(article != null){
            Title.setText(article.getTitle());
            Content.setText(article.getContenu());
            Update.setText(" Date : "+article.getDate()+"\n edited by \t\t "+article.getUserEmail()+" \t ");
        }

    }



    private void resolvedependency() {
        ((JournalApplication) getApplication()).getComponent().inject(ArticleDetailActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.resourcemodifcontent, menu);

        // Setup search widget in action bar
//        SearchView searchView = (SearchView) menu.findItem(R.id.editentry).getActionView();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.editentry:
                   articleDetailPresenter.EditArticle(article);
            break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void editArticle(Article article) {
        Intent gotoEditPage = new Intent(this,EditArticleActivity.class);
        gotoEditPage.putExtra(ArticlesActivity.ArticleDetail,article);
        gotoEditPage.putExtra(ArticlesActivity.ArticleDetailMode,ArticlesActivity.EditArticleMode);
        startActivity(gotoEditPage);

    }



}
