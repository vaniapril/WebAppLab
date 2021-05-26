package models.units.components;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {
    @Column(name = "high52week")
    public double high52week;
    @Column(name = "low52week")
    public double low52week;
    @Column(name = "current")
    public double current;

    public double getHigh52week() {
        return high52week;
    }

    public void setHigh52week(double high52week) {
        this.high52week = high52week;
    }

    public double getLow52week() {
        return low52week;
    }

    public void setLow52week(double low52week) {
        this.low52week = low52week;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public Price(){}

    public Price(double high52week, double low52week, double current){
        this.high52week = high52week;
        this.low52week = low52week;
        this.current = current;
    }
}
