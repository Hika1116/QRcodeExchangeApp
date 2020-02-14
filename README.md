# QRsampleApp
QRsampleApp

・概要
　暗号化したQRコードによるデータ交換アプリ。
　暗号キーを交換したアプリ間のみQRコード読取が可能です。

・技術
　QRコード読取/生成　　Zxing
　暗号化								RSA（Java標準ライブラリ）

・画面機能一覧
　セットアップ画面
     - 公開鍵（QRコード）の送信機能
     - 公開鍵（QRコード）の読取機能
 
	ホーム画面
	 - 機能選択
	    ：QR読み取り機能orQR生成機能を使用するかを選択
	
	QR読み取り機能
	 - QR読み取り画面
	 - 読み取り結果表示画面
	
	QR生成機能
	 - テキスト入力画面
	 - QRコード表示画面
	 