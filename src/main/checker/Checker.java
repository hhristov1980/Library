package main.checker;

import main.library.Library;
import main.readings.Reading;

import java.time.LocalDateTime;

public class Checker extends Thread{
    private Library library;
    private Reading reading;

    public Checker(Library library, Reading reading){
        this.library = library;
        this.reading = reading;
    }

    @Override
    public void run() {
        while (!reading.isReturned()){
            if(reading.getDateTimeToReturn().isBefore(LocalDateTime.now())){
                double interest = reading.rentPrice()*0.01;
                double moneyToPay = Math.round(library.getMoneyToReceive().get(reading.getName())+interest);
                library.updateMoneyToReceive(reading,moneyToPay);
                System.out.println("You must return the "+reading.getName()+". You owe "+moneyToPay);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
