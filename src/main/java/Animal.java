public class Animal{
    private String name;
    private String age;
    private String health;
    private int id;

    public Animal(String name, String age, String health){
        this.name = name;
        this.age = age;
        this.health = health;
    }

    public String getName(){
        return name;
    }

    public String getAge(){
        return age;
    }

    public String getHealth(){
        return health;
    }
    
    public int getId(){
        return id;
    }
}