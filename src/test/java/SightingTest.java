import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

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
    assertEquals("Ben", testSighting.getRanger());
  }



}
