import java.text.DateFormat;
import java.util.List;
import org.sql2o.*;

public class Sighting extends Animal implements AnimalInterface{
    private String location;
    private String rangerName;

    public Sighting(String location, String rangerName) {
        this.location = location;
        this.rangerName = rangerName;
    }

    public String getLocation() {
        return location;
    }

    public String getRangerName() {
        return rangerName;
    }

  

    public String getFormattedDate() {
        return DateFormat.getDateTimeInstance().format(spotted);
      }

      @Override
  public boolean equals(Object otherSighting){
    if (!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.id == newSighting.id && this.location.equals(newSighting.location) && this.rangerName.equals(newSighting.rangerName);
    }
  }

  public static List<Sighting> all() {
    String sql = "SELECT * FROM sightings";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Sighting.class);
    }
  }

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (location, rangerName, spotted) VALUES (:location, :rangerName, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
        .addParameter("rangerName", this.rangerName)
        .executeUpdate()
        .getKey();
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings where id=:id";
      Sighting sighting = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
      if(sighting == null){
        throw new IndexOutOfBoundsException("I'm sorry, I think this sighting was not made");
      }
      return sighting;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM sightings WHERE id=:id";
      con.createQuery(sql).addParameter("id", this.id).executeUpdate();
      String joinSql = "DELETE FROM animals_sightings WHERE sighting_id=:id";
      con.createQuery(joinSql).addParameter("id", this.id).executeUpdate();
    }
  }

  public void addAnimal(Animal animal){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO animals_sightings (sighting_id, animal_id) VALUES(:sighting_id, :animal_id)";
      con.createQuery(sql)
        .addParameter("sighting_id", this.id)
        .addParameter("animal_id", animal.getId())
        .executeUpdate();
    }
  }

  public List<NotEndangered> getNotEndangeredAnimals(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT animals.* FROM animals JOIN animals_sightings ON (animals.id = animals_sightings.animal_id) WHERE animals_sightings.sighting_id = :id AND animals.endangered=false";
      return con.createQuery(sql).addParameter("id", this.id).throwOnMappingFailure(false).executeAndFetch(NotEndangered.class);
    }
  }

  public List<EndangeredAnimal> getEndangeredAnimals(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT animals.* FROM animals JOIN animals_sightings ON (animals.id = animals_sightings.animal_id) WHERE animals_sightings.sighting_id = :id AND animals.endangered=true";
      return con.createQuery(sql).addParameter("id", this.id).throwOnMappingFailure(false).executeAndFetch(EndangeredAnimal.class);
    }
  }

  public static List<Sighting> listByDate() {
    String sql = "SELECT * FROM sightings ORDER BY spotted DESC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Sighting.class);
    }
  }


}