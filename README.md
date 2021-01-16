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
01-16/07:11:05 TRACE [localhost.localdomain-startStop-1] TerryPratchettFilterGroovy - init
01-16/07:12:58 TRACE [http-nio-0.0.0.0-8080-exec-3] HelloWorldGroovy - init
01-16/07:12:58 TRACE [http-nio-0.0.0.0-8080-exec-3] TerryPratchettFilterGroovy - doFilter
01-16/07:12:58 TRACE [http-nio-0.0.0.0-8080-exec-3] HelloWorldGroovy - doGet
```

## Что дальше?

* Ознакомьтесь с [описанием технологии Servlet](https://docs.oracle.com/javaee/7/tutorial/servlets.htm).
* Изучите [список фильтров, встроенных в Tomcat 8.5](https://tomcat.apache.org/tomcat-8.5-doc/config/filter.html).
* Посмотрите аналогичные проекты на других языках:
    * Clojure - https://github.com/alexanderfefelov/bgbilling-servlet-demo-clojure,
    * Java - https://github.com/alexanderfefelov/bgbilling-servlet-demo,
    * Kotlin - https://github.com/alexanderfefelov/bgbilling-servlet-demo-kotlin,
    * Scala - https://github.com/alexanderfefelov/bgbilling-servlet-demo-scala.
