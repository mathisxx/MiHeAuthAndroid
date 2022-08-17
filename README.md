# MiHeAuthAndroid



Add this to your build.gradle and run sync:

```groovy
implementation 'io.github.yl172326:MiHeAuthAndroid:1.3'
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
 //default using
AuthHandler handler = new AuthHandler() {
        @Override
        public MiheUserModel handle(String passedData) {
    		//get the specified user with data passed from h5 page
        MiheUserModel user = getUser();
        return user;
        }
    };

AuthorizeManger manager = new AuthorizeManger();
manager.initWithHandler(context, webView, handler);

```

Get  datas to verify html webpage :

```java
 //only for customize
//String s = manager.getAuthorizeDataFromH5();//获取校验数据，可选
```

Already  has user info :

```java
 //already has the user info ， only for customize
 //   MiheUserModel user = getUser();
 //  manager.initWithUser(context, webView, user);
```
if set minifyEnabled true ,add follows

```java
 -keep class com.mihe.MiHeAuthAndroid.**{*;}
```
