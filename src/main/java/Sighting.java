public class Sighting {
    private String location;
    private String rangerName;
    private int id;

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

    public int getId() {
        return id;
    }
}