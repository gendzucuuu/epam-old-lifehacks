package by.epam.java.training.lifehacks.exception;

public class ServiceException extends Exception{
    public ServiceException() {}

    public ServiceException(String m) {
        super(m);
    }
    public ServiceException(Throwable th) {
        super(th);
    }
    public ServiceException(String m, Throwable th) {
        super(m, th);
    }
}
