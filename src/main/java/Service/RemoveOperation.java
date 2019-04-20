package Service;

import Domain.Entity;
import Repository.IRepository;

public class RemoveOperation<T extends Entity> extends UndoRedoOperation<T> {

    private T removedEntity;

    public RemoveOperation(IRepository<T> repository, T removedEntity) {
        super(repository);
        this.removedEntity = removedEntity;
    }

    @Override
    public void doUndo() {
        repository.insert(removedEntity);
    }

    @Override
    public void doRedo() {
        repository.remove(removedEntity.getId());
    }
}
