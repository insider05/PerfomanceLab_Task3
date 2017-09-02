package ru.pflb;

public class Truck extends Vehicle{
    public Integer cargoCapacity;//В тоннах.

    public static Truck parseTruck(String str) throws IllegalArgumentException{
        Truck truck;
        String[] props = str.split(",");
        if (props.length != 3) throw new IllegalArgumentException();
        truck = new Truck();
        truck.name = props[0];
        truck.maxSpeed = Integer.parseInt(props[1]);
        truck.cargoCapacity = Integer.parseInt(props[2]);
        return truck;
    }

    @Override
    public String toString() {
        return "Грузовик{" +
                "Название='" + name + '\'' +
                ", Грузоподъемность=" + cargoCapacity +
                ", Максимальная скорость=" + maxSpeed +
                '}';
    }
}
