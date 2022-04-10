package by.epam.java.training.lifehacks.exception;

public class DaoException extends Exception {
    public DaoException() {}

    public DaoException(String m) {
        super(m);
    }
    public DaoException(Throwable th) {
        super(th);
    }
    public DaoException(String m, Throwable th) {
        super(m, th);
    }

}