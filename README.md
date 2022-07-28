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

``` shell
./gradlew bootRun
```

</div>

> **_نکته:_** بعد از اجرای برنامه، پنل دیتابیس H2 در مسیر `localhost:4200/h2-console` در دسترس است.

> **_نکته_** مستندات API در مسیر `localhost:4200/swagger-ui.html` در دسترس است.

</div>
