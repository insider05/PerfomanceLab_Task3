package ru.pflb;

public class Car extends Vehicle {
    public Float brakingDistanceMax;//В километрах

    public static Car parseCar(String str) throws IllegalArgumentException{
        Car car;
        String[] props = str.split(",");
        if (props.length != 3) throw new IllegalArgumentException();
        car = new Car();
        car.name = props[0];
        car.maxSpeed = Integer.parseInt(props[1]);
        car.brakingDistanceMax = Float.parseFloat(props[2]);
        return car;
    }
}
