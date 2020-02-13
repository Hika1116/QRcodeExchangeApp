package com.example.qrsampleapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qrsampleapp.R;

/***
 * ホーム画面フラグメント
 */
public class HomeFragment extends Fragment {

    public Button mQRRead;
    public Button mQRGenerate;

    public OnReadQRButtonClickListener readButtonListener;
    public OnGenerateQRButtonClickListener generateButtonListener;


    @CheckResult
    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mQRRead = (Button) view.findViewById(R.id.read_qr_button);
        mQRGenerate = (Button) view.findViewById(R.id.generate_qr_button);


        mQRRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readButtonListener.onClickToQRRead();
            }
        });

        mQRGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateButtonListener.onClickToQRGenerate();
            }
        });
    }

    public interface OnReadQRButtonClickListener {
        void onClickToQRRead();
    }

    public interface OnGenerateQRButtonClickListener {
        void onClickToQRGenerate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnReadQRButtonClickListener){
            readButtonListener = (OnReadQRButtonClickListener) context;
        }else {
            throw new ClassCastException("activity が OnReadQRButtonClickListener を実装していません.");
        }

        if(context instanceof OnGenerateQRButtonClickListener){
            generateButtonListener= (OnGenerateQRButtonClickListener) context;
        }else {
            throw new ClassCastException("activity が OnGenerateQRButtonClickListener を実装していません.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        readButtonListener = null;
        generateButtonListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
