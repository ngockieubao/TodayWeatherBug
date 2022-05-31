package com.example.todayweather.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.todayweather.R
import com.example.todayweather.util.Constants
import com.example.todayweather.util.SharedPref
import java.lang.IllegalStateException

class SettingDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    // Dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (activity?.let {
            val options = arrayOf(
                context?.getString(R.string.celsius),
                context?.getString(R.string.fahrenheit)
            )
            var selectedItem = 0
            // Init var to build dialog
            val builder = AlertDialog.Builder(it)

            // Set dialog title
            builder.setTitle(R.string.dialog_settings)
            builder.setSingleChoiceItems(
                options, 0
            ) { dialogInterface: DialogInterface, item: Int ->
                selectedItem = item
            }
                // Set btn do
                .setPositiveButton(R.string.setting, DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(requireActivity(), "Settings ${options[selectedItem]}", Toast.LENGTH_SHORT).show()
                    if (options[selectedItem].equals(context?.getString(R.string.celsius)))
                        Constants.SHARED_PREF_VALUE = Constants.SHARED_PREF_CELSIUS
                    if (options[selectedItem].equals(context?.getString(R.string.fahrenheit)))
                        Constants.SHARED_PREF_VALUE = Constants.SHARED_PREF_FAHRENHEIT
                })
                // Set btn cancel
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(requireActivity(), "Cancel", Toast.LENGTH_SHORT).show()
                })
            // Build
            builder.create()
        } ?: throw IllegalStateException("Fragment cannot be null"))
    }
}