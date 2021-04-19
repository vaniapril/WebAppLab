package models;

import models.exceptions.ValidationException;
import models.units.*;
import models.units.components.Price;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Validator shared;
    public static Validator sharedInstance(){
        if (null == shared){
            shared = new Validator();
        }
        return shared;
    }
    private Validator(){}

    private boolean validateCryptocurrency(Cryptocurrency cryptocurrency){
        return validateFinancialUnit(cryptocurrency);
    }
    private boolean validateMonetaryUnit(MonetaryUnit monetaryUnit){
        return validateFinancialUnit(monetaryUnit) && validateCountry(monetaryUnit.country);
    }
    private boolean validatePreciousUnit(PreciousMetal preciousMetal){
        return validateFinancialUnit(preciousMetal);
    }
    private boolean validateStock(Stock stock){
        return validateFinancialUnit(stock);
    }

    private boolean validateFinancialUnit(FinancialUnit financialUnit){
        return validateCode(financialUnit.code)
                && validateName(financialUnit.name)
                && notEmpty(financialUnit.type)
                && notEmpty(financialUnit.uuid)
                && validatePrice(financialUnit.price);
    }

    public void validate(FinancialUnit financialUnit, Class clazz) throws ValidationException {
        if (Cryptocurrency.class.equals(clazz)) {
            if (!validateCryptocurrency((Cryptocurrency) financialUnit)) throw new ValidationException();
        } else if (MonetaryUnit.class.equals(clazz)) {
            if (!validateMonetaryUnit((MonetaryUnit) financialUnit)) throw new ValidationException();
        } else if (PreciousMetal.class.equals(clazz)) {
            if (!validatePreciousUnit((PreciousMetal) financialUnit)) throw new ValidationException();
        } else if (Stock.class.equals(clazz)) {
            if (!validateStock((Stock) financialUnit)) throw new ValidationException();
        } else {
            throw new ValidationException();
        }
    }

    private boolean validateCode(String code){
        Pattern pattern = Pattern.compile("[A-Z]{2,4}");
        Matcher matcher = pattern.matcher(code);
        return notEmpty(code) && matcher.matches();
    }

    private boolean validateName(String name){
        //USDollar
        //Belarusian Ruble
        Pattern pattern = Pattern.compile("[A-Z]{1,}[a-z]{1,}( [A-Z]{0,1}[a-z]{1,})*");
        Matcher matcher = pattern.matcher(name);
        return notEmpty(name) && matcher.matches();
    }

    private boolean validateCountry(String country){
        //Belarus
        //United States
        Pattern pattern = Pattern.compile("([A-Z]{1}[a-z]{1,} {0,1}){1,}");
        Matcher matcher = pattern.matcher(country);
        return notEmpty(country) && matcher.matches();
    }

    private boolean validatePrice(Price price){
        return price != null && price.high52week >= price.current && price.current >= price.low52week;
    }

    private boolean notEmpty(String country){
        return country != null && !country.isEmpty();
    }
}
