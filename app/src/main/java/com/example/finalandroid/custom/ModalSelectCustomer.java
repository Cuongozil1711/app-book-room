package com.example.finalandroid.custom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalandroid.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalSelectCustomer extends BottomSheetDialogFragment {
    private BottomSelectOptionListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_gender, container, false);

        Button nam  = v.findViewById(R.id.nam);
        Button nu = v.findViewById(R.id.nu);

        nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickOption("Nam");
                dismiss();
            }
        });

        nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickOption("Nữ");
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSelectOptionListener {
        void onClickOption(String text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSelectOptionListener) context;

        }
        catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }
}
