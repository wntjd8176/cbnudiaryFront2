package com.example.diaryapp

import com.example.diaryapp.HotPostItem
import com.example.diaryapp.Network.RetrofitInstance
import com.example.diaryapp.Serivce.PostApiService
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HotPostFragment : Fragment(), PostItemsAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostItemsAdapter
    private  var hotPostItemList: MutableList<HotPostItem> = mutableListOf()
    private lateinit var clickedItem: HotPostItem
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
        var rootView = inflater.inflate(R.layout.fragment_hot_post, container, false)
        recyclerView = rootView.findViewById(R.id.hot_post_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
       var drawerLayout = rootView.findViewById<DrawerLayout>(R.id.drawerLayout)
        var imgMenu = rootView.findViewById<ImageView>(R.id.imageMenu)
        val addPost = rootView.findViewById<TextView>(R.id.add_post)

        /*hotPostItemList = mutableListOf(
            HotPostItem("user1", "title 1", "2024-09-01"),
          /*  HotPostItem("user2", "title 2", "2024-08-21", "testetset", "2", "comm"),
            HotPostItem("user3", "title 3", "2024-08-31", "testetset", "2", "comm"),
            HotPostItem("user4", "title 4", "2024-08-31", "testetset", "2", "comm"),
            HotPostItem("user5", "title 5", "2024-08-31", "testetset", "2", "comm"),
            HotPostItem("user6", "title 6", "2024-08-31", "testetset", "2", "comm"),
            HotPostItem("user7", "title 7", "2024-08-31", "testetset", "2", "comm"),*/
        ) */

       // recyclerView = rootView.findViewById(R.id.hot_post_list)
     //  recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = PostItemsAdapter(hotPostItemList, requireContext(), this)
        recyclerView.adapter = adapter

        fetchPosts()


        addPost.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val writePostFragment = WritePostFragment.newInstance("","")
            transaction.replace(R.id.mainFrameLayout, writePostFragment)
            transaction.commit()
        }

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
                R.id.nav_post -> {
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    val communityFragment = NormalPostFragment.newInstance("","")
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
    private fun fetchPosts() {
        val postApiService = RetrofitInstance.create(PostApiService::class.java)

        postApiService.getAllPosts().enqueue(object : Callback<List<HotPostItem>> {
            override fun onResponse(call: Call<List<HotPostItem>>, response: Response<List<HotPostItem>>) {
                if (response.isSuccessful && response.body() != null) {
                    hotPostItemList.clear()
                    hotPostItemList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Failed to load posts: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<HotPostItem>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HotPostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        clickedItem = hotPostItemList[position]
        val postFragment = PostFragment().apply {
            arguments = Bundle().apply {
                putString("postTitle", clickedItem.ptitle ?: "No Title")
                putString("postContent", clickedItem.pContent ?: "No Content")
                putString("userName", clickedItem.userID ?: "Unknown User")

            }
        }

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrameLayout, postFragment)  // R.id.fragment_container는 프래그먼트가 표시될 레이아웃 ID
            commit()
        }
    }
}