package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.models.Person;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from Person",
                new BeanPropertyRowMapper<>(Person.class));
    }
}
/** JdbcTemplate

В данном случае мы используем бин JdbcTemplate,
который мы создали в SpringConfig.java
и который использует настройки бызы данных из метода
 (бина) dataSource()

Spring JdbcTemplate - обертка над JDBC API,
которая добавляет ряд удобств, по сравнению с JDBC API

Для работы с jdbcTemplate
в качестве 2 аргумента мы должны указать
реализацию интерфейса RowMapper

Это такой объект, который отображает строки из таблицы.
Каждую строку, полученую в результате этого запроса
из нашей таблицы Person он отобразит в объект класса
Person. Row mapper мы реализуем самостоятельно
в классе PersonMapper
(см рядом файл PersonMapper.txt для примера)

Если названия колонок совпадают с названиями полей,
то вместо RowMapper мы можем использовать
встроенный маппер BeanPropertyRowMapper
 */
/** index() - показать (список) всех людей
В том случае, если у нас поля соответствуют колонкам,
мы делаем как в методе, используюя BeanPropertyRowMapper
Если не соотвествуют, то мы должны писать так:
return jdbcTemplate.query("SELECT * Person", new RowMapper<Person>())
и самостоятельно реализовывать
RowMapper в отдельном классе PersonMapper,
а потом заменить RowMapper на нашу реализацию
Далее можно сделать соотвествующий метод контроллера,
который будет использовать данный метод
 */
