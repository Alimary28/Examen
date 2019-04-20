package Domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rent extends Entity {

    private String carId;
    private LocalDate date;
    private LocalTime time;
    private int kilometers;

    public Rent(String id, String carId, LocalDate date, LocalTime time, int kilometers) {
        super(id);
        this.carId = carId;
        this.date = date;
        this.time = time;
        this.kilometers = kilometers;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id='" + id + '\'' +
                ", carId='" + carId + '\'' +
                ", date=" + date +
                ", time=" + time +
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }
}
