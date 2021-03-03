package main.readings;

import java.time.Duration;

public class Textbook extends Reading{

    private String author;

    public Textbook(ReadingType type, String name, String publisher, String category, String author) {
        super(type, name, publisher,category);

        if(author.length()>0){
            this.author = author;
        }
    }

    @Override
    public ReadingType validateType() {
        return ReadingType.TEXTBOOK;
    }

    @Override
    public Duration rentTimeLimit() {
        return Duration.ofSeconds(1);
    }

    @Override
    public double rentPrice() {
        return 3;
    }
}
