package de.saxsys.jfx.tabellen;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import com.google.inject.Inject;

import de.saxsys.jfx.tabellen.model.Person;

public class PersonTable extends TableView<Person> {

	@Inject
	public PersonTable(PersonService persons) {
		TableColumn<Person, String> firstNameCol = new TableColumn<>(
				"First Name");

		TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
		TableColumn<Person, Integer> ageCol = new TableColumn<>("Age");
		TableColumn<Person, Person> townCol = new TableColumn<>("Town");

		TableColumn<Person, String> townName = new TableColumn<>("Town Name");
		TableColumn<Person, Number> townZipCode = new TableColumn<>("Zip Code");

		// Combine Townname and Zip to Town
		townCol.getColumns().addAll(townName, townZipCode);

		// Add Mjor colums
		getColumns().addAll(firstNameCol, lastNameCol, ageCol, townCol);

		itemsProperty().bind(persons.getPersons());

		lastNameCol
				.setCellValueFactory(new PropertyValueFactory<Person, String>(
						"lastname"));
		ageCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>(
				"age"));

		townName.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Person, String> param) {
				return param.getValue().townProperty().get().nameProperty();
			}
		});

		townZipCode
				.setCellValueFactory(new Callback<CellDataFeatures<Person, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(
							CellDataFeatures<Person, Number> param) {
						// TODO Auto-generated method stub
						return param.getValue().townProperty().get()
								.zipCodeProperty();
					}
				});

		System.out.println("Table Init Done");
	}
}
