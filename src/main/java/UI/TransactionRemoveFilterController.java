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

import java.time.LocalDate;

public class TransactionRemoveFilterController {

    public TableView tblTransactions;
    public TableColumn colTransactionId;
    public TableColumn colTransactionThingId;
    public TableColumn colTransactionCardId;
    public TableColumn colTransactionDate;
    public TableColumn colTransactionTime;
    public TextField txtStartDay;
    public TextField txtStartMonth;
    public TextField txtStartYear;
    public TextField txtEndDay;
    public TextField txtEndMonth;
    public TextField txtEndYear;
    public Button btnRemove;
    public Button btnCancel;
    public Button btnUndo;
    public Button btnRedo;

    public RentService rentService;

    private ObservableList<Rent> rents = FXCollections.observableArrayList();

    public void setService(RentService rentService) {
        this.rentService = rentService;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            rents.addAll(rentService.getAllRents());
            tblTransactions.setItems(rents);
        });
    }

    public void btnRemoveClick(ActionEvent actionEvent) {
        try {
            int startDay = Integer.parseInt(txtStartDay.getText());
            int startMonth = Integer.parseInt(txtStartMonth.getText());
            int startYear = Integer.parseInt(txtStartYear.getText());
            LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

            int endDay = Integer.parseInt(txtEndDay.getText());
            int endMonth = Integer.parseInt(txtEndMonth.getText());
            int endYear = Integer.parseInt(txtEndYear.getText());
            LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

            rentService.removeTransactionsWithinDateInterval(startDate, endDate);

            rents.clear();
            rents.addAll(rentService.getAllRents());
            tblTransactions.setItems(rents);
        } catch (RuntimeException error) {
            Common.showValidationError(error.getMessage());
        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnUndoRemoveTransactionsClick(ActionEvent actionEvent) {
        rentService.undo();
        rents.clear();
        rents.addAll(rentService.getAllRents());
    }

    public void btnRedoRemoveTransactionsClick(ActionEvent actionEvent) {
        rentService.redo();
        rents.clear();
        rents.addAll(rentService.getAllRents());
    }
}
