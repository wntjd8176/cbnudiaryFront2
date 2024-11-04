package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AlarmFragment : Fragment() {
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
        var rootView =  inflater.inflate(R.layout.fragment_alarm, container, false)

        val alarmHour = rootView.findViewById<Spinner>(R.id.alaram_hour)
        val alarmMin = rootView.findViewById<Spinner>(R.id.alaram_min)
        val alarmSaveBtn = rootView.findViewById<Button>(R.id.save_alarm_button)

        var selectedHour = ""
        var selectedMin = ""

        val spnHourAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.alaramHour,
            android.R.layout.simple_spinner_dropdown_item
        )
        val spnMinAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.alarmMin,
            android.R.layout.simple_spinner_dropdown_item
        )

        alarmHour.adapter = spnHourAdapter
        alarmMin.adapter = spnMinAdapter

        alarmHour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedHour = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val defaultHour = "00"
                // 사용자 설정한 시간이 되도록 반영 필요, 설정한 시간이 없는 유저는 default 값 설정
            }
        }

        alarmMin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedMin = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                val defaultMin = "00"
                // 사용자 설정한 시간이 되도록 반영 필요, 설정한 시간이 없는 유저는 default 값 설정
            }
        }

        alarmSaveBtn.setOnClickListener {
            Toast.makeText(requireContext(), "${selectedHour}시 ${selectedMin}분으로 알림 시간이 변경되었습니다.", Toast.LENGTH_SHORT).show()
            // 유저가 설정한 시간으로 알림이 되도록 db 업데이트
        }

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlarmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}