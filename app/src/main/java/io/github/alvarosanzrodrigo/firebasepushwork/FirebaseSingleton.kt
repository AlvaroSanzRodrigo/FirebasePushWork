package io.github.alvarosanzrodrigo.firebasepushwork

import android.content.ContentValues.TAG
import android.support.v4.app.NotificationCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


// Singleton class que al final no me ha dicho el profe
class FirebaseSingleton : FirebaseMessagingService() {

    //Aqui cojo el token de identificacion del dispositivo
    override fun onNewToken(token: String?) {
        Log.d("SerivicioFirebase", "Refreshed token: " + token!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "io.github.alvarosanzrodrigo"
            val descriptionText = "io.github.alvarosanzrodrigo"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("io.github.alvarosanzrodrigo", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        //aqui recivo la notificacion de firebase
        Log.d("SerivicioFirebase", "From: " + remoteMessage?.from)
        //Poner esto esa mal pero aqui creo el channel cuando me llega el mensaje
        createNotificationChannel()

        //Aqui creo una notificacion nueva con un builder
        val mBuilder = NotificationCompat.Builder(this, "io.github.alvarosanzrodrigo")
            .setSmallIcon(R.drawable.notification_template_icon_low_bg)
            .setContentTitle(remoteMessage?.notification?.title)
            .setContentText(remoteMessage?.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        //Esto hace el intent de la notificacion automaticamente
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, mBuilder.build())
        }
    }
}