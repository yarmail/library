package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.dao.PersonDAO;
import project.models.Person;
import project.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
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
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable ("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute ("person") @Valid Person person,
        BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors()){
            return "people/edit";
        }

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
/** newPerson(), create()  - методы для создания нового Person
newPerson()
Данный метод контроллера обрабатывает GET запрос (ссылку)
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
/** edit(), update() - редактирование Person
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

/** Валидация на уровне представления
в методах create(), update()
мы ставим @Valid перед объектами
Когда значения из формы внедряются в наши объекты,
происходит их проверка на ограничения в модели. Если
есть ошибка, то она помещается в специальный
объект, BindingResult, который мы ставим после
нашего объекта (это важно). Далее мы делаем простую проверку -
если ошибки при заполнении есть, то мы возвращаемся к
той же форме, которую заполняли, повторно, и в представлении,
показываем, что есть ошибка - возвращаем объект обратно
и объект ошибки
*/
/** Валидация на уровне базы данных
Мы сделали проверку данных, которые не нуждаются
в запросе базы данных, теперь добавляем
проверку и обработку сообщения от БД
(при создании пользователя с полностью одинаковым ФИО)

Внедряем объект PersonValidator и будем его использовать
его в методе create():
personValidator.validate(person, bindingResult);
В качестве первого объекта мы передаем валидатору
объект  person, который пришёл с формы, а в объект
bindingResult будут складываться ошибки и от @Valid
и от PersonValidator

Тестируем ситуацию, создаем нового одинакового Person
http://localhost:8080/people/new
и смотрим теперь результат
validation_full_name.png
 */