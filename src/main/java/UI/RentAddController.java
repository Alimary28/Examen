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
    public TextField txtDayOfRent;
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

            int dayOfRent = Integer.parseInt(txtDayOfRent.getText());
            int kilometers = Integer.parseInt(txtKilometers.getText());
            rentService.addRent(id, carId, dayOfRent, kilometers);
            btnCancelClick(actionEvent);
        } catch (RuntimeException error) {
            Common.showValidationError(error.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            String id = String.valueOf(spnId.getValue());
            String carId = txtCarId.getText();

            int dayOfRent = Integer.parseInt(txtDayOfRent.getText());
            int kilometers = Integer.parseInt(txtKilometers.getText());

            rentService.updateRent(id, carId, dayOfRent, kilometers);
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
