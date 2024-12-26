
package miPaquete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class menuMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaPU");
        EntityManager em = emf.createEntityManager();

//    servicios
        getionLibro libroService = new getionLibro(em);
        gestionUsuario usuarioService = new gestionUsuario(em);
        gestionEjemplares ejemplarService = new gestionEjemplares(em);
        gestionPrestamo prestamoService = new gestionPrestamo(em);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            // Mostrar menú
            System.out.println("===== Menú Biblioteca =====");
            System.out.println("1. Registrar un libro");
            System.out.println("2. Registrar un usuario");
            System.out.println("3. Registrar un ejemplar");
            System.out.println("4. Registrar un préstamo");
            System.out.println("5. Registrar una devolución");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Introduce el ISBN del libro: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Introduce el título del libro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Introduce el autor del libro: ");
                    String autor = scanner.nextLine();
                    libroService.registrarLibro(isbn, titulo, autor);
                    System.out.println("Libro registrado exitosamente.");
                    break;

                case 2:
                    System.out.print("Introduce el DNI del usuario: ");
                    String dni = scanner.nextLine();
                    System.out.print("Introduce el nombre del usuario: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Introduce el email del usuario: ");
                    String email = scanner.nextLine();
                    System.out.print("Introduce la contraseña del usuario: ");
                    String password = scanner.nextLine();
                    System.out.print("Introduce el tipo de usuario (normal/administrador): ");
                    String tipo = scanner.nextLine();
                    usuarioService.registrar(dni, nombre, email, password, tipo);
                    System.out.println("Usuario registrado exitosamente.");
                    break;

                case 3:
                    System.out.print("Introduce el ISBN del libro para el ejemplar: ");
                    isbn = scanner.nextLine();
                    Libro libro = em.find(Libro.class, isbn);
                    System.out.print("Introduce el estado del ejemplar (Disponible/Prestado/Dañado): ");
                    String estado = scanner.nextLine();
                    ejemplarService.registrarEjemplar(libro, estado);
                    System.out.println("Ejemplar registrado exitosamente.");
                    break;

                case 4:
                    System.out.print("Introduce el DNI del usuario: ");
                    dni = scanner.nextLine();
                    System.out.print("Introduce el ID del ejemplar: ");
                    Long idEjemplar = scanner.nextLong();
                    scanner.nextLine(); // limpiamos bufer
                    Usuario usuario = em.find(Usuario.class, dni);
                    Ejemplar ejemplar = em.find(Ejemplar.class, idEjemplar);
                    prestamoService.registrarPrestamo(usuario, ejemplar);
                    System.out.println("Préstamo registrado exitosamente.");
                    break;

                case 5:
                    System.out.print("Introduce el ID del préstamo: ");
                    Long idPrestamo = scanner.nextLong();
                    scanner.nextLine();

                    Prestamo prestamo = em.find(Prestamo.class, idPrestamo);

                    prestamoService.registrarDevolucion(prestamo);
                    System.out.println("Devolución registrada exitosamente.");
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 0);

        em.close();
        emf.close();
        scanner.close();
    }
}
