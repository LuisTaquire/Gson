package dao;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexion {

    private static Connection cnx = null;

    public static Connection conectar() throws Exception {

        try {

        String user = "sa";
        String pwd = "rut12345";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost;databaseName=dbgson";

            Class.forName(driver).newInstance();
            cnx = DriverManager.getConnection(url, user, pwd);

        } catch (Exception e) {
            System.out.println("Hay un error de conexión SQL SERVER" + e.getMessage());
        }
        return cnx;
    }

    public static void cerrarCnx() throws Exception {
        if (Conexion.cnx != null) {
            cnx.close();
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        Conexion cnx = new Conexion();     
        if (cnx.conectar() != null) {
            System.out.println("Conectado ");
        }else{
            System.out.println("Falla Conectar ");
        } 
    }

    public static Connection getCnx() {
        return cnx;
    }

    public static void setCnx(Connection aCnx) {
        cnx = aCnx;
    }
}
