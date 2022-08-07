package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.dao.BookDAO;
import project.dao.PersonDAO;
import project.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if(bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}

/** personDAO
Помимо DAO для книг мы внедряем DAO для Person
для использования в методе show()
 */
/** index() - показать все книги
 Аналогично index() в PeopleController
 берем все книги в БД и засовываем их в "books",
 которую используем  на странице: books/index.html
 */
/** show() - показать книгу и её владельца
Если у книги есть владелец, то мы его "owner" отправляем
на страницу представления (books/show)
Если его нету, то на страницу представления мы отправляем
список людей "people", чтобы с помощью выпадающего
списка мы могли назначить владельца книги
(кстати для этого нам нужно поле personDAO)

Для проведения экспериментта, в таблице Book
мы ставим null у какой-нибудь книги в колонке person_id
и её показываем (в моем случае у 1 книги)

Также в представлении show используются ещё 2 метода,
назначить книгу и освободить книгу, см ниже.
*/
/** release() - освободить книгу,
сделать её ничейной.
После этого вернуться на ту же страницу show.html
 */
/** assign() - присвоить(назначить) книгу
 новому владельцу
 После этого вернуться на ту же страницу show.html
 */

