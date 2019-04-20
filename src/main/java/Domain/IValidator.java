package Domain;

public interface IValidator<T extends Entity> {

   public void validate(T entity);
}
