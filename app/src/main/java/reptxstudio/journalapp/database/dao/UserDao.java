package reptxstudio.journalapp.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import reptxstudio.journalapp.database.model.User;


/**
 * Created by DadjaBASSOU on 12/28/17.
 */

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("DELETE * FROM User")
    List<User> deleteAllUsers();
}
