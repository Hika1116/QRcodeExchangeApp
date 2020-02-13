package com.example.qrsampleapp.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.qrsampleapp.R;

/**
 * QRコード生成画面フラグメント
 */
public class QrGenerateFragment extends Fragment {

    public Button mConvert;
    public Button mBack;
    public OnConvertButtonListener convertListener;
    public OnBackButtonListener backListener;

    public EditText editText;

    private int cnt;

    /**
     * @return newInstance
     */
    public static QrGenerateFragment newInstance() {
        QrGenerateFragment fragment = new QrGenerateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_generate_frangemt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mConvert = (Button) view.findViewById(R.id.conversion_button);
        mBack = (Button) view.findViewById(R.id.back_button);
        editText = (EditText)view.findViewById(R.id.editText);

        mConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertListener.onClickConvert();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backListener.onClickBack();
            }
        });
    }

    public interface OnConvertButtonListener {
        void onClickConvert();
    }

    public interface OnBackButtonListener {
        void onClickBack();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof QrGenerateFragment.OnConvertButtonListener){
            convertListener = (QrGenerateFragment.OnConvertButtonListener) context;
        }else {
            throw new ClassCastException("activity が OnConvertButtonListener を実装していません.");
        }
        if(context instanceof QrGenerateFragment.OnBackButtonListener){
            backListener = (QrGenerateFragment.OnBackButtonListener) context;
        }else {
            throw new ClassCastException("activity が OnConvertButtonListener を実装していません.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.convertListener = null;
        this.backListener = null;
    }

}
