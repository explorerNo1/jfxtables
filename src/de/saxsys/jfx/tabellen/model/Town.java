package de.saxsys.jfx.tabellen.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class Town {

	private final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper zipCode = new ReadOnlyIntegerWrapper();

	public Town(String name, int zipCode) {
		this.name.set(name);
		this.zipCode.set(zipCode);
	}

	public ReadOnlyStringProperty nameProperty() {
		return name.getReadOnlyProperty();
	}

	public ReadOnlyIntegerProperty zipCodeProperty() {
		return zipCode.getReadOnlyProperty();
	}

	public String getName() {
		return name.get();
	}

	public int getZipCode() {
		return zipCode.get();
	}

}
