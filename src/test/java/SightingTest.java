import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public  class SightingTest{

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void Sighting_instantiatesCorrectly_true() {
      Sighting testSighting = new Sighting("Zone A","Ben");
      assertEquals(true, testSighting instanceof Sighting);
    }


  @Test
  public void Sighting_instantiatesWithLocation_String() {
    Sighting testSighting = new Sighting("Zone A","Ben");
    assertEquals("Zone A", testSighting.getLocation());
  }


  @Test
  public void Sighting_instantiatesWithRanger_String() {
    Sighting testSighting = new Sighting("Zone A","Ben");
    assertEquals("Ben", testSighting.getRangerName());
  }

  @Test
  public void save_successfullyAddsSightingToDatabase_List() {
    Sighting testSighting = new Sighting("Zone A","Ben");
    testSighting.save();
    assertTrue(Sighting.all().get(0).equals(testSighting));
  }

  @Test
  public void equals_returnsTrueIfSightingsAreSame_true() {
    Sighting testSighting = new Sighting("Zone A","Ben");
    Sighting anotherSighting = new Sighting("Zone A","Ben");
    assertTrue(testSighting.equals(anotherSighting));
  }

  @Test
  public void save_assignsIdToSighting() {
    Sighting testSighting = new Sighting("Zone A","Ben");
    testSighting.save();
    Sighting savedSighting = Sighting.all().get(0);
    assertEquals(savedSighting.getId(), testSighting.getId());
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    Sighting firstSighting = new Sighting("Zone A","Ben");
    firstSighting.save();
    Sighting secondSighting = new Sighting("Zone B","James");
    secondSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(firstSighting));
    assertEquals(true, Sighting.all().get(1).equals(secondSighting));
  }

  @Test
  public void find_returnsSightingWithSameId_secondSighting() {
    Sighting firstSighting = new Sighting("Zone A","Ben");
    firstSighting.save();
    Sighting secondSighting = new Sighting("Zone A","Ben");
    secondSighting.save();
    assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
  }

  // @Test
  public void delete_deletesEntryInDatabase_0(){
    Sighting testSighting = new Sighting("Zone A","Ben");
    testSighting.save();
    testSighting.delete();
    assertEquals(0, Sighting.all().size());
  }

  @Test
  public void addAnimal_addsNotEndangeredAnimalToSighting(){
    Sighting testSighting = new Sighting("Zone A","Ben");
    testSighting.save();
    NotEndangered testNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    testNotEndangeredAnimal.save();
    testSighting.addAnimal(testNotEndangeredAnimal);
    NotEndangered  savedNotEndangeredAnimal = testSighting.getNotEndangeredAnimals().get(0);
    assertTrue(testNotEndangeredAnimal.equals(savedNotEndangeredAnimal));
  }

  @Test
  public void getNotEndangeredAnimals_returnsAllNotEndangeredAnimals_int(){
    Sighting testSighting = new Sighting("Zone A","Ben");
    testSighting.save();
    NotEndangered testNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    testNotEndangeredAnimal.save();
    testSighting.addAnimal(testNotEndangeredAnimal);
    List savedAnimals = testSighting.getNotEndangeredAnimals();
    assertEquals(1, savedAnimals.size());
    assertTrue(savedAnimals.contains(testNotEndangeredAnimal));
  }

  @Test
  public void addAnimal_addsEndangeredAnimalToSighting(){
    Sighting testSighting = new Sighting("Zone A","Ben");
    testSighting.save();
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    testEndangeredAnimal.save();
    testSighting.addAnimal(testEndangeredAnimal);
    EndangeredAnimal  savedEndangeredAnimal = testSighting.getEndangeredAnimals().get(0);
    assertTrue(testEndangeredAnimal.equals(savedEndangeredAnimal));
  }

  @Test
  public void getEndangeredAnimals_returnsAllEndangeredAnimals_int(){
    Sighting testSighting = new Sighting("Zone A","Ben");
    testSighting.save();
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    testEndangeredAnimal.save();
    testSighting.addAnimal(testEndangeredAnimal);
    List savedAnimals = testSighting.getEndangeredAnimals();
    assertEquals(1, savedAnimals.size());
    assertTrue(savedAnimals.contains(testEndangeredAnimal));
  }

  @Test
  public void listByDate_sortsSightingsListByMostRecent_Sighting(){
    Sighting firstSighting = new Sighting("Zone A","Ben");
    firstSighting.save();
    Sighting secondSighting = new Sighting("Zone B", "Joe");
    secondSighting.save();
    assertEquals(secondSighting, Sighting.listByDate().get(0));
  }

}
