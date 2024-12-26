package miPaquete;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;

public class gestionUsuario {
    private DAO_Generico <Usuario> usuarioDAO;

    public gestionUsuario(EntityManager entity) {
        this.usuarioDAO = new DAO_Generico<>(entity);
    }

    public void registrar(String dni, String nombre, String email, String password, String tipo) {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setTipo(tipo);
        usuarioDAO.save(usuario);
    }

    public void registrarPenalizacion (Usuario usuario, int diasPenalizacion) {
        usuario.setPenalizacionHasta(LocalDate.now().plusDays(diasPenalizacion));
        usuarioDAO.update(usuario);
    }
}
