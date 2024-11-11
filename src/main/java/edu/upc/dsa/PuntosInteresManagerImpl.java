package edu.upc.dsa;

import edu.upc.dsa.exceptions.PuntoNoExistenteException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;

import java.util.*;

public class PuntosInteresManagerImpl implements PuntosInteresManager {
    private static PuntosInteresManager instance;

    final static Logger logger = Logger.getLogger(PuntosInteresManagerImpl.class);

    public static PuntosInteresManager getInstance() {
        if (instance==null) instance = new PuntosInteresManagerImpl();
        return instance;
    }

    private List<PuntoInteres> puntosMapa;
    private HashMap<String, Usuario> usuarioHashMap;

    private PuntosInteresManagerImpl() {
        this.puntosMapa=new LinkedList<>();
        this.usuarioHashMap=new HashMap<>();
    }

    @Override
    public Usuario nuevoUsuario(String id, String nombre, String apellidos, String correo, String nacimiento) {
        Usuario u=new Usuario(id,nombre,apellidos,correo,nacimiento);
        usuarioHashMap.put(id,u);
        logger.info("Nuevo Usuario: "+u.toString());
        return u;
    }

    @Override
    public List<Usuario> listaUsuarios() {
        List<Usuario> valueList=new ArrayList<>(usuarioHashMap.values());
        List<Usuario> usuariosList=new ArrayList<>();
        //SOLO QUEREMOS MOSTRAR NOMBRE Y APELLIDO Y ID
        for(Usuario u: valueList){
            Usuario usuarioMostrar=new Usuario();
            usuarioMostrar.setNombre(u.getNombre());
            usuarioMostrar.setApellidos(u.getApellidos());
            usuarioMostrar.setId(u.getId());
            usuariosList.add(usuarioMostrar);
        }
        Collections.sort(usuariosList,Comparator.comparing(Usuario::getApellidos).thenComparing(Usuario::getNombre));
        logger.info("Usuarios Size: "+usuariosList.size());
        return usuariosList;
    }

    @Override
    public Usuario infoUsuario(String id) throws UsuarioNotFoundException {
        Usuario u= this.usuarioHashMap.get(id);
        if(u==null){
            logger.warn("Usuario No Encontrado");
            throw new UsuarioNotFoundException("User Not Found");
        }
        else{
            logger.info("Usuario: "+u.toString());
            return u;
        }
    }

    @Override
    public PuntoInteres nuevoPuntoInteres(int horizontal, int vertical, ElementType tipo) {
        PuntoInteres p = new PuntoInteres(horizontal,vertical,tipo);
        this.puntosMapa.add(p);
        logger.info("Nuevo Punto: "+p.toString());
        logger.info("Puntos: "+sizePuntos());
        return p;
    }

    @Override
    public void registrarPunto(String idUsuario, int horizontal, int vertical) throws PuntoNoExistenteException, UsuarioNotFoundException {
        Usuario u= this.usuarioHashMap.get(idUsuario);
        boolean found=false;
        if(u==null){
            logger.warn("Usuario No Encontrado");
            throw new UsuarioNotFoundException("User Not Found");
        }
        for(PuntoInteres puntoMapa:this.puntosMapa){
            if(puntoMapa.getHorizontal()==horizontal && puntoMapa.getVertical()==vertical){
                logger.info("Punto Encontrado");
                found=true;
                u.addPunto(puntoMapa);
                logger.info("Punto Añadido a "+u.toString());
                break;
            }
        }
        if(!found){
            logger.warn("Punto No Encontrado");
            throw new PuntoNoExistenteException("Punto No Encontrado");
        }
    }

    @Override
    public List<PuntoInteres> getPuntosUsuario(String idUsuario) throws UsuarioNotFoundException {
        Usuario u= this.usuarioHashMap.get(idUsuario);
        if(u==null){
            logger.warn("Usuario No Encontrado");
            throw new UsuarioNotFoundException("User Not Found");
        }
        logger.info("Puntos Interes Usuario: "+u.getPuntosInteres().size());
        return u.getPuntosInteres();
    }

    @Override
    public List<Usuario> getUsuariosPorPunto(int horizontal, int vertical) throws PuntoNoExistenteException {
        PuntoInteres punto=null;
        for(PuntoInteres puntoMapa:this.puntosMapa){
            if(puntoMapa.getHorizontal()==horizontal && puntoMapa.getVertical()==vertical){
                logger.info("Punto Encontrado");
                punto=puntoMapa;
                break;
            }
        }
        if(punto==null){
            logger.warn("Punto No Encontrado");
            throw new PuntoNoExistenteException("Punto No Encontrado");
        }
        List<Usuario> usuariosPunto=new LinkedList<>();
        for(Usuario u: this.usuarioHashMap.values()){
            if(u.getPuntosInteres().contains(punto)){
                //Solo quiero que se muestre nombre, apellido y id
                Usuario usuarioMostrar=new Usuario();
                usuarioMostrar.setApellidos(u.getApellidos());
                usuarioMostrar.setNombre(u.getNombre());
                usuarioMostrar.setId(u.getId());
                usuariosPunto.add(usuarioMostrar);
            }
        }
        logger.info("Usuarios Por Punto: "+usuariosPunto.size());
        return usuariosPunto;
    }

    @Override
    public List<PuntoInteres> getPuntosPorTipo(ElementType tipo) {
        List<PuntoInteres> puntosList=new LinkedList<>();
        for(PuntoInteres punto:this.puntosMapa){
            if(punto.getTipo().equals(tipo)){
                puntosList.add(punto);
            }
        }
        logger.info("Puntos Encontrados: "+puntosList.size());
        return puntosList;
    }

    @Override
    public int sizePuntos() {
        return this.puntosMapa.size();
    }

    @Override
    public int sizeUsuarios() {
        return this.usuarioHashMap.size();
    }

    @Override
    public void clear() {
        this.usuarioHashMap.clear();
        this.puntosMapa.clear();
    }
}
