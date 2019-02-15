import java.util.List;
import org.sql2o.*;

public abstract class Animal{
    public String name;
    public String age;
    public int id;
    public boolean endangered;
    public String species;

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public boolean isEndangered(){
        return endangered;
    }

    public String getSpecies(){
        return species;
    }

    @Override
    public boolean equals(Object otherAnimal){
      if (!(otherAnimal instanceof Animal)) {
        return false;
      } else {
        Animal newAnimal = (Animal) otherAnimal;
        return this.id == newAnimal.id && this.name.equals(newAnimal.name) &&
          this.species.equals(newAnimal.species);
      }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO animals (name, endangered, species) VALUES (:name, :endangered, :species)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .addParameter("endangered", this.endangered)
            .addParameter("species", this.species)
            .executeUpdate()
            .getKey();
        }
      }

      public List<Sighting> getSightings(){
        try(Connection con = DB.sql2o.open()){
          String sql = "SELECT sightings.* FROM sightings JOIN animals_sightings ON (sightings.id = animals_sightings.sighting_id) WHERE animals_sightings.animal_id = :id";
          return con.createQuery(sql).addParameter("id", this.id).executeAndFetch(Sighting.class);
        }
      }

      public void delete(){
        try(Connection con = DB.sql2o.open()){
          String sql = "DELETE FROM animals WHERE id=:id";
          con.createQuery(sql).addParameter("id", this.id).executeUpdate();
          String joinSql = "DELETE FROM animals_sightings WHERE animal_id=:id";
          con.createQuery(joinSql).addParameter("id", this.id).executeUpdate();
        }
      }
  
}