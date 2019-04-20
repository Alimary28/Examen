package UI;

import Domain.Car;
import Domain.Rent;
import Service.CarService;
import Service.RentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class SearchController {


    public TableView tblCars;
    public TableColumn colCarId;
    public TableColumn colCarModel;
    public TableColumn colCarKilometers;
    public TableColumn colCarPrice;
    public TableView tblRents;
    public TableColumn colRentId;
    public TableColumn colRentCarId;
    public TableColumn colRentDays;
    public Button btnSearch;
    public Button btnCancel;
    public TextField txtSearchText;

    public CarService carService;
    public RentService rentService;

    private ObservableList<Car> cars = FXCollections.observableArrayList();
    private ObservableList<Rent> rents = FXCollections.observableArrayList();

    public void setService(CarService carService, RentService rentService) {
        this.carService = carService;
        this.rentService = rentService;
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        try {
            String text = txtSearchText.getText();
            List<Car> carResults = carService.fullTextSearch(text);
            List<Rent> rentResults = rentService.fullTextSearch(text);

            cars.clear();
            cars.addAll(carResults);
            tblCars.setItems(cars);

            rents.clear();
            rents.addAll(rentResults);
            tblRents.setItems(rents);
        } catch (RuntimeException error) {
            Common.showValidationError(error.getMessage());
        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
