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
    public TableColumn colRentDays;
    public TextField txtDayOfRent;
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
            int days = Integer.parseInt(txtDayOfRent.getText());

            List<Rent> rentResults = rentService.transactionsWithinTimeInterval(days);

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
