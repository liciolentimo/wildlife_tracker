import java.util.List;
import org.sql2o.*;

public class EndangeredAnimal extends Animal implements AnimalInterface{

    private String age;
    private String health;

    public static final String HEALTH_HEALTHY = "healthy";
    public static final String HEALTH_ILL = "ill";
    public static final String HEALTH_OK = "okay";

    public static final String AGE_NEW = "newborn";
    public static final String AGE_YOUNG = "young";
    public static final String AGE_ADULT = "adult";

  

    public EndangeredAnimal(String name, String age, String health){
        this.name = name;
        this.age = age;
        this.health = health;
        endangered = true;
    }


    public String getAge(){
        return age;
    }

    public String getHealth(){
        return health;
    }
    
   
}