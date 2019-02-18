import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class EndangeredAnimalTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void EndangeredAnimal_instantiatesCorrectly_true() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    assertEquals(true, testAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void EndangeredAnimal_instantiatesWithName_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    assertEquals("Bear", testEndangeredAnimal.getName());
  }

  @Test
  public void EndangeredAnimal_instantiatesWithAge_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    assertEquals("young", testEndangeredAnimal.getAge());
  }

  @Test
  public void EndangeredAnimal_instantiatesWithHealth_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    assertEquals("healthy", testEndangeredAnimal.getHealth());
  }

  @Test
  public void save_successfullyAddsEndangeredAnimalToDatabase_List() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    testEndangeredAnimal.save();
    assertTrue(EndangeredAnimal.all().get(0).equals(testEndangeredAnimal));
  }

  @Test
  public void equals_returnsTrueIfAnimalNamesAreSame_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    EndangeredAnimal anotherEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    assertTrue(testEndangeredAnimal.equals(anotherEndangeredAnimal));
  }

  @Test
  public void save_assignsIdToEndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    testEndangeredAnimal.save();
    EndangeredAnimal savedEndangeredAnimal = EndangeredAnimal.all().get(0);
    assertEquals(savedEndangeredAnimal.getId(), testEndangeredAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Lion", "adult", "healthy","mammal");
    secondEndangeredAnimal.save();
    assertEquals(true, EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
    assertEquals(true, EndangeredAnimal.all().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void find_returnsEndangeredAnimalWithSameId_secondEndangeredAnimal() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Lion", "adult", "healthy","mammal");
    secondEndangeredAnimal.save();
    assertEquals(EndangeredAnimal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
  }

  @Test
  public void delete_deletesEntryInDatabase_0(){
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    testEndangeredAnimal.save();
    testEndangeredAnimal.delete();
    assertEquals(0, EndangeredAnimal.all().size());
  }

  @Test
  public void getSightings_returnsAllSightings_int(){
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy","mammal");
    testEndangeredAnimal.save();
    Sighting testSighting = new Sighting("Zone A", "Dave");
    testSighting.save();
    testSighting.addAnimal(testEndangeredAnimal);
    List savedSightings = testEndangeredAnimal.getSightings();
    assertEquals(1, savedSightings.size());
    assertTrue(savedSightings.contains(testSighting));
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void find_throwsExceptionIfAnimalNotFound() {
    EndangeredAnimal.find(1);
  }

}
