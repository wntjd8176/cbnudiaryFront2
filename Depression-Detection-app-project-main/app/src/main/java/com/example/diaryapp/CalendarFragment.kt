package com.example.diaryapp
import com.example.diaryapp.Serivce.DiaryApiService
import com.example.diaryapp.Network.RetrofitInstance
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast
import java.util.Locale

private const val TAG_Diary = "ic_diary"

class CalendarFragment : Fragment() {
    private lateinit var bottomNavActivity: BottomNavActivity
    private lateinit var listView: ListView
    private val diaryApiService :DiaryApiService = RetrofitInstance.create(DiaryApiService::class.java)
    private var selectedDate: String = ""
    private val diaryList: MutableList<DiaryApiService.DiaryDTO> = mutableListOf()
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
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
        val todayDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
        val clickedDate = rootView.findViewById<CalendarView>(R.id.calendarView)
        var sv = rootView.findViewById<SearchView>(R.id.search_calendar)
        var selectedDate = todayDate

        listView = rootView.findViewById(R.id.calendarListView)
        fetchDiaries(todayDate, addButton)
       // updateListView(todayDate, addButton) // 오늘 날짜로 초기화

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
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
          //  val formattedDate = dateTimeFormat.format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(selectedDate)!!)
           // val todayDate = dateTimeFormat.format(Date())
            val formattedDate = dateTimeFormat.format(SimpleDateFormat("yyyy-MM-dd").parse(selectedDate))
            Log.i("SelectedDate", selectedDate)
            fetchDiaries(selectedDate, addButton)
           // updateListView(selectedDate, addButton)
        }

        addButton.setOnClickListener {
            try {


                val dateToPass = selectedDate ?: SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
                val formattedDate = dateTimeFormat.format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(selectedDate)!!)
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val writeDiaryFragment = WriteDiaryFragment.newInstance(bottomNavActivity, formattedDate)
                transaction.replace(R.id.mainFrameLayout, writeDiaryFragment)
                bottomNavActivity.setSelectedNavItem(R.id.ic_diary)
                transaction.addToBackStack(null)
                transaction.commit()

            } catch (e: Exception) {
                Log.e("CalendarFragment", "Error navigating to WriteDiaryFragment", e)
                Toast.makeText(requireContext(), "일기 작성 화면으로 이동할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        listView.setOnItemClickListener { parent, _, position, _ ->
           // val selectedDiary = parent.getItemAtPosition(position) as String
            val selectedDiary = diaryList[position]
            Log.i("ListView", "Item clicked: $selectedDiary")

            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val diaryFragment = DiaryFragment.newInstance(bottomNavActivity, selectedDate,

                    title = selectedDiary.dtitle,
                    content = selectedDiary.diaryContent,
                    emotion = selectedDiary.emotions)
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

    private fun fetchDiaries(date: String, addButton: FloatingActionButton) {
        val userID = Myapp.getPreferences().getString("loggedInUserId", null)

        if (userID.isNullOrEmpty()) {
            Log.e("CalendarFragment", "User ID is null")
            Toast.makeText(requireContext(), "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val formattedDate = if (date.length == 10) { // "yyyy-MM-dd" 확인
            date
          /*  val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            dateTimeFormat.format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)!!)*/
        } else {
            date // 이미 올바른 형식이라면 그대로 사용
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = diaryApiService.getDiaryList(userID, formattedDate)
                Log.i("SelectedDate", formattedDate)
                if (response.isSuccessful) {
                    val diaryEntries = response.body() ?: emptyList()
                    withContext(Dispatchers.Main) {
                        updateListView(diaryEntries, addButton)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "일기를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("CalendarFragment", "Failed to fetch diaries: ${response.errorBody()?.string()}")
                }
                Log.d("CalendarFragment", "Fetching diaries for date: $formattedDate")
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "오류가 발생했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                Log.e("CalendarFragment", "Error fetching diaries", e)
            }
        }
    }

    private fun updateListView(diaryEntries:List<DiaryApiService.DiaryDTO>, addButton: FloatingActionButton) {
        Log.d("DiaryList", "Fetched Diaries: ${diaryEntries.toString()}")

        val diaryList: MutableList<DiaryApiService.DiaryDTO> = diaryEntries.toMutableList()
       // val displayList: MutableList<String> = mutableListOf()
        val displayList = mutableListOf<String>()
        //val displayList = diaryEntries.map { it.dtitle }.toMutableList()

       /* for (diaryEntry in diaryEntries) {
            val contentPreview = if (diaryEntry.diaryContent.length > 30) {
                "${diaryEntry.diaryContent.substring(0, 30)}..."
            } else {
                diaryEntry.diaryContent
            }
            val tmp = "${diaryEntry.dtitle}\n$contentPreview"
            diaryList.add(tmp)

        } */

        if (diaryList.isEmpty()) {
            addButton.isEnabled = true
            displayList.add("작성된 일기가 없습니다.")
           /* diaryList.add(
                DiaryApiService.DiaryDTO(
                    createdDate = "",
                    dtitle = "작성된 일기가 없습니다.",
                    diaryContent = "",
                    emotions = 0, // 기본값 설정
                    diaryUserID="",
                    resultEmotion = 0
                )
            )*/

        }else{
            displayList.addAll(diaryEntries.map { it.dtitle })
        }
        Log.d("DisplayList", "Display List: $displayList")
      /*  val displayList = diaryList.map { diaryEntry ->
            val contentPreview = if (diaryEntry.diaryContent.length > 30) {
                "${diaryEntry.diaryContent.substring(0, 30)}..."
            } else {
                diaryEntry.diaryContent
            }
            "${diaryEntry.dtitle}\n$contentPreview"
        }  */

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
        listView.adapter = adapter

        Log.d("ListView", "ListView adapter set with ${displayList.size} items")

        listView.setOnItemClickListener { _, _, position, _ ->
            if (diaryList.isEmpty()) {
                // 일기가 없는 경우 아무 동작도 하지 않음
                Toast.makeText(requireContext(), "작성된 일기가 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnItemClickListener
            }
            val selectedDiary = diaryEntries[position]

            val diaryFragment = DiaryFragment.newInstance(
                bottomNavActivity = bottomNavActivity,
                selectedDate = selectedDiary.createdDate,

                title =  selectedDiary.dtitle,
                content = selectedDiary.diaryContent,
                emotion = selectedDiary.emotions

            )
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, diaryFragment)
                .addToBackStack(null)
                .commit()
        }
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
