package com.kalashnyk.denys.main_operation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_base_dialog.*


open class Dialog : DialogFragment() {

    private val buttons: List<AppCompatButton> by lazy {
        listOf(btn_dialog_first, btn_dialog_second, btn_dialog_third)
    }

    private lateinit var dialogData: DialogData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialog()
    }

    private fun setDialog() {
        tv_title_dialog.text = dialogData.title
        dialogData.text?.let {
            tv_dialog_text_body.text = it
            tv_dialog_text_body.visibility = View.VISIBLE
        }
        dialogData.customLayout?.let {
            val view: View = LayoutInflater.from(context).inflate(it, null)
            dialog_custom_layout.addView(view)
            dialog_custom_layout.visibility = View.VISIBLE
        }
        dialog_buttons_container.orientation = dialogData.buttonsOrientation
        if(dialogData.buttons.isEmpty()) {
            buttons[0].setDialogButton(DialogButton(
                getString(R.string.cancel),
                View.OnClickListener { dismiss() }
            ))
        }
        else {
            for (i in 1..dialogData.buttons.size) {
                buttons[i - 1].setDialogButton(dialogData.buttons[i - 1])
            }
        }
    }

    private fun AppCompatButton.setDialogButton(dialogButton: DialogButton) {
        text = dialogButton.text
        setOnClickListener(dialogButton.onClickListener)
        visibility = View.VISIBLE
    }

    private data class DialogData(
        val title: String,
        val text: String?,
        @LayoutRes val customLayout: Int?,
        @LinearLayoutCompat.OrientationMode val buttonsOrientation: Int,
        val buttons: List<DialogButton>
    )

    data class DialogButton(
        val text: String,
        val onClickListener: View.OnClickListener
    )

    object DialogBuilder {
        fun getDialog(
            title: String,
            text: String? = null,
            @LayoutRes customLayout: Int? = null,
            @LinearLayoutCompat.OrientationMode buttonsOrientation: Int = LinearLayoutCompat.HORIZONTAL,
            buttons: List<DialogButton> = listOf()
        ) =
            buildDialog(
                DialogData(
                    title,
                    text,
                    customLayout,
                    buttonsOrientation,
                    buttons
                )
            )

        private fun buildDialog(dialogData: DialogData) = Dialog().apply {
            this.dialogData = dialogData
        }
    }
}