package UI;

import Service.RentService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class RentAddController {


    public Spinner spnId;
    public TextField txtCarId;
    public TextField txtDayOfTransaction;
    public TextField txtMonthOfTransaction;
    public TextField txtYearOfTransaction;
    public TextField txtHour;
    public TextField txtMinutes;
    public TextField txtKilometers;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnCancel;

    private RentService rentService;

    public void setService(RentService rentService) {
        this.rentService = rentService;
    }

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            String id = String.valueOf(spnId.getValue());
            String carId = txtCarId.getText();

            int dayOfTransaction = Integer.parseInt(txtDayOfTransaction.getText());
            int monthOfTransaction = Integer.parseInt(txtMonthOfTransaction.getText());
            int yearOfTransaction = Integer.parseInt(txtYearOfTransaction.getText());
            LocalDate dateOfTransaction = LocalDate.of(yearOfTransaction, monthOfTransaction, dayOfTransaction);

            int hour = Integer.parseInt(txtHour.getText());
            int minutes = Integer.parseInt(txtMinutes.getText());
            int kilometers = Integer.parseInt(txtKilometers.getText());
            LocalTime timeOfTransaction = LocalTime.of(hour, minutes);
            rentService.addRent(id, carId, dateOfTransaction, timeOfTransaction, kilometers);
            btnCancelClick(actionEvent);
        } catch (RuntimeException error) {
            Common.showValidationError(error.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            String id = String.valueOf(spnId.getValue());
            String carId = txtCarId.getText();

            int dayOfTransaction = Integer.parseInt(txtDayOfTransaction.getText());
            int monthOfTransaction = Integer.parseInt(txtMonthOfTransaction.getText());
            int yearOfTransaction = Integer.parseInt(txtYearOfTransaction.getText());
            LocalDate dateOfTransaction = LocalDate.of(yearOfTransaction, monthOfTransaction, dayOfTransaction);

            int hour = Integer.parseInt(txtHour.getText());
            int minutes = Integer.parseInt(txtMinutes.getText());
            int kilometers = Integer.parseInt(txtKilometers.getText());
            LocalTime timeOfTransaction = LocalTime.of(hour, minutes);

            rentService.updateRent(id, carId, dateOfTransaction, timeOfTransaction, kilometers);
            btnCancelClick(actionEvent);
        } catch (RuntimeException error) {
            Common.showValidationError(error.getMessage());
        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
