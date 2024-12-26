package miPaquete;

import jakarta.persistence.EntityManager;

public class gestionEjemplares {
    private DAO_Generico<Ejemplar> ejemplarDAO;

    public gestionEjemplares(EntityManager entity) {
        this.ejemplarDAO = new DAO_Generico<>(entity);
    }

    public void registrarEjemplar(Libro l , String estado) {
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setIsbn(l);
        ejemplar.setEstado(estado);
        ejemplarDAO.save(ejemplar);
    }
}
