package models.exceptions;

import java.util.NoSuchElementException;

public class ExceptionMessage {
    public static String get(Exception e){
        if (e instanceof DaoLayerException){
            return "Failed to access data.";
        }
        if (e instanceof ServiceLayerException){
            return "Failed to process data.";
        }
        if (e instanceof ServletLayerException){
            return "Failed to input data.";
        }
        return "Unknown error.";
    }
}
