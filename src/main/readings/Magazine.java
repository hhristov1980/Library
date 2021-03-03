package main.readings;

import java.time.Duration;
import java.time.LocalDate;

public class Magazine extends Reading{
    private int no;
    private LocalDate dateOfPublishing;

    public Magazine(ReadingType type, String name, String publisher, String category, int no, LocalDate dateOfPublishing) {
        super(type, name, publisher,category);

        if(no>0){
            this.no = no;
        }
        this.dateOfPublishing = dateOfPublishing;
    }

    public LocalDate getDateOfPublishing() {
        return dateOfPublishing;
    }

    public int getNo() {
        return no;
    }

    @Override
    public ReadingType validateType() {
        return ReadingType.MAGAZINE;
    }

    @Override
    public Duration rentTimeLimit() {
        return null;
    }

    @Override
    public double rentPrice() {
        return 0;
    }
}
