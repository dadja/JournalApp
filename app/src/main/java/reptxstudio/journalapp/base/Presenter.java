package reptxstudio.journalapp.base;

/**
 * Created by DadjaBASSOU on 10/15/16.
 *
 * her we definie what every presenter should have the activity or fragment lifecycle..
 *
 *  L'architecture de l'application repose sur un modele MVP et de ce fait il est necessaire de creer un Presenter
 * avec les methodes associes au cycle de vie d'une activite ou d'un fragment en ce sens que ce presenter y seras intimement lie.
 */

public interface Presenter {

    void onCreate();

    void onPause();

    void onResume();

    void onDestroy();
}
