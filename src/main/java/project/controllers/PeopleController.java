package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dao.PersonDAO;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "people/show";
    }
}
/** @RequestMapping("/people")
Данный контроллер будет обрабатывать все запросы
которые начинаются с /people. Не забываем
косую черту, это важно
 */
/** personDAO
Для запроса методов DAO внедряем сюда
(и в Spring) объект personDAO через
конструктор и добавляем
@Autowired  для наглядности
*/
/** index() - обработка запроса на показ всех людей
 @GetMapping() - обработка Get запроса, здесь
 в скобках ничего нет, значит родительского "/people"

 Что происходит: мы получаем список всех людей из
 базы данных (personDAO.index()) и кладем его
 в переменную "people",  которую, в свою очередь
 отправим в представление  people/index. Для этого
 мы привлекаем пустую переменную типа Model

 Далее можно создать представление в папке
 views, сделать people, а в нем index.html
 и потом протестировать все слои сделав
 запрос http://localhost:8080/people
 */
/** show() - показать человека и все его книги

@GetMapping("/{id}")
Мы получаем запрос с id со страницы показа всех людей (index)
типа "people/3", и показываем его на странице  "people/show"

@PathVariable("id") int id
С помощью этой аннотации мы извлекаем из гет запроса
выше {id}, присваиваем его переменной int id и опираемся
на него при вызове определенного человека по его id
и других действиях

Model model
Используем переменную типа Model
для передачи нужных переменных - "person" и "books"
в представление people/show

Далее создаем соотвествующее представление
 */