package com.example.healthbots;

 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.speech.tts.TextToSpeech;

import org.w3c.dom.Text;

import java.util.Locale;

public class WebViewActivity extends Activity {
 
	private WebView webView;
	public TextToSpeech t1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR) {
					t1.setLanguage(Locale.UK);
				}
			}
		});

		//JSInterface jsInterface = new JSInterface(this,t1);
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		//webView.addJavascriptInterface(jsInterface, "JSInterface");
//		webView.loadUrl("http://www.google.com");
	}
 
}

class JSInterface {
	public TextToSpeech t1;
	private Activity activity;

	public JSInterface(Activity activiy,TextToSpeech t) {
		this.activity = activiy;
		this.t1 = t;
	}

	public void Say(String sayText){
		Log.d("SAY",sayText);
		/*t1.stop();
		t1.shutdown();*/
		t1.speak(sayText, TextToSpeech.QUEUE_FLUSH, null);
	}
}