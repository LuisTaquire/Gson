package model;

import java.util.Date;
import java.text.SimpleDateFormat;



public class Persona {

    private Integer IDPER;
    private String NOMPER;
    private String APEPATPER;
    private String APEMATPER;
    private String SEXPER;
    private String DNIPER;
    private String CODUBI;

  //PARA VISTAS INNER JOIN 
    private String DISUBI;
    
    
    
    
    //GET SET

    public Integer getIDPER() {
        return IDPER;
    }

    public void setIDPER(Integer IDPER) {
        this.IDPER = IDPER;
    }

    public String getNOMPER() {
        return NOMPER;
    }

    public void setNOMPER(String NOMPER) {
        this.NOMPER = NOMPER;
    }

    public String getAPEPATPER() {
        return APEPATPER;
    }

    public void setAPEPATPER(String APEPATPER) {
        this.APEPATPER = APEPATPER;
    }

    public String getAPEMATPER() {
        return APEMATPER;
    }

    public void setAPEMATPER(String APEMATPER) {
        this.APEMATPER = APEMATPER;
    }

    public String getSEXPER() {
        return SEXPER;
    }

    public void setSEXPER(String SEXPER) {
        this.SEXPER = SEXPER;
    }

    public String getDNIPER() {
        return DNIPER;
    }

    public void setDNIPER(String DNIPER) {
        this.DNIPER = DNIPER;
    }

   

    public String getCODUBI() {
        return CODUBI;
    }

    public void setCODUBI(String CODUBI) {
        this.CODUBI = CODUBI;
    }


    public String getDISUBI() {
        return DISUBI;
    }

    public void setDISUBI(String DISUBI) {
        this.DISUBI = DISUBI;
    }
    
}
