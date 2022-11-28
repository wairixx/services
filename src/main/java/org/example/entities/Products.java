package org.example.entities;

import lombok.Getter;
import lombok.Setter;

public class Products{
    @Getter
    @Setter
    public int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    private Integer typeId;


    public Products(String name, int price, int id) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "\n" + "id=" + id + "\n" + "name=" + name + "\n" +
                "price=" + price;
    }
}