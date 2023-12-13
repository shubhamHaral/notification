package com.example.fcmpushnotification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Build
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId="notification_channel"
const val channelName="com.example.fcmpushnotification"
class MyFirebaseMessagingService:FirebaseMessagingService(){
    // generate the notification
    //attach the notification created with the custom layout
    // show the notification

    override fun onMessageReceived(remotemessage: RemoteMessage) {
        generateNotification(remotemessage.notification!!.title!!,remotemessage.notification!!.title!!)
    }
    fun getRemoteView(title: String,message: String) {
        val remoteView=RemoteViews("com.example.fcmpushnotification",R.layout.notification)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.message,message)
        remoteView.setImageViewResource(R.id.app_logo,R.drawable.img)
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    fun generateNotification(title:String, message:String){
        val intent=Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


        val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        var builder:NotificationCompat.Builder=NotificationCompat.Builder(applicationContext,
            channelId)
            .setSmallIcon(R.drawable.img)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,100))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent);

        builder= builder.setContent(getRemoteView(title,message))

        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            val notificationChannel=NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
      notificationManager.notify(0,builder.build())

    }
}

private fun NotificationCompat.Builder.setContent(remoteView: Unit): NotificationCompat.Builder {
    TODO("Not yet implemented")
}




