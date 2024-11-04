package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CommunityFragment : Fragment() {
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
        var rootView =  inflater.inflate(R.layout.fragment_community, container, false)
        var drawerLayout = rootView.findViewById<DrawerLayout>(R.id.drawerLayout)
        var imgMenu = rootView.findViewById<ImageView>(R.id.imageMenu)

        val moreHotPostBtn = rootView.findViewById<TextView>(R.id.more_hot)
        val morePhotoBtn = rootView.findViewById<TextView>(R.id.more_photo)

        var hotTitle1 = rootView.findViewById<TextView>(R.id.hot_title1)
        var hotTitle2 = rootView.findViewById<TextView>(R.id.hot_title2)
        var hotTitle3 = rootView.findViewById<TextView>(R.id.hot_title3)

        var photoTitle1 = rootView.findViewById<TextView>(R.id.photo_title1)
        var photoTitle2 = rootView.findViewById<TextView>(R.id.photo_title2)
        var photoTitle3 = rootView.findViewById<TextView>(R.id.photo_title3)


        hotTitle1.setText("hot 게시판 타이틀1")
        hotTitle2.setText("hot 게시판 타이틀2")
        hotTitle3.setText("hot 게시판 타이틀3")

        photoTitle1.setText("포토 게시판 타이틀1")
        photoTitle2.setText("포토 게시판 타이틀2")
        photoTitle3.setText("포토 게시판 타이틀3")

        moreHotPostBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val hotPostFragment = HotPostFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, hotPostFragment)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        morePhotoBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val photoPostFragment = PhotoPostFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, photoPostFragment)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommunityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}