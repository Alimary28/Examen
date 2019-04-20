package Domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rent extends Entity {

    private String carId;
    private int days;
    private int kilometers;

    public Rent(String id, String carId, int days, int kilometers) {
        super(id);
        this.carId = carId;
        this.days = days;
        this.kilometers = kilometers;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id='" + id + '\'' +
                ", carId='" + carId + '\'' +
                ", days=" + days +
                ", kilometers=" + kilometers +
                '}';
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }
}
