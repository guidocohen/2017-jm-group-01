package views.Windows;

import java.awt.Color;

import javax.swing.JOptionPane;

import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.UserException;

import views.vm.IndicadorAMostrar;
import views.vm.IndicadoresViewModel;

public class IndicadorWindow extends SimpleWindow<IndicadoresViewModel> {

	public IndicadorWindow(WindowOwner parent) {
		super(parent, new IndicadoresViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Agregar Indicador Nuevo");

		crearTablaIndicadores(mainPanel);

		new Label(mainPanel).setText("Ingrese el Tipo del indicador a agregar").setBackground(Color.CYAN);
		new TextBox(mainPanel).bindValueToProperty("nombre");
		new Label(mainPanel).setText("Ingrese la formula correspondiente al indicador").setBackground(Color.CYAN);
		new TextBox(mainPanel).bindValueToProperty("formula");
	}

	@Override
	protected void addActions(Panel actionPanel) {
		new Button(actionPanel).setCaption("Agregar").setAsDefault().onClick(() -> {
			try {
				this.getModelObject().addIndicador();
			} catch(UserException ex){
				throw new UserException(ex.getMessage());
			} catch(Exception e){
				JOptionPane.showMessageDialog(null,"Por favor complete los campos correctamente");
			}
		});
		new Button(actionPanel).setCaption("Cancelar").onClick(this::close).setBackground(Color.RED);
	}

	protected void crearTablaIndicadores(Panel mainPanel) {
		new Label(mainPanel).setText("Indicadores Definidos:").setWidth(200);

		Table<IndicadorAMostrar> tablaIndicadores = new Table<>(mainPanel, IndicadorAMostrar.class);
		tablaIndicadores.bindItemsToProperty("indicadores");
		tablaIndicadores.setNumberVisibleRows(10);

		new Column<>(tablaIndicadores).setTitle("Nombre").bindContentsToProperty("nombre");
		new Column<>(tablaIndicadores).setTitle("Formula").bindContentsToProperty("formula");
	}

}
