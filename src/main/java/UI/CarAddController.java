package UI;

import Service.CarService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CarAddController {

    public Spinner spnId;
    public TextField txtModel;
    public TextField txtKilometers;
    public TextField txtPrice;
    public Button btnAdd;
    public Button btnUpdate;
    public Button btnCancel;

    private CarService carService;

    public void setService(CarService carService) {
        this.carService = carService;
    }


    public void btnAddClick(ActionEvent actionEvent) {
        try {
            String id = String.valueOf(spnId.getValue());
            String model = txtModel.getText();
            int kilometers = Integer.parseInt(txtKilometers.getText());
            double price = Double.parseDouble(txtPrice.getText());
            carService.addCar(id, model, kilometers, price);
            btnCancelClick(actionEvent);
        } catch (RuntimeException error) {
            Common.showValidationError(error.getMessage());
        }
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            String id = String.valueOf(spnId.getValue());
            String model = txtModel.getText();
            int kilometers = Integer.parseInt(txtKilometers.getText());
            double price = Double.parseDouble(txtPrice.getText());

            carService.updateCar(id, model, kilometers, price);
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
