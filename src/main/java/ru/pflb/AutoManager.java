package ru.pflb;

import java.util.HashMap;
import java.util.Map;

public class AutoManager {
    //Синглтон
    static public AutoManager giveInstance(){
        //implementation
        return new AutoManager();//Вставил, чтобы IDE не ругался.
    }

    public void addVehicle(Vehicle vehicle){
        //implementation
    }

    //Возвращает TRUE, если продажа прошла успешно. Иначе возвращает FALSE.
    public boolean receiveSaleRequest(SaleRequest saleRequest){
        //implementation
        return true;//Вставил, чтобы IDE не ругался.
    }

    public Map<Integer, Vehicle> getVehicles(){
        //implementation
        return new HashMap<Integer, Vehicle>();//Вставил, чтобы IDE не ругался.
    }

    public Map<String, Float> getSellers(){
        //implementation
        return new HashMap<String, Float>();//Вставил, чтобы IDE не ругался.
    }
}
