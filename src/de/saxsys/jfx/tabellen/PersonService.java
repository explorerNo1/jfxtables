package de.saxsys.jfx.tabellen;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.saxsys.jfx.tabellen.model.Person;
import de.saxsys.jfx.tabellen.model.Town;

public class PersonService {

	private final ListProperty<Person> persons = new SimpleListProperty<>(
			FXCollections.observableArrayList());

	public PersonService() {
		Person alex = new Person();
		alex.setAge(26);
		alex.setFirstname("Alexander");
		alex.setLastname("Casall");
		alex.setTown(new Town("Dresden", 123459));

		Person steffi = new Person();
		steffi.setAge(28);
		steffi.setFirstname("Stefanie");
		steffi.setLastname("Alberecht");
		steffi.setTown(new Town("Dresden", 123459));

		Person stefan = new Person();
		stefan.setAge(29);
		stefan.setFirstname("Stefan");
		stefan.setLastname("Heinze");
		stefan.setTown(new Town("Dresden", 123459));

		persons.addAll(alex, steffi, stefan);
	}

	public ObservableValue<? extends ObservableList<Person>> getPersons() {
		return persons;
	}

}
