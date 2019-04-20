package Repository;

import Domain.Entity;
import Domain.IValidator;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class JSonFileRepository<T extends Entity> implements IRepository<T> {

    private IValidator<T> validator;
    private String filename;
    private Map<String, T> storage = new HashMap<>();
    private Type type;

    public JSonFileRepository(IValidator<T> validator, String filename, Type type) {
        this.validator = validator;
        this.filename = filename;
        this.type = type;
    }

    private void loadFromFile() {
        storage.clear();
        Gson gson = new Gson();
        try (FileReader in = new FileReader(filename)) {
            try (BufferedReader bufferedReader = new BufferedReader(in)) {
                //String contents = gson.fromJson(bufferedReader.readLine(), Collection<type>);
                //T[] entities = gson.fromJson(bufferedReader, type);
                //for (T entity: entities)
                //storage.put(entity.getId(), entity);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    T entity = gson.fromJson(line, type);
                    storage.put(entity.getId(), entity);
                }
            }
        } catch (Exception exception) {
            System.out.println("Error loading from file: " + exception.getMessage());
        }
    }

    private void writeToFile() {
        Gson gson = new Gson();
        try (FileWriter out = new FileWriter(filename)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(out)) {
                //bufferedWriter.write(gson.toJson(storage.values()));
                for (T entity : storage.values()) {
                    bufferedWriter.write(gson.toJson(entity));
                    bufferedWriter.newLine();
                }
            }
        } catch (Exception exception) {
            System.out.println("Error writing to file: " + exception.getMessage());
        }
    }

    @Override
    public T findById(String id) {
        loadFromFile();
        return storage.get(id);
    }

    @Override
    public void insert(T entity) {
        loadFromFile();
        validator.validate(entity);
        storage.put(entity.getId(), entity);
        writeToFile();
    }

    @Override
    public void update(T entity) {
        loadFromFile();
        validator.validate(entity);
        storage.put(entity.getId(), entity);
        writeToFile();
    }

    @Override
    public void remove(String id) {
        loadFromFile();
        if (!storage.containsKey(id))
            throw new RepositoryException("There is no entity with the given ID to remove!");
        storage.remove(id);
        writeToFile();
    }

    @Override
    public List<T> getAll() {
        loadFromFile();
        return new ArrayList<>(storage.values());
    }

    @Override
    public Map<String, T> getStorage() {
        loadFromFile();
        return storage;
    }
}
