package Domain;

import java.util.Calendar;

public class CarValidator implements IValidator<Car> {

    public void validate(Car car){
        String errors = "";

        if (car.getPrice() <= 0)
            errors += "Price must be greater than 0!\n";

        if (car.getKilometers() < 0)
            errors += "Kilometers are not valid!\n";

        if (!errors.isEmpty())
            throw new CarValidatorException("\n" + errors);
    }
}
