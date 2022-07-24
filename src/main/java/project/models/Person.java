package project.models;

public class Person {
    private int id;
    private String fullName;
    private int yearOfBirth;

    public Person() {
    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}

/*
Примечания
Создаем также пустой конструктор, т.к. нам понадобится
пустой объект для работы с представлением в Спринг

Также мы используем конструктор для 2-х полей,
т.к. генерация id отдается в нашем решении БД.

Геттеры и сеттеры мы делаем для всех полей

@Component сюда не нужен
 */
