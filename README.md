# bgbilling-servlet-demo-groovy

## Что это?

bgbilling-servlet-demo-groovy - это Groovy-версия демонстрационной реализации сервлетов и фильтров для использования совместно с сервером [BGBilling](https://bgbilling.ru/).

## Требования

* git
* JDK 8
* [gradle](https://gradle.org/)

## Как это установить?

Выполните команды:

```
git clone https://github.com/alexanderfefelov/bgbilling-servlet-demo-groovy
cd bgbilling-servlet-demo-groovy
gradle assemble
```

Скопируйте jar-файл, созданный в результате в каталоге `build/libs`, в каталог `lib/custom` вашего экземпляра BGBilling.

## Привет, мир!

- [HelloWorldGroovy.groovy](src/main/groovy/com/github/alexanderfefelov/bgbilling/servlet/demo/HelloWorldGroovy.groovy)
- [TerryPratchettFilterGroovy.groovy](src/main/groovy/com/github/alexanderfefelov/bgbilling/servlet/demo/TerryPratchettFilterGroovy.groovy)

В конфигурацию BGBilling добавьте:

```properties
# Servlet: Привет, мир!
#
custom.servlet.keys=HelloWorldGroovy
#                   │              │
#                   └────┬─────────┘
#                        │
#                  Ключ сервлета                               Класс сервлета
#                        │                                            │
#              ┌─────────┴────┐       ┌───────────────────────────────┴─────────────────────────────────┐
#              │              │       │                                                                 │
custom.servlet.HelloWorldGroovy.class=com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorldGroovy
custom.servlet.HelloWorldGroovy.mapping=/demo-servlet/hello-world-groovy
#                                       │                              │
#                                       └───────────────┬──────────────┘
#                                                       │
#                                           Часть URL после контекста
#
custom.servlet.HelloWorldGroovy.filter.keys=TerryPratchettGroovy
#                                           │                  │
#                                           └──────┬───────────┘
#                                                  │
#                                             Ключ фильтра
#                                                  │
#                                      ┌───────────┴──────┐
#                                      │                  │
custom.servlet.HelloWorldGroovy.filter.TerryPratchettGroovy.name=TerryPratchettGroovy
custom.servlet.HelloWorldGroovy.filter.TerryPratchettGroovy.class=com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterGroovy
#                                                                 │                                                                           │
#                                                                 └──────────────────────────────────┬────────────────────────────────────────┘
#                                                                                                    │
#                                                                                              Класс фильтра
```

Перезапустите BGBilling.

Если всё в порядке, в логах можно будет увидеть:

```
01-16/07:10:56  INFO [main] Server - Add custom servlet from setup...
01-16/07:10:56  INFO [main] Server - Custom.servlet.keys => HelloWorldGroovy
01-16/07:10:56  INFO [main] Server - Custom.servlet.class => com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorldGroovy
01-16/07:10:56  INFO [main] Server - Custom.servlet.mapping => /demo-servlet/hello-world-groovy
01-16/07:10:56  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.HelloWorldGroovy to /demo-servlet/hello-world-groovy
01-16/07:10:56  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterGroovy to /demo-servlet/hello-world-groovy
```

Теперь выполните:

```
http --verbose --check-status \
  GET http://bgbilling-server.backpack.test:63081/billing/demo-servlet/hello-world-groovy
```

В результате на запрос:

```
GET /billing/demo-servlet/hello-world-groovy HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: bgbilling-server.backpack.test:63081
User-Agent: HTTPie/1.0.3
```

будет получен ответ:

```
HTTP/1.1 200 OK
Content-Length: 13
Date: Sat, 16 Jan 2021 04:12:58 GMT
X-Clacks-Overhead: GNU Terry Pratchett

Hello, World!
```

## О системе

- [SysInfoGroovy.groovy](src/main/groovy/com/github/alexanderfefelov/bgbilling/servlet/demo/SysInfoGroovy.groovy)
- [UptimePuncherFilterGroovy.groovy](src/main/groovy/com/github/alexanderfefelov/bgbilling/servlet/demo/UptimePuncherFilterGroovy.groovy)

Добавьте в конфигурацию BGBilling:

```properties
# Servlet: О системе
#
custom.servlet.keys=SysInfoGroovy
#                   │           │
#                   └───┬───────┘
#                       │
#                 Ключ сервлета                            Класс сервлета
#                       │                                         │
#              ┌────────┴──┐       ┌──────────────────────────────┴───────────────────────────────┐
#              │           │       │                                                              │
custom.servlet.SysInfoGroovy.class=com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfoGroovy
custom.servlet.SysInfoGroovy.mapping=/demo-servlet/sys-info-groovy
#                                    │                           │
#                                    └─────────────┬─────────────┘
#                                                  │
#                                      Часть URL после контекста
#
custom.servlet.SysInfoGroovy.filter.keys=UptimePuncherGroovy,TerryPratchettGroovy,CORS
#                                        │                 │ │                  │ │  │
#                                        └─────┬───────────┘ └────────┬─────────┘ └─┬┘
#                                              │                      │             │
#                                         Ключ фильтра           Ещё фильтр       И ещё один
#                                              │
#                                   ┌──────────┴──────┐
#                                   │                 │
custom.servlet.SysInfoGroovy.filter.UptimePuncherGroovy.name=UptimePuncherGroovy
custom.servlet.SysInfoGroovy.filter.UptimePuncherGroovy.class=com.github.alexanderfefelov.bgbilling.servlet.demo.UptimePuncherFilterGroovy
#                                                             │                                                                          │
#                                                             └──────────────────────────────────┬───────────────────────────────────────┘
#                                                                                                │
#                                                                                          Класс фильтра
custom.servlet.SysInfoGroovy.filter.TerryPratchettGroovy.name=TerryPratchettGroovy
custom.servlet.SysInfoGroovy.filter.TerryPratchettGroovy.class=com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterGroovy
custom.servlet.SysInfoGroovy.filter.CORS.name=CORS
custom.servlet.SysInfoGroovy.filter.CORS.class=org.apache.catalina.filters.CorsFilter
custom.servlet.SysInfoGroovy.filter.CORS.init-param.keys=AllowedOrigins
#                                                        │            │
#                                                        └───┬────────┘
#                                                            │
#                                                      Ключ параметра    Название параметра
#                                                            │                    │
#                                                   ┌────────┴───┐      ┌─────────┴────────┐
#                                                   │            │      │                  │
custom.servlet.SysInfoGroovy.filter.CORS.init-param.AllowedOrigins.name=cors.allowed.origins
custom.servlet.SysInfoGroovy.filter.CORS.init-param.AllowedOrigins.value=*
#                                                                        │
#                                                                        │
#                                                               Значение параметра
```

Перезапустите BGBilling.

Теперь в логах будет так:

```
01-16/09:51:38  INFO [main] Server - Add custom servlet from setup...
01-16/09:51:38  INFO [main] Server - Custom.servlet.keys => SysInfoGroovy
01-16/09:51:38  INFO [main] Server - Custom.servlet.class => com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfoGroovy
01-16/09:51:38  INFO [main] Server - Custom.servlet.mapping => /demo-servlet/sys-info-groovy
01-16/09:51:38  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.SysInfoGroovy to /demo-servlet/sys-info-groovy
01-16/09:51:38  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.UptimePuncherFilterGroovy to /demo-servlet/sys-info-groovy
01-16/09:51:38  INFO [main] Server - Add mapping: com.github.alexanderfefelov.bgbilling.servlet.demo.TerryPratchettFilterGroovy to /demo-servlet/sys-info-groovy
01-16/09:51:38  INFO [main] Server - Add mapping: org.apache.catalina.filters.CorsFilter to /demo-servlet/sys-info-groovy
```

и в ответ на запрос:

```
http --verbose --check-status \
  GET http://bgbilling-server.backpack.test:63081/billing/demo-servlet/sys-info-groovy \
    "Origin: http://example.com"
```

```
GET /billing/demo-servlet/sys-info-groovy HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: bgbilling-server.backpack.test:63081
Origin: http://example.com
User-Agent: HTTPie/1.0.3
```

вы получите:

```
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: http://example.com
Content-Length: 488
Date: Sat, 16 Jan 2021 07:21:59 GMT
Vary: Origin
X-BGBilling-Server-Uptime: Started: 16.01.2021 10:21:45 Uptime: 0 d 00:00:13
X-Clacks-Overhead: GNU Terry Pratchett

Modules
--------------------------------------------------

0 kernel 8.0.1320 / 16.12.2020 18:10:08
2 inet 8.0.832 / 15.12.2020 17:06:32
1 card 8.0.307 / 06.10.2020 01:52:21
3 npay 8.0.287 / 19.11.2020 18:41:17
5 subscription 8.0.128 / 06.10.2020 01:52:39
4 rscm 8.0.272 / 06.10.2020 01:52:36

Runtime
--------------------------------------------------

Hostname/IP address: bgbilling-server.backpack.test/172.17.0.8
Available processors: 8
Memory free / max / total, MB: 288 / 444 / 355
```

## Логи

Для того, чтобы собирать логи сервлетов в отдельный файл, необходимо изменить `data/log4j.xml`.

Добавьте новый аппендер:

```xml
<appender name="SERVLET" class="org.apache.log4j.RollingFileAppender">
    <param name="File" value="log/servlet.log"/>
    <param name="MaxFileSize" value="50MB"/>
    <param name="MaxBackupIndex" value="3"/>
    <param name="Append" value="true"/>

    <layout class="org.apache.log4j.PatternLayout">
        <param name="ConversionPattern" value="%d{MM-dd/HH:mm:ss} %5p [%t] %c{1} - %m%n"/>
    </layout>

    <filter class="ru.bitel.common.logging.Log4JMDCFilter">
        <param name="key" value="nestedContext"/>
        <param name="value" value="servlet"/>
    </filter>
</appender>
```

и исправьте имеющийся:

```xml
<appender name="ASYNC" class="ru.bitel.common.logging.Log4jAsyncAppender">
    <appender-ref ref="APPLICATION"/>
    <appender-ref ref="ERROR"/>
    <appender-ref ref="MQ"/>
    <appender-ref ref="SCRIPT"/>
    <appender-ref ref="SERVLET"/>
</appender>
```

В результате после перезапуска BGBilling в файле `log/servlet.log` можно будет увидеть что-то вроде:

```
01-16/10:21:58 TRACE [localhost.localdomain-startStop-1] UptimePuncherFilterGroovy - init
01-16/10:21:58 TRACE [localhost.localdomain-startStop-1] TerryPratchettFilterGroovy - init
01-16/10:21:59 TRACE [http-nio-0.0.0.0-8080-exec-1] SysInfoGroovy - init
01-16/10:21:59 TRACE [http-nio-0.0.0.0-8080-exec-1] UptimePuncherFilterGroovy - doFilter
01-16/10:21:59 TRACE [http-nio-0.0.0.0-8080-exec-1] TerryPratchettFilterGroovy - doFilter
01-16/10:21:59 TRACE [http-nio-0.0.0.0-8080-exec-1] SysInfoGroovy - doGet
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Изучите [список фильтров, встроенных в Tomcat 8.5](https://tomcat.apache.org/tomcat-8.5-doc/config/filter.html).
* Посмотрите аналогичные проекты на других языках:
    * Clojure - https://github.com/alexanderfefelov/bgbilling-servlet-demo-clojure,
    * Java - https://github.com/alexanderfefelov/bgbilling-servlet-demo,
    * Kotlin - https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin,
    * Scala - https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala.
* Посмотрите, как можно расширить функционал BGBilling с помощью других механизмов:
  * Dynaction - https://github.com/alexanderfefelov/bgbilling-dynaction-demo,
  * Dynservice - https://github.com/alexanderfefelov/bgbilling-dynservice-demo.
