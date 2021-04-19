package models.exceptions;

import java.util.NoSuchElementException;

public class ExceptionMessage {
    public static String get(Exception e){
        if (e instanceof DaoLayerException){
            return "Failed to access data.";
        }
        if (e instanceof ValidationException || e instanceof NumberFormatException){
            return "Invalid data format.";
        }
        if (e instanceof NoSuchElementException){
            return "Item not found.";
        }
        return "Unknown error.";
    }
}
