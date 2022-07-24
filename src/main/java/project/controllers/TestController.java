package project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}

/*
Тестовый контроллер и вид
Запрос в браузере:
http://localhost:8080/test

Желательно запускать первым для проверки:
1. Общая работа настроек
2. Нормальная работа Tomcat
3. Тест связки только контроллер - представление
(без DAO и DB)
4. Настройки UTF-8 и поддержки русского языка
 */