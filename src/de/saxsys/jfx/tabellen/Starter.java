package de.saxsys.jfx.tabellen;

import java.util.List;

import javafx.scene.Scene;
import javafx.stage.Stage;

import com.google.inject.Inject;
import com.google.inject.Module;

import de.saxsys.jfx.mvvm.guice.MvvmfxGuiceApplication;

public class Starter extends MvvmfxGuiceApplication {

	@Inject
	private PersonTable table;

	@Override
	public void initGuiceModules(List<Module> modules) throws Exception {
	}

	@Override
	public void startMvvmfx(Stage stage) throws Exception {

		Scene scene = new Scene(table, 300, 500);

		table.setMinWidth(Double.MAX_VALUE);
		table.setMaxWidth(Double.MAX_VALUE);
		table.setPrefWidth(Double.MAX_VALUE);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
