package com.geekymusketeers.uncrack.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
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

        fun hideKeyboard(activity: Activity) {
            val view: View? = activity.currentFocus
            view?.let {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}