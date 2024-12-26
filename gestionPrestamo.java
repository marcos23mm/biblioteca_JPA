package miPaquete;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class gestionPrestamo {
    private DAO_Generico<Prestamo> prestamoDAO;

    public gestionPrestamo(EntityManager entity) {
        this.prestamoDAO = new DAO_Generico<>(entity);
    }

    public void registrarPrestamo(Usuario usuario, Ejemplar ejemplar) {
        if(usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(LocalDate.now())) {
            throw new IllegalStateException("El usuario tiene una penalización activa.");
        }
        if(!"Disponible". equalsIgnoreCase(ejemplar.getEstado())){
            throw new IllegalStateException("Ejemplar no disponible.");
        }
        if(usuario.getPrestamos().stream().filter(prestamo -> prestamo.getFechaDevolucion() == null).count() > 3) {
            throw new IllegalStateException("El usuario ya tiene 3 préstamos activos.");
        }
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setEjemplar(ejemplar);
        prestamo.setFechaInicio(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(15));
        ejemplar.setEstado("Prestado");

        prestamoDAO.save(prestamo);
        prestamoDAO.update(prestamo);
    }
    public void registrarDevolucion(Prestamo prestamo) {
        prestamo.setFechaDevolucion(LocalDate.now());
        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setEstado("Disponible");

        prestamoDAO.update(prestamo);
    }
}
