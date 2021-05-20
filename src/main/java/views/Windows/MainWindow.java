package views.Windows;

import java.awt.Color;

import javax.swing.JOptionPane;

import org.uqbar.arena.widgets.*;

import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.SimpleWindow;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.UserException;

import modelo.Cuenta;
import modelo.Empresa;
import views.vm.MainViewModel;
import views.vm.IndicadorAMostrar;

@SuppressWarnings("serial")
public class MainWindow extends SimpleWindow<MainViewModel> {

	public MainWindow(WindowOwner parent) {
		super(parent, new MainViewModel());
	}

	@Override
	protected void addActions(Panel actionPanel) {
		new Button(actionPanel).setCaption("Ver Cuentas/Indicadores").setAsDefault().onClick(() -> {
			try {
				this.getModelObject().buscarCuentasEmpresa();
				this.getModelObject().buscarIndicadores();
			} catch (UserException e) {
				throw new UserException(e.getMessage());
			} catch (Exception e) {
				// e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Por favor complete todos los campos correctamente para realizar la busqueda");
			}
		}).setBackground(Color.CYAN);
		new Button(actionPanel).setCaption("Agregar Indicador").onClick(this::agregarIndicador).setBackground(Color.CYAN);
		new Button(actionPanel).setCaption("Guardar Indicadores").onClick(() -> {
			try {
				this.getModelObject().guardarIndicadoresDefinidos();
			} catch (Exception e) {
				// e.printStackTrace();
				throw new UserException("Error al guardar indicadores");
			}
		}).setBackground(Color.CYAN);
		new Button(actionPanel).setCaption("Recuperar Indicadores").onClick(() -> {
			try {
				this.getModelObject().cargarIndicadoresDefinidos();
			} catch (UserException e) {
				throw new UserException(e.getMessage());
			} catch (Exception e) {
				// e.printStackTrace();
				throw new UserException("Error al recuperar indicadores");
			}
		}).setBackground(Color.CYAN);
		new Button(actionPanel).setCaption("Clean").onClick(this::clean).setBackground(Color.CYAN);
		new Button(actionPanel).setCaption("Salir").onClick(this::close).setBackground(Color.RED);

	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		new Label(mainPanel).setText("Hola Hector!").setForeground(Color.WHITE).setBackground(Color.MAGENTA)
				.setWidth(400);
		this.setTitle("Donde invertir");

		crearFileSelector(mainPanel, "Ingresar archivo de empresas", "pathEmpresas");

		crearLabel(mainPanel, "Elija una Empresa");

		Selector<Empresa> selectorOperacion = new Selector<Empresa>(mainPanel).allowNull(true);
		selectorOperacion.bindItemsToProperty("empresas");
		selectorOperacion.bindValueToProperty("empresaSeleccionada");
		
		crearLabel(mainPanel, "Año:");
		new NumericField(mainPanel).bindValueToProperty("anio");

		crearTablaCuentas(mainPanel);
		crearTablaIndicadores(mainPanel);

		crearFileSelector(mainPanel, "Ingresar archivo para guardar/recuperar indicadores", "pathIndicadores");
		
	}

	protected void crearTablaCuentas(Panel mainPanel) {
		new Label(mainPanel).setText("Cuentas:").setWidth(200);

		Table<Cuenta> tablaCuentas = new Table<>(mainPanel, Cuenta.class);
		tablaCuentas.bindItemsToProperty("cuentas");
		tablaCuentas.setNumberVisibleRows(7);

		hacerColumnaCuentaYBindear(tablaCuentas, "Tipo", "tipo");
		hacerColumnaCuentaYBindear(tablaCuentas, "Resultado", "resultado");
		hacerColumnaCuentaYBindear(tablaCuentas, "Desde Fecha", "periodo.desdeFecha");
		hacerColumnaCuentaYBindear(tablaCuentas, "Hasta Fecha", "periodo.hastaFecha");

		tablaCuentas.setWidth(300);
		tablaCuentas.setHeight(500);
	}

	protected void crearTablaIndicadores(Panel mainPanel) {
		new Label(mainPanel).setText("Indicadores:").setWidth(200);

		Table<IndicadorAMostrar> tablaIndicadores = new Table<>(mainPanel, IndicadorAMostrar.class);
		tablaIndicadores.bindItemsToProperty("indicadores");
		tablaIndicadores.setNumberVisibleRows(7);

		hacerColumnaIndicadorYBindear(tablaIndicadores, "Tipo", "nombre");
		hacerColumnaIndicadorYBindear(tablaIndicadores, "Formula", "formula");
		hacerColumnaIndicadorYBindear(tablaIndicadores, "Resultado", "resultado");
	}

	protected void hacerTextBoxYBindear(Panel mainPanel, String label, String propiedad) {
		crearLabel(mainPanel, label);
		new TextBox(mainPanel).bindValueToProperty(propiedad);
	}

	protected void hacerColumnaCuentaYBindear(Table<Cuenta> tablaCuentas, String titulo, String propiedad) {
		new Column<>(tablaCuentas).setTitle(titulo).bindContentsToProperty(propiedad);
	}

	protected void hacerColumnaIndicadorYBindear(Table<IndicadorAMostrar> tablaIndicadores, String titulo, String propiedad) {
		new Column<>(tablaIndicadores).setTitle(titulo).bindContentsToProperty(propiedad);
	}

	protected void hacerTextBoxDateYBindear(Panel mainPanel, String label, String propiedad) {
		crearLabel(mainPanel, label);
		new TextBox(mainPanel).bindValueToProperty(propiedad).setTransformer(new LocalDateTransformer());
	}

	protected void crearLabel(Panel mainPanel, String nombreLabel) {
		new Label(mainPanel).setText(nombreLabel).setBackground(Color.CYAN).setWidth(200);
	}

	protected void crearFileSelector(Panel mainPanel, String caption, String propiedad) {
		new FileSelector(mainPanel).setCaption(caption).bindValueToProperty(propiedad);
		new Label(mainPanel).bindValueToProperty(propiedad);
	}

	public void clean() {
		this.getModelObject().clean();
	}

	public void agregarIndicador() {
		Window<?> window = new IndicadorWindow(this);
		window.open();
	}
	
}
