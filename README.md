# IGA_WORKS 

## 개요

IGA_WORKS는 사용자가 메뉴를 클릭할 때, 어떤 메뉴를 누가 언제 어디서 무엇을 클릭했는지 등의 세세한 정보들을 서버에 기록합니다

이를 이용하여, 앱의 사용자들이 어떤 메뉴를 어떤 시간대에 가장 활발하게 사용했는지 등의 정보를 분석할 수 있습니다.

언뜻 보면 단순해보이는 데이터이지만, 활용하기에 따라서는 마케팅부터 개발의 방향성을 정하는 중요한 데이터가 됩니다.

<hr>

## 목차

### [AAR 삽입방법](#AAR-삽입-방법)

[1. AAR 파일 프로젝트에 추가](#1aar-파일-프로젝트에-추가)

[2. build.gradle 설정](#2buildgradle-설정)

[3. AndroidManifest.xml 설정](#3AndroidManifestxml-설정)

### [사용법](###사용법)

[1. 사용자 정보 설정](#1사용자-정보-설정)

[2. 클릭 이벤트 처리](#2클릭-이벤트-처리)

[3. 로그인 처리](#3로그인-처리)

[4. 로그아웃 처리](#4로그아웃-처리)

<hr>

## AAR 삽입 방법

### 1.AAR 파일 프로젝트에 추가

![AAR File setting](/images/AAR경로-설정.png)

받은 AAR 파일을 \('IGASDK를 사용할 프로젝트 최상단 디렉터리'/libs\) 파일에 집어넣습니다.

### 2.build.gradle설정


``` xml
android {
    compileSdk 33

    defaultConfig {
        ...
        minSdk 24
        ...
    } 
...
dependencies {
  // AAR Implementation
  implementation files('../libs/iga_works_sdk-debug.aar')
  ...
}
```

1. build.gradle(:app)에 들어갑니다.
2. IGASDK가 지원하는 최소 Android API는 24이므로 defaultConfig 블록에 minSdk를 24로 변경합니다.
3. AAR을 사용하기 위해 dependencies에서 해당 라이브러리를 구현합니다.

### 3. AndroidManifest.xml 설정

``` xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <application
        android:name="com.hig.iga_works_sdk.IGASDKApplication"
        ...
```
application 태그의 name 속성을 IGASDKApplication로 설정합니다.

이로 인해 사용자의 앱이 실행될 때, 기본 Application으로 초기화 되지 않고 IGASDKApplication로 초기화됩니다.

<hr>

## 사용법

### 1.사용자 정보 설정

IGASDK는 사용자의 정보를 설정하여 데이터를 수집할 때, 사용자의 정보도 같이 수집할 수 있습니다.
IGASDK의 setUserProperty(Map map)메소드를 이용하여 이를 구현할 수 있습니다.

**\[Java 코드\]**

``` java
Map<String, Object> mapOfUserProperty = new HashMap<>();
mapOfUserProperty.put("birthyear", 2022);
mapOfUserProperty.put("gender", "Unknown");
mapOfUserProperty.put("level", 220);
mapOfUserProperty.put("gold", 999999);
IGASDK.setUserProperty(mapOfUserProperty);
```

**\[Kotlin 코드\]**

``` kotlin
val mapOfUserProperty = HashMap<String, Object>()
mapOfUserProperty.put("birthyear", 2022)
mapOfUserProperty.put("gender", "Unknown")
mapOfUserProperty.put("level", 220)
mapOfUserProperty.put("gold", 999999)
IGASDK.setUserProperty(mapOfUserProperty)
```


**\[코드 실행 로그\]**

```
D  request jsonValue : {"evt":{"created_at":"20221028220052","event":"MainActivity - Click Register Button","location":{"lat":37.421998333333335,"lng":-122.084},"param":{"menu_name":"com.hig.iga_works:id\/button_register_member_info","menu_id":2131231206},"user_properties":{"birthyear":2022,"gender":"Unknown","level":220,"gold":999999}},"common":{"identity":{"adid":"a5f21bc1-4a1d-48e0-8829-6dee007da8c7","adid_opt_out":false},"device_info":{"os":30,"model":"sdk_gphone_x86","resolution":"1080x1776","is_portrait":true,"platform":"android","network":"mobile","carrier":"Android","language":"en","country":"US"},"package_name":"com.hig.iga_works_sdk","appkey":"inqbator@naver.com"}}
I  response code : 200
D  response : {"result":true,"message":"ok"}
```

<hr>

### 2.클릭-이벤트-처리
IGASDK는 View.OnClickListner를 상속 받는 IGAMenuClickListener 클래스를 제공합니다.
메뉴 클릭 이벤트를 처리할 때, 해당 클래스를 상속 받는 ClickListener를 사용하면 사용자가 따로 처리하지 않아도 알아서 메뉴에 대한 정보를 서버로 전송합니다.

**\[Java 코드\]**
``` java
Button buttonMenu = findViewById(R.id.button_menu);
buttonMenu.setOnClickListener(new IGAMenuClickListener("사용자가 설정하고 싶은 이벤트명") {
    @Override
    public void onClick(View view) {
        super.onClick(view);
        // 사용자가 하고 싶은 행위 
    }
});
```

**\[Kotlin 코드\]**

``` kotlin
val buttonMenu = findViewById<Button>(R.id.button_findid_submit)
buttonMenu.setOnClickListener(object: IGAMenuClickListener("사용자가 설정하고 싶은 이벤트명") {
    override fun onClick(view: View?) {
        super.onClick(view)
        // 사용자가 하고 싶은 행위
    }
})
```

**\[코드 실행 로그\]**

``` 
D IGASDK  : request jsonBody : {"evt":{"created_at":"20221028202637","event":"test_event","param":{"menu_name":"menu1","menu_id":"30"},"user_properties":{"birthyear":0,"level":0,"gold":0}},"common":{"identity":{"adid":"a5f21bc1-4a1d-48e0-8829-6dee007da8c7","adid_opt_out":false},"device_info":{"os":30,"model":"sdk_gphone_x86","resolution":"1080x1776","is_portrait":true,"platform":"android","network":"mobile","carrier":"Android","language":"en","country":"US"},"package_name":"com.hig.iga_works_sdk","appkey":"inqbator@naver.com"}}
D IGASDK  : response code : 200
D IGASDK  : response : {"result":true,"message":"ok"}
```

**\[코드 실행 결과 요청하는 JSON 파일\]**
```
{

    "evt":{
        "created_at":"20221028202637",
        "event":"사용자가 설정하고 싶은 이벤트명",
        "param":{
            "menu_name":"menu1",
            "menu_id":"30"
        },
        "user_properties":{
            "birthyear":0,
            "level":0,
            "gold":0
        }
    },
    "common":{
        "identity":{
            "adid":"a5f21bc1-4a1d-48e0-8829-6dee007da8c7",
            "adid_opt_out":false
        },
        "device_info":{
            "os":30,
            "model":"sdk_gphone_x86",
            "resolution":"1080x1776",
            "is_portrait":true,
            "platform":"android",
            "network":"mobile",
            "carrier":"Android",
            "language":"en",
            "country":"US"
        },
        "package_name":"com.hig.iga_works_sdk",
        "appkey":"inqbator@naver.com"
    }

}
```



<br>
만약, 사용자가 설정하고 싶은 이벤트명이 없으면 기본 생성자를 사용하면 됩니다.

기본 생성자를 사용하는 경우에 이벤트명은 click이 나옵니다.

**\[Java 코드\]**
``` java
Button buttonMenu = findViewById(R.id.button_menu);
buttonMenu.setOnClickListener(new IGAMenuClickListener() {
    @Override
    public void onClick(View view) {
        super.onClick(view);
        // 사용자가 하고 싶은 행위 
    }
});
```

**\[Kotlin 코드\]**
``` kotlin
val buttonMenu = findViewById<Button>(R.id.button_findid_submit)
buttonMenu.setOnClickListener(object: IGAMenuClickListener() {
    override fun onClick(view: View?) {
        super.onClick(view)
        // 사용자가 하고 싶은 행위
    }
})
```

**\[코드 실행 로그\]**

``` 
D IGASDK  : request jsonBody : {"evt":{"created_at":"20221028202637","event":"click","param":{"menu_name":"menu1","menu_id":"30"},"user_properties":{"birthyear":0,"level":0,"gold":0}},"common":{"identity":{"adid":"a5f21bc1-4a1d-48e0-8829-6dee007da8c7","adid_opt_out":false},"device_info":{"os":30,"model":"sdk_gphone_x86","resolution":"1080x1776","is_portrait":true,"platform":"android","network":"mobile","carrier":"Android","language":"en","country":"US"},"package_name":"com.hig.iga_works_sdk","appkey":"inqbator@naver.com"}}
D IGASDK  : response code : 200
D IGASDK  : response : {"result":true,"message":"ok"}
```



**\[코드 실행 결과 요청하는 JSON 파일\]**
``` json
{

    "evt":{
        "created_at":"20221028141823",
        "event":"click",
        "location":{
            "lat":37.421998333333335,
            "lng":-122.084
        },
        "param":{
            "menu_name":"com.hig.iga_works:id\/button_menu",
            "menu_id":2131230819
        },
        "user_properties":{
            "birthyear":0,
            "level":0,
            "gold":0
        }
    },
    "common":{
        "identity":{
            "adid":"a5f21bc1-4a1d-48e0-8829-6dee007da8c7",
            "adid_opt_out":false
        },
        "device_info":{
            "os":30,
            "model":"sdk_gphone_x86",
            "resolution":"1080x1776",
            "is_portrait":true,
            "platform":"android",
            "network":"mobile",
            "carrier":"Android",
            "language":"en",
            "country":"US"
        },
        "package_name":"com.hig.iga_works_sdk",
        "appkey":"inqbator@naver.com"
    }

}
```

<hr>

### 3.로그인-처리

사용자들의 데이터를 수집하는 것도 중요하지만, 더욱 중요한 것은 상호작용하는 주체가 누구인지입니다.

특정 사용자의 데이터를 수집하는 것으로 해당 사용자의 기호를 추측할 수 있고 이는 더 효율적인 사용자 파악과 마케팅에 도움이 됩니다.

IGASDK에서는 이를 위해 로그인 사용자를 등록하는 메소드(login(String userId))를 제공합니다. 


**\[Java Kotlin 공통 코드\]**
``` java
// if : 로그인 성공하면, 사용자의 아이디를 등록합니다.
if (isLoginSuccess) {
    // 매개 변수로 사용자의 아이디를 입력합니다.
    IGASDK.login("userId");
}
```
<hr>

### 4.로그아웃-처리

사용자가 로그아웃을 했는데도 해당 사용자의 정보를 수집하는 행위는 잘못된 정보 수집이나 개인정보 침해로 이어질 수 있습니다.

이를 방지하기 위해서 IGASDK에서는 사용자의 정보를 삭제하는 메소드(logout())를 제공합니다.

**\[Java Kotlin 공통 코드\]**
``` java
// if : 로그아웃에 성공하면 사용자의 정보를 삭제합니다.
if (isLogoutSuccess) {
    IGASDK.logout();
}
```

<hr>

### 5.로컬 푸시

IGASDK의 로컬 푸시를 사용하면 간단하게 푸시 메시지를 사용자에게 보낼 수 있습니다.

IGASDK의 setLocalPushNotification() 메소드를 사용해서 푸시 메시지를 보내보세요.


**\[Java 코드\]**

``` java
IGASDK.LocalPushProperties lpp = new IGASDK.LocalPushProperties(
        "contentTitle",
        "contentText",
        "summaryText",
        5 * 1000,
        1,
        NotificationManager.IMPORTANCE_HIGH
);
IGASDK.setLocalPushNotification(lpp);
```

**\[Kotlin 코드\]**

``` kotlin
val lpp = IGASDK.LocalPushProperties(
        "contentTitle",
        "contentText",
        "summaryText",
        5 * 1000,
        1,
        NotificationManager.IMPORTANCE_HIGH
)
IGASDK.setLocalPushNotification(lpp)
```


IGASDK의 LocalPushProperties 클래스는 알림을 보내기 위해 필요한 설정을 기록하는 클래스입니다.

멤버 변수를 하나 하나 설명해보겠습니다.

String contentTitle = 알림의 제목을 나타냅니다.

String contentText = 알림의 내용을 나타냅니다.

String summaryText = 알림의 요약 내용을 나타냅니다.

long millisecondForDelay = 알림이 현재 시각을 기준으로 몇 ms 후에 나타날지를 설정하는 변수입니다. 만약 알림이 즉시 뜨길 원하면 0을 5초 후면 5000 = 5 * 1000을 입력하면 됩니다.

int eventId = 알림의 채널입니다.

int importance = 알림의 중요도입니다. 

알림의 중요도로는 밑에 5가지가 올 수 있습니다. 내용을 파악하고 원하는 알림을 설정하시면 됩니다.

**\[실행 결과\]**

![](/images/notification.png)


**\[알림 중요도\]**


알림 중요도(importance)를 설정함으로써, 알림을 받았을 때 기기에 반응이 달라집니다.

아래 중요도 표를 참조하여 알림을 설정해주세요.


``` java
NotificationManager.IMPORTANCE_HIGH         // 알림 표시 ON / 소리 / 팝업으로 표시
NotificationManager.IMPORTANCE_DEFAULT      // 알림 표시 ON / 소리
NotificationManager.IMPORTANCE_LOW          // 알림 표시 ON / 무음 / 알림 최소화 OFF
NotificationManager.IMPORTANCE_MIN          //  알림 표시 ON / 무음 / 알림 최소화 ON
NotificationManager.IMPORTANCE_NONE         // 알림 표시 OFF
```











