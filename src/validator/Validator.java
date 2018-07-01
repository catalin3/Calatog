package validator;

import java.util.List;

public interface Validator<T> {
    public void validate(T t) throws ValidateException;
    public void exist(List<T> list, T t) throws ValidateException;
}
