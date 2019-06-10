package NursingHome.dataclass;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

public class Bed {
    private IntegerProperty id;
    private IntegerProperty roomID;
    private IntegerProperty rank;
    private BooleanProperty isPeople;

    public Bed(IntegerProperty id, IntegerProperty roomID, IntegerProperty rank, BooleanProperty isPeople) {
        this.id = id;
        this.roomID = roomID;
        this.rank = rank;
        this.isPeople = isPeople;
    }

    public Bed() {
    }

    public int getId() { return id.get(); }

    public IntegerProperty idProperty() { return id; }

    public void setId(int id) { this.id.set(id); }

    public int getRoomID() { return roomID.get(); }

    public IntegerProperty roomIDProperty() { return roomID; }

    public void setRoomID(int roomID) { this.roomID.set(roomID); }

    public int getRank() { return rank.get(); }

    public IntegerProperty rankProperty() { return rank; }

    public void setRank(int rank) { this.rank.set(rank); }

    public boolean isIsPeople() { return isPeople.get(); }

    public BooleanProperty isPeopleProperty() { return isPeople; }

    public void setIsPeople(boolean isPeople) { this.isPeople.set(isPeople); }
}
