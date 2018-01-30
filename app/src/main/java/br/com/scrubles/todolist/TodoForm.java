package br.com.scrubles.todolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.scrubles.todolist.model.Activity;

public class TodoForm extends DialogFragment {

    private NoticeDialogListener listener;
    private EditText activityTitle;
    private Activity activity;

    public void setListener(NoticeDialogListener listener) {
        this.listener = listener;
    }

    public static TodoForm getInstance(Activity activity) {
        TodoForm todoForm = new TodoForm();
        todoForm.activity = activity;
        return todoForm;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Activity");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.todo_form, null);
        activityTitle = (EditText) view.findViewById(R.id.title);
        activityTitle.setText(activity.getTitle(), TextView.BufferType.EDITABLE);
        builder.setView(view).setPositiveButton(R.string.todo_list_save, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                String title = activityTitle.getText().toString();
                if(title != null && !"".equals(title.trim())) {
                    activity.setTitle(title);
                    if(activity.getCreatedDate() == null)
                        activity.setCreatedDate(Calendar.getInstance());
                    listener.onDialogPositiveClick(activity);
                } else {
                    Toast.makeText(getContext(), "Text is required", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(R.string.todo_list_cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                TodoForm.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(Activity activity);
    }
}
