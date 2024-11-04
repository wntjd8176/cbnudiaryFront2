package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChangeUserInfoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var bottomNavActivity: BottomNavActivity


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
        var rootView =  inflater.inflate(R.layout.fragment_change_user_info, container, false)

        val changePWBtn = rootView.findViewById<Button>(R.id.changePW)
        val changeBabyNameBtn = rootView.findViewById<Button>(R.id.changeBabyName)
        val changeDDayBtn = rootView.findViewById<Button>(R.id.changeDDay)
        val changeEmail = rootView.findViewById<Button>(R.id.changeEmail)
        val changeBabyPhotoBtn = rootView.findViewById<Button>(R.id.changeBabyPhoto)

        changePWBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val changePWFragment = ChangePWFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, changePWFragment)
                transaction.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        changeBabyNameBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val babyNameFragment = BabyNameFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, babyNameFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        changeDDayBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val changeDDayFragment = ChangeDDayFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, changeDDayFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        changeEmail.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val changeEmailFragment = ChangeEmailFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, changeEmailFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChangeUserInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}