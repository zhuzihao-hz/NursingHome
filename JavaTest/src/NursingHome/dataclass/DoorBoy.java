package NursingHome.dataclass;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DoorBoy extends WorkerBase {
    private StringProperty workPlace;

    public DoorBoy(){
        super();
        this.workPlace=new SimpleStringProperty("");
    }

    public String getDoorBoyInfo(){
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getDate()+"','"+this.getSalary()+"','"+this.getWorkPlace()+"')";
        return temp;
    }

    public String getWorkPlace() { return workPlace.get(); }
    public void setWorkPlace(String workPlace) { this.workPlace.set(workPlace); }
    public StringProperty workPlaceProperty() { return workPlace; }
}
