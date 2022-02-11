# MiHeAuthAndroid



Add this to your podfile and run pod install to install:

```groovy
implementation 'io.github.yl172326:MiHeAuthAndroid:1.0'
```

**Manual installation**

Drag the folder into your project.

**Usage**

Import the header file and declare an ivar property:

```java
import com.mihe.MiHeAuthAndroid.AuthorizeManger;
import com.mihe.MiHeAuthAndroid.MiheUserModel;
```


Instantiate MiHeAuth with a WebView:

```java
 WebSettings webSettings = webView.getSettings();
 webSettings.setJavaScriptEnabled(true);
 AuthorizeManger manager = new AuthorizeManger();
 manager.init(this,webView);
```

Get  datas to verify html webpage :

```objective-c
NSString* s = [AuthorizeManger getAuthorizeDataFromH5];//获取校验数据，可选
```

Passed  User  Info  to html webpage  :

```objective-c
  [AuthorizeManger setUpBridgeFromData: ^(id data, WVJBResponseCallback responseCallback) {
 	NSLog(@"ObjC ------ called with: %@", data);
    //get the specified user with data passed from h5 page
 	NSMutableDictionary *dict = [NSMutableDictionary dictionary];
  dict[@"uid"] = @"666uiud";//用户id 必填
  dict[@"cert"] = @"888";//用户登录凭证
  dict[@"mobile"] = @"111";//手机号
  dict[@"name"] = @"222";//用户姓名
  dict[@"nickName"] = @"999nick";//用户昵称
  dict[@"img"] = @"000img";//用户头
    NSString *str = [ ViewController convertToJsonData:dict];
    [NSThread sleepForTimeInterval:10];
    responseCallback(str);
}];


```

or just :

```java
 //already has the user info
     MiheUserModel user = getUser();
     manager.initWithUser(this,webView,user);
```

