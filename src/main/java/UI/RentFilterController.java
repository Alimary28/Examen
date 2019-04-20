package UI;

import Domain.Rent;
import Service.RentService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.List;

public class RentFilterController {

    public TableView tblRents;
    public TableColumn colRentId;
    public TableColumn colRentCarId;
    public TableColumn colTransactionDate;
    public TableColumn colTransactionTime;
    public TableColumn colKilometers;
    public TextField txtStartHour;
    public TextField txtStartMinutes;
    public TextField txtEndHour;
    public TextField txtEndMinutes;
    public Button btnShowResults;
    public Button btnCancel;

    public RentService rentService;

    private ObservableList<Rent> rents = FXCollections.observableArrayList();

    public void setService(RentService rentService) {
        this.rentService = rentService;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            rents.addAll(rentService.getAllRents());
            tblRents.setItems(rents);
        });
    }

    public void btnShowResultsClick(ActionEvent actionEvent) {
        try {
            int startHour = Integer.parseInt(txtStartHour.getText());
            int startMinutes = Integer.parseInt(txtStartMinutes.getText());
            LocalTime startTime = LocalTime.of(startHour, startMinutes);

            int endHour = Integer.parseInt(txtEndHour.getText());
            int endMinutes = Integer.parseInt(txtEndMinutes.getText());
            LocalTime endTime = LocalTime.of(endHour, endMinutes);

            List<Rent> rentResults = rentService.transactionsWithinTimeInterval(startTime, endTime);

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
