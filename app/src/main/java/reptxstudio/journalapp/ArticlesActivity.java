package reptxstudio.journalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reptxstudio.journalapp.adapter.ArticlesAdapter;
import reptxstudio.journalapp.application.JournalApplication;
import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.mvp.presenter.ArticlesPresenter;
import reptxstudio.journalapp.mvp.view.ArticlesView;

public class ArticlesActivity extends AppCompatActivity implements ArticlesView,ArticlesAdapter.Onpositionlistener {


    public static final String ArticleDetail="articledetail";
    public static final String ArticleDetailMode="articledetailmode";
    public static final int EditArticleMode = 0;
    public static final int NewArticleMode = 1;

    ArticlesPresenter articlesPresenter;

    @BindView(R.id.notesrecyclerview)
    RecyclerView ArticlesRecyclerView;

    ArticlesAdapter adapter;

    @BindView(R.id.addnotesbtn)
    FloatingActionButton AddNoteBtn;


    @BindView(R.id.userinfos)
    TextView userinfos;


    @Inject
    SharedPreferences pref;

    @Inject
    ArticleDao articleDao;

    ArrayList<Article> articles = new ArrayList<>();
    RecyclerView.LayoutManager manager;

    boolean isFirsLogin=false;

    String name ;//= pref.getString(SignInActivity.User_Name,getString(R.string.johnname));
    String Email ;//= pref.getString(SignInActivity.User_Email,getString(R.string.johnemail));
    String Cellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        resolvedependency();
        ButterKnife.bind(this);
        articlesPresenter = new ArticlesPresenter(this,articleDao);
        articlesPresenter.InitGoogleClient(this);

        isFirsLogin= getIntent().getBooleanExtra(SignInActivity.FirstLogin,false);

         name = pref.getString(SignInActivity.User_Name,getString(R.string.johnname));
         Email = pref.getString(SignInActivity.User_Email,getString(R.string.johnemail));
         Cellphone = pref.getString(SignInActivity.User_Cellphone,getString(R.string.johnnumber));

        getSupportActionBar().setTitle(R.string.notes);

        userinfos.setText("Welcome in : "+name);

        setupViews();


    }

    private void setupViews() {
        adapter = new ArticlesAdapter(articles,this);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        ArticlesRecyclerView.setLayoutManager(manager);
        ArticlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ArticlesRecyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(isFirsLogin){
            //go fetch data online we new here
//            isFirsLogin =false;
            Log.d("Articles ","FirstTime");
            articlesPresenter.GetArticles(true,Email);
        }
        else{
            Log.d("Articles ","Not First Time");
            //go fetch data localy first
        articlesPresenter.GetArticles(false,Email);
        }
    }

    @OnClick(R.id.addnotesbtn)
    public void AddNote(View view){
        articlesPresenter.CreateNewNote();
    }


    private void resolvedependency() {
        ((JournalApplication) getApplication()).getComponent().inject(ArticlesActivity.this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.articlesactivitymenu, menu);

        // Setup search widget in action bar
//        SearchView searchView = (SearchView) menu.findItem(R.id.editentry).getActionView();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.signout:
                articlesPresenter.SignOut(pref,articleDao,this);
                break;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void loadArticles(List<Article>  articles){
        if(isFirsLogin){
            isFirsLogin =false;
            if(articles != null && articles.size() > 0){
                Log.d("Articles "+articles.get(0).getTitle()," <==> "+articles.get(0).getContenu());
                Log.d("Articles "+articles.get(1).getTitle()," <==> "+articles.get(1).getContenu());
            }
            Log.d("First Remote Articles ","Received");
            adapter.addData(articles);
        }
        else{
            Log.d("Local Articles ","Received");
            adapter.replaceData(articles);
        }


    }

    @Override
    public void clearArticles() {

    }

    @Override
    public void showNoDataMessage() {

    }

    @Override
    public void showErrorMessage(String error) {

    }

    @Override
    public void showArticleDetail(Article article) {
        Intent gotodetail  = new Intent(this,ArticleDetailActivity.class);
        //TOOD PASS THE ARTICLE HERE
        gotodetail.putExtra(ArticleDetail,article);
        startActivity(gotodetail);
    }

    @Override
    public void stopLoadingIndicator() {

    }

    @Override
    public void showEmptySearchResult() {

    }

    @Override
    public void createNewNote() {
        Intent gotodetail  = new Intent(this,EditArticleActivity.class);
        gotodetail.putExtra(ArticleDetailMode,NewArticleMode);
        startActivity(gotodetail);
    }

    @Override
    public void SignOut() {
        Intent gooback = new Intent(this,SignInActivity.class);
        startActivity(gooback);
    }

    @Override
    public void onclick(int position) {

//        Snackbar.make(ArticlesRecyclerView,"choosen is "+position,Snackbar.LENGTH_LONG).show();
        articlesPresenter.showArticleDetail(articles.get(position));
        //on item clicked...
//
    }


//    @Override
//    protected void onStart() {
//        articlesPresenter.InitGoogleClient(this);
//        super.onStart();
//    }
}
