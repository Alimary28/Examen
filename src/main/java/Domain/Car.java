package Domain;

public class Car extends Entity {

    private String model;
    private int kilometers;
    private double price;

    public Car(String id, String model, int kilometers, double price) {
        super(id);
        this.model = model;
        this.kilometers = kilometers;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", kilometers=" + kilometers +
                ", price=" + price +
                '}';
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
