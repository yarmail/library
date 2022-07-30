package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.dao.PersonDAO;
import project.models.Person;

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

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable ("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute ("person") Person person,
                            @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
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
/** newPerson() create()  - методы для создания нового Person
 newPerson()
 Данный контроллер обрабатывает GET запрос (ссылку)
 people/new и возвращает эту страницу с формой,
 добавляя туда пустой объект для заполнения

 create()
 Обрабатывет запрос из формы в представлении, отправленные
 по адресу /people Post запросом, получает заполненный
 объект и сохраняет его в БД
 После обработки формы перенаправляемся
 на страницу /people, чтобы увидеть вновь созданного
 Person в общем списке
 */
/** edit() update() - редактирование Person
Примерно также, как было с созданием нового Person
мы сначала по ссылке на редактирование заходитм
на соотвествующую форму (страницу),
а потом закрепляем это в БД

edit()
Обрабатывает ссылку типа
people/{id}/edit со страницы show (или напрямую)

@PathVariable ("id") int id
Из сылки получаем id Person

personDAO.show(id)
По этому id находим Person

заносим его в переменную "person"

people/edit
отправлем переменную на страницу
и открывая эту страницу мы уже видим данные
Person которые будем редактировать

---

update()
Если я правильно понимаю,
этот метот обрабатывает Patch запросы вида
/people/{id}, где id берется из объекта
"person", который мы отправляли для формы
(страницы) edit в методе edit().
Также оттуда приходит новый person
@ModelAttribute ("person") Person person
c обновленными данными Person'a
Мы также берем id из ссылки, которую
обрабатываем
@PathVariable("id") int id
и сохраняем измененного Person
через DAO в БД
personDAO.update(id, person);

После обработки формы мы показываем
общий список people, чтобы увидеть
изменения, которые мы внесли в Person
return "redirect:/people";
 */
/** delete() - удаление Person
 */