<div dir="rtl">

# سرویس REST API پروژه روانشناسی

این سرویس مبتنی بر فریم‌ورک اسپرینگ است و در آدرس
[https://api.psychology.ml]()
در دسترس می‌باشد.

## نکات فنی

### دیتابیس:

- برای اینیشیالایز کردن دیتابیس از ابزار مایگریشن Liquibase استفاده شده است.
- در محیط تست و توسعه از دیتابیس امبدد H2 استفاده شده است.
- در محیط پروداکشن از دیتابیس Postgresql استفاده شده است.

## پیش‌نیازها

برای ساخت و اجرای برنامه به ابزارهای زیر نیاز دارید:

- [JDK 18](https://jdk.java.net/18/)

## اجرای برنامه بر روی سیستم لوکال

برای اجرای برنامه از
[Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#running-your-application)
استفاده کنید:

<div dir="ltr">

### Unix Based

``` shell
./gradlew bootRun
```

### Windows

``` 
.\gradlew.bat bootRun
```

</div>

> **_نکته:_** بعد از اجرای برنامه، پنل دیتابیس H2 در مسیر [`localhost:8080/h2-console`](localhost:8080/h2-console) در دسترس است. و همچنین مستندات API در مسیر [`localhost:8080/swagger-ui/index.html`](localhost:8080/swagger-ui/index.html) در دسترس است.

</div>
