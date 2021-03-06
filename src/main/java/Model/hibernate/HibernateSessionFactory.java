package Model.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class HibernateSessionFactory {
    // nombre y ubicacion del archivo de confi guracion
    public static String CONFIG_FILE = "src/main/java/Model/hibernate/hibernate.cfg.xml";
    private static SessionFactory sessionFactory = null;
    private static Session session = null;


    public static Session getSession() throws URISyntaxException, SQLException {

        //Verifico si no se inicio secion con un sessionFactoru y session no esta abierto
        if( sessionFactory==null || !session.isOpen() )
        {
            //Me fijo si existe la variable del sistema sino inicio de forma local
            if(System.getenv("JAWSDB_URL") != null){
                sessionFactory = configurarSesionJAWSDB();
            }else{
                sessionFactory = configurarSesionLocal();
            }
            session = sessionFactory.openSession();
        }
        return session;
    }

    //Metodos para configurar la base de datos
    private static SessionFactory configurarSesionJAWSDB() throws URISyntaxException, SQLException{
        Configuration conf = new Configuration();
        File f = new File(CONFIG_FILE);
        conf.configure(f);
        URI jdbUri = new URI(System.getenv("JAWSDB_URL"));

        String port = String.valueOf(jdbUri.getPort());
        System.out.println("URL DE LA BASE DE DATOS: "+"jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath());
        conf.setProperty("hibernate.connection.url", "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath());
        System.out.println("USUARIO: "+jdbUri.getUserInfo().split(":")[0]);
        conf.setProperty("hibernate.connection.username", jdbUri.getUserInfo().split(":")[0]);
        System.out.println("CONTRASENIA: "+ jdbUri.getUserInfo().split(":")[1]);
        conf.setProperty("hibernate.connection.password", jdbUri.getUserInfo().split(":")[1]);
        System.out.println(jdbUri);
        return conf.buildSessionFactory();
    }

    private static SessionFactory configurarSesionLocal(){
        Configuration conf = new Configuration();
        File f = new File(CONFIG_FILE);
        conf.configure(f);
        conf.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/database_queMePongo?serverTimezone=UTC&useSSL=false");
        conf.setProperty("hibernate.connection.username", "root");
        conf.setProperty("hibernate.connection.password", "2522");

        return conf.buildSessionFactory();
    }

}
