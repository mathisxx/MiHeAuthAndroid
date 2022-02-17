# MiHeAuthAndroid



Add this to your build.gradle and run sync:

```groovy
implementation 'io.github.yl172326:MiHeAuthAndroid:1.1'
```

**Manual installation**

Drag the folder into your project.

**Usage**

Import the header file and declare an ivar property:

```java
import com.mihe.MiHeAuthAndroid.AuthorizeManger;
import com.mihe.MiHeAuthAndroid.AuthHandler;
import com.mihe.MiHeAuthAndroid.MiheUserModel;
```


Instantiate MiHeAuth with a WebView:

```java
 
AuthHandler handler = new AuthHandler() {
        @Override
        public MiheUserModel handle(String passedData) {
    		//get the specified user with data passed from h5 page
        MiheUserModel user = getUser();
        Gson gson = new Gson();
        return gson.toJson(user);
        }
    };

AuthorizeManger manager = new AuthorizeManger();
manager.initWithHandler(context, webView, handler);

```

Get  datas to verify html webpage :

```objective-c
String s = manager.getAuthorizeDataFromH5();//获取校验数据，可选
```

Already  has user info :

```java
 //already has the user info
     MiheUserModel user = getUser();
     manager.initWithUser(context, webView, user);
```
