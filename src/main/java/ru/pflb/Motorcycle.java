package ru.pflb;

public class Motorcycle extends Vehicle {
    public String color;

    public static Motorcycle parseMotorcycle(String str) throws IllegalArgumentException{
        Motorcycle motorcycle;
        String[] props = str.split(",");
        if (props.length != 3) throw new IllegalArgumentException();
        motorcycle = new Motorcycle();
        motorcycle.name = props[0];
        motorcycle.maxSpeed = Integer.parseInt(props[1]);
        motorcycle.color = props[2];
        return motorcycle;
    }

    @Override
    public String toString() {
        return "Мотоцикл{" +
                "Название='" + name + '\'' +
                ", Цвет='" + color + '\'' +
                ", Максимальная скорость=" + maxSpeed +
                '}';
    }
}
