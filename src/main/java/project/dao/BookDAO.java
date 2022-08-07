package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.models.Book;
import project.models.Person;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("select * from Book where book_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query
                ("select Person.* from Book join Person " +
                     "on Book.person_id = Person.person_id where Book.book_id = ?",
                      new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("update Book set person_id=null where book_id=?", id);
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update(
        "update book set person_id=? where book_id =?", selectedPerson.getPersonId(), id);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into Book(title, author, year) values(?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }


}
/** index() - показать все книги
(подробнее в PersonDAO)
 */
/** show() - показать выбранную книгу
Мы получаем на вход id книги и если такой id
есть в базе - выдаем её, если нет, возвращаем null
Используется для страницы views/books/show.html
 */
/** getBookOwner() - получить владельца книги
Пояснения запроса к БД
Join'им таблицы Book и Person и получаем человека,
которому принадлежит книга с указанным id
Выбираем все колонки таблицы Person из объединенной таблицы
Используется на странице views/books/show.html
 */
/** release() - освободить книгу
Этот метод используется, когда человек возвращает книгу в библиотеку
Используется на странице views/books/show.html
 */
/** assign() - назначить книгу человеку
Этот метод вызывается, когда человек получает книгу и становится
её владельцем
Используется на странице views/books/show.html
 */
/** save() - сохранить новую Книгу
Используется при обработке формы для создания
новой книги на страице views/books/new.html
 */
