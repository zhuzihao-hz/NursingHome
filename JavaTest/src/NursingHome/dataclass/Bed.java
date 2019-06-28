package NursingHome.dataclass;

import javafx.beans.property.*;

public class Bed {
    private StringProperty id;
    private StringProperty roomID;
    private IntegerProperty status;

    public Bed(StringProperty id, StringProperty roomID, IntegerProperty status) {
        this.id = id;
        this.roomID = roomID;
        this.status = status;
    }

    public Bed() {
        this.id=new SimpleStringProperty("");
        this.roomID=new SimpleStringProperty("");
        this.status=new SimpleIntegerProperty(0);
    }

    public boolean equal(Bed bed){
        if (this.getId().equals(bed.getId()) && this.getRoomID().equals(bed.getRoomID())){
            return true;
        }else{
            return false;
        }
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public String getRoomID() { return roomID.get(); }
    public StringProperty roomIDProperty() { return roomID; }
    public void setRoomID(String roomID) { this.roomID.set(roomID); }

    public Integer getStatus(){return status.get();}
    public IntegerProperty statusProperty() { return status; }
    public void setStatus(int status) { this.status.set(status); }

    public String getBedInfo() {
        String temp;
        temp="('"+this.getId()+"','"+this.getRoomID()+"','"+this.getStatus()+"')";
        return temp;
    }
}
