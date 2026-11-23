package arg.com.utn.donatrack.persistencia;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Persistencia {

  private static EntityManagerFactory factory;

  public static synchronized EntityManagerFactory factory() {
    if (factory == null) {
      Map<String, Object> overrides = new HashMap<>();

      String databaseUrl = System.getenv("DATABASE_URL");
      String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
      String dbUser = System.getenv("DB_USER");
      String dbPassword = System.getenv("DB_PASSWORD");

      if (databaseUrl != null && !databaseUrl.isBlank()) {
        try {
          String cleanUrl = databaseUrl.replace("postgresql://", "http://").replace("postgres://", "http://");
          URI uri = URI.create(cleanUrl);
          if (uri.getUserInfo() != null) {
            String[] userInfo = uri.getUserInfo().split(":");
            overrides.put("hibernate.connection.username", userInfo[0]);
            if (userInfo.length > 1) {
              overrides.put("hibernate.connection.password", userInfo[1]);
            }
          }
          int port = uri.getPort() == -1 ? 5432 : uri.getPort();
          String path = uri.getPath();
          String finalJdbc = "jdbc:postgresql://" + uri.getHost() + ":" + port + path + "?sslmode=require";
          overrides.put("hibernate.connection.url", finalJdbc);
        } catch (Exception e) {
          System.err.println("Error parsing DATABASE_URL: " + e.getMessage());
        }
      } else if (jdbcUrl != null && !jdbcUrl.isBlank()) {
        overrides.put("hibernate.connection.url", jdbcUrl);
        if (dbUser != null) overrides.put("hibernate.connection.username", dbUser);
        if (dbPassword != null) overrides.put("hibernate.connection.password", dbPassword);
      }

      if (overrides.isEmpty()) {
        factory = Persistence.createEntityManagerFactory("postgres-persistence-unit");
      } else {
        factory = Persistence.createEntityManagerFactory("postgres-persistence-unit", overrides);
      }
    }
    return factory;
  }

  public static EntityManager crearEntityManager() {
    return factory().createEntityManager();
  }
}