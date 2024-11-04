package com.example.diaryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EmotionDialogFragment : DialogFragment() {
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
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_emotion_dialog, container, false)
        var goodEmotion = rootView.findViewById<RadioButton>(R.id.radioButton1)
        var sadEmotion = rootView.findViewById<RadioButton>(R.id.radioButton2)
        var angryEmotion = rootView.findViewById<RadioButton>(R.id.radioButton3)
        var depressedEmotion = rootView.findViewById<RadioButton>(R.id.radioButton4)
        var noneEmotion = rootView.findViewById<RadioButton>(R.id.radioButton5)

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmotionDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}