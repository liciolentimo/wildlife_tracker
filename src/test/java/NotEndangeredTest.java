import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public  class NotEndangeredTest{

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void NotEndangered_instantiatesCorrectly_true() {
      NotEndangered testAnimal = new NotEndangered("Bear","mammal");
      assertEquals(true, testAnimal instanceof NotEndangered);
    }

    @Test
  public void NotEndangered_instantiatesWithName_String() {
    NotEndangered testAnimal = new NotEndangered("Bear","mammal");
    assertEquals("Bear", testAnimal.getName());
  }

  @Test
  public void NotEndangered_instantiatesWithSpecies_String() {
    NotEndangered testAnimal = new NotEndangered("Bear","mammal");
    assertEquals("mammal", testAnimal.getSpecies());
  }

  @Test
  public void save_successfullyAddsNotEndangeredAnimalToDatabase_List() {
    NotEndangered testNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    testNotEndangeredAnimal.save();
    assertTrue(NotEndangered.all().get(0).equals(testNotEndangeredAnimal));
  }

  @Test
  public void equals_returnsTrueIfAnimalNamesAreSame_true() {
    NotEndangered testNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    NotEndangered anotherNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    assertTrue(testNotEndangeredAnimal.equals(anotherNotEndangeredAnimal));
  }

  @Test
  public void save_assignsIdToNotEndangeredAnimal() {
    NotEndangered testNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    testNotEndangeredAnimal.save();
    NotEndangered savedNotEndangeredAnimal = NotEndangered.all().get(0);
    assertEquals(savedNotEndangeredAnimal.getId(), testNotEndangeredAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfNotEndangeredAnimal_true() {
    NotEndangered firstNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    firstNotEndangeredAnimal.save();
    NotEndangered secondNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    secondNotEndangeredAnimal.save();
    assertEquals(true, NotEndangered.all().get(0).equals(firstNotEndangeredAnimal));
    assertEquals(true, NotEndangered.all().get(1).equals(secondNotEndangeredAnimal));
  }

  @Test
  public void find_returnsNotEndangeredAnimalWithSameId_secondNotEndangeredAnimal() {
    NotEndangered firstNotEndangeredAnimal = new NotEndangered("Bear","mammal");
    firstNotEndangeredAnimal.save();
    NotEndangered secondNotEndangeredAnimal = new NotEndangered("Lion","mammal");
    secondNotEndangeredAnimal.save();
    assertEquals(NotEndangered.find(secondNotEndangeredAnimal.getId()), secondNotEndangeredAnimal);
  }

  @Test
  public void delete_deletesNotEndangeredAnimal_true() {
    NotEndangered testNotEndangeredAnimal = new NotEndangered("Lion","mammal");
    testNotEndangeredAnimal.save();
    testNotEndangeredAnimal.delete();
    assertEquals(null, NotEndangered.find(testNotEndangeredAnimal.getId()));
  }

}
