package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChangePWFragment : Fragment() {
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
        var rootView = inflater.inflate(R.layout.fragment_change_pw, container, false)
        var newPW = rootView.findViewById<EditText>(R.id.newPW)
        var newPW2 = rootView.findViewById<EditText>(R.id.newPW2)
        var saveNewPWBtn = rootView.findViewById<Button>(R.id.save_newPW_button)

        var dialogFragment = PWDialogFragment()
        dialogFragment.show(parentFragmentManager, PWDialogFragment.TAG)

        saveNewPWBtn.setOnClickListener {
            if (newPW.text.toString() == newPW2.text.toString()) {
                Toast.makeText(context, "새로운 비밀번호로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                //DB 저장
            } else {
                Toast.makeText(context, "입력한 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChangePWFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}