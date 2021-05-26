package models.units.components;


public interface Identifiable<T> {
    T getId();
    void setId(T id);
}
