import Domain.*;
import Repository.IRepository;
import Repository.JSonFileRepository;
import Service.CarService;
import Service.RentService;
import UI.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
            Parent root = fxmlLoader.load();

            IValidator<Car> carValidator = new CarValidator();
            IValidator<Rent> rentValidator = new RentValidator();

            IRepository<Car> carIRepository = new JSonFileRepository<>(carValidator,"cars.json", Car.class);
            IRepository<Rent> rentIRepository = new JSonFileRepository<>(rentValidator,"rents.json", Rent.class);

            CarService carService = new CarService(carIRepository, rentIRepository);
            RentService rentService = new RentService(rentIRepository, carIRepository);

        Controller controller = fxmlLoader.getController();
        controller.setServices(carService, rentService);

        primaryStage.setTitle("Cars Manager");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
