package com.example.diaryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView

class SearchFragment : Fragment() {
    private var param1: String? = null
    private lateinit var bottomNavActivity: BottomNavActivity
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            bottomNavActivity =
                it.getSerializable(ARG_BOTTOM_NAV_ACTIVITY) as? BottomNavActivity
                    ?: throw IllegalArgumentException("BottomNavActivity must not be null")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_search, container, false)
        var svp = rootView.findViewById<SearchView>(R.id.search_page)

        listView = rootView.findViewById(R.id.searchListView)

        val diaryEntries = test()
        val diaryList: MutableList<String> = mutableListOf()

        activity?.let {
            if (it is BottomNavActivity) {
                bottomNavActivity = it
            } else {
                throw IllegalStateException("Activity must be of type BottomNavActivity")
            }
        }

        for (diaryEntry in diaryEntries) {
            val title = diaryEntry.title
            if (title.contains(param1.orEmpty())) {
                val contentPreview = if (diaryEntry.content.length > 30) {
                    "${diaryEntry.content.substring(0, 30)}..."
                } else {
                    diaryEntry.content
                }
                val tmp = "${diaryEntry.date}  ${diaryEntry.title}\n$contentPreview"
                diaryList.add(tmp)
            }
        }

        if (diaryList.isEmpty()) {
            diaryList.add("검색된 일기가 없습니다.")
        }

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, diaryList)
        listView.adapter = adapter

        svp.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                performSearch(newText.orEmpty())
                return true
            }
        })

        listView.setOnItemClickListener { parent, _, position, _ ->
            var selected = parent.getItemAtPosition(position) as String
            selected = selected.substring(0,10)
            Log.i("ListView", "Item clicked: ${selected}")

            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val diaryFragment = DiaryFragment.newInstance(bottomNavActivity, selected)
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
    private fun performSearch(query: String) {
        val diaryEntries = test()
        val diaryList: MutableList<String> = mutableListOf()

        for (diaryEntry in diaryEntries) {
            val title = diaryEntry.title
            if (title.contains(query, ignoreCase = true)) {
                val contentPreview = if (diaryEntry.content.length > 30) {
                    "${diaryEntry.content.substring(0, 30)}..."
                } else {
                    diaryEntry.content
                }
                val tmp = "${diaryEntry.date}  ${diaryEntry.title}\n$contentPreview"
                diaryList.add(tmp)
            }
        }

        adapter.clear()
        adapter.addAll(diaryList)
        adapter.notifyDataSetChanged()

        if (diaryList.isEmpty()) {
            diaryList.add("검색된 일기가 없습니다.")
        }
    }

    companion object {
        private const val ARG_PARAM1 = "search"
        private const val ARG_BOTTOM_NAV_ACTIVITY = "bottom_nav_activity"

        @JvmStatic
        fun newInstance(
            bottomNavActivity: BottomNavActivity,
            param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putSerializable(ARG_BOTTOM_NAV_ACTIVITY, bottomNavActivity)

                }
            }
    }
}