package UI;

import Domain.Car;
import Domain.Rent;
import Service.CarService;
import Service.RentService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

public class Controller {


    public TableView tblCars;
    public TableColumn colCarId;
    public TableColumn colCarModel;
    public TableColumn colCarKilometers;
    public TableColumn colCarPrice;
    public TableView tblRents;
    public TableColumn colRentId;
    public TableColumn colRentCarId;
    public TableColumn colTransactionDate;
    public TableColumn colTransactionTime;
    public TableColumn colKilometers;

    private CarService carService;
    private RentService rentService;

    private ObservableList<Car> cars = FXCollections.observableArrayList();
    private ObservableList<Rent> rents = FXCollections.observableArrayList();

    public void setServices(CarService carService, RentService rentService) {
        this.carService = carService;
        this.rentService = rentService;
    }


    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            cars.addAll(carService.getAllCars());
            tblCars.setItems(cars);
            rents.addAll(rentService.getAllRents());
            tblRents.setItems(rents);
        });
    }

    public void upsertCarSettings(FXMLLoader fxmlLoader, String title) {
        try {
            Scene scene = new Scene(fxmlLoader.load(),235,200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            CarAddController controller = fxmlLoader.getController();
            controller.setService(carService);
            stage.showAndWait();

            cars.clear();
            cars.addAll(carService.getAllCars());
        } catch (IOException error) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window: Car Add/Update", error);
        }
    }

    public void btnAddCarClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/carAdd.fxml"));
        upsertCarSettings(fxmlLoader, "Car Add");
    }

    public void btnUpdateCarClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/carUpdate.fxml"));
        upsertCarSettings(fxmlLoader, "Car Update");
    }

    public void btnCarRemoveClick(ActionEvent actionEvent) {
        Car selected = (Car) tblCars.getSelectionModel().getSelectedItem();
        if (selected != null)
            try {
                carService.removeCar(selected.getId());
                cars.clear();
                cars.addAll(carService.getAllCars());
            } catch (RuntimeException error) {
                Common.showValidationError(error.getMessage());
            }
    }


    public void upsertRentSettings(FXMLLoader fxmlLoader, String title) {
        try {
            Scene scene = new Scene(fxmlLoader.load(),270,250);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            RentAddController controller = fxmlLoader.getController();
            controller.setService(rentService);
            stage.showAndWait();

            rents.clear();
            rents.addAll(rentService.getAllRents());
        } catch (IOException error) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window: Rent Add/Update", error);
        }
    }

    public void btnAddRentClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/rentAdd.fxml"));
        upsertRentSettings(fxmlLoader,"Rent Add");
    }

    public void btnUpdateRentClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/rentUpdate.fxml"));
        upsertRentSettings(fxmlLoader,"Rent Update");
    }

    public void btnRemoveRentClick(ActionEvent actionEvent) {
        Rent selected = (Rent) tblRents.getSelectionModel().getSelectedItem();
        if (selected != null)
            try {
                rentService.removeRent(selected.getId());
                rents.clear();
                rents.addAll(rentService.getAllRents());
            } catch (RuntimeException error) {
                Common.showValidationError(error.getMessage());
            }
    }

    public void btnTextSearchClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/search.fxml"));

            Scene scene = new Scene(fxmlLoader.load(),1400,900);
            Stage stage = new Stage();
            stage.setTitle("Full Text Search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            SearchController controller = fxmlLoader.getController();
            controller.setService(carService, rentService);
            stage.showAndWait();

        } catch (IOException error) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window: Full Text Search", error);
        }
    }

    public void btnRentFilterClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/rentFilter.fxml"));

            Scene scene = new Scene(fxmlLoader.load(),600,600);
            Stage stage = new Stage();
            stage.setTitle("Rent Filtering");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            RentFilterController controller = fxmlLoader.getController();
            controller.setService(rentService);
            stage.showAndWait();

        } catch (IOException error) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window: Rent Filtering", error);
        }
    }

    public void btnCarSortClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/carSort.fxml"));

            Scene scene = new Scene(fxmlLoader.load(),600,600);
            Stage stage = new Stage();
            stage.setTitle("Sort by rents");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            CarSortController controller = fxmlLoader.getController();
            controller.setService(carService);
            stage.showAndWait();

        } catch (IOException error) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window: Car Sort by rents", error);
        }
    }

    /*public void btnTransactionRemoveFilterClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/transactionRemoveFilter.fxml"));

            Scene scene = new Scene(fxmlLoader.load(),600,600);
            Stage stage = new Stage();
            stage.setTitle("Rent Remove Filter");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            TransactionRemoveFilterController controller = fxmlLoader.getController();
            controller.setService(rentService);
            stage.showAndWait();

        } catch (IOException error) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window: Rent Remove Filter", error);
        }
    }*/

   /* public void btnClientBonusPoints(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/clientBonusPoints.fxml"));

            Scene scene = new Scene(fxmlLoader.load(),700,700);
            Stage stage = new Stage();
            stage.setModel("Client Bonus Points");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ClientBonusPointsController controller = fxmlLoader.getController();
            controller.setService(clientService);
            stage.showAndWait();

        } catch (IOException error) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window: Client Bonus Points", error);
        }
    }*/

    public void btnCarUndoClick(ActionEvent actionEvent) {
        carService.undo();
        cars.clear();
        cars.addAll(carService.getAllCars());
    }

    public void btnCarRedoClick(ActionEvent actionEvent) {
        carService.redo();
        cars.clear();
        cars.addAll(carService.getAllCars());
    }

    public void btnRentUndoClick(ActionEvent actionEvent) {
        rentService.undo();
        rents.clear();
        rents.addAll(rentService.getAllRents());
    }

    public void btnRentRedoClick(ActionEvent actionEvent) {
        rentService.redo();
        rents.clear();
        rents.addAll(rentService.getAllRents());
    }
}
