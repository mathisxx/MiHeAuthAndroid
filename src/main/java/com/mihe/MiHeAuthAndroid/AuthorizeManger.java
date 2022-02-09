package com.mihe.MiHeAuthAndroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.google.gson.Gson;


public class AuthorizeManger {
    WebView webView;
    Context mContext;
    String passedData = "";
    MiheUserModel user;

    @SuppressLint("SetJavaScriptEnabled")
    public void init(Context c, WebView wv) {
        webView = wv;
        mContext = c;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(c), "mihe");
    }

    public String getAuthorizeDataFromH5() {
        return passedData;
    }

    public void setUser(MiheUserModel model) {
        this.user = model;
    }

    public void initWithUser(Context c, WebView wv, MiheUserModel model) {
        setUser(model);
        init(c, wv);
    }

    //接口名称自定义
    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public String getUserId(String data) {
            if (user != null) {
                Gson gson = new Gson();
                return gson.toJson(user);
            }
            return "";
        }

        @JavascriptInterface
        public String setParams(String data) {
            passedData = data;
            return data;
        }

    }
}
