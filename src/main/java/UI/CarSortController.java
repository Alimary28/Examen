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

public class CarSortController {

    public TableView tblCars;
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
    }
}
