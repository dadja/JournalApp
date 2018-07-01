package reptxstudio.journalapp.mvp.model;

import android.content.SharedPreferences;
import android.text.TextUtils;

import reptxstudio.journalapp.SignInActivity;

public class SignInModel {



    public void SaveUser(String name,String email,String numtel,SharedPreferences prefs){

        SharedPreferences.Editor SaveUserData = prefs.edit();

        if(email != null && !TextUtils.isEmpty(email))
            SaveUserData.putString(SignInActivity.User_Email,email);

        if(name != null && !TextUtils.isEmpty(name))
            SaveUserData.putString(SignInActivity.User_Name,name);


        if(numtel !=  null && !TextUtils.isEmpty(numtel))
            SaveUserData.putString(SignInActivity.User_Cellphone,numtel);

        SaveUserData.commit();


    }


}
