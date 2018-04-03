package com.kemalettinsargin.mylib.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kemalettinsargin.mylib.Util;
import com.kemalettinsargin.mylib.customListeners.OnInputListener;
import com.kemalettinsargin.mylib.objects.TypeFaces;
import com.sahnisemanyazilim.ezanisaat.R;


/**
 * Written by "كمال الدّين صارغين"  on 13.08.2017.
 * و من الله توفیق
 */


public class AlertViewHolder implements View.OnClickListener, DialogInterface.OnShowListener {
    private View root;
    private Button btnOk, btnCancel;
    private TextView message, title;
    private Dialog mDialog;
    private EditText editEmail;
    private DialogInterface.OnClickListener dialogListener;
    private OnInputListener onInputListener;
    public static final int TYPE_ERROR = 0, TYPE_SUCCESS = 1, TYPE_EMAIL = 2, TYPE_INPUT = 3;
    private int type = TYPE_SUCCESS;
    private String input;

    public AlertViewHolder(View view) {
        this.root = view;
        btnOk = (Button) gC(R.id.btn_ok);
        btnCancel = (Button) gC(R.id.btn_cancel);
        title = (TextView) gC(R.id.text_title);
        message = (TextView) gC(R.id.text_message);
        editEmail = (EditText) gC(R.id.edit_mail);
        TypeFaces tf = new TypeFaces().createTypefaces(getContext());
        editEmail.setTypeface(tf.medium);
        editEmail.setVisibility(View.GONE);
        btnOk.setTypeface(tf.bold);
        btnCancel.setTypeface(tf.bold);
        title.setTypeface(tf.bold);
        message.setTypeface(tf.book);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    public AlertViewHolder setType(int type) {
        this.type = type;
        switch (type) {
            case TYPE_SUCCESS:
                break;
            case TYPE_ERROR:
                break;
            case TYPE_EMAIL:
                title.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
                editEmail.setVisibility(View.VISIBLE);
                break;
            case TYPE_INPUT:
                message.setVisibility(View.GONE);
                editEmail.setVisibility(View.VISIBLE);
                editEmail.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                break;
        }
        return this;
    }

    public AlertViewHolder setMessage(String message) {
        this.message.setVisibility(message == null || message.isEmpty() ? View.GONE : View.VISIBLE);
        this.message.setText(message);
        return this;
    }

    public AlertViewHolder setMessage(Spanned message) {
        this.message.setText(message);
        return this;
    }

    public AlertViewHolder setTitle(String title) {
        this.title.setVisibility(title == null || title.isEmpty() ? View.GONE : View.VISIBLE);
        this.title.setText(title);
        if(type==TYPE_INPUT){
            editEmail.setHint(title);
        }
        return this;
    }


    public AlertViewHolder setPositiveButton(String btnPos) {
        btnOk.setText(btnPos);
        return this;
    }

    public AlertViewHolder setNegativeButton(String btnNegative) {
        if (btnNegative == null || btnNegative.isEmpty()) {
            btnCancel.setVisibility(View.GONE);
            gC(R.id.center_view).setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = ((ViewGroup.MarginLayoutParams) btnOk.getLayoutParams());
            params.rightMargin = params.leftMargin;
            return this;
        }
        btnCancel.setText(btnNegative);
        return this;
    }

    public AlertViewHolder setDialogListener(DialogInterface.OnClickListener listener) {
        this.dialogListener = listener;
        return this;
    }

    public AlertViewHolder setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
        return this;
    }

    @Override
    public void onClick(View v) {

        int button;
        switch (v.getId()) {
            case R.id.btn_ok:
                if (type == TYPE_EMAIL) {
                    if (editEmail.getText().toString().isEmpty()) {
//                        Util.showToast(getContext(), getContext().getString(R.string.req_mail));
                        return;
                    } else if (!Util.isEmailValid(editEmail.getText().toString())) {
//                        Util.showToast(getContext(), getContext().getString(R.string.req_valid_email));
                        return;
                    }
                    if(onInputListener!=null)
                    onInputListener.onInput(editEmail.getText().toString());
                } else if (type == TYPE_INPUT && onInputListener != null) {
                    if (editEmail.getText().toString().trim().isEmpty()) {
//                        Util.showToast(getContext(), getContext().getString(R.string.req_field));
                        return;
                    }
                    Util.hideKeyboard(getContext(),editEmail);
                    onInputListener.onInput(editEmail.getText().toString());

                }

                button = DialogInterface.BUTTON_POSITIVE;
                break;
            case R.id.btn_cancel:
            default:
                button = DialogInterface.BUTTON_NEGATIVE;
                break;
        }
        mDialog.dismiss();
        if (dialogListener != null)
            dialogListener.onClick(mDialog, button);
    }

    public AlertViewHolder setDialog(Dialog dialog) {
        this.mDialog = dialog;
        dialog.setOnShowListener(this);
        return this;
    }

    private Context getContext() {
        return root.getContext();
    }

    private View gC(int id) {
        return root.findViewById(id);
    }

    @Override
    public void onShow(DialogInterface dialog) {
        ((View) root.getParent()).setBackgroundResource(0);
        if(type==TYPE_INPUT||type==TYPE_EMAIL){
            editEmail.requestFocus();
            Util.showKeyboard(getContext(),editEmail);
        }
    }

}
