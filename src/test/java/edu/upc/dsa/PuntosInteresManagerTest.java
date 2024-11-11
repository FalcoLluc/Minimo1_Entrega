package edu.upc.dsa;

import edu.upc.dsa.exceptions.PuntoNoExistenteException;
import edu.upc.dsa.exceptions.UsuarioNotFoundException;
import edu.upc.dsa.models.ElementType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;

public class PuntosInteresManagerTest {
    PuntosInteresManager pm;

    @Before
    public void setUp() {
        this.pm = PuntosInteresManagerImpl.getInstance();
        this.pm.nuevoUsuario("1","Lluc","Fernández","llff2003ae@gmail.com","25/11/2003");
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
        this.pm.registrarPunto("1",10,10);
        this.pm.registrarPunto("1",40,20);
        Assert.assertEquals(2,this.pm.getPuntosUsuario("1").size());
    }

    @Test
    public void saltaError(){
        Assert.assertThrows(PuntoNoExistenteException.class,()->this.pm.registrarPunto("1",50,50));
        Assert.assertThrows(UsuarioNotFoundException.class,()->this.pm.registrarPunto("5",10,10));
    }

    @Test
    public void listaUsuariosPunto(){
        this.pm.registrarPunto("1",10,10);
        Assert.assertEquals(this.pm.getUsuariosPorPunto(10,10).size(),1);
    }
    @Test
    public void listaPuntosTipo(){
        this.pm.nuevoPuntoInteres(23,45,ElementType.BRIDGE);
        Assert.assertEquals(this.pm.getPuntosPorTipo(ElementType.BRIDGE).size(),2);
    }
}
