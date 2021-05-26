package models.units;

//Акции

import models.units.components.Price;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock extends FinancialUnit{
    public Stock(){
        super();
    }

    public Stock(String uuid, String name, double high52week, double low52week, double current, String code){
        super(uuid, name, high52week, low52week, current, code);
        type = "stock";
    }

    @Override
    public void update(FinancialUnit unit) throws Exception {
        setCode(unit.code);
        setPrice(new Price(unit.price.high52week, unit.price.low52week, unit.price.current));
        setName(unit.name);
    }

    @Override
    public String toString() {
        return "{" + name + ", " +
                price.high52week + ", " +
                price.low52week + ", " +
                price.current + ", " +
                code + "}";
    }
}
