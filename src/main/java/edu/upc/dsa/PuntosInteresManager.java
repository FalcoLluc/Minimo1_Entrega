package edu.upc.dsa;

import edu.upc.dsa.exceptions.ElementTypeNoValidoException;
import edu.upc.dsa.exceptions.IdUsadoException;
import edu.upc.dsa.exceptions.PuntoNoExistenteException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.Usuario;

import java.time.LocalDate;
import java.util.List;

public interface PuntosInteresManager {
    public Usuario nuevoUsuario(String id, String nombre, String apellidos, String correo, LocalDate nacimiento) throws IdUsadoException;
    public List<Usuario> listaUsuarios();
    public Usuario infoUsuario(String id) throws UsuarioNotFoundException;
    public PuntoInteres nuevoPuntoInteres(int horizontal, int vertical, ElementType tipo) throws ElementTypeNoValidoException;
    public void registrarPunto(String idUsuario,int horizontal,int vertical) throws PuntoNoExistenteException,UsuarioNotFoundException;
    public List<PuntoInteres> getPuntosUsuario(String idUsuario) throws UsuarioNotFoundException;
    public List<Usuario> getUsuariosPorPunto(int horizontal,int vertical)throws PuntoNoExistenteException;
    public List<PuntoInteres> getPuntosPorTipo(ElementType tipo);

    public int sizePuntos();
    public int sizeUsuarios();

    public void clear();
}
