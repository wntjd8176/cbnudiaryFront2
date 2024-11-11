package com.example.diaryapp
import android.util.Log
import  com.example.diaryapp.Network.RetrofitInstance
import com.example.diaryapp.Serivce.LoginApiService
import com.example.diaryapp.Serivce.LoginApiService.LoginRequest
import com.example.diaryapp.Serivce.LoginApiService.LoginResponse
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.analytics.FirebaseAnalytics
import androidx.core.app.ActivityCompat
import android.Manifest
import android.os.Build
import com.example.diaryapp.LocateInfoFragment
class MainActivity : AppCompatActivity() {
    lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        var findID: TextView = findViewById(R.id.findID)
        var findPW: TextView = findViewById(R.id.findPW)
        var register: TextView = findViewById(R.id.register)
        var login: Button = findViewById(R.id.login)

        val editTextId = findViewById<TextInputEditText>(R.id.editTextId)
        val editTextPassword = findViewById<TextInputEditText>(R.id.editTextPassword)
        val loginApiService = RetrofitInstance.create(LoginApiService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }

        findID.setOnClickListener {
            val intent = Intent(this, FindIDPage::class.java)
            startActivity(intent)
        }

        findPW.setOnClickListener {
            val intent = Intent(this, FindPWPage::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val id = editTextId.text.toString()
            val password = editTextPassword.text.toString()

            if (id.isNotEmpty() && password.isNotEmpty()) {
                // FirebaseMessaging을 사용하여 FCM 토큰 가져오기
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("Login", "Fetching FCM token failed", task.exception)
                        return@addOnCompleteListener
                    }

                    val fcmToken = task.result
                    Log.d("Login", "FCM Token: $fcmToken")

                    val request = LoginRequest(id, password, fcmToken)

                    val call = loginApiService.login(request)
                    call.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
                                Log.d("Login", "Response: $loginResponse")
                                if (loginResponse?.status == "success") {

                                    val userID = loginResponse.userID
                                    val sharedPref = Myapp.getPreferences()
                                    with(sharedPref.edit()) {
                                        putString(
                                            "loggedInUserId",
                                            loginResponse.userID
                                        ) // 현재 로그인된 사용자 ID 저장
                                        apply()
                                    }
                                    Log.d("Login", "User ID saved: ${loginResponse.userID}")

                                    val intent =
                                        Intent(this@MainActivity, BottomNavActivity::class.java)
                                    startActivity(intent)

                                } else {
                                    Log.e(
                                        "SERVER_ERROR",
                                        "에러 코드: ${response.code()}, 에러 메시지: ${
                                            response.errorBody()?.string()
                                        }"
                                    )
                                    Toast.makeText(
                                        this@MainActivity,
                                        loginResponse?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Log.e(
                                    "SERVER_ERROR",
                                    "에러 코드: ${response.code()}, 에러 메시지: ${
                                        response.errorBody()?.string()
                                    }"
                                )
                                Toast.makeText(
                                    this@MainActivity,
                                    " 아이디나 비밀번호를 확인해주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                this@MainActivity,
                                "네트워크 오류가 발생했습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
                } else {
                    editTextId.error = "아이디를 입력하세요"
                    editTextPassword.error = "비밀번호를 입력하세요"
                }


            }
        if (intent.hasExtra("openFragment")) {
            val fragmentName = intent.getStringExtra("openFragment")
            if (fragmentName == "LocateInfoFragment") {
                // LocateinfoFragment 로드
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LocateInfoFragment.newInstance(null))
                    .commit()
            }
        }




        }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent) // 인텐트를 업데이트하여 이후에 접근할 수 있도록 설정합니다.

        if (intent != null && intent.hasExtra("openFragment")) {
            val fragmentName = intent.getStringExtra("openFragment")
            Log.d("MainActivity", "onNewIntent - Fragment to be opened: $fragmentName")

            if (fragmentName == "LocateInfoFragment") {
                Log.d("MainActivity", "onNewIntent - Loading LocateInfoFragment...")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LocateInfoFragment.newInstance(null))
                    .commit()
            }
        }
    }





    }




