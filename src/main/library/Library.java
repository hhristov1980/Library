package main.library;

import main.checker.Checker;
import main.exception.MagazineException;
import main.person.Person;
import main.readings.Book;
import main.readings.Magazine;
import main.readings.Reading;
import main.readings.Textbook;
import main.util.Randomizer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Library {
    private String name;
    private double money;
    private ConcurrentSkipListMap<Reading.ReadingType, ConcurrentSkipListMap<String, CopyOnWriteArrayList<Reading>>> catalogue;
    private ConcurrentSkipListMap<String, Double> moneyToReceive;

    public Library(String name, double money){
        if(name.length()>0){
            this.name = name;
        }
        if(money>0){
            this.money = money;
        }
        catalogue = new ConcurrentSkipListMap<>();
        moneyToReceive = new ConcurrentSkipListMap<>();
    }

    public synchronized void addReadings(Reading reading){
        if(!catalogue.containsKey(reading.getType())){
            catalogue.put(reading.getType(),new ConcurrentSkipListMap<>());
        }
        if(!catalogue.get(reading.getType()).containsKey(reading.getCategory())){
            catalogue.get(reading.getType()).put(reading.getCategory(), new CopyOnWriteArrayList<>());
        }
        catalogue.get(reading.getType()).get(reading.getCategory()).add(reading);
    }

    public synchronized Reading takeReading(Person person){

        ArrayList<Reading> listToChoose = new ArrayList<>();
        for (Map.Entry<Reading.ReadingType, ConcurrentSkipListMap<String, CopyOnWriteArrayList<Reading>>> type: catalogue.entrySet()){
            for(Map.Entry<String, CopyOnWriteArrayList<Reading>> category: type.getValue().entrySet()){
                    switch (type.getKey()){
                        case TEXTBOOK:
                            ArrayList<Textbook> tempList = new ArrayList<>();
                            for(Reading r: category.getValue()){
                                Textbook t = (Textbook) r;
                                tempList.add(t);
                            }
                            Collections.sort(tempList,((o1, o2) -> o1.getName().compareTo(o2.getName())));
                            listToChoose.addAll(tempList);
                            break;
                        case MAGAZINE:
                            ArrayList<Magazine> tempList1 = new ArrayList<>();
                            for(Reading r: category.getValue()){
                                Magazine m = (Magazine) r;
                                tempList1.add(m);
                            }
                            Collections.sort(tempList1,((o1, o2) -> Integer.compare(o1.getNo(),(o2.getNo()))));
                            listToChoose.addAll(tempList1);
                            break;
                        case BOOK:
                            ArrayList<Book> tempList2 = new ArrayList<>();
                            for(Reading r: category.getValue()){
                                Book b = (Book) r;
                                tempList2.add(b);
                            }
                            Collections.sort(tempList2,((o1, o2) -> o1.getDateOfPublishing().compareTo(o2.getDateOfPublishing())));
                            listToChoose.addAll(tempList2);
                            break;
                    }
            }
        }
        int index = Randomizer.getRandomInt(0,listToChoose.size()-1);
        Reading reading = listToChoose.get(index);
        if(reading.getType().equals(Reading.ReadingType.MAGAZINE)){
            try {
                throw new MagazineException();
            } catch (MagazineException e) {
                System.out.println(e.getMessage());

            }
            return null;
        }
        else{
            LocalDateTime takeDateTime = LocalDateTime.now();
            reading.enterTakeDateTime(takeDateTime);
            reading.markReturnedTaken(false);
            catalogue.get(reading.getType()).get(reading.getCategory()).remove(reading);
            listToChoose.remove(reading);
            moneyToReceive.put(reading.getName(), reading.rentPrice());
            Checker checker = new Checker(this,reading);
            checker.start();

            return reading;
        }

    }

    public synchronized void returnReading(Person person){
        Reading r = person.getReading();
        catalogue.get(r.getType()).get(r.getCategory()).add(r);
        LocalDateTime returnDateTime  = LocalDateTime.now();
        r.enterReturnDateTime(returnDateTime);
        r.markReturnedTaken(true);
        double moneyToPay = moneyToReceive.get(r.getName());
        person.payForBook(moneyToPay);
        this.money+=moneyToPay;
        moneyToReceive.remove(person.getPersonName());

    }

    public void updateMoneyToReceive(Reading reading, double amount){
        moneyToReceive.put(reading.getName(), amount);
    }

    public ConcurrentSkipListMap<String, Double> getMoneyToReceive() {
        return moneyToReceive;
    }
}
