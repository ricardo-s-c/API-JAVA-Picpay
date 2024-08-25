package br.com.dev.ricardo.picpay_backend_java.exeption;

public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException (String message) {
        super(message);
    }
}
