package NursingHome.dataclass;

import javafx.beans.property.*;

public class Bed {
    private StringProperty id;
    private StringProperty roomID;
    private StringProperty rank;
    private StringProperty isPeople;

    public Bed(StringProperty id, StringProperty roomID, StringProperty rank, StringProperty isPeople) {
        this.id = id;
        this.roomID = roomID;
        this.rank = rank;
        this.isPeople = isPeople;
    }

    public Bed() {
        this.id=new SimpleStringProperty("");
        this.roomID=new SimpleStringProperty("");
        this.rank=new SimpleStringProperty("");
        this.isPeople=new SimpleStringProperty("");
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public String getRoomID() { return roomID.get(); }
    public StringProperty roomIDProperty() { return roomID; }
    public void setRoomID(String roomID) { this.roomID.set(roomID); }

    public String getRank() { return rank.get(); }
    public StringProperty rankProperty() { return rank; }
    public void setRank(String rank) { this.rank.set(rank); }

    public String getIsPeople() { return isPeople.get(); }
    public StringProperty isPeopleProperty() { return isPeople; }
    public void setIsPeople(String isPeople) { this.isPeople.set(isPeople); }

    public String getBedInfo() {
        String temp;
        temp="('"+this.getId()+"','"+this.getRoomID()+"','"+this.getIsPeople()+"','"+this.getRank()+"')";
        return temp;
    }
}
