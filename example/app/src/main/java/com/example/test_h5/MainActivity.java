package com.example.test_h5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.test_h5.databinding.ActivityMainBinding;
import com.mihe.MiHeAuthAndroid.AuthorizeManger;
import com.mihe.MiHeAuthAndroid.AuthHandler;
import com.mihe.MiHeAuthAndroid.MiheUserModel;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    String referer = "https://mihepay.ruwii.com/";
    boolean[] firstVisitWXH5PayUrl = {true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WebView webView = findViewById(R.id.web);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setSavePassword(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("weixin://")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "该手机没有安装微信", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                } else if (url.startsWith("alipays://") || url.startsWith("alipay")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    } catch (Exception e) {
                        return true;
                    }
                }
                if (!(url.startsWith("http") || url.startsWith("https"))) {
                    return true;
                }
                if (url.contains("wx.tenpay.com")) {
                    // 兼容 Android 4.4.3 和 4.4.4 两个系统版本设置 referer 无效的问题
                    if (("4.4.3".equals(android.os.Build.VERSION.RELEASE))
                            || ("4.4.4".equals(android.os.Build.VERSION.RELEASE))) {
                        if (firstVisitWXH5PayUrl[0]) {
                            view.loadDataWithBaseURL(referer, "<script>window.location.href=\"" + url + "\";</script>",
                                    "text/html", "utf-8", null);
                            // 修改标记位状态，避免循环调用
                            // 再次进入微信H5支付流程时记得重置状态 firstVisitWXH5PayUrl = true
                            firstVisitWXH5PayUrl[0] = false;
                        }
                        // 返回 false 由系统 WebView 自己处理该 url
                        return false;
                    } else {
                        HashMap<String, String> map = new HashMap<>(1);
                        map.put("Referer", referer);
                        view.loadUrl(url, map);
                        return true;
                    }
                }
                return false;
            }
        });
        AuthorizeManger manager = new AuthorizeManger();
        manager.initWithHandler(this, webView, handler);

        webView.loadUrl("https://t57.ruwii.com/mihe/#/");
    }

    AuthHandler handler = passedData -> {//passedData 整体格式为json字符
        //get the specified user with data passed from h5 page
        MiheUserModel user = getUser();
        return user;
    };

    MiheUserModel getUser() {
        MiheUserModel user = new MiheUserModel();
        user.uid = "8098908908908";//用户id
        user.cert="cert666";//用户凭证，可以是token sessionid 等信息
        user.img="img333";
        user.mobile = "17898888888";
        user.nickName = "nickname666";
        return user;
    }

}
