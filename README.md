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

### 2.로그인-처리

### 3.로그아웃-처리
