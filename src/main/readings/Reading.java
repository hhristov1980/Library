package main.readings;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class Reading {
    private ReadingType type;
    private String name;
    private String publisher;
    private String category;
    private TreeMap<LocalDateTime, LocalDateTime> chronology;
    private LocalDateTime lastTakeDateTime;
    private LocalDateTime dateTimeToReturn;
    private boolean isReturned;

    public Reading(ReadingType type, String name, String publisher, String category){
        this.type = validateType();
        if(name.length()>0){
            this.name = name;
        }
        if(publisher.length()>0){
            this.publisher = publisher;
        }
        if(category.length()>0){
            this.category = category;
        }
        chronology = new TreeMap<>();
        isReturned = true;

    }
    public void enterTakeDateTime(LocalDateTime takeDateTime){
        chronology.put(takeDateTime, null);
        this.lastTakeDateTime = takeDateTime;
        this.dateTimeToReturn = lastTakeDateTime.plus(rentTimeLimit());
    }
    public void enterReturnDateTime(LocalDateTime returnDateTime){
        LocalDateTime takeDateTime = chronology.lastKey();
        chronology.put(takeDateTime,returnDateTime);
        this.lastTakeDateTime = null;
        this.dateTimeToReturn = null;
    }

    public abstract ReadingType validateType();
    public abstract Duration rentTimeLimit();
    public abstract double rentPrice();

    public ReadingType getType() {
        return type;
    }

    public enum ReadingType{
        BOOK, MAGAZINE, TEXTBOOK
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void markReturnedTaken(boolean isReturned){
        this.isReturned = isReturned;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public LocalDateTime getDateTimeToReturn() {
        return dateTimeToReturn;
    }
}
