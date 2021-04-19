package models.units;

//Драгоценный металл

public class PreciousMetal extends FinancialUnit{

    public PreciousMetal(){
        super();
    }

    public PreciousMetal(String uuid, String name, double high52week, double low52week, double current, String code){
        super(uuid, name, high52week, low52week, current, code);
        type = "preciousmetal";
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
