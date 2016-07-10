package cz.hatoff.camel.examples.csv.bindy.pojo;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.util.Date;

@CsvRecord(separator = ",")
public class Person {

    @DataField(pos = 1)
    private String name;

    @DataField(pos = 2)
    private double amount;

    @DataField(pos = 3, pattern = "dd/MM/yyyy")
    private Date date;

    public Person() {
    }

    public Person(String name, double amount, Date date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

        if (Double.compare(person.amount, amount) != 0) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return date != null ? date.equals(person.date) : person.date == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
