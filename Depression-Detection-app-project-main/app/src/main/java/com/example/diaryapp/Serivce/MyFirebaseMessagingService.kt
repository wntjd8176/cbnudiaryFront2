package com.example.diaryapp.Serivce

import com.example.diaryapp.MainActivity
import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationCompat
import com.example.diaryapp.R
import android.app.NotificationChannel
import android.util.Log

import android.os.Build
class MyFirebaseMessagingService:FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // 메시지를 수신하면 알림을 표시
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM", "Message data payload: ${remoteMessage.data}")

            val messageType = remoteMessage.data["messageType"] ?: "default"


            // 메시지 유형에 따라 알림 처리 분기
            if (messageType == "depress_5") {
                Log.d("FCM", "Depression alert triggered (5 times)")
                //remoteMessage.notification?.let {
                   sendNotification("우울감감지", "5회", true) // 5번째 알림일 때 Fragment로 이동
                //}
            } else {
                Log.d("FCM", "General notification")
                remoteMessage.notification?.let {
                    sendNotification(it.title,it.body ,false) // 일반 알림
                }
            }
        } else {
            Log.d("FCM", "No data payload in message")
        }
    }

    private fun sendNotification(title: String?, messageBody: String?,moveToFragment: Boolean) {
        Log.d("FCM", "sendNotification called with title: $title, body: $messageBody, moveToFragment: $moveToFragment")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel"
            val channelName = "Default Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
        // MainActivity로 이동하는 인텐트 생성
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            if (messageBody=="5회") {
                putExtra("openFragment", "LocateInfoFragment") // 5번째 알림일 경우 Fragment를 열 수 있도록 플래그 추가
            }
        }

        // PendingIntent에 FLAG_IMMUTABLE 플래그 추가
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            //PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // NotificationCompat.Builder 설정
        val notificationBuilder = NotificationCompat.Builder(this, "default_channel")
            .setSmallIcon(R.drawable.ic_notification) // 알림 아이콘 설정
            .setContentTitle(title) // 알림 제목
            .setContentText(messageBody) // 알림 내용
            .setAutoCancel(true) // 알림 클릭 시 자동 닫힘
            .setContentIntent(pendingIntent) // 알림 클릭 시 실행할 PendingIntent

        // NotificationManager를 ContextCompat로 안전하게 가져오기
        val notificationManager = ContextCompat.getSystemService(
            this, NotificationManager::class.java
        )

        // 알림 표시
        notificationManager?.notify(0, notificationBuilder.build())
    }


}
