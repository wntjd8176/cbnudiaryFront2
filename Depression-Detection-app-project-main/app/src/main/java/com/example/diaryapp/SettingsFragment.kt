package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        var rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        val changeUserInfoBtn = rootView.findViewById<Button>(R.id.chage_uesr_info_btn)
        val alarmBtn = rootView.findViewById<Button>(R.id.alarm_btn)
        val exportDiaryBtn = rootView.findViewById<Button>(R.id.export_diary_btn)
        val restoreDiaryBtn = rootView.findViewById<Button>(R.id.restore_diary_btn)
        val withdrawalBtn = rootView.findViewById<Button>(R.id.withdrawal_btn)

        changeUserInfoBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val changeUserInfoFragment = ChangeUserInfoFragment.newInstance("","")
                transaction.addToBackStack(null)
                transaction.replace(R.id.mainFrameLayout, changeUserInfoFragment)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        exportDiaryBtn.setOnClickListener {
            // 일기 내보내기 기능 추가 필요
            Toast.makeText(context, "일기 내보내기 완료\n등록된 이메일로 일기가 전송되었습니다.", Toast.LENGTH_SHORT).show()
        }

        restoreDiaryBtn.setOnClickListener {
            // 일기 복구 기능 추가
            Toast.makeText(context, "일기 복구가 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }

        alarmBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val alarmFragment = AlarmFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, alarmFragment)
                transaction.addToBackStack(null)
                transaction.commit()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        withdrawalBtn.setOnClickListener {
            try {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                val withdrawalFragment = WithdrawalFragment.newInstance("","")
                transaction.replace(R.id.mainFrameLayout, withdrawalFragment)
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
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}