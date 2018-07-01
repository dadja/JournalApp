package reptxstudio.journalapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import reptxstudio.journalapp.database.model.Article;


@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article... article);

    @Update
    void update(Article article);

    @Update
    void update(Article... article);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM Article")
    List<Article> getAllArticles();


    @Query("DELETE FROM Article")
    void deleteAll();

    @Query("SELECT * FROM Article WHERE id == :id")
    Article getArticleById(int id);
}
