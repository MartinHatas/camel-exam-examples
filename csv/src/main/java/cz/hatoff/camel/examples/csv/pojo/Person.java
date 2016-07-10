package cz.hatoff.camel.examples.csv.pojo;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@CsvRecord(separator = ";", generateHeaderColumns=true, skipFirstLine = true, quoting = false)
public class Person {

    @DataField(pos = 1, columnName = "Name")
    private String name;

    @DataField(pos = 2, columnName = "Price", precision = 1, decimalSeparator = ".")
    private BigDecimal price;

    @DataField(pos = 3, columnName = "Date", pattern = "dd/MM/yyyy")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (price != null ? !(price.setScale(1, RoundingMode.HALF_UP).compareTo(person.price.setScale(1, RoundingMode.HALF_UP)) == 0) : person.price != null) return false;
        return date != null ? date.equals(person.date) : person.date == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
