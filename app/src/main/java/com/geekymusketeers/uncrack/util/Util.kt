package com.geekymusketeers.uncrack.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.geekymusketeers.uncrack.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class Util {
    companion object{
        fun log(message: String) {
            Log.d("", message)
        }

        fun getBaseStringForFiltering(originalString: String) : String {
            val stringBuilder = StringBuilder()

            for (char in originalString.toCharArray()) {
                if (char.isLetter())
                    stringBuilder.append(char)
            }

            return stringBuilder.toString()
        }

        fun Context.createBottomSheet(): BottomSheetDialog {
            return BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        }

        fun Activity.createBottomSheet(): BottomSheetDialog {
            return BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        }

        fun View.setBottomSheet(bottomSheet: BottomSheetDialog) {
            bottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheet.setContentView(this)
            bottomSheet.create()
            bottomSheet.show()
        }
    }
}