package cz.hatoff.camel.examples.xml;

import java.math.BigDecimal;
import java.util.Date;

public class Person {

    private String name;
    private BigDecimal price;
    private Date date;

    public Person() {
    }

    public Person(String name, BigDecimal price, Date date) {
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
