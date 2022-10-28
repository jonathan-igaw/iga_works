# IGA_WORKS 

## 개요

IGA_WORKS는 사용자가 메뉴를 클릭할 때, 어떤 메뉴를 누가 언제 어디서 무엇을 클릭했는지 등의 세세한 정보들을 서버에 기록합니다

이를 이용하여, 앱의 사용자들이 어떤 메뉴를 어떤 시간대에 가장 활발하게 사용했는지 등의 정보를 분석할 수 있습니다.

언뜻 보면 단순해보이는 데이터이지만, 활용하기에 따라서는 마케팅부터 개발의 방향성을 정하는 중요한 데이터가 됩니다.

<hr>

## 목차

### [AAR 삽입방법](###AAR-삽입-방법)

[1. AAR 파일 프로젝트에 추가](##1.AAR-파일-프로젝트에-추가)

[2. build.gradle 설정](##2.build.gradle-설정)

[3. AndroidManifest.xml 설정](##3.AndroidManifest.xml-설정)

### [사용법](###사용법)
[1. 클릭 이벤트 처리](##1.클릭-이벤트-처리)

[2. 로그인 처리](##2.로그인-처리)

[3. 로그아웃 처리](##3.로그아웃-처리)

<hr>

## AAR 삽입 방법

### 1. AAR 파일 프로젝트에 추가

![AAR File setting](/images/AAR경로-설정.png)

받은 AAR 파일을 \('IGASDK를 사용할 프로젝트 최상단 디렉터리'/libs\) 파일에 집어넣습니다.

### 2. build.gradle설정


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
### 1.클릭-이벤트-처리
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
10-28 20:26:37.643 20935 20959 D IGASDK  : request jsonBody : {"evt":{"created_at":"20221028202637","event":"test_event","param":{"menu_name":"menu1","menu_id":"30"},"user_properties":{"birthyear":0,"level":0,"gold":0}},"common":{"identity":{"adid":"a5f21bc1-4a1d-48e0-8829-6dee007da8c7","adid_opt_out":false},"device_info":{"os":30,"model":"sdk_gphone_x86","resolution":"1080x1776","is_portrait":true,"platform":"android","network":"mobile","carrier":"Android","language":"en","country":"US"},"package_name":"com.hig.iga_works_sdk","appkey":"inqbator@naver.com"}}
10-28 20:26:37.703 20935 20959 D IGASDK  : response code : 200
10-28 20:26:37.703 20935 20959 D IGASDK  : response : {"result":true,"message":"ok"}
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
10-28 20:26:37.643 20935 20959 D IGASDK  : request jsonBody : {"evt":{"created_at":"20221028202637","event":"click","param":{"menu_name":"menu1","menu_id":"30"},"user_properties":{"birthyear":0,"level":0,"gold":0}},"common":{"identity":{"adid":"a5f21bc1-4a1d-48e0-8829-6dee007da8c7","adid_opt_out":false},"device_info":{"os":30,"model":"sdk_gphone_x86","resolution":"1080x1776","is_portrait":true,"platform":"android","network":"mobile","carrier":"Android","language":"en","country":"US"},"package_name":"com.hig.iga_works_sdk","appkey":"inqbator@naver.com"}}
10-28 20:26:37.703 20935 20959 D IGASDK  : response code : 200
10-28 20:26:37.703 20935 20959 D IGASDK  : response : {"result":true,"message":"ok"}
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


### 2.로그인-처리

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


### 3.로그아웃-처리

사용자가 로그아웃을 했는데도 해당 사용자의 정보를 수집하는 행위는 잘못된 정보 수집이나 개인정보 침해로 이어질 수 있습니다.

이를 방지하기 위해서 IGASDK에서는 사용자의 정보를 삭제하는 메소드(logout())를 제공합니다.

**\[Java Kotlin 공통 코드\]**
``` java
// if : 로그인 성공하면, 사용자의 아이디를 등록합니다.
if (isLogoutSuccess) {
    IGASDK.logout();
}
```





