public class Alert {
    private String Vehicle;
    private Integer ID;

    Alert(String vehicle, Integer ID) {
        this.Vehicle = vehicle;
        this.ID = ID;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
