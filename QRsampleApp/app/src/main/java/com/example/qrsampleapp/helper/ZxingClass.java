package com.example.qrsampleapp.helper;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

public class ZxingClass {

    private final int size = 500;

    public Bitmap createQrCode(String context) throws WriterException {

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

        HashMap hints = new HashMap();

        //文字コードの指定
        hints.put(EncodeHintType.CHARACTER_SET, "shiftjis");
        //誤り訂正レベルを指定
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //QRコードのバージョンを指定
        hints.put(EncodeHintType.QR_VERSION, 20);

        //QRコードをBitmapで作成
        Bitmap bitmap = barcodeEncoder.encodeBitmap(context, BarcodeFormat.QR_CODE, size, size, hints);

        return bitmap;
    }

}
