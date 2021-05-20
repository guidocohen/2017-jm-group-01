package modelo.repositorios.persistencia;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

/**
 *
 * @author David
 */
public class RepoPersistencia implements WithGlobalEntityManager {

    public void iniciar() {
        entityManager().getTransaction().begin();
    }

    public void confirmar() {
        entityManager().getTransaction().commit();
        entityManager().clear();
    }

    public void revertir() {
        entityManager().getTransaction().rollback();
        entityManager().clear();
    }
}
