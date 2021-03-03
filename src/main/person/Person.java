package main.person;

import main.library.Library;
import main.readings.Reading;
import main.util.Randomizer;

public class Person extends Thread{
    private String personName;
    private double money;
    private Reading reading;
    private Library library;

    public Person(String personName, double money, Library library){
        if(personName.length()>0){
            this.personName = personName;
        }
        if(money>10){
            this.money = money;
        }
        else{
            this.money = 10;
        }
        this.library = library;
    }

    @Override
    public void run() {
        reading = library.takeReading(this);
        if(reading==null){
            return;
        }
        try {
            Thread.sleep(Randomizer.getRandomInt(0,3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        library.returnReading(this);

        reading = null;
        System.out.println(this.personName+" finished");
    }

    public String getPersonName() {
        return personName;
    }

    public Reading getReading() {
        return reading;
    }

    public void payForBook(double money){
        if(this.money>=money){
            this.money-=money;
            System.out.println(personName+" paid "+money+" for renting of "+reading.getName());
        }
        else {
            System.out.println("I'm sorry. I haven't enough money. Take your book back, I'm runnig away!");
        }
    }
}
