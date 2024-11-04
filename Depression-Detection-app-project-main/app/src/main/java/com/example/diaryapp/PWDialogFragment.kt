package com.example.diaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class PWDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_pw_dialog, container, false)
        var inputPWChangePW = rootView.findViewById<EditText>(R.id.inputPW_changePW)
        var cancelBtnChangePW = rootView.findViewById<Button>(R.id.cancel_changePW)
        var checkPWChangePW = rootView.findViewById<Button>(R.id.checkPW_changePW)
        var testPW = "1234qwer" //테스트용 텍스트

        checkPWChangePW.setOnClickListener {
            if ( inputPWChangePW.text.toString() == testPW) {
                dismiss()
            } else {
                Toast.makeText(context, "비밀번호를 잘못 입력하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        cancelBtnChangePW.setOnClickListener {
            dismiss()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val changeUserInfoFragment = ChangeUserInfoFragment.newInstance("","")
            transaction.replace(R.id.mainFrameLayout, changeUserInfoFragment)
            transaction.remove(this).commit()

//            transaction.commit()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 외부 터치를 막기 위해 isCancelable 속성을 false로 설정
        isCancelable = false
    }

    companion object {
        const val TAG = "YourDialogFragment"
    }
}