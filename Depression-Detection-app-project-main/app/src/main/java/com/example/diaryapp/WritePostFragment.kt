package com.example.diaryapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WritePostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WritePostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_write_post, container, false)

        var drawerLayout = rootView.findViewById<DrawerLayout>(R.id.drawerLayout)
        var imgMenu = rootView.findViewById<ImageView>(R.id.imageMenu)

        var postTitle = rootView.findViewById<EditText>(R.id.post_title)
        var postContent = rootView.findViewById<EditText>(R.id.post_content)
        var wordCount = rootView.findViewById<TextView>(R.id.user_word_count)
        var writeDate = rootView.findViewById<TextView>(R.id.write_date)
        var saveBtn = rootView.findViewById<Button>(R.id.save_btn)
        var normalPostBtn = rootView.findViewById<RadioButton>(R.id.normal_post_btn)
        var photoPostBtn = rootView.findViewById<RadioButton>(R.id.photo_post_btn)
        var imgView = rootView.findViewById<ImageView>(R.id.imageView1)
        var todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())


        normalPostBtn.setOnClickListener{
            imgView.visibility = View.GONE
        }

        photoPostBtn.setOnClickListener{
            imgView.visibility = View.VISIBLE
        }

        // save 버튼 클릭 시 이벤트
        saveBtn.setOnClickListener {
            val title = postTitle.text.toString()
            val content = postContent.text.toString()
            val userName = "User's Name" // 실제 글을 작성한 유저 이름으로 변경해줘야함

            val postFragment = PostFragment.newInstance(title, content, userName)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainFrameLayout, postFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        // 작성일 = 오늘 날짜
        writeDate.text = todayDate

        // 본문 내용 글씨 카운트
        postContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                wordCount.text = "0/1000"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userInput = postContent.text.toString()
                wordCount.text = userInput.length.toString() + "/1000"
            }

            override fun afterTextChanged(s: Editable?) {
                var userInput = postContent.text.toString()
                wordCount.text = userInput.length.toString() + "/1000"
            }
        }
        )

        imgMenu.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val naviView = rootView.findViewById<NavigationView>(R.id.navigationView)
        naviView.itemIconTintList = null

        naviView.setNavigationItemSelectedListener { memuItem ->
            when (memuItem.itemId) {
                R.id.nav_home -> {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    val communityFragment = CommunityFragment.newInstance("","")
                    transaction.replace(R.id.mainFrameLayout, communityFragment)
                    transaction.commit()
                }
                R.id.nav_hot -> {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    val hotPostFragment = HotPostFragment.newInstance("","")
                    transaction.replace(R.id.mainFrameLayout, hotPostFragment)
                    transaction.commit()
                }
                R.id.nav_photo -> {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    val photoPostFragment = PhotoPostFragment.newInstance("","")
                    transaction.replace(R.id.mainFrameLayout, photoPostFragment)
                    transaction.commit()
                }
                else -> { }
            }
            true
        }

        return rootView
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WritePostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}