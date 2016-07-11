package cz.hatoff.camel.examples.xml;


import java.util.ArrayList;
import java.util.List;

public class PersonList {

    private List<Person> persons = new ArrayList<Person>();

    public PersonList() {
    }

    public PersonList(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
