package project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.dao.PersonDAO;
import project.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDAO.getPersonByFullName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        }
    }
}

/** Описание
Валидация на уровне базы данных.
По условиям ТЗ
(см. 05 DB script.txt)
у нас должно быть уникальное ФИО Person
full_name varchar(100) not null unique,

Когда мы создаем нового человека
и не обрабатываем правильно эту ситуацию,
(пытаемся создать человека с таким же ФИО)
мы получаем очень некрасивое сообщение от
БД типа:
"
HTTP Status 500 – Internal Server Error
Message Request processing failed;
nested exception is org.springframework.dao.DuplicateKeyException:
PreparedStatementCallback; SQL [insert into Person(full_name, year_of_birth) values (?, ?)];
ОШИБКА: повторяющееся значение ключа нарушает ограничение уникальности "person_name_key"
"
Чтобы пользователю получать понятное объяснение, а не это
мы используем этот валидатор

supports() - в этом методе мы привязываем
наш валидатор к классу Person
(показываем с каким классом ему работать)

validate() - собственно сам метод проверки,
и замена сообщения от базы данных на более
удобное
 */