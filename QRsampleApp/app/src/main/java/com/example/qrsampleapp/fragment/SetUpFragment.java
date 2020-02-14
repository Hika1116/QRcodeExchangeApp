package com.example.qrsampleapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.qrsampleapp.R;

/**
 *
 * 初期セットアップ画面フラグメント
 */
public class SetUpFragment extends Fragment {

    public Button bNext;
    public CheckBox cGetKey;
    public CheckBox cSendKey;

    public OnNextButtonListener onNextButtonListener;
    public OnGetKeyCheckBoxListener onGetKeyCheckBoxListener;
    public  OnSendKeyCheckBoxListener onSendKeyCheckBoxListener;

    /**
     *
     * @return newInstance
     */
    public static SetUpFragment newInstance() {
        SetUpFragment fragment = new SetUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bNext = (Button)view.findViewById(R.id.next_button);
        bNext.setEnabled(false);
        cGetKey = (CheckBox)view.findViewById(R.id.get_key_checkbox);
        cSendKey = (CheckBox)view.findViewById(R.id.send_key_checkbox);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextButtonListener.onClickNext();
            }
        });

        cGetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetKeyCheckBoxListener.onCheckGetKey();
            }
        });

        cSendKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendKeyCheckBoxListener.onCheckSendKey();
            }
        });

    }

    public interface OnNextButtonListener {
        void onClickNext();
    }

    public interface OnGetKeyCheckBoxListener {
        void onCheckGetKey();
    }

    public interface OnSendKeyCheckBoxListener {
        void onCheckSendKey();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnNextButtonListener){
            onNextButtonListener = (OnNextButtonListener) context;
        }else {
            throw new ClassCastException("activity が OnNextButtonListener を実装していません.");
        }
        if(context instanceof OnGetKeyCheckBoxListener){
            onGetKeyCheckBoxListener= (OnGetKeyCheckBoxListener) context;
        }else {
            throw new ClassCastException("activity が OnGetKeyCheckBoxListener を実装していません.");
        }
        if(context instanceof OnSendKeyCheckBoxListener){
            onSendKeyCheckBoxListener= (OnSendKeyCheckBoxListener) context;
        }else {
            throw new ClassCastException("activity が OnSendKeyCheckBoxListener を実装していません.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNextButtonListener = null;
        onGetKeyCheckBoxListener = null;
        onSendKeyCheckBoxListener = null;
    }
}
