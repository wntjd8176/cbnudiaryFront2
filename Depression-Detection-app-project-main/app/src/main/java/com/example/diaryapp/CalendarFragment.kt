package com.example.diaryapp

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import android.widget.SearchView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG_Diary = "ic_diary"

class CalendarFragment : Fragment() {
    private lateinit var bottomNavActivity: BottomNavActivity
    private lateinit var listView: ListView

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
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)

        val addButton = rootView.findViewById<FloatingActionButton>(R.id.addButton)
        val todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val clickedDate = rootView.findViewById<CalendarView>(R.id.calendarView)
        var sv = rootView.findViewById<SearchView>(R.id.search_calendar)
        var selectedDate = todayDate

        listView = rootView.findViewById(R.id.calendarListView)
        updateListView(todayDate, addButton) // 오늘 날짜로 초기화

        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("SearchView", "Query submitted: $query")

                // 엔터 키가 눌렸을 때의 처리
                query?.let {
                    try {
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        val searchFragment = SearchFragment.newInstance(bottomNavActivity, query)
                        transaction.replace(R.id.mainFrameLayout, searchFragment)
                        // transaction.addToBackStack(null)
                        transaction.commit()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("SearchView", "Query changed: $newText")
                return true
            }
        })


        sv.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // 엔터 키가 눌렸을 때의 처리
                val query = sv.query.toString()
                Log.i("SearchView", "Enter key pressed with query: $query")

                sv.onActionViewCollapsed()
                sv.clearFocus()
                handleSearchQuery(query)

                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        clickedDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            Log.i("SelectedDate", selectedDate)

            updateListView(selectedDate, addButton)
        }

        addButton.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val writeDiaryFragment = WriteDiaryFragment.newInstance(bottomNavActivity, selectedDate)
                transaction.replace(R.id.mainFrameLayout, writeDiaryFragment)
                bottomNavActivity.setSelectedNavItem(R.id.ic_diary)
//                transaction.addToBackStack(null)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedDiary = parent.getItemAtPosition(position) as String
            Log.i("ListView", "Item clicked: $selectedDiary")

            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val diaryFragment = DiaryFragment.newInstance(bottomNavActivity, selectedDate)
                transaction.replace(R.id.mainFrameLayout, diaryFragment)
                bottomNavActivity.setSelectedNavItem(R.id.ic_diary)
                transaction.addToBackStack(null)
                transaction.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rootView
    }

    private fun updateListView(selectedDate: String, addButton: FloatingActionButton) {
        val diaryEntries = test()
        val diaryList: MutableList<String> = mutableListOf()

        for (diaryEntry in diaryEntries) {
            val date = diaryEntry.date

            if (date == selectedDate) {
                val contentPreview = if (diaryEntry.content.length > 30) {
                    "${diaryEntry.content.substring(0, 30)}..."
                } else {
                    diaryEntry.content
                }
                val tmp = "${diaryEntry.title}\n$contentPreview"
                diaryList.add(tmp)
                addButton.visibility = View.GONE
            }
        }

        if (diaryList.isEmpty()) {
            diaryList.add("작성된 일기가 없습니다.")
            addButton.isEnabled = true
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, diaryList)
        listView.adapter = adapter
    }

    private fun handleSearchQuery(query: String) {
        Log.i("SearchView", "Handling search query: $query")
        try {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val searchFragment = SearchFragment.newInstance(bottomNavActivity,query)
            transaction.replace(R.id.mainFrameLayout, searchFragment)
            // transaction.addToBackStack(null)
            transaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        fun newInstance(bottomNavActivity: BottomNavActivity): CalendarFragment {
            val fragment = CalendarFragment()
            fragment.bottomNavActivity = bottomNavActivity
            return fragment
        }
    }
}
