package br.com.scrubles.todolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.scrubles.todolist.model.Activity;

public class TodoForm extends DialogFragment {

    private NoticeDialogListener listener;
    private TextInputLayout textInputLayout;
    private EditText activityTitle;
    private Activity activity;
    private boolean formSubmitted;

    public void setListener(NoticeDialogListener listener) {
        this.listener = listener;
    }

    public static TodoForm getInstance(Activity activity) {
        TodoForm todoForm = new TodoForm();
        todoForm.activity = activity;
        todoForm.formSubmitted = isEmptyString(activity.getTitle());
        return todoForm;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.todo_form, null);
        textInputLayout = view.findViewById(R.id.text_input_layout);
        activityTitle = view.findViewById(R.id.title);
        activityTitle.setText(activity.getTitle(), TextView.BufferType.EDITABLE);
        activityTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String error = formSubmitted && isEmptyString(s) ? getString(R.string.todo_list_empty_title) : null;
                textInputLayout.setError(error);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
            .setTitle(getString(R.string.todo_list_activity))
            .setView(view)
            .setPositiveButton(R.string.todo_list_save, null)
            .setNegativeButton(R.string.todo_list_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    TodoForm.this.getDialog().cancel();
                }
            }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        formSubmitted = true;
                        String title = activityTitle.getText().toString();
                        if(!isEmptyString(title)) {
                            activity.setTitle(title);
                            if(activity.getCreatedDate() == null)
                                activity.setCreatedDate(Calendar.getInstance());
                            listener.onDialogPositiveClick(activity);
                            dismiss();
                        } else
                            textInputLayout.setError(getString(R.string.todo_list_empty_title));
                    }
                });
            }
        });
        return dialog;
    }

    private static boolean isEmptyString(CharSequence s) {
        return s == null || "".equals(s.toString().trim());
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(Activity activity);
    }
}
