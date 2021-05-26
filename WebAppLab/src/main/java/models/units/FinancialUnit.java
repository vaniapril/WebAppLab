package models.units;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.units.components.Identifiable;
import models.units.components.Price;

import javax.persistence.*;

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

@MappedSuperclass
@Entity
abstract public class FinancialUnit implements Identifiable<String> {
    public String type;
    @Id
    public String uuid;
    @Column(name = "name")
    public String name;
    @JsonSerialize
    @Embedded
    public Price price;
    @Column(name = "code")
    public String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return getId();
    }

    public void setUuid(String uuid) {
        setId(uuid);
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

    public abstract void update(FinancialUnit unit) throws Exception;


    @Override
    public String getId() {
        return uuid;
    }

    @Override
    public void setId(String id) {
        uuid = id;
    }
}
