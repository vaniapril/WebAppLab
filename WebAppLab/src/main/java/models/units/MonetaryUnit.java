package models.units;

import models.units.components.Price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//Денежная еденица
@Entity
@Table(name = "monetaryunit")
public class MonetaryUnit extends FinancialUnit{
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "country")
    public String country;

    public MonetaryUnit(){
        super();
    }

    public MonetaryUnit(String uuid, String name, double high52week, double low52week, double current, String code, String country){
        super(uuid, name, high52week, low52week, current, code);
        this.country = country;
        type = "monetaryunit";
    }

    @Override
    public void update(FinancialUnit unit) throws Exception {
        setCode(unit.code);
        setPrice(new Price(unit.price.high52week, unit.price.low52week, unit.price.current));
        setName(unit.name);
        MonetaryUnit mUnit = (MonetaryUnit) unit;
        setCountry(mUnit.country);
    }

    @Override
    public String toString() {
        return "{" + name + ", " +
                price.high52week + ", " +
                price.low52week + ", " +
                price.current + ", " +
                code + ", " +
                country + "}";
    }
}
