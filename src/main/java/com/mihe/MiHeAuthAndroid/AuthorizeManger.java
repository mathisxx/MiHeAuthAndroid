package com.mihe.MiHeAuthAndroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.google.gson.Gson;

import javax.security.auth.callback.UnsupportedCallbackException;


public class AuthorizeManger {
    WebView webView;
    Context mContext;
    String passedData = "";
    MiheUserModel user;
    WebAppInterface webInterface;

    @SuppressLint("SetJavaScriptEnabled")
    public void init(Context c, WebView wv) {
        webView = wv;
        mContext = c;
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public String getAuthorizeDataFromH5() {
        return passedData;
    }

    public void setUser(MiheUserModel model) {
        this.user = model;
        if (webInterface != null){
            webInterface.user = model;
        }
    }

    public void initWithUser(Context c, WebView wv, MiheUserModel model) {
        init(c, wv);
        webInterface = new WebAppInterface(c,null,model);
        webView.addJavascriptInterface(webInterface, "mihe");
    }

    public void initWithHandler(Context c, WebView wv, AuthHandler handler) {
        init(c, wv);
        webInterface = new WebAppInterface(c,handler,null);
        webView.addJavascriptInterface(webInterface, "mihe");
    }

    //接口名称自定义
    public class WebAppInterface {
        Context mContext;
        AuthHandler handler;
        MiheUserModel user;

        WebAppInterface(Context c,AuthHandler ha,MiheUserModel u) {
            mContext = c;
            handler = ha;
            user = u;
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
        public String getUserIdwithParams(String data) {
            if (handler != null ){
                AuthorizeManger.this.passedData = data;
                MiheUserModel user = handler.handle(data);
                if (user != null) {
                    Gson gson = new Gson();
                    return gson.toJson(user);
                }
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
