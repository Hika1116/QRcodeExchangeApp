package com.example.qrsampleapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.qrsampleapp.R;
import com.example.qrsampleapp.fragment.DisplayQrCodeFragment;
import com.example.qrsampleapp.fragment.QrGenerateFragment;
import com.example.qrsampleapp.helper.RSACipherClass;

import java.security.interfaces.RSAPublicKey;


public class QRGenerateActivity extends AppCompatActivity implements QrGenerateFragment.OnConvertButtonListener, QrGenerateFragment.OnBackButtonListener, DisplayQrCodeFragment.OnBackToEditFormButtonListener {

    RSAPublicKey opponentPublicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.opponentPublicKey = (RSAPublicKey)getIntent().getSerializableExtra("opponentPublicKey");
        setContentView(R.layout.activity_qrgenerate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, QrGenerateFragment.newInstance());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onClickConvert() {
        String editText = "null";
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null && fragment instanceof QrGenerateFragment) {
            editText = ((QrGenerateFragment) fragment).editText.getText().toString();
        }
        Log.d("edit", editText);

        try {
            //暗号化
            String encryption = RSACipherClass.encryptionByPuclicPey(editText, this.opponentPublicKey);
            Log.d("encryption", encryption);
            //QRコード表示画面へ遷移
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(fragmentManager != null){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // BackStackを設定
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, DisplayQrCodeFragment.newInstance(encryption.toString()));
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    public void onClickBackEditForm() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager != null) {
            fragmentManager.popBackStack();
        }
    }
}
