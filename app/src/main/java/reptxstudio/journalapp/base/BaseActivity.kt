package reptxstudio.totalapp.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(){
//    private var mSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        registerReceiver(ConnectivityReceiver(),
//                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


    private fun showMessage(isConnected: Boolean) {

        if (!isConnected) {

            val messageToUser = "You are offline now." //TODO

//            mSnackBar = Snackbar.make(findViewById(R.id.rootLayout), messageToUser, Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
//            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
//            mSnackBar?.show()
        } else {
//            mSnackBar?.dismiss()
        }


    }

    override fun onResume() {
        super.onResume()

//        ConnectivityReceiver.connectivityReceiverListener = this
    }

//    /**
//     * Callback will be called when there is change
//     */
//    override fun onNetworkConnectionChanged(isConnected: Boolean) {
//        showMessage(isConnected)
//    }
}