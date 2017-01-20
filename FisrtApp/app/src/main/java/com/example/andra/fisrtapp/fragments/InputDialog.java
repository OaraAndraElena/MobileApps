package com.example.andra.fisrtapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.example.andra.R;

import com.example.andra.fisrtapp.activities.DestinationActivity;

public class InputDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final boolean addState = args.getBoolean("addState");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View InputView = inflater.inflate(R.layout.input_dest, null);

        final EditText place = (EditText) InputView.findViewById(R.id.place);
        final EditText date = (EditText) InputView.findViewById(R.id.date);
        final NumberPicker price = (NumberPicker) InputView.findViewById(R.id.price);
        price.setMaxValue(5000);


        if (!addState) {
            place.setText(args.getString("place"));
            price.setValue(args.getInt("price"));
            date.setText(args.getString("date"));
        }

        builder.setView(InputView)
                // Add action buttons
                .setPositiveButton(addState ? "ADD" : "EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DestinationActivity list = (DestinationActivity) getActivity();
                        if (!addState){
                            list.updateDestination(args.getInt("id"), place.getText().toString(), price.getValue(),date.getText().toString());
                        } else {
                            list.addDestination(place.getText().toString(),price.getValue(),date.getText().toString());
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        InputDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
