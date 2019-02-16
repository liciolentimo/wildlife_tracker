import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class NotEndangered extends Animal{
    public NotEndangered(String name, String species){
        this.name = name;
        this.species = species;
        endangered = false;
    }

    public static List<NotEndangered> all() {
        String sql = "SELECT * FROM animals WHERE endangered = 'false'";
        try(Connection con = DB.sql2o.open()) {
          return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(NotEndangered.class);
        }
      }

      public static NotEndangered find(int id) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM animals where id=:id";
          NotEndangered animal = con.createQuery(sql)
            .addParameter("id", id)
            .throwOnMappingFailure(false)
            .executeAndFetchFirst(NotEndangered.class);
          if(animal == null){
            throw new IndexOutOfBoundsException("Sorry, this animal cannot be found.");
          }
          return animal;
        }
      }

      public static NotEndangered findAnimalByName(String name) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM animals where name=:name";
          NotEndangered animal = con.createQuery(sql)
            .addParameter("name", name)
            .throwOnMappingFailure(false)
            .executeAndFetchFirst(NotEndangered.class);
          return animal;
        }
      }
}