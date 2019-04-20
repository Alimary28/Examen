package Domain;

import java.util.Calendar;

public class RentValidator implements IValidator<Rent> {

    public void validate(Rent rent){
        String errors = "";

        if (rent.getDays() < 0)
            errors += "The days of rent must be greater than 0 !";

        if (!errors.isEmpty())
            throw new RentValidatorException("\n" + errors);
    }

}
