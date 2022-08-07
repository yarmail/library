package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.models.Book;
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
        List<Person> people = jdbcTemplate.query("select * from Person",
                new BeanPropertyRowMapper<>(Person.class));
        System.out.println(people);
        return people;
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from Person where person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                        .stream().findAny().orElse(null);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("select * from Book where person_id=?",
                new  Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into Person(full_name, year_of_birth) values (?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("update Person set full_name=?, year_of_birth=? where person_id=?",
                updatePerson.getFullName(), updatePerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Person where person_id=?", id);
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

System.out.println(people);
добавленно временно, для примера,
чтобы посмотреть в консоли,  что именно забирается из БД
и передается в контроллер, можно упростить, если не нужно

 */
/** show(), getBooksByPersonId()
 show() -  показать одного, если такой есть
 (поиск по id) или вернуть null.  Также можно сделать
 Optional<Person> show(int id)
 но в конце будет только .findAny() (без orElse)
 ---
 Так как на странице представления мы должны
 показать не только пользователя, но и все его
 книги добавляем ещё метод
 getBooksByPersonId()
 В этом методе мы берем id (который мы будем получать
 из вызова и по нему находим все его книги в таблице
 Book через внешний ключ person_id
 */
/** save()
 Этот метод мы используем для сохранения информации о новом Person
 полученной с формы на странице

 */
/** update() - обновить данные Person
новыми данными
 */
/** delete() - удалить Person из базы
*/