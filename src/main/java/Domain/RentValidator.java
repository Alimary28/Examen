package Domain;

import java.util.Calendar;

public class RentValidator implements IValidator<Rent> {

    public void validate(Rent rent){
        String errors = "";

        if (rent.getDate().getYear() < 1940 || rent.getDate().getYear() > Calendar.getInstance().get(Calendar.YEAR))
            errors += "The kilometers of reservation must be between 1940 and " + Calendar.getInstance().get(Calendar.YEAR) + "!\n";

        if (!errors.isEmpty())
            throw new RentValidatorException("\n" + errors);
    }

}
