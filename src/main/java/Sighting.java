public class Sighting {
    private String location;
    private String ranger;
    private int id;

    public Sighting(String location, String ranger) {
        this.location = location;
        this.ranger = ranger;
    }

    public String getLocation() {
        return location;
    }

    public String getRanger() {
        return ranger;
    }

    public int getId() {
        return id;
    }
}