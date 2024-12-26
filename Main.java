package miPaquete;

import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaPU");
        EntityManager em = emf.createEntityManager();

        getionLibro libroService = new getionLibro(em);
        gestionUsuario usuarioService = new gestionUsuario(em);
        gestionPrestamo prestamoService = new gestionPrestamo(em);

// registrar libros
        //libroService.registrarLibro("1234567890123", "Java Programming", "John Doe");
        //libroService.registrarLibro("9876543210987", "Database Systems", "Jane Smith");

//// registrar user
//        usuarioService.registrar("12345678C", "Alice", "alice@example.com", "password123", "normal");
//        usuarioService.registrar("87654321D", "Bob", "bob@example.com", "password456", "administrador");

//// registrar ejemplares
//        gestionEjemplares ejemplarService = new gestionEjemplares(em);
//        Libro libro = em.find(Libro.class, "1234567890123");
//        ejemplarService.registrarEjemplar(libro, "Disponible");

//// registrar prestamo
        Usuario usuario = em.find(Usuario.class, 6);
        Ejemplar ejemplar = em.find(Ejemplar.class, 8);
        prestamoService.registrarPrestamo(usuario, ejemplar);

//// registrar devolucion
//        Prestamo prestamo = em.find(Prestamo.class, 3);
//        prestamoService.registrarDevolucion(prestamo);

        em.close();
        emf.close();
    }
}
