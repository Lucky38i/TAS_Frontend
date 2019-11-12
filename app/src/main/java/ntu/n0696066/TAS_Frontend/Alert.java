package ntu.n0696066.TAS_Frontend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Alert {
    private String Vehicle;
    private Integer ID;

    /**
     * Parameterized constructor for Alert Class
     * @param vehicle Vehicle string
     * @param ID ID of Alert
     */
    @JsonCreator
    Alert( @JsonProperty("vehicle") String vehicle, @JsonProperty("ID") int ID) {
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
