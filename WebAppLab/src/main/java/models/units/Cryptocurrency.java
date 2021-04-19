package models.units;

//Криптовалюта

public class Cryptocurrency extends FinancialUnit{
    public Cryptocurrency(){
        super();
    }

    public Cryptocurrency(String uuid, String name, double high52week, double low52week, double current, String code){
        super(uuid, name, high52week, low52week, current, code);
        type = "cryptocurrency";
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
