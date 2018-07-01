package reptxstudio.journalapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import reptxstudio.journalapp.database.dao.ArticleDao;
import reptxstudio.journalapp.database.model.Article;


/**
 * Created by DadjaBASSOU on 12/28/17.
 */


@Database(entities = {Article.class},version = 3)
public abstract class JournalDatabase extends RoomDatabase {


    private static final String DB_NAME = "yourjournal.db";
    private static volatile JournalDatabase instance;

    //force migration from old data to  new database and keeping old data
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static synchronized JournalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static JournalDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                JournalDatabase.class,
                DB_NAME)
                //force migration by dropping old data and set up new database for new data
                .fallbackToDestructiveMigration()
                .build();
    }


//    public abstract UserDao getUserDao();
    public abstract ArticleDao getArticleDao();

}
