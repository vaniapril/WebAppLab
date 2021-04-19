package models.units;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.units.components.Price;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @Type(value = Cryptocurrency.class, name = "cryptocurrency"),
        @Type(value = MonetaryUnit.class, name = "monetaryunit"),
        @Type(value = PreciousMetal.class, name = "preciousmetal"),
        @Type(value = Stock.class, name = "stock")
})
abstract public class FinancialUnit {
    public String type;
    public String uuid;
    public String name;
    @JsonSerialize
    public Price price;
    public String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FinancialUnit(){}

    FinancialUnit(String uuid, String name, double high52week, double low52week, double current, String code){
        this.uuid = uuid;
        this.name = name;
        this.price = new Price(high52week, low52week, current);
        this.code = code;
    }


}
