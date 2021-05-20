package modelo;

import excepciones.FiltrarPorNombreException;
import excepciones.FiltrarPorPeriodoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.uqbar.commons.utils.Observable;

@Observable
@Entity
@Table(name = "EMPRESA")
public class Empresa {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "edad")
    private int edad;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cuenta> cuentas = new ArrayList<>();

    public Empresa(String nombre) {
        this.nombre = nombre;
    }

    public Empresa() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public List<Cuenta> filtrarCuentasPorPeriodo(Periodo unPeriodo) {
        return this.cuentas.stream().filter(cuenta -> cuenta.esDe(unPeriodo)).collect(Collectors.toList());
    }

    public List<Cuenta> filtrarCuentasPorNombre(String nombre) {
        return cuentas.stream().filter(cuenta -> cuenta.getTipo().equals(nombre)).collect(Collectors.toList());
    }

    public Double obtenerResultadoDeCuenta(String nombre, Periodo periodo) {
        List<Cuenta> cuentasFiltradas = filtrarCuentasPorNombre(nombre).stream()
                .filter(cuenta -> cuenta.esDe(periodo))
                .collect(Collectors.toList());
        if (cuentasFiltradas.isEmpty()) {
            throw new FiltrarPorNombreException("la cuenta " + nombre + " para el periodo " + periodo + " de la empresa "+ this.nombre);
        } else if (cuentasFiltradas.size() > 1) {
            throw new FiltrarPorPeriodoException(
                    "Se encontro mas de 1 valor para la cuenta " + nombre + " en el periodo " + periodo + " de la empresa "+ this.nombre);
        }
        return new Double(cuentasFiltradas.get(0).getResultado());
    }

    @Override
    public String toString() {
        return getNombre();
    }

}
