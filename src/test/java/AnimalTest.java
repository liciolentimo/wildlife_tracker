import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public  class AnimalTest{

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void Animal_instantiatesCorrectly_true() {
      Animal testAnimal = new Animal("Bear","young","healthy");
      assertEquals(true, testAnimal instanceof Animal);
    }


  @Test
  public void Animal_instantiatesWithName_String() {
    Animal testAnimal = new Animal("Bear","young","healthy");
    assertEquals("Bear", testAnimal.getName());
  }


  @Test
  public void Animal_instantiatesWithAge_String() {
    Animal testAnimal = new Animal("Bear","young","healthy");
    assertEquals("young", testAnimal.getAge());
  }


  @Test
  public void Animal_instantiatesWithHealth_String() {
    Animal testAnimal = new Animal("Bear","young","healthy");
    assertEquals("healthy", testAnimal.getHealth());
  }



}
