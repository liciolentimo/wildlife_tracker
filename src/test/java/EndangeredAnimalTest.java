import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class EndangeredAnimalTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void EndangeredAnimal_instantiatesCorrectly_true() {
    EndangeredAnimal testAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    assertEquals(true, testAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void EndangeredAnimal_instantiatesWithName_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    assertEquals("Bear", testEndangeredAnimal.getName());
  }

  @Test
  public void EndangeredAnimal_instantiatesWithAge_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    assertEquals("young", testEndangeredAnimal.getAge());
  }

  @Test
  public void EndangeredAnimal_instantiatesWithHealth_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    assertEquals("healthy", testEndangeredAnimal.getHealth());
  }

  @Test
  public void save_successfullyAddsEndangeredAnimalToDatabase_List() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    testEndangeredAnimal.save();
    assertTrue(EndangeredAnimal.all().get(0).equals(testEndangeredAnimal));
  }

  @Test
  public void equals_returnsTrueIfAnimalNamesAreSame_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    EndangeredAnimal anotherEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    assertTrue(testEndangeredAnimal.equals(anotherEndangeredAnimal));
  }

  @Test
  public void save_assignsIdToEndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    testEndangeredAnimal.save();
    EndangeredAnimal savedEndangeredAnimal = EndangeredAnimal.all().get(0);
    assertEquals(savedEndangeredAnimal.getId(), testEndangeredAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Lion", "adult", "healthy");
    secondEndangeredAnimal.save();
    assertEquals(true, EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
    assertEquals(true, EndangeredAnimal.all().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void find_returnsEndangeredAnimalWithSameId_secondEndangeredAnimal() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Bear", "young", "healthy");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Lion", "adult", "healthy");
    secondEndangeredAnimal.save();
    assertEquals(EndangeredAnimal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
  }

  @Test
  public void delete_deletesEndangeredAnimal_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("eagle", "newborn", "healthy");
    testEndangeredAnimal.save();
    testEndangeredAnimal.delete();
    assertEquals(null, EndangeredAnimal.find(testEndangeredAnimal.getId()));
  }

}
