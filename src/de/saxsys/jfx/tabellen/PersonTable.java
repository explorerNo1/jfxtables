package de.saxsys.jfx.tabellen;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.saxsys.jfx.tabellen.model.Person;
import de.saxsys.jfx.tabellen.model.Town;

public class PersonTable extends TableView<Person> {

	private static final String DEFAULT_FILL = "";
	private final PersonService personsService;
	private final TownService townService;

	private final TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
	private final TableColumn<Person, Integer> ageCol = new TableColumn<>("Age");
	private final TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");

	private final TableColumn<Person, Town> townCol = new TableColumn<>("Town");
	private final TableColumn<Person, Town> townName = new TableColumn<>("Town Name");
	private final TableColumn<Person, Town> townZipCode = new TableColumn<>("Zip Code");

	private final TableColumn<Person, Double> solvencyCol = new TableColumn<>("Solvency");

	@Inject
	public PersonTable(PersonService personsService, TownService townsService) {

		this.personsService = personsService;
		this.townService = townsService;

		setEditable(true);

		initFirstNameCol();
		initLastNameCol();
		initAgeCol();
		initTownNameCol();
		initTownZipCol();
		initSolvencyCol();

		initColumns();
		System.out.println("Table Init Done");
	}

	private void initFirstNameCol() {
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstname"));
	}

	private void initLastNameCol() {
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastname"));
	}

	private void initAgeCol() {
		ageCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
	}

	private void initTownZipCol() {
		townZipCode.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Town>() {

			@Override
			public String toString(Town object) {
				if (object == null) {
					return DEFAULT_FILL;
				}
				return String.valueOf(object.getZipCode());
			}

			@Override
			public Town fromString(String string) {
				try {
					return townService.getTownForZip(Integer.parseInt(string));
				} catch (NumberFormatException e) {
					return null;
				}

			}
		}));
		townZipCode.setCellValueFactory(cellDataFeature -> cellDataFeature.getValue().townProperty());

	}

	private void initTownNameCol() {
		townName.setCellValueFactory(p -> p.getValue().townProperty());
		townName.setCellFactory(ComboBoxTableCell.<Person, Town> forTableColumn(new StringConverter<Town>() {

			@Override
			public String toString(Town object) {
				if (object == null) {
					return DEFAULT_FILL;
				}
				return object.getName();
			}

			@Override
			public Town fromString(String string) {
				return townService.getTownForName(string);
			}
		}, townService.getTowns().getValue()));

	}

	private void initSolvencyCol() {
		solvencyCol.setCellValueFactory(new PropertyValueFactory<Person, Double>("solvency"));
		solvencyCol.setCellFactory(ProgressBarTableCell.forTableColumn());
	}

	@SuppressWarnings("unchecked")
	private void initColumns() {

		// Combine Townname and Zip to Town
		townCol.getColumns().addAll(townName, townZipCode);

		// Add Major colums
		getColumns().addAll(firstNameCol, lastNameCol, ageCol, townCol, solvencyCol);

		itemsProperty().bind(personsService.getPersons());
	}
}
