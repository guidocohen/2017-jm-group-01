package modelo.metodologias;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import modelo.Empresa;
import modelo.Periodo;

import modelo.condiciones.Condicion;

@Entity
@Table(name = "METODOLOGIA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Metodologia {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "metodologia_id")
    private Set<Condicion> condiciones;

    @Column(name = "nombre")
    private String nombre;

    public Metodologia(String nombre, Condicion... condiciones) {
        this.nombre = nombre;
        this.condiciones = new HashSet<>(Arrays.asList(condiciones));
    }
    
    protected Metodologia(){ }

    public Long getId() {
        return id;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    private void setCondiciones(Set<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public Set<Condicion> getCondiciones() {
        return condiciones;
    }

    public List<RankingEmpresa> evaluar(List<Empresa> empresas, Periodo periodo) {
        List<RankingEmpresa> rankings = RankingEmpresa.fromEmpresas(empresas);
        condiciones.forEach(condicion -> condicion.evaluar(rankings, periodo));
        List<RankingEmpresa> ordenadas = rankings.stream()
                .filter(RankingEmpresa::getEsConvenienteInvertir)
                .sorted(
                        Comparator.comparing(RankingEmpresa::getSumadorDePosicion)
                                .thenComparing(RankingEmpresa::getCantCondicionesOk)
                                .thenComparing((r1,r2)-> r1.getEmpresa().getNombre().compareTo(r2.getEmpresa().getNombre()))
                )
                .collect(Collectors.toList());
        return ordenadas;
    }

}
