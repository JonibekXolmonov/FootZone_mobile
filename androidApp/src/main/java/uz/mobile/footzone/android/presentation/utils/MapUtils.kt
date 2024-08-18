package uz.mobile.footzone.android.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import uz.mobile.footzone.android.R
import uz.mobile.footzone.presentation.main.Location

@SuppressLint("QueryPermissionsNeeded")
fun Context.shareStadiumLocationToNavigators(location: Location) {
    try {
        val uriYandex =
            "yandexnavi://build_route_on_map?lat_to=${location.latitude}&lon_to=${location.longitude}"
        val intentYandex = Intent(Intent.ACTION_VIEW, Uri.parse(uriYandex))
        intentYandex.setPackage("ru.yandex.yandexnavi")

        val uriGoogle =
            Uri.parse("google.navigation:q=${location.latitude},${location.longitude}&mode=w")
        val intentGoogle = Intent(Intent.ACTION_VIEW, uriGoogle)
        intentGoogle.setPackage("com.google.android.apps.maps")

        val chooserIntent =
            Intent.createChooser(intentYandex, getString(R.string.chooser_title))
        val arr = arrayOfNulls<Intent>(2)
        arr[0] = intentYandex
        arr[1] = intentGoogle

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arr)
        val activities = packageManager.queryIntentActivities(chooserIntent, 0)
        if (activities.size > 0) {
            startActivity(chooserIntent)
        } else {
            Toast.makeText(
                this,
                getString(R.string.navigator_not_avialable),
                Toast.LENGTH_SHORT
            ).show()
        }
    } catch (e: Exception) {

    }
}