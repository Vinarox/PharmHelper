package by.bsu.slabko.vladislav.pharmhelper.dialogs;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;


public class DialogRoute extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_route, null);
        final EditText startEditText = view.findViewById(R.id.start_point);
        final EditText endEditText = view.findViewById(R.id.end_point);
        final CheckBox checkBox = view.findViewById(R.id.dialogCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    startEditText.setEnabled(false);
                else
                    startEditText.setEnabled(true);
            }
        });

        builder.setView(view)
                .setPositiveButton("Показать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(checkBox.isChecked()) {
                            if (endEditText.getText().length() > 0) {
                                //SearchResultActivity.startFindingRoute("current" + "&" +
                                //      String.valueOf(endEditText.getText()));
                                //Constants.isDialogFinished = "current" + "&" +
                                //      String.valueOf(endEditText.getText());
                            }
                        }
                        else {
                            System.out.println();
                            if (startEditText.getText().length() > 0 && endEditText.getText().length() > 0) {
                                /*SearchResultActivity.getInstance().startFindingRoute(
                                        String.valueOf(startEditText.getText()) + "&" +
                                        String.valueOf(endEditText.getText()));*/
                                //Constants.isDialogFinished = String.valueOf(startEditText.getText()) +
                                //      "&" + String.valueOf(endEditText.getText());
                            }
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Constants.isDialogFinished = "nope";
                        DialogRoute.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
