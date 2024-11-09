package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
private const val TAG_Diary = "ic_home"
class HomeFragment : Fragment() {
    private lateinit var bottomNavActivity: BottomNavActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            if (it is BottomNavActivity) {
                bottomNavActivity = it
            } else {
                throw IllegalStateException("Activity must implement BottomNavActivity")
            }
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

        var locateBtn = rootView.findViewById<Button>(R.id.locate_button)
        homeDate.text = todayDate

        locateBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val locateFragment = LocateInfoFragment.newInstance(bottomNavActivity)
                transaction.replace(R.id.mainFrameLayout, locateFragment)
                transaction.addToBackStack(null)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 회원가입시, 유저가 입력한 태명 값을 db에서 가져와야 함
        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance(bottomNavActivity: BottomNavActivity) : HomeFragment{
            val fragment = HomeFragment()
            fragment.bottomNavActivity = bottomNavActivity
            return fragment
        }
    }
}