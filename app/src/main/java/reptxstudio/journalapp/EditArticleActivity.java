package reptxstudio.journalapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reptxstudio.journalapp.application.JournalApplication;
import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.mvp.presenter.ArticleEditPresenter;
import reptxstudio.journalapp.mvp.view.ArticleEditView;

public class EditArticleActivity extends AppCompatActivity implements ArticleEditView{


    ArticleEditPresenter articleEditPresenter;

    @BindView(R.id.entrycontenttext)
    EditText editContent;

    @BindView(R.id.edititle)
    EditText editTitle;

    @BindView(R.id.done)
    Button done;

    @Inject
    ArticleDao articleDao;

    @Inject
    SharedPreferences preferences;


    int ActionMode=-1;
    Article article = null;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article);
        resolvedependency();
        ButterKnife.bind(this);
        articleEditPresenter = new ArticleEditPresenter(this,articleDao);

//        AndroidThreeTen.init(this);

       actionBar = getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);


//        Config.GetCurrentDate(now().toString());

        ActionMode = getIntent().getIntExtra(ArticlesActivity.ArticleDetailMode,-1);
        if(ActionMode != -1){
            if(ActionMode == ArticlesActivity.EditArticleMode){

                actionBar.setTitle(getString(R.string.updatenote));

                article = getIntent().getParcelableExtra(ArticlesActivity.ArticleDetail);
                if(article != null){
                    editTitle.setText(article.getTitle());
                    editContent.setText(article.getContenu());
                }
            }
            else{
                actionBar.setTitle(getString(R.string.newnote));
            }
        }
        else{
            actionBar.setTitle(getString(R.string.newnote));
        }
    }

    private void resolvedependency() {
        ((JournalApplication) getApplication()).getComponent().inject(EditArticleActivity.this);
    }



    @OnClick(R.id.done)
     public void  modiforaddArticle (View view)
    {
         String content = editContent.getText().toString();
         String date = getactualdate();
         String title =editTitle.getText().toString();
         String useremail= preferences.getString(SignInActivity.User_Email,getString(R.string.johnemail));
         articleEditPresenter.validatedataandinsertorupdate(article,title,content,date,useremail,articleDao,ActionMode);
    }

    @Override
    public void error(String message) {
        Snackbar.make(done,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorTitle(String message) {
        Snackbar.make(done,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorContent(String message) {
        Snackbar.make(done,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void dateError(String message) {
        Snackbar.make(done,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void ArticlehasbeenInserted(String message) {
        Snackbar.make(done,message,Snackbar.LENGTH_LONG).show();
    }


//    public Instant now() {
////        Log.d("Time","my time is "+Instant.now());
//        return Instant.now();
//    }



    public String getactualdate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat =new  SimpleDateFormat(" dd/MM/yyyy");
//          val dateFormat = SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy")
//         dateFormat.format(cal.getTime());
         return dateFormat.format(cal.getTime());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
