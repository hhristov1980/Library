package main;

import main.library.Library;
import main.person.Person;
import main.readings.Book;
import main.readings.Magazine;
import main.readings.Reading;
import main.readings.Textbook;

import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {
        Library library = new Library("IT Library",30);
        Reading book = new Book(Reading.ReadingType.BOOK,"War and Peace", "IT Talents", "Lev Tolstoi", "Clasic", LocalDate.of(2000,12,1));
        Reading magazine = new Magazine(Reading.ReadingType.MAGAZINE,"AutoMoto", "IT Talents", "Automobiles", 1, LocalDate.of(2020,12,1));
        Reading textbook = new Textbook(Reading.ReadingType.TEXTBOOK,"History of Bulgaria", "IT Talents", "History", "Stefan Tsanev");
        Person p = new Person("Test",40,library);
        library.addReadings(book);
        library.addReadings(magazine);
        library.addReadings(textbook);
        p.start();
    }
}
