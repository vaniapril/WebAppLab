package models.units;

//Денежная еденица
public class MonetaryUnit extends FinancialUnit{
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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
    public String toString() {
        return "{" + name + ", " +
                price.high52week + ", " +
                price.low52week + ", " +
                price.current + ", " +
                code + ", " +
                country + "}";
    }
}
