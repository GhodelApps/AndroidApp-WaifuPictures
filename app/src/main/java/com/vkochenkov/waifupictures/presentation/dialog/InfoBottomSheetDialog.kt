package com.vkochenkov.waifupictures.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vkochenkov.waifupictures.R

class InfoBottomSheetDialog: BottomSheetDialogFragment() {

    lateinit var infoSizeTv: TextView

    var imageSizeInfo = "no data"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bottom_sheet_info, container, false)
        infoSizeTv = root.findViewById(R.id.sheet_info_size)
        infoSizeTv.text = imageSizeInfo
        return root
    }
}