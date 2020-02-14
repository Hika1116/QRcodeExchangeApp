package com.example.qrsampleapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.qrsampleapp.R;
import com.example.qrsampleapp.fragment.DisplayPrivateKeyFragment;
import com.example.qrsampleapp.fragment.SetUpFragment;
import com.example.qrsampleapp.helper.CreateKeyClass;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

public class SetUpActivity extends AppCompatActivity implements SetUpFragment.OnNextButtonListener, SetUpFragment.OnGetKeyCheckBoxListener, SetUpFragment.OnSendKeyCheckBoxListener  {

    //自デバイスの暗号キー
    RSAPublicKey publicKey;   //公開鍵
    RSAPrivateKey privateKey; //秘密鍵

    //相手デバイスの公開鍵
    RSAPublicKey opponentPublicKey;

    //鍵交換を処理したかの判定
    private boolean is_send_publickey = false;
    private boolean is_get_publickey = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {

            if(publicKey == null && publicKey == null){
                //暗号キーの生成
                CreateKeyClass createKeyClass = new CreateKeyClass();
                publicKey = createKeyClass.createPublicKey();
                privateKey = createKeyClass.createPrivateKey();

                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, SetUpFragment.newInstance());
                    fragmentTransaction.commit();
                }
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public void onClickNext() {

        if(this.opponentPublicKey == null || this.privateKey == null){
            Toast.makeText(this, "キー交換に失敗しています。再起動してもう一度実行してください。", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(SetUpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        intent.putExtra("privateKey", this.privateKey);
        intent.putExtra("opponentPublicKey", this.opponentPublicKey);//TODO:本番時は入れ替える
//        intent.putExtra("opponentPublicKey", this.publicKey);//テスト用


        Log.d("send privateKey",this.publicKey.getModulus().toString());
        Log.d("send opponentPublicKey",this.publicKey.getModulus().toString());


        startActivity(intent);

    }

    @Override
    public void onCheckGetKey() {
        new IntentIntegrator(this).setOrientationLocked(true).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result == null){
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        //QRを読み込まなかった場合
        if(result.getContents() == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            this.is_get_publickey = true;

            // 取得した文字列から公開鍵復元
            String[] splitPublicKeyInfos = result.getContents().split(",");
            BigInteger modules = new BigInteger(splitPublicKeyInfos[0]);
            BigInteger publicExponent = new BigInteger(splitPublicKeyInfos[1]);
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modules, publicExponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            this.opponentPublicKey = (RSAPublicKey)keyFactory.generatePublic(publicKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckSendKey() {

        this.is_send_publickey = true;

        //送信する公開鍵文字列の作成
        StringBuffer context = new StringBuffer();
        context.append(publicKey.getModulus());
        context.append(",");
        context.append(publicKey.getPublicExponent());

        //QRコード表示画面へ遷移
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // BackStackを設定
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.container, DisplayPrivateKeyFragment.newInstance(context.toString()));
            fragmentTransaction.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null && fragment instanceof SetUpFragment) {
            ((SetUpFragment) fragment).cGetKey.setChecked(is_get_publickey);
            ((SetUpFragment) fragment).cGetKey.setEnabled(!is_get_publickey);
            ((SetUpFragment) fragment).cSendKey.setChecked(is_send_publickey);
            ((SetUpFragment) fragment).cSendKey.setEnabled(!is_send_publickey);

            if(is_get_publickey && is_send_publickey){
                ((SetUpFragment) fragment).bNext.setEnabled(true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //セットアップ画面内容更新
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null && fragment instanceof SetUpFragment) {
            ((SetUpFragment) fragment).cGetKey.setChecked(is_get_publickey);
            ((SetUpFragment) fragment).cGetKey.setEnabled(!is_get_publickey);
            ((SetUpFragment) fragment).cSendKey.setChecked(is_send_publickey);
            ((SetUpFragment) fragment).cSendKey.setEnabled(!is_send_publickey);
            if(is_get_publickey && is_send_publickey){
                ((SetUpFragment) fragment).bNext.setEnabled(true);
            }
        }

    }
}
