package miPaquete;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;

public class gestionBiblioteca {
    private final DAO_Generico<Prestamo> prestamoDAO;
    private final DAO_Generico<Usuario> usuarioDAO;
    private final DAO_Generico<Ejemplar> ejemplarDAO;

    public gestionBiblioteca(EntityManager entityManager) {
        this.prestamoDAO = new DAO_Generico<>(entityManager);
        this.usuarioDAO = new DAO_Generico<>(entityManager);
        this.ejemplarDAO = new DAO_Generico<>(entityManager);
    }

    public boolean puedeRealizarPrestamo(Usuario usuario) {
        if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(LocalDate.now())) {
            return false;
        }
        long prestamosActivos = usuario.getPrestamos().stream().filter(p -> p.getFechaDevolucion() == null).count();
        return prestamosActivos < 3;
    }

    public void registrarPrestamo(Usuario usuario, Ejemplar ejemplar) {
        if (!puedeRealizarPrestamo(usuario)) {
            throw new IllegalStateException("El usuario no puede realizar más préstamos.");
        }
        if (!"Disponible".equalsIgnoreCase(ejemplar.getEstado())) {
            throw new IllegalStateException("El ejemplar no está disponible.");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaInicio(LocalDate.now());
        prestamo.setFechaDevolucion(null);
        ejemplar.setEstado("Prestado");

        prestamoDAO.save(prestamo);
        ejemplarDAO.update(ejemplar);
    }

    public void registrarDevolucion(Prestamo prestamo) {
        prestamo.setFechaDevolucion(LocalDate.now());
        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setEstado("Disponible");

        prestamoDAO.update(prestamo);
        ejemplarDAO.update(ejemplar);

        if (prestamo.getFechaDevolucion().isAfter(prestamo.getFechaInicio().plusDays(15))) {
            Usuario usuario = prestamo.getUsuario();
            usuario.setPenalizacionHasta(LocalDate.now().plusDays(15));
            usuarioDAO.update(usuario);
        }
    }
}
