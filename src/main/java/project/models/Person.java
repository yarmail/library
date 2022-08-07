package project.models;

public class Person {
    private int personId;
    private String fullName;
    private int yearOfBirth;

    public Person() {
    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", fullName='" + fullName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}

/** Примечания
Создаем также пустой конструктор, т.к. нам понадобится
пустой объект для работы с представлением в Спринг

Также мы используем конструктор для всех полей кроме id
т.к. генерация id отдается в нашем решении БД.

Геттеры и сеттеры мы делаем для всех полей,
так как кое-где в представлениях требуется .getPersonId

@Component сюда не нужен
 */
