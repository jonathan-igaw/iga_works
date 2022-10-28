# IGA_WORKS 사용법

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

**\[코드 실행 결과 요청하는 JSON 파일\]**
```
{

    "evt":{
        "created_at":"20221028135824",
        "event":"사용자가 설정하고 싶은 이벤트명",
        // location은 사용자가 위치 요청 권한을 설정하지 않으면 null 처리 됩니다.
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

### 3.로그아웃-처리
