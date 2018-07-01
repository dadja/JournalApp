package reptxstudio.journalapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by DadjaBASSOU on 10/4/16.
 */

public class Config {


   private static final String FIRSTTIME = "firstime";

   public static final String FIRSTOREARTICLESKEY="Articles";
   public static final String FIRSTOREUSERSKEY="Users";
   public static String SHARED_PREF_NAME="yourjournalapp";
   public static String BASE_URL="http://myjournalappfirebase.com";



   public static String GetCurrentDate()
   {
      Calendar c = Calendar.getInstance();
      SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
      String formattedDate = df.format(c.getTime());

          return formattedDate;
   }


    public static String GetCurrentDate(String basedate)
    {

        SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-dd");
        String formattedDate = df.format(new Date(basedate));

        return formattedDate;
    }



}
