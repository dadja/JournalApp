package reptxstudio.journalapp.base;





import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DadjaBASSOU on 10/15/16.
 * then we create abstract presenter class thet implement our previously created presenter
 *
 *  L'architecture de l'application repose sur un modele MVP et de ce fait il est necessaire de creer un Presenter
 * avec les methodes associes au cycle de vie d'une activite ou d'un fragment en ce sens que ce presenter y seras intimement lie.
 * Ici nous rajoutons les methodes RX pour nous faciliter leurs implementations,utilisation et ou gestion des ressources plus tard
 *
 */

public abstract class BasePresenter implements Presenter{


//    private CompositeSubscription mCompositeSubscriptions;
    private CompositeDisposable mCompositeDisposable;

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

        configureSubscriptions();

    }

//    private CompositeSubscription configureSubscriptions() {
//
//        if(mCompositeSubscriptions == null || mCompositeSubscriptions.isUnsubscribed())
//        {
//            mCompositeSubscriptions = new CompositeSubscription();
//        }
//
//        return mCompositeSubscriptions;
//    }

    private CompositeDisposable configureSubscriptions() {

        if(mCompositeDisposable == null || mCompositeDisposable.isDisposed())
        {
            mCompositeDisposable = new CompositeDisposable();
        }

        return mCompositeDisposable;
    }


    @Override
    public void onDestroy() {
        unsuscribeAll();
    }

//    protected void unsuscribeAll() {
//        if(mCompositeSubscriptions != null)
//        {
//            //lets unscribe
//            mCompositeSubscriptions.unsubscribe();
//            //clear the susbscription object
//            mCompositeSubscriptions.clear();
//            //and make it null to make sure everything was released
//            mCompositeSubscriptions =null;
//        }
//    }

    protected void unsuscribeAll() {
        if(mCompositeDisposable != null)
        {
            //lets unscribe
            mCompositeDisposable.dispose();
            //clear the susbscription object
            mCompositeDisposable.clear();
            //and make it null to make sure everything was released
            mCompositeDisposable =null;
        }
    }

    //Now here is the important part of rx what we want to do is to set up a susbscription
    //and to do so we need obs ervable which catch the data we looking for..
    // an observer to look for data in the obsevable
    // and subscription to be able to nest our observer and abservable to the right threas..
    //As the type can be any as we on the Base lets drop the T Representation

    protected <T>  void subscribe(Observable<T> observable, Observer<T> observer)
    {

                                observable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(observer);

//
//        Disposable subscription= observable
//                //do the observable task which is to fetch data to the server on a new thread
//                .subscribeOn(Schedulers.newThread())
//                //then when we have all these data locally lets get it the UITthread to display it or do whatever we want with it..
//                .observeOn(AndroidSchedulers.mainThread())
//                //when we ask you to unscribe do it on computations
//                .unsubscribeOn(Schedulers.computation())
//                //and the guy who going to see the data you got locally will be the observer..
//                .subscribe(observer);
//        //now that our subscritpion is defined lets add it to our list of predefined subscriptions if they exist..
//        configureSubscriptions().add();
    }








}
