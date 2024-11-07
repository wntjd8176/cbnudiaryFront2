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
class MyFirebaseMessagingService:FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // 메시지를 수신하면 알림을 표시
        remoteMessage.notification?.let {
            sendNotification(it.title, it.body)
        }
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        // MainActivity로 이동하는 인텐트 생성
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        // PendingIntent에 FLAG_IMMUTABLE 플래그 추가
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
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
