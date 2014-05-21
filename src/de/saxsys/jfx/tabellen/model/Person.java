package de.saxsys.jfx.tabellen.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

	private final StringProperty firstname = new SimpleStringProperty();
	private final StringProperty lastname = new SimpleStringProperty();

	private final IntegerProperty age = new SimpleIntegerProperty();
	private final SimpleObjectProperty<Town> town = new SimpleObjectProperty<>();

	public String getFirstname() {
		return this.firstname.get();
	}

	public String getLastname() {
		return this.lastname.get();
	}

	public int getAge() {
		return this.age.get();
	}

	public Town getTown() {
		return this.town.get();
	}

	public void setFirstname(String firstname) {
		this.firstname.set(firstname);
	}

	public void setLastname(String lastname) {
		this.lastname.set(lastname);
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public void setTown(Town town) {
		this.town.set(town);
	}

	public StringProperty firstnameProperty() {
		return firstname;
	}

	public StringProperty lastnameProperty() {
		return lastname;
	}

	public IntegerProperty ageProperty() {
		return age;
	}

	public SimpleObjectProperty<Town> townProperty() {
		return town;
	}

}
