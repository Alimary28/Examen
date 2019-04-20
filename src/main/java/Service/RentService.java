package Service;

import Domain.Car;
import Domain.Rent;
import Repository.IRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RentService {

    private IRepository<Rent> rentIRepository;
    private IRepository<Car> carIRepository;
    private Stack<UndoRedoOperation<Rent>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Rent>> redoableOperations = new Stack<>();

    public RentService(IRepository<Rent> rentIRepository, IRepository<Car> carIRepository) {
        this.rentIRepository = rentIRepository;
        this.carIRepository = carIRepository;
    }

    /**
     * Adds a rent.
     * @param id is the ID of the rent.
     * @param carId is the ID of the car on the reservation.

     * @param days are the days of rent was made.

     * @throws RentServiceExceptiom if the film's ID does not exist or the film is not scheduled to run.
     */

    public void addRent(String id, String carId, int days, int kilometers) {
        Rent rent = new Rent(id, carId, days, kilometers);
        if (rentIRepository.getStorage().containsKey(id))
            throw new RentServiceExceptiom(String.format("A rent with the ID %s already exists!", id));

        Car rented = carIRepository.findById(carId);
        if (rented == null)
            throw new RentServiceExceptiom(String.format("There is no car with the ID %s!", carId));

        rentIRepository.insert(rent);
        undoableOperations.add(new AddOperation<>(rentIRepository, rent));
        redoableOperations.clear();

       /* Client cardClient = clientRepository.findById(cardId);
        if (cardClient != null)
            cardClient.setPoints(cardClient.getPoints() + (int)rented.getPrice() / 10);*/
    }

    /**
     * Updates a rent.
     * @param id is the new ID of the transaction.
     * @param days  of rent
     * @throws RentServiceExceptiom if there is no reservation with the given ID to update.
     */

    public void updateRent(String id, String carId, int days, int kilometers) {
        Rent rent = new Rent(id, carId, days, kilometers);
        if (!rentIRepository.getStorage().containsKey(id))
            throw new RentServiceExceptiom(String.format("There is no rent with the ID %s!", id));
        undoableOperations.add(new UpdateOperation<>(rentIRepository, rentIRepository.findById(id), rent));
        redoableOperations.add(new UpdateOperation<>(rentIRepository, rent, rentIRepository.findById(id)));
        rentIRepository.update(rent);
    }

    /**
     * Removes a transaction.
     * @param id is the ID of the transaction to remove.
     * @throws RentServiceExceptiom if there is no transaction with the given ID to remove.
     */

    public void removeRent(String id) {
        if (!rentIRepository.getStorage().containsKey(id))
            throw new RentServiceExceptiom(String.format("There is no rent with the ID %s!", id));
        undoableOperations.add(new RemoveOperation<>(rentIRepository, rentIRepository.findById(id)));
        redoableOperations.clear();
        rentIRepository.remove(id);
    }

    /**
     * @return a list of all rents.
     */

    public List<Rent> getAllRents() {
        return rentIRepository.getAll();
    }

    /**
     * Searches rents whose fields contain a given text.
     * @param text is the text searched for.
     * @return a list of rents whose fields contain text.
     */

    public List<Rent> fullTextSearch(String text) {
        List<Rent> results = new ArrayList<>();
        for (Rent rent : getAllRents())
            if (rent.getCarId().contains(text))
                results.add(rent);
        return results;
    }

    /**
     * Searches for rents within a given time interval.

     * @return a list with rents that match the given criteria.
     */

    public List<Rent> transactionsWithinTimeInterval(int Days) {
        List<Rent> results = new ArrayList<>();
        for (Rent rent : rentIRepository.getAll())
            if (rent.getDays() > 0)
                results.add(rent);
        return results;
    }

    /*public void removeTransactionsWithinDateInterval(LocalDate start, LocalDate end) {
        List<Rent> canceled = new ArrayList<>();

        for (Rent rent : rentIRepository.getAll())
            if (rent.getDate().isAfter(start) && rent.getDate().isBefore(end)) {
                rentIRepository.remove(rent.getId());
                canceled.add(rent);
            }
        undoableOperations.add(new TransactionDeleteOperation<>(rentIRepository, canceled));
        redoableOperations.clear();
    }*/

    public void undo() {
        if (!undoableOperations.isEmpty()) {
            UndoRedoOperation<Rent> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableOperations.add(lastOperation);
        }
    }

    public void redo() {
        if (!redoableOperations.isEmpty()) {
            UndoRedoOperation<Rent> lastOperation = redoableOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }
}
