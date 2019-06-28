package NursingHome.dataclass;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private StringProperty id;
    private StringProperty rank;
    private IntegerProperty totalBed;
    private IntegerProperty freeBed;

    public Room() {
        this.id=new SimpleStringProperty("");
        this.rank=new SimpleStringProperty("");
        this.totalBed=new SimpleIntegerProperty(0);
        this.freeBed=new SimpleIntegerProperty(0);
    }

    public Room(StringProperty id, StringProperty rank, IntegerProperty totalBed, IntegerProperty freeBed) {
        this.id = id;
        this.rank = rank;
        this.totalBed = totalBed;
        this.freeBed = freeBed;
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public String getRank() { return rank.get(); }
    public StringProperty rankProperty() { return rank; }
    public void setRank(String rank) { this.rank.set(rank); }

    public int getTotalBed() { return totalBed.get(); }
    public IntegerProperty totalBedProperty() { return totalBed; }
    public void setTotalBed(int totalBed) { this.totalBed.set(totalBed); }

    public int getFreeBed() { return freeBed.get(); }
    public IntegerProperty freeBedProperty() { return freeBed; }
    public void setFreeBed(int freeBed) { this.freeBed.set(freeBed); }
}
