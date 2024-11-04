package com.example.diaryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.diaryapp.databinding.HomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.io.Serializable

private const val TAG_CALENDAR = "ic_calendar"
private const val TAG_HOME = "ic_home"
private const val TAG_Diary = "ic_diary"
private const val TAG_Save_Diary = "ic_save_diary"
private const val TAG_Community = "ic_community"
private const val TAG_Settings = "ic_settings"

class BottomNavActivity : AppCompatActivity(), Serializable  {

    private lateinit var binding : HomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())


        setFragment(TAG_HOME, HomeFragment())
        binding.homeBottomNav.selectedItemId = R.id.ic_home

        binding.homeBottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.ic_calendar-> setFragment(TAG_CALENDAR, CalendarFragment())
                R.id.ic_home -> setFragment(TAG_HOME, HomeFragment())
                R.id.ic_community -> setFragment(TAG_Community, CommunityFragment())
                R.id.ic_settings -> setFragment(TAG_Settings, SettingsFragment())
                R.id.ic_diary -> {
                    if (checkDiary(todayDate)) {
                        setFragment(TAG_Diary, DiaryFragment())
                    } else {
                        setFragment(TAG_Diary, WriteDiaryFragment())
                    }
                }
            }
            true
        }
    }

    fun checkDiary(selectedDate: String): Boolean {
        val diaryEntries = test()
        val diaryList: MutableList<String> = mutableListOf()

        for (diaryEntry in diaryEntries) {
            val date = diaryEntry.date

            if (date == selectedDate) {
                return true
            }
        }
        return false
    }

    fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        val existingFragment = manager.findFragmentByTag(tag)

        // 중복 추가 방지
        if (existingFragment != null) {
            // 이미 추가된 Fragment라면 아무 작업도 하지 않음
            fragTransaction.replace(R.id.mainFrameLayout, fragment, tag)
//                .addToBackStack(null)
        } else {
            // Fragment가 없으면 추가
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
//                .addToBackStack(null)

            // 기존 Fragment 숨김 처리
            val calendar = manager.findFragmentByTag(TAG_CALENDAR)
            val home = manager.findFragmentByTag(TAG_HOME)
            val diary = manager.findFragmentByTag(TAG_Diary)
            val saveDiary = manager.findFragmentByTag(TAG_Save_Diary)
            val community = manager.findFragmentByTag(TAG_Community)
            val settings = manager.findFragmentByTag(TAG_Settings)

            if (calendar != null) {
                fragTransaction.hide(calendar)
            }

            if (home != null) {
                fragTransaction.hide(home)
            }

            if (diary != null) {
                fragTransaction.hide(diary)
            }

            if (saveDiary != null) {
                fragTransaction.hide(saveDiary)
            }

            if (community != null) {
                fragTransaction.hide(community)
            }

            if (settings != null) {
                fragTransaction.hide(settings)
            }

            // 해당 태그에 따라서 show 처리
            when (tag) {
                TAG_CALENDAR -> if (calendar != null) fragTransaction.show(calendar)
                TAG_HOME -> if (home != null) fragTransaction.show(home)
                TAG_Diary -> if (diary != null) fragTransaction.show(diary)
                TAG_Save_Diary -> if (saveDiary != null) fragTransaction.show(saveDiary)
                TAG_Community -> if (community != null) fragTransaction.show(community)
                TAG_Settings -> if (settings != null) fragTransaction.show(settings)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }

    fun setSelectedNavItem(itemId: Int) {
        binding.homeBottomNav.selectedItemId = itemId
    }

}