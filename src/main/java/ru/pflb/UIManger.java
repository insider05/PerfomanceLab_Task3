package ru.pflb;

import java.rmi.NotBoundException;
import java.util.Map;

public class UIManger {
    private static UIManger uiManger;
    private boolean alive = true;
    private int state = 0;
    private int vehicleExpected = 0;
    private AutoManager autoManager;

    /**
     * state:
     * 0 - начальное состояние.
     * 1 - выбор типа добавляемого транспортного средства
     * 2 - ввод данных нового транспортного средства
     * 3 - ввод данных о продаже транспортного средства
     */

    /**
     * vehicleExpected:
     * 0 - ввод транспорта не ожидается
     * 1 - ожидает ввод мотоцикла
     * 2 - ожидает ввод легковой машины
     * 3 - ожилает ввод грузовика
     */

    //Возвращает FALSE, если был принят запрос на закрытие программы, иначе возварщает TRUE
    public boolean isAlive() {
        return alive;
    }

    //Возвращает TRUE, если autoManager != null, иначе False
    public boolean bind(AutoManager autoManager) {
        if (autoManager == null) return false;
        this.autoManager = autoManager;
        return true;
    }

    //Возвращает объект UIManager
    public static UIManger giveInstance() {
        if (uiManger == null) {
            uiManger = new UIManger();
        }
        return uiManger;
    }

    //Запрещает создавать новые объекты UIManager
    private UIManger() {

    }

    //Обрабатывает введенные данные
    public void processInput(String input) throws NotBoundException {
        int inputNum;
        if (autoManager == null) {
            throw new NotBoundException("AutoManager is not bound to UIManager");
        }
        switch (state) {
            //Выбор запроса
            case 0: {
                if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5")) {
                    System.out.println("Ошибка: запрос выбран неправильно. Попробуйте еще раз.");
                    return;
                } else {
                    inputNum = Integer.parseInt(input);
                    switch (inputNum) {
                        case 1: {
                            state = 1;
                            System.out.println("Выберите тип вводимого транспорта:\n1: Мотоцикл\n2: Легковая машина\n3: Грузовик" +
                                    "\nотмена: Отменить добавление транспортного средства");
                            return;
                        }
                        case 2: {
                            printVehicleList();
                            return;
                        }
                        case 3: {
                            state = 3;
                            System.out.println("Введите \"<имя продавца>,<id продоваемой машины>,<цена>\". " +
                                    "Или напишите \"отмена\", чтобы отменить продажу.");
                            return;
                        }
                        case 4: {
                            printSellerList();
                            return;
                        }
                        case 5:{
                            alive = false;
                            return;
                        }
                    }
                }
            }
            //Выбор типа транспорта для добавления
            case 1: {
                if (wantToCancel(input)) {
                    cancel();
                    return;
                }
                if (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
                    System.out.println("Неправильно выбран тип транспорта. Попробуйте еще раз.");
                    return;
                }
                inputNum = Integer.parseInt(input);
                state = 2;
                switch (inputNum) {
                    case 1: {
                        vehicleExpected = 1;
                        System.out.println("Введите \"<название>,<максимальная скорость>,<цвет>\". " +
                                "Или напишите \"отмена\", чтобы отменить добавление.");
                        return;
                    }
                    case 2: {
                        vehicleExpected = 2;
                        System.out.println("Введите \"<название>,<максимальная скорость>,<максимальный тормозной путь>\". " +
                                "Или напишите \"отмена\", чтобы отменить добавление.");
                        return;
                    }
                    case 3: {
                        vehicleExpected = 3;
                        System.out.println("Введите \"<название>,<максимальная скорость>,<Максимальная грузоподъемность>\". " +
                                "Или напишите \"отмена\", чтобы отменить добавление.");
                        return;
                    }
                }
            }
            //Добавление транспорта определенного типа
            case 2: {
                if (wantToCancel(input)) {
                    cancel();
                    return;
                }
                Vehicle vehicle = null;
                try {
                    switch (vehicleExpected) {
                        case 1: {
                            vehicle = Motorcycle.parseMotorcycle(input);
                            break;
                        }
                        case 2: {
                            vehicle = Car.parseCar(input);
                            break;
                        }
                        case 3: {
                            vehicle = Truck.parseTruck(input);
                            break;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Данные транспортного средства были введены с ошибкой. Попробуйте еще раз.");
                    return;
                }
                if (vehicle != null){
                    autoManager.addVehicle(vehicle);
                    state = 0;
                    vehicleExpected = 0;
                    System.out.println("Транспортное средство было успешно добавлено");
                    greetUser();
                }
                return;
            }
            //Продажа транспорта
            case 3:{
                if (wantToCancel(input)) {
                    cancel();
                    return;
                }
                SaleRequest saleRequest = null;
                try {
                    saleRequest = SaleRequest.parseSaleRequest(input);
                }catch (IllegalArgumentException e){
                    System.out.println("Данные для совершения продажи были введены с ошибкой. Попробуйте еще раз.");
                    return;
                }
                if (saleRequest != null){
                    if (autoManager.receiveSaleRequest(saleRequest)){
                        System.out.println("Продажа совершилась успешно.");
                        cancel();
                        return;
                    }else {
                        System.out.println("Не удалось продать транспортное средство. Попробуйте еще раз");
                    }

                }
                return;
            }
        }
    }

    private void printVehicleList() {
        if (autoManager.getVehicles().isEmpty()){
            System.out.println("Нет транспорта");
            return;
        }
        System.out.println("Имеющийся транспорт:");
        for (Map.Entry<Integer, Vehicle> entry : autoManager.getVehicles().entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private void printSellerList() {
        if (autoManager.getSellers().isEmpty()){
            System.out.println("Продаж не совершалось");
            return;
        }
        System.out.println("Статистика по продажам:");
        for (Map.Entry<String, Float> entry : autoManager.getSellers().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public void greetUser() {
        System.out.println("Выберите запрос:\n1: Добавить транспортное средство\n2: Отобразить имеющийся транспорт" +
                "\n3: Продать транспортное средство\n4: Отобразить статистику по продажам\n5: Выйти из программы");
    }

    public boolean wantToCancel(String input){
        if (input.equals("отмена")){
            return true;
        } else {
          return false;
        }
    }

    public void cancel(){
        state = 0;
        greetUser();
    }


}
