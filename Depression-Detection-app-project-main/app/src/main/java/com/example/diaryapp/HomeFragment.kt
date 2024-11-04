package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_home, container, false)

        var homeDate = rootView.findViewById<TextView>(R.id.todayDate_home)
        var babyName = rootView.findViewById<TextView>(R.id.baby_name_home)
        var todayDate = SimpleDateFormat("yyyy-MM-dd (E)").format(Date())

        homeDate.text = todayDate

        // 회원가입시, 유저가 입력한 태명 값을 db에서 가져와야 함
        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}