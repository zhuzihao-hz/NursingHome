package NursingHome.dataclass;

import javafx.beans.property.StringProperty;

public class DoorBoy extends WorkerBase {
    private StringProperty workPlace;

    public String getWorkPlace() { return workPlace.get(); }
    public void setWorkPlace(String workPlace) { this.workPlace.set(workPlace); }
    public StringProperty workPlaceProperty() { return workPlace; }
}
