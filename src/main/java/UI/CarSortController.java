package UI;

import Service.CarService;
import Service.RentVM;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarSortController {

    /*public TableView tblCars;
    public TableColumn colCarId;
    public TableColumn colCarModel;
    public TableColumn colCarKilometers;
    public TableColumn colCarPrice;
    public TableColumn colCarRents;

    private CarService carService;

    private ObservableList<RentVM> cars = FXCollections.observableArrayList();

    public void setService(CarService carService) {
        this.carService = carService;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            try {
                List<RentVM> sorted = carService.getOrderedByTransactions();

                cars.addAll(sorted);
                tblCars.setItems(cars);
            } catch (RuntimeException error) {
                Common.showValidationError(error.getMessage());
            }
        });
    }*/

    public TableView tblCars;
    public TableColumn colCarId;
    public TableColumn colCarModel;
    public TableColumn colCarRents;

    private CarService carService;

    private ObservableList<RentVM> rents = FXCollections.observableArrayList();

    public void setService(CarService carService){
        this.carService = carService;
    }

    private void initialize(){
        Platform.runLater(() -> {
            try {
                List<RentVM> orderedCars = carService.getOrderedByTransactions();
                rents.addAll(orderedCars);
                tblCars.setItems(rents);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window: Cars by rents.", e);
            }
        });
    }
}
