package miPaquete;

import jakarta.persistence.EntityManager;

public class getionLibro {
    private DAO_Generico<Libro> libroDAO;

    public getionLibro(EntityManager entity) {
        this.libroDAO = new DAO_Generico<>(entity);
    }

    public void registrarLibro(String isbn, String titulo, String autor) {
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libroDAO.save(libro);

    }

}
