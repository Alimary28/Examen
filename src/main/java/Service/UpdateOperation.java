package Service;

import Domain.Entity;
import Repository.IRepository;

public class UpdateOperation<T extends Entity> extends UndoRedoOperation<T>{

    private T entity;
    private T updatedEntity;

    public UpdateOperation(IRepository<T> repository, T entity, T updatedEntity) {
        super(repository);
        this.entity = entity;
        this.updatedEntity = updatedEntity;
    }

    @Override
    public void doUndo() {
        repository.update(entity);
    }

    @Override
    public void doRedo() {
        repository.update(updatedEntity);
    }
}
