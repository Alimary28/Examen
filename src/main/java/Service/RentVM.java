package Service;

import Domain.Car;

public class RentVM {

    public Car car;
    public int rents;

    public String id;
    public String model;
    public int kilometers;
    public double price;

    public RentVM(Car car, int rents) {
        this.car = car;
        this.rents = rents;
    }

    public int getRents() {
        return rents;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public int getKilometers() {
        return kilometers;
    }

    public double getPrice() {
        return price;
    }

}
