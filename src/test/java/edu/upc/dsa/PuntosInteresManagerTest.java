package edu.upc.dsa;

import edu.upc.dsa.exceptions.IdUsadoException;
import edu.upc.dsa.exceptions.PuntoNoExistenteException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;
import java.time.LocalDate;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;

public class PuntosInteresManagerTest {
    PuntosInteresManager pm;
    Usuario u1;
    Usuario u2;

    @Before
    public void setUp() {
        this.pm = PuntosInteresManagerImpl.getInstance();
        u1=this.pm.nuevoUsuario("1","Lluc","Fernández","llff2003ae@gmail.com", LocalDate.of(2003, 11, 25));
        u2=this.pm.nuevoUsuario("2","Pepe","Alavarez","pepito@gmail.com", LocalDate.of(2001, 12, 17));
        this.pm.nuevoPuntoInteres(10,10, ElementType.BRIDGE);
        this.pm.nuevoPuntoInteres(40,20, ElementType.COIN);
    }

    @After
    public void tearDown() {
        // És un Singleton
        this.pm.clear();
    }

    @Test
    public void registrarPuntos(){
        this.pm.registrarPunto("1",40,20);
        this.pm.registrarPunto("1",10,10);
        Assert.assertEquals(2,this.pm.getPuntosUsuario("1").size());
        Assert.assertEquals(ElementType.COIN,this.pm.getPuntosUsuario("1").get(0).getTipo());
    }
    @Test
    public void ordenaAlfabeticamente(){
        this.pm.nuevoUsuario("3","Juan","Dominguez","domi@gmail.com", LocalDate.of(2000, 12, 17));
        Assert.assertEquals(this.pm.listaUsuarios().get(0).getId(),"2");
        Assert.assertEquals(this.pm.listaUsuarios().get(1).getId(),"3");
        Assert.assertEquals(this.pm.listaUsuarios().get(2).getId(),"1");
    }

    @Test
    public void saltaError(){
        Assert.assertThrows(PuntoNoExistenteException.class,()->this.pm.registrarPunto("1",50,50));
        Assert.assertThrows(UsuarioNotFoundException.class,()->this.pm.registrarPunto("5",10,10));
        Assert.assertThrows(IdUsadoException.class,()->this.pm.nuevoUsuario("1","Juan","Alvarez","correo",LocalDate.now()));
    }

    @Test
    public void listaUsuariosPunto(){
        Assert.assertEquals(this.pm.getUsuariosPorPunto(10,10).size(),0);
        this.pm.registrarPunto("1",10,10);
        Assert.assertEquals(this.pm.getUsuariosPorPunto(10,10).size(),1);
    }
    @Test
    public void listaPuntosTipo(){
        this.pm.nuevoPuntoInteres(23,45,ElementType.BRIDGE);
        Assert.assertEquals(this.pm.getPuntosPorTipo(ElementType.BRIDGE).size(),2);
    }
}
