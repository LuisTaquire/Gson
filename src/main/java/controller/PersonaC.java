package controller;

import dao.PersonaImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import model.Persona;

@Named(value = "personaC") //@ManagedBean
@SessionScoped

public class PersonaC implements Serializable {

    private PersonaImpl dao;
    private Persona persona;
    private List<Persona> lstPersona;

    public PersonaC() {
        persona = new Persona();
        dao = new PersonaImpl();
    }

    public void Limpiar() {
        persona = new Persona();
    }

    public void registrar() {
        try {
            persona.setCODUBI(dao.obtenerCodigoUbigeo(persona.getCODUBI()));
            dao.registrar(persona);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "PER Registrado con éxito"));
            Limpiar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Registro", "PER no registrado"));
            System.out.println("error en registrarC PER" + e.getMessage());
        }
    }

    public void buscarDNI() {
        try {
            System.out.println("tengo miedo");
            dao.buscardni(persona);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Busqueda", "DNI no encontrado"));
            System.out.println("error en Busqueda DNI" + e.getMessage());
        }
    }

    public void eliminar() {
        try {
            dao.eliminar(persona);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación", "PER Eliminado con éxito"));
            listar();
            Limpiar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "PER no Eliminado"));
            System.out.println("error en eliminarC PER" + e.getMessage());
        }
    }

    public void modificar() {
        try {
            dao.modificar(persona);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación", "PER Modificado con éxito"));
            Limpiar();
            listar();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Modificación", "PER no Modificado"));
            System.out.println("error en modificarC PER" + e.getMessage());
        }
    }

    public void listar() {
        try {
            lstPersona = dao.listar();
            System.out.println("Se listo Correctamente PER");
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "PER no Listar"));
            System.out.println("error en listarC PER " + e.getMessage());
        }
    }

    public List<String> completeTextUbigeo(String query) throws SQLException, Exception {
        PersonaImpl daoUbi = new PersonaImpl();
        return daoUbi.autocompleteUbigeo(query);
    }

    // SET GET
    public PersonaImpl getDao() {
        return dao;
    }

    public void setDao(PersonaImpl dao) {
        this.dao = dao;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Persona> getLstPersona() {
        return lstPersona;
    }

    public void setLstPersona(List<Persona> lstPersona) {
        this.lstPersona = lstPersona;
    }

}
