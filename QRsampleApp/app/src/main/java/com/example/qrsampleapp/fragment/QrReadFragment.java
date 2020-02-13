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
import android.widget.TextView;

import com.example.qrsampleapp.R;

/**
 *
 */
public class QrReadFragment extends Fragment {

    public TextView tResult;


    /**
     *
     * @return newInstance
     */
    public static QrReadFragment newInstance() {
        QrReadFragment fragment = new QrReadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_qr_read, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //結果テキストのセット
        tResult = (TextView)view.findViewById(R.id.result_text);

        Bundle bundle = getArguments();
        String result_text = bundle.getString("result_text");

        tResult.setText(result_text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
