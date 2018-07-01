package reptxstudio.journalapp.database.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

//@Entity(foreignKeys = @ForeignKey(entity = User.class,
//        parentColumns = "id",
//        childColumns = "userid",
//        onDelete = CASCADE))

@Entity
public class Article implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String contenu;
    private String date;
    private String type;
//    @ColumnInfo(name = "userid")
//    private int idUser;
    private String UserEmail;



    @Ignore
    public Article(){

    }



    public Article(int id, String title, String contenu, String date, String type,String UserEmail) {
        this.id = id;
        this.title = title;
        this.contenu = contenu;
        this.date = date;
        this.type = type;
        this.UserEmail=UserEmail;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }



    @Ignore
    protected Article(Parcel in) {
        id = in.readInt();
        title = in.readString();
        contenu = in.readString();
        date = in.readString();
        type = in.readString();
        UserEmail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(contenu);
        dest.writeString(date);
        dest.writeString(type);
        dest.writeString(UserEmail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
