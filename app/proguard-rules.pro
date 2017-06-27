#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames         #混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify           #不预校验
-verbose                  #表示打印混淆的详细信息
-ignorewarnings          # 抑制警告
-dontoptimize            #不进行优化,建议使用此选项，因为根据proguard-android-optimize.txt.txt中的描述，优化可能会造成一些潜在风险，不能保证在所有版本的Dalvik上都正常运行。
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses,Signature,Exceptions
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * extends android.view.View{
    <fields>;
    <methods>;
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * extends android.widget.** {
    <fields>;
    <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
  public protected <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
    *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#不混淆注解
-keep class * extends java.lang.annotation.Annotation {
    *;
}
-keep interface * extends java.lang.annotation.Annotation {
    *;
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
    <fields>;
    <methods>;
}
#----------------------------------------------------------------------------
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------



#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** {
    <fields>;
    <methods>;
}

-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** {
    <fields>;
    <methods>;
}

-dontwarn com.baidu.**
-keep class com.baidu.** {
    <fields>;
    <methods>;
}



-dontwarn com.google.zxing.**
-keep class com.google.zxing.** {
    <fields>;
    <methods>;
}

-dontwarn java.annotation.**
-keep class java.annotation.** {
    <fields>;
    <methods>;
}


-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn okhttp3.**
-keep class okhttp3.** {
    <fields>;
    <methods>;
}


-dontwarn okio.**
-keep class okio.** {
    <fields>;
    <methods>;
}

#WheelPicker
-keepattributes InnerClasses,Signature
-keepattributes *Annotation*

-keep class cn.qqtheme.framework.entity.** { *;}

#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------


#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------

#---------------------------------5.自身-----------------------



#----------------------------------------------------------------------------


#---------------------------------------------------------------------------------------------------