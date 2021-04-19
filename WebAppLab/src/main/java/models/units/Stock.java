package models.units;

//Акции

public class Stock extends FinancialUnit{
    public Stock(){
        super();
    }

    public Stock(String uuid, String name, double high52week, double low52week, double current, String code){
        super(uuid, name, high52week, low52week, current, code);
        type = "stock";
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
