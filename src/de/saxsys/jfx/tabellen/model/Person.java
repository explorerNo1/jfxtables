package de.saxsys.jfx.tabellen.model;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class Person {

	private final StringProperty firstname = new SimpleStringProperty();
	private final StringProperty lastname = new SimpleStringProperty();

	private final IntegerProperty age = new SimpleIntegerProperty();
	private final SimpleObjectProperty<Town> town = new SimpleObjectProperty<>();

	private final DoubleProperty solvency = new SimpleDoubleProperty();

	public Person() {
		town.addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
				System.out.println("Town Changed for " + firstname.get() + " " + newValue.getName());
		});
		firstname.addListener((observable, oldValue, newValue) -> System.out.println("New firstname: "
				+ firstname.get()));
		lastname.addListener((observable, oldValue, newValue) -> System.out.println("New lastname: " + lastname.get()));

		fakeSolvencyProgress();
	}

	private void fakeSolvencyProgress() {
		Timeline timeline = new Timeline();
		KeyFrame target = new KeyFrame(Duration.seconds(new Random().nextInt(5) + 5), new KeyValue(solvency,
				new Random().nextDouble()));
		timeline.getKeyFrames().add(target);
		timeline.setAutoReverse(true);
		timeline.setCycleCount(2);
		timeline.play();
		timeline.setOnFinished(e -> fakeSolvencyProgress());
	}

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

	public void setSolvency(double solvency) {
		this.solvency.set(solvency);
	}

	public double getSolvency() {
		return solvency.get();
	}

	public StringProperty firstnameProperty() {
		return firstname;
	}

	public StringProperty lastnameProperty() {
		return lastname;
	}

	public DoubleProperty solvencyProperty() {
		return solvency;
	}

	public IntegerProperty ageProperty() {
		return age;
	}

	public SimpleObjectProperty<Town> townProperty() {
		return town;
	}

}
