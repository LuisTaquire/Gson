package dao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Persona;

public class PersonaImpl extends Conexion implements ICRUD<Persona> {
// IDACTA LIBACTA  FECREGACTA  OBSACTA   ESTACTA  IDPER  IDINT

    @Override
    public void registrar(Persona per) throws Exception {
        String sql = "insert into PERSONA "
                + "(NOMPER,APEPATPER, APEMATPER, SEXPER, DNIPER,CODUBI) values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getNOMPER());
            ps.setString(2, per.getAPEPATPER());
            ps.setString(3, per.getAPEMATPER());
            ps.setString(4, per.getSEXPER());
            ps.setString(5, per.getDNIPER());
            ps.setString(6, per.getCODUBI());


            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registrarPacDao" + e.getMessage());
        } finally {
            this.cerrarCnx();
        }
    }

    @Override
    public void modificar(Persona per) throws Exception {
//        + "(NOMPER,APEPATPER, APEMATERPER, GENPER, DNIPER, FECNACPER,  DIRPER, NACPER, CELPER,"
//                + " ESTPER, CODUBI, ESTCIVPER , IDPER ) values(?,?,?,?,?,?,?,?,?,?,?,?)";

        String sql = "update PERSONA set "
                + "NOMPER=?,APEPATPER=?, APEMATPER=?, SEXPER=?, DNIPER=?, CODUBI=? where IDPER=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getNOMPER());
            ps.setString(2, per.getAPEPATPER());
            ps.setString(3, per.getAPEMATPER());
            ps.setString(4, per.getSEXPER());
            ps.setString(5, per.getDNIPER());
            ps.setString(6, per.getCODUBI());
            ps.setInt(7, per.getIDPER());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en modificar Persona Dao" + e.getMessage());
        } finally {
            this.cerrarCnx();
        }
    }

    @Override
    public void eliminar(Persona per) throws Exception {
        String sql = "delete from PERSONA where IDPER=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
       
            ps.setInt(1, per.getIDPER());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminar Persona Dao" + e.getMessage());
        } finally {
            this.cerrarCnx();
        }
    }

    public void buscardni(Persona per) throws Exception {
        String leerdni = per.getDNIPER();
        String enlace = "https://dniruc.apisperu.com/api/v1/dni/"+ leerdni +"?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                JsonObject rootobj = root.getAsJsonObject();
                String apellido_paterno = rootobj.get("apellidoPaterno").getAsString();
                String apellido_materno = rootobj.get("apellidoMaterno").getAsString();
                String nombres = rootobj.get("nombres").getAsString();

                System.out.println("Resultado\n");
                System.out.println(apellido_paterno + "\n" + apellido_materno + "\n" + nombres + "\n");

                per.setNOMPER(nombres);
                per.setAPEPATPER(apellido_paterno);
                per.setAPEMATPER(apellido_materno);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Busqueda", "DNI no encontrado"));
        }
    }

    @Override
    public List<Persona> listar() throws Exception {

        List<Persona> listado = new ArrayList<>();
        Persona per;
        String sql = "select a.IDPER, a.NOMPER, a.APEPATPER, a.APEMATPER, a.SEXPER, a.DNIPER, p.DISUBI, p.CODUBI from dbo.PERSONA a\n"
                + "INNER JOIN UBIGEO p\n"
                + "ON a.CODUBI=p.CODUBI;";
        try {
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                per = new Persona();
                per.setIDPER(rs.getInt("IDPER"));
                per.setNOMPER(rs.getString("NOMPER"));
                per.setAPEPATPER(rs.getString("APEPATPER"));
                per.setAPEMATPER(rs.getString("APEMATPER"));
                per.setSEXPER(rs.getString("SEXPER"));
                per.setDNIPER(rs.getString("DNIPER"));
                per.setCODUBI(rs.getString("CODUBI"));
                per.setDISUBI(rs.getString("DISUBI"));
                listado.add(per);
            }

            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en la listado Persona Dao " + e.getMessage());
        } finally {
            this.cerrarCnx();
        }
        return listado;
    }



    public List<String> autocompleteUbigeo(String consulta) throws SQLException, Exception {
        List<String> lista = new ArrayList<>();
        String sql = "select top 10 concat(DISUBI, ', ', PROUBI, ', ',DPTUBI) AS UBIGEODESC from UBIGEO WHERE DISUBI LIKE ?";
        try {
            PreparedStatement ps = this.conectar().prepareCall(sql);
            ps.setString(1, "%" + consulta + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("UBIGEODESC"));
            }
        } catch (Exception e) {
            System.out.println("Error en autocompletado Ubigeo Dao" + e.getMessage());
        }
        return lista;
    }

    public String obtenerCodigoUbigeo(String cadenaUbi) throws SQLException, Exception {
        String sql = "select CODUBI FROM UBIGEO WHERE concat(DISUBI, ', ', PROUBI, ', ',DPTUBI) = ?";
        try {
            PreparedStatement ps = this.conectar().prepareCall(sql);
            ps.setString(1, cadenaUbi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("CODUBI");
            }
            return rs.getString("CODUBI");
        } catch (Exception e) {
            System.out.println("Error en obtenerCodigoUbigeo PER" + e.getMessage());
            throw e;
        }
    }

    //SET GET
}
