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
import android.widget.ImageView;

import com.example.qrsampleapp.R;
import com.example.qrsampleapp.helper.ZxingClass;
import com.google.zxing.WriterException;


public class DisplayQrCodeFragment extends Fragment {

//    public Button mBack;
    public OnBackToEditFormButtonListener backListener;

    public ImageView qrImageView;

    public String context;

    private int cnt;
    /**
     * @param 変換元テキスト
     * @return newInstance
     */
    public static DisplayQrCodeFragment newInstance(String context) {
        DisplayQrCodeFragment fragment = new DisplayQrCodeFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_qr_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        qrImageView = (ImageView) view.findViewById(R.id.qr_imageView);


        //QRコード生成
        try {
            ZxingClass zxing = new ZxingClass();
            qrImageView.setImageBitmap(zxing.createQrCode(context));
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    public interface OnBackToEditFormButtonListener {
        void onClickBackEditForm();
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
