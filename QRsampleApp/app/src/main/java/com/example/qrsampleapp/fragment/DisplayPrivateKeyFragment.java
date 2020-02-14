package com.example.qrsampleapp.fragment;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qrsampleapp.R;
import com.example.qrsampleapp.helper.ZxingClass;
import com.google.zxing.WriterException;


public class DisplayPrivateKeyFragment extends Fragment {

    public ImageView imgPrivateKey;

    public String context;

    public DisplayPrivateKeyFragment() {
        // Required empty public constructor
    }

    public static DisplayPrivateKeyFragment newInstance(String context) {
        DisplayPrivateKeyFragment fragment = new DisplayPrivateKeyFragment();
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
        return inflater.inflate(R.layout.fragment_display_private_key, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgPrivateKey = (ImageView)view.findViewById(R.id.img_private_key);
        //QRコード生成
        try {
            ZxingClass zxing = new ZxingClass();
            imgPrivateKey.setImageBitmap(zxing.createQrCode(context));
        } catch (WriterException e) {
            e.printStackTrace();
        }

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
