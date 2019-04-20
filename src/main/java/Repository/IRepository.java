package Repository;

import Domain.Entity;

import java.util.List;
import java.util.Map;

public interface IRepository<T extends Entity> {

    /**
     * Finds an entity by ID.
     * @param id is the ID of the entity to find.
     * @return the entity with the given ID if it exists.
     */

    T findById(String id);

    /**
     * Adds an entity.
     * @param entity is the entity to add.
     */

    void insert(T entity);

    /**
     * Updates an entity if it already exists.
     * @param entity is the entity to update.
     */

    void update(T entity);

    /**
     * Removes an entity.
     * @param id is the ID of the entity to remove.
     * @throws RepositoryException if there is no entity with the given ID.
     */

    void remove(String id);

    /**
     * @return a list of all entities.
     */

    List<T> getAll();

    Map<String, T> getStorage();
}
