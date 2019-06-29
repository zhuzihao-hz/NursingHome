package NursingHome.dataclass;

import javafx.beans.property.*;

public class Customer {
    private StringProperty id;
    private StringProperty name;
    private IntegerProperty age;
    private StringProperty enterTime;
    private StringProperty roomID;
    private StringProperty bedID;
    private StringProperty phone;
    private StringProperty careWorker;
    private IntegerProperty rank;
    private StringProperty relationName;
    private StringProperty relation;
    private StringProperty relationPhone;

    public Customer(StringProperty id, StringProperty name, IntegerProperty age, StringProperty enterTime, StringProperty roomID, StringProperty bedID, StringProperty phone, StringProperty careWorker, IntegerProperty rank, StringProperty relationName, StringProperty relation, StringProperty relationPhone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.enterTime = enterTime;
        this.roomID = roomID;
        this.bedID = bedID;
        this.phone = phone;
        this.careWorker = careWorker;
        this.rank = rank;
        this.relationName = relationName;
        this.relation = relation;
        this.relationPhone = relationPhone;
    }

    public Customer() {
        this.id = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.age = new SimpleIntegerProperty(0);
        this.enterTime = new SimpleStringProperty("");
        this.roomID = new SimpleStringProperty("");
        this.bedID = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.careWorker = new SimpleStringProperty("");
        this.rank = new SimpleIntegerProperty(0);
        this.relationName = new SimpleStringProperty("");
        this.relation = new SimpleStringProperty("");
        this.relationPhone = new SimpleStringProperty("");
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public int getAge() { return age.get(); }
    public IntegerProperty ageProperty() { return age; }
    public void setAge(int age) { this.age.set(age); }

    public String getEnterTime() { return enterTime.get(); }
    public StringProperty enterTimeProperty() { return enterTime; }
    public void setEnterTime(String enterTime) { this.enterTime.set(enterTime); }

    public String getRoomID() { return roomID.get(); }
    public StringProperty roomIDProperty() { return roomID; }
    public void setRoomID(String roomID) { this.roomID.set(roomID); }

    public String getBedID() { return bedID.get(); }
    public StringProperty bedIDProperty() { return bedID; }
    public void setBedID(String bedID) { this.bedID.set(bedID); }

    public String getPhone() { return phone.get(); }
    public StringProperty phoneProperty() { return phone; }
    public void setPhone(String phone) { this.phone.set(phone); }

    public String getCareWorker() { return careWorker.get(); }
    public StringProperty careWorkerProperty() { return careWorker; }
    public void setCareWorker(String careWorker) { this.careWorker.set(careWorker); }

    public int getRank() { return rank.get(); }
    public IntegerProperty rankProperty() { return rank; }
    public void setRank(int rank) { this.rank.set(rank); }

    public String getRelationName() { return relationName.get(); }
    public StringProperty relationNameProperty() { return relationName; }
    public void setRelationName(String relationName) { this.relationName.set(relationName); }

    public String getRelation() { return relation.get(); }
    public StringProperty relationProperty() { return relation; }
    public void setRelation(String relation) { this.relation.set(relation); }

    public String getRelationPhone() { return relationPhone.get(); }
    public StringProperty relationPhoneProperty() { return relationPhone; }
    public void setRelationPhone(String relationPhone) { this.relationPhone.set(relationPhone); }

    public String getCustomerInfo() {
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getAge()+"','"+this.getEnterTime()+"','"+this.getRoomID()+"','"+this.getBedID()+"','"+this.getPhone()+"','"+this.getCareWorker()+"','"+this.getRank()+"','"+this.getRelationName()+"','"+this.getRelation()+"','"+this.getRelationPhone()+"')";
        return temp;
    }
}
