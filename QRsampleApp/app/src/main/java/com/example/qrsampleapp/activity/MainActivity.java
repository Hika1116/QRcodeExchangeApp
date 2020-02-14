package com.example.qrsampleapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.qrsampleapp.R;
import com.example.qrsampleapp.fragment.HomeFragment;
import com.example.qrsampleapp.fragment.QrReadFragment;
import com.example.qrsampleapp.helper.RSACipherClass;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class MainActivity
        extends AppCompatActivity
        implements  HomeFragment.OnReadQRButtonClickListener, HomeFragment.OnGenerateQRButtonClickListener{

    private boolean is_read_qr = false;
    private Bundle toQrReadFragmentBundle;

    //自機の秘密鍵
    RSAPrivateKey privateKey; //秘密鍵
    //相手デバイスの公開鍵
    RSAPublicKey opponentPublicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toQrReadFragmentBundle = new Bundle();

        Intent intent = getIntent();
        this.privateKey = (RSAPrivateKey)intent.getSerializableExtra("privateKey");
        this.opponentPublicKey = (RSAPublicKey)intent.getSerializableExtra("opponentPublicKey");


        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, HomeFragment.newInstance());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onClickToQRRead() {
        new IntentIntegrator(this).setOrientationLocked(true).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result == null){
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if(result.getContents() == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            return;
        }

        this.is_read_qr = true;

        try {
            //復号化
            String plain = RSACipherClass.decryptionByPrivateKey(result.getContents(), this.privateKey);
            toQrReadFragmentBundle = new Bundle();
            toQrReadFragmentBundle.putString("result_text", plain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickToQRGenerate() {
        Log.d("click","click generate button");
        Intent intent = new Intent(MainActivity.this, QRGenerateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        intent.putExtra("opponentPublicKey",this.opponentPublicKey);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(this.is_read_qr){
            this.is_read_qr = false;
            //QRコード表示画面へ遷移
            FragmentManager fragmentManager = getSupportFragmentManager();
            if(fragmentManager != null){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // BackStackを設定
                fragmentTransaction.addToBackStack(null);
                QrReadFragment fragment = QrReadFragment.newInstance();
                fragment.setArguments(toQrReadFragmentBundle);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        }
    }
}

