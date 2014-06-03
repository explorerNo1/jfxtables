package de.saxsys.jfx.tabellen;

import java.time.LocalDate;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Pagination;
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

//TODO report bug reorder solvency
public class PersonTable extends TableView<Person> {

	private static final String DEFAULT_FILL = "";
	private final PersonService personsService;
	private final TownService townService;

	private final TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
	private final TableColumn<Person, LocalDate> ageCol = new TableColumn<>("Age");
	private final TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");

	private final TableColumn<Person, Town> townCol = new TableColumn<>("Town");
	private final TableColumn<Person, Town> townName = new TableColumn<>("Town Name");
	private final TableColumn<Person, Town> townZipCode = new TableColumn<>("Zip Code");

	private final TableColumn<Person, Double> solvencyCol = new TableColumn<>("Solvency");
	private FilteredList<Person> virtualizedPersons;

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

		addColumns();
		System.out.println("Table Init Done");

		// Size of Table to parent
		setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Loading of big data
		initPagination();

	}

	private void initPagination() {
		int itemCount = 10;
		final Pagination pagination = new Pagination(personsService.getPersons().getValue().size() / itemCount);

		virtualizedPersons = personsService.getPersons().filtered(t -> {
			int indexOf = personsService.getPersons().indexOf(t);
			int currentItemCount = pagination.getCurrentPageIndex() * itemCount;
			if (indexOf > currentItemCount && indexOf < currentItemCount + itemCount)
				return true;
			else
				return false;
		});

		itemsProperty().bind(new SimpleListProperty<>(virtualizedPersons));
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
		ageCol.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("birth"));
		ageCol.setCellFactory(c -> new AgePickerCell<>());
		ageCol.setOnEditCommit(e -> {
			System.out.println(e.getTableView());
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setBirth(e.getNewValue());
		});
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
	private void addColumns() {

		// Combine Townname and Zip to Town
		townCol.getColumns().addAll(townName, townZipCode);

		// Add Major colums
		getColumns().addAll(firstNameCol, lastNameCol, ageCol, townCol, solvencyCol);

		itemsProperty().bind(new SimpleListProperty<>(personsService.getPersons()));
	}
}
