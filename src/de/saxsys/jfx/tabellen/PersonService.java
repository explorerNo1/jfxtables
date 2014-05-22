package de.saxsys.jfx.tabellen;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.google.inject.Inject;

import de.saxsys.jfx.tabellen.model.Person;

public class PersonService {

	private final ListProperty<Person> persons = new SimpleListProperty<>(FXCollections.observableArrayList());

	@Inject
	public PersonService(TownService towns) {
		Person alex = new Person();
		alex.setAge(26);
		alex.setFirstname("Alexander");
		alex.setLastname("Casall");
		alex.setTown(towns.getTowns().getValue().get(0));
		alex.setSolvency(0.8);
		Person steffi = new Person();
		steffi.setAge(28);
		steffi.setFirstname("Stefanie");
		steffi.setLastname("Alberecht");

		steffi.setSolvency(0.7);
		steffi.setTown(towns.getTowns().getValue().get(0));

		Person stefan = new Person();
		stefan.setAge(29);
		stefan.setSolvency(0.3);
		stefan.setFirstname("Stefan");
		stefan.setLastname("Heinze");
		stefan.setTown(towns.getTowns().getValue().get(1));

		persons.addAll(alex, steffi, stefan);
	}

	public ObservableValue<? extends ObservableList<Person>> getPersons() {
		return persons;
	}

}
