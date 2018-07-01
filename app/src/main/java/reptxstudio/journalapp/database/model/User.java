package reptxstudio.journalapp.database.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//@Entity
public class User {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  int id;
    private String nom;
    private String email;
    private String numerotel;





    public User(@NonNull int id,String numerotel, String nom,String email) {
        this.numerotel = numerotel;
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public String getNumerotel() {
        return numerotel;
    }

    public void setNumerotel(String numerotel) {
        this.numerotel = numerotel;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
