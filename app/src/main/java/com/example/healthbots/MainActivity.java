package com.example.healthbots;
 
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.webkit.SslErrorHandler;

import java.util.Locale;

public class MainActivity extends Activity {
 
//	private Button button;
	private WebView webView;
	private WebChromeClient webchromeclient;
 
//	public void onCreate(Bundle savedInstanceState) {
//		final Context context = this;
// 
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
// 
//		button = (Button) findViewById(R.id.buttonUrl);
// 
//		button.setOnClickListener(new OnClickListener() {
// 
//		  @Override
//		  public void onClick(View arg0) {
//		    Intent intent = new Intent(context, WebViewActivity.class);
//		    startActivity(intent);
//		  }
// 
//		});
// 
//	}
    public TextToSpeech t1;
    public JSInterface jsInterface;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		//SkypeCall jsInterface = new SkypeCall();
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
        this.jsInterface = new JSInterface(this,t1);
		webView.setWebViewClient(new MyAppWebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true); //Maybe you don't need this rule
        settings.setAllowUniversalAccessFromFileURLs(true);
        webView.addJavascriptInterface(jsInterface, "JSInterface");

        webView.setWebChromeClient(new WebChromeClient());
		//webView.loadUrl("file:///android_asset/html2/irobiGlobal.html");
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d("fghfgh", "No SDCARD");
        } else {
            webView.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/html2/irobiGlobal.html");
        }
		context = getApplicationContext();
	}
	
	static Context context;
	
	@Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
 
}

class MyAppWebViewClient extends WebViewClient {
    
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        if(Uri.parse(url).getHost().endsWith("html5rocks.com")) {
//            return false;
//        }
//         
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        view.getContext().startActivity(intent);
//        return true;
    	return false;
    }
    
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed(); // Ignore SSL certificate errors
    }
}


class SkypeCall{
	public void callSkype(){
		try {
            //Intent sky = new Intent("android.intent.action.CALL_PRIVILEGED");
            //the above line tries to create an intent for which the skype app doesn't supply public api

            Intent sky = new Intent("android.intent.action.VIEW");
            sky.setData(Uri.parse("skype:"));
            Log.d("UTILS", "calling");
            MainActivity.context.startActivity(sky);
        } catch (ActivityNotFoundException e) {
            Log.e("SKYPE CALL", "Skype failed", e);
        }
	}
}   