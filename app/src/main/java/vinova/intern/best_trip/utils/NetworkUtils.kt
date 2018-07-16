package vinova.intern.best_trip.utils

import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException

class NetworkUtils{
fun isNetworkAvailable(context:Context?):Boolean{
    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}

fun isOnline(): Boolean {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val     exitValue = ipProcess.waitFor()
        return (exitValue == 0)
    }
    catch (e : IOException){ e.printStackTrace(); }
    catch ( e:InterruptedException )          { e.printStackTrace(); }
    return false
}
}