package Service;

import Domain.Car;
import Domain.Rent;
import Repository.IRepository;

import java.util.*;

public class CarService {

    private IRepository<Car> carIRepository;
    private IRepository<Rent> rentIRepository;
    private Stack<UndoRedoOperation<Car>> undoableOperations = new Stack();
    private Stack<UndoRedoOperation<Car>> redoableOperations = new Stack();

    /**
     * Instantiates a service for things.
     * @param carIRepository is the repository used.
     */

    public CarService(IRepository<Car> carIRepository, IRepository<Rent> rentIRepository) {
        this.carIRepository = carIRepository;
        this.rentIRepository = rentIRepository;
    }

    /**
     * Adds a car.
     * @param id is the ID of the car to add.
     * @param model is the model of the car to add.
     * @param kilometers  of the car to add.
     * @param price is the price for the car to add.
     * @throws CarServiceException if the car with the ID to add already exists.
     */

    public void addCar(String id, String model, int kilometers, double price) {
        Car car = new Car(id, model, kilometers, price);
        if (carIRepository.getStorage().containsKey(id))
            throw new CarServiceException(String.format("A car with the ID %s already exists!", id));
        carIRepository.insert(car);
        undoableOperations.add(new AddOperation<>(carIRepository, car));
        redoableOperations.clear();
    }

    /**
     * Updates a car.
     * @param id is the new ID of the car.
     * @param model is the new model of the car.
     * @param kilometers of the car.
     * @param price is the new price for the car
     * @throws CarServiceException if there is no car with the given ID to update.
     */

    public void updateCar(String id, String model, int kilometers, double price) {
        Car car = new Car(id, model, kilometers, price);
        if (!carIRepository.getStorage().containsKey(id))
            throw new CarServiceException(String.format("There is no car with the ID %s!", id));
        undoableOperations.add(new UpdateOperation<>(carIRepository, carIRepository.findById(id), car));
        redoableOperations.add(new UpdateOperation<>(carIRepository, car, carIRepository.findById(id)));
        carIRepository.update(car);
    }

    /**
     * Removes a film.
     * @param id is the ID of the car to remove.
     * @throws CarServiceException if there is no film with the given ID to remove.
     */

    public void removeCar(String id) {
        if (!carIRepository.getStorage().containsKey(id))
            throw new CarServiceException(String.format("There is no car with the ID %s!", id));
        undoableOperations.add(new RemoveOperation<>(carIRepository, carIRepository.findById(id)));
        redoableOperations.clear();
        carIRepository.remove(id);
    }

    /**
     * @return a list of all films.
     */

    public List<Car> getAllCars() {
        return carIRepository.getAll();
    }

    /**
     * Searches cars whose fields contain a given text.
     * @param text is the text searched for.
     * @return a list of films whose fields contain text.
     */

    public List<Car> fullTextSearch(String text) {
        List<Car> results = new ArrayList<>();
        for (Car car : getAllCars())
            if (car.getModel().toLowerCase().contains(text.toLowerCase()) || Integer.toString(car.getKilometers()).contains(text) ||
                    Double.toString(car.getPrice()).contains(text))
                results.add(car);
        return results;
    }

    public List<RentVM> getOrderedByTransactions() {
        Map<String, Integer> frequencies = new HashMap<>();
        for (Rent rent : rentIRepository.getAll()) {
            String thingId = rent.getCarId();
            if (frequencies.containsKey(thingId))
                frequencies.put(thingId, frequencies.get(thingId) + 1);
            else
                frequencies.put(thingId, 1);
        }

        List<RentVM> results = new ArrayList<>();
        for (String thingId : frequencies.keySet()) {
            Car car = carIRepository.findById(thingId);
            results.add(new RentVM(car, frequencies.get(thingId)));
        }
        results.sort((f1, f2) -> f2.rents - f1.rents);
        return results;
    }

    public void undo() {
        if (!undoableOperations.isEmpty()) {
            UndoRedoOperation<Car> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableOperations.add(lastOperation);
        }
    }

    public void redo() {
        if (!redoableOperations.isEmpty()) {
            UndoRedoOperation<Car> lastOperation = redoableOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }

    public IRepository<Car> getCarIRepository() { return carIRepository; }
}
