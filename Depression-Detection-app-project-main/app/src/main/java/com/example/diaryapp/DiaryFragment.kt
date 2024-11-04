package com.example.diaryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DiaryFragment : Fragment() {
    private var selectedDate: String? = ARG_SELECTED_DATE
    private lateinit var bottomNavActivity: BottomNavActivity



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedDate = it.getString(DiaryFragment.ARG_SELECTED_DATE)
            bottomNavActivity = it.getSerializable(DiaryFragment.ARG_BOTTOM_NAV_ACTIVITY) as? BottomNavActivity
                ?: throw IllegalArgumentException("BottomNavActivity must not be null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_diary_page, container, false)
        var rewriteButton = rootView.findViewById<Button>(R.id.rewrite_button)
        var diaryDate = rootView.findViewById<TextView>(R.id.diary_date)
        var deleteButton = rootView.findViewById<Button>(R.id.delete_button)
        var diaryTitle = rootView.findViewById<TextView>(R.id.diary_page_title)
        var diaryContent = rootView.findViewById<TextView>(R.id.diary_page_content)
        var diaryEmotion = rootView.findViewById<TextView>(R.id.diary_page_emotion)
        var diaryLength = rootView.findViewById<TextView>(R.id.diary_page_count)

        var todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        Log.i("selectedDate???", selectedDate.toString())

        if (selectedDate.isNullOrEmpty()) {
            diaryDate.text = todayDate

            // test 파일로 작성, 추후 DB에 담긴 일기로 가져와야 함
            for (diaryEntry in test()) {
                val date = diaryEntry.date

                if (date == todayDate) {
                    diaryTitle.text = diaryEntry.title
                    diaryContent.text = diaryEntry.content
                    diaryEmotion.text = diaryEntry.emotion
                    diaryLength.text = diaryEntry.content.length.toString() + "/1000"
                }
            }
        } else {
            diaryDate.text = selectedDate.toString()

            // test 파일로 작성, 추후 DB에 담긴 일기로 가져와야 함
            for (diaryEntry in test()) {
                val date = diaryEntry.date

                if (date == selectedDate) {
                    diaryTitle.text = diaryEntry.title
                    diaryContent.text = diaryEntry.content
                    diaryEmotion.text = diaryEntry.emotion
                    diaryLength.text = diaryEntry.content.length.toString() + "/1000"
                }
            }
        }


        // 삭제버튼 동작 이벤트 작성
        deleteButton.setOnClickListener {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        rewriteButton.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val writeDiaryFragment = WriteDiaryFragment.newInstance(bottomNavActivity, selectedDate)
                transaction.replace(R.id.mainFrameLayout, writeDiaryFragment)
                transaction.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rootView
    }

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"
        private const val ARG_BOTTOM_NAV_ACTIVITY = "bottom_nav_activity"

        @JvmStatic
        fun newInstance(bottomNavActivity: BottomNavActivity, selectedDate: String?): DiaryFragment {
            return DiaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTED_DATE, selectedDate.orEmpty())
                    putSerializable(DiaryFragment.ARG_BOTTOM_NAV_ACTIVITY, bottomNavActivity)
                }
            }
        }
    }
}