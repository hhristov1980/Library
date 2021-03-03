package main.readings;

import java.time.Duration;
import java.time.LocalDate;

public class Book extends Reading{
    private String author;
    private LocalDate dateOfPublishing;

    public Book(ReadingType type, String name, String publisher, String author, String category, LocalDate dateOfPublishing) {
        super(type, name, publisher, category);

        if(author.length()>0){
            this.author = author;
        }
        this.dateOfPublishing = dateOfPublishing;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }

    @Override
    public ReadingType validateType() {
        return ReadingType.BOOK;
    }

    @Override
    public Duration rentTimeLimit() {
        return Duration.ofSeconds(3);
    }

    @Override
    public double rentPrice() {
        return 2;
    }
}
