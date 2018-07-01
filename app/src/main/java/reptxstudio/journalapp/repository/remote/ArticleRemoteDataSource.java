package reptxstudio.journalapp.repository.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import reptxstudio.journalapp.database.model.Article;
import reptxstudio.journalapp.repository.ArticleDataSource;
import reptxstudio.journalapp.utils.Config;

public class ArticleRemoteDataSource implements ArticleDataSource {

        //TODO get data from firestore or send it to firestore from here

    FirebaseFirestore firebaseFirestore;



    public static final String ArticleTitle="title";
    public static final String ArticleContent="contenu";
    public static final String ArticleDate="date";
    public static final String ArticleType="type";
    public static final String ArticleUserEmail="UserEmail";

    public  ArticleRemoteDataSource(){
        firebaseFirestore = FirebaseFirestore.getInstance();
    }



    @Override
    public List<Article> loadArticles(boolean forceRemote,String useremail) {

        final List<Article> remotearticles= new ArrayList<>();

         firebaseFirestore.collection(Config.FIRSTOREUSERSKEY).document(useremail)
                .collection(Config.FIRSTOREARTICLESKEY).get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful() && task.isComplete()){

                            Article myarticle ;

                            for(DocumentSnapshot doc :  task.getResult())
                            {
                                myarticle = doc.toObject(Article.class);
                                Log.d("RemoteData", myarticle.getTitle() + " content => " + myarticle.getContenu()+" size "+task.getResult().size());//
                                 remotearticles.add(myarticle);

                                 //TODO Use eventbus to notify your ArtcilesActivity that you had new data
                            }

                        }

                    }
                }) ;

//        return Flowable.just(remotearticles);

        return remotearticles;
    }

    @Override
    public void addArticle(Article article) {

        HashMap<String,Object> data = new HashMap<>();

        data.put(ArticleTitle,article.getTitle());
        data.put(ArticleContent,article.getContenu());
        data.put(ArticleType,article.getType());
        data.put(ArticleDate,article.getDate());
        data.put(ArticleUserEmail,article.getUserEmail());

        firebaseFirestore.collection(Config.FIRSTOREUSERSKEY).document(article.getUserEmail())
                .collection(Config.FIRSTOREARTICLESKEY).document()
                .set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }})
                .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }});


    }

    @Override
    public void clearData(String articleUserEmail) {
        firebaseFirestore.collection(Config.FIRSTOREUSERSKEY).document(articleUserEmail).delete();
    }





}
