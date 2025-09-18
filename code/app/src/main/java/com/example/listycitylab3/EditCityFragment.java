package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        void onUpdateCity(City city);
    }
    private EditCityDialogListener listener;
    private static final String ARG_CITY = "city";
    private City city;

    public static EditCityFragment newInstance(@Nullable City city) {
        EditCityFragment f = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton( "Save", (dialog, which) -> {
                    String name = editCityName.getText().toString().trim();
                    String province = editProvinceName.getText().toString().trim();
                    city.setName(name);
                    city.setProvince(province);
                    listener.onUpdateCity(city);
                    })
                .create();
    }
}
