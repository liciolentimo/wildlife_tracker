import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class EndangeredAnimal extends Animal implements AnimalInterface {

  private String age;
  private String health;

  public static final String HEALTH_HEALTHY = "healthy";
  public static final String HEALTH_ILL = "ill";
  public static final String HEALTH_OK = "okay";

  public static final String AGE_NEW = "newborn";
  public static final String AGE_YOUNG = "young";
  public static final String AGE_ADULT = "adult";

  public EndangeredAnimal(String name, String age, String health, String species) {
    this.name = name;
    this.age = age;
    this.health = health;
    this.species = species;
    endangered = true;
  }

  public String getAge() {
    return age;
  }

  public String getHealth() {
    return health;
  }

  public static List<EndangeredAnimal> all() {
    String sql = "SELECT * FROM animals WHERE endangered = 'true'";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(EndangeredAnimal.class);
    }
  }

  public static EndangeredAnimal find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals where id=:id";
      EndangeredAnimal animal = con.createQuery(sql).addParameter("id", id).throwOnMappingFailure(false)
          .executeAndFetchFirst(EndangeredAnimal.class);
      if (animal == null) {
        throw new IndexOutOfBoundsException("Sorry, this animal cannot be found.");
      }
      return animal;
    }
  }

  @Override
  public void save() {
    super.save(); // calls the parent constructor with no arguments
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET health=:health, age=:age WHERE id=:id";
      con.createQuery(sql).addParameter("health", this.health).addParameter("age", this.age).addParameter("id", this.id)
          .executeUpdate();
    }
  }

  public static EndangeredAnimal findAnimalByName(String name) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals where name=:name";
      EndangeredAnimal animal = con.createQuery(sql).addParameter("name", name).throwOnMappingFailure(false)
          .executeAndFetchFirst(EndangeredAnimal.class);
      return animal;
    }
  }

}