package NursingHome.dataclass;

import javafx.beans.property.*;

public class Customer {
    private StringProperty customerId;
    private StringProperty customerName;
    private IntegerProperty customerAge;
    private StringProperty customerEnterTime;
    private IntegerProperty customerRoomID;
    private IntegerProperty customerBedID;
    private StringProperty customerPhone;
    private StringProperty customerCareWorker;
    private IntegerProperty customerCareType;
    private StringProperty customerRelationName;
    private StringProperty customerRelation;
    private StringProperty customerRelationPhone;

    public Customer(StringProperty customerId, StringProperty customerName, IntegerProperty customerAge, StringProperty customerEnterTime, IntegerProperty customerRoomID, IntegerProperty customerBedID, StringProperty customerPhone, StringProperty customerCareWorker, IntegerProperty customerCareType, StringProperty customerRelationName, StringProperty customerRelation, StringProperty customerRelationPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.customerEnterTime = customerEnterTime;
        this.customerRoomID = customerRoomID;
        this.customerBedID = customerBedID;
        this.customerPhone = customerPhone;
        this.customerCareWorker = customerCareWorker;
        this.customerCareType = customerCareType;
        this.customerRelationName = customerRelationName;
        this.customerRelation = customerRelation;
        this.customerRelationPhone = customerRelationPhone;
    }

    public Customer() {
        this.customerId = new SimpleStringProperty("");
        this.customerName = new SimpleStringProperty("");
        this.customerAge = new SimpleIntegerProperty(0);
        this.customerEnterTime = new SimpleStringProperty("");
        this.customerRoomID = new SimpleIntegerProperty(0);
        this.customerBedID = new SimpleIntegerProperty(0);
        this.customerPhone = new SimpleStringProperty("");
        this.customerCareWorker = new SimpleStringProperty("");
        this.customerCareType = new SimpleIntegerProperty(0);
        this.customerRelationName = new SimpleStringProperty("");
        this.customerRelation = new SimpleStringProperty("");
        this.customerRelationPhone = new SimpleStringProperty("");
    }

    public int getCustomerRoomID() { return customerRoomID.get(); }
    public IntegerProperty customerRoomIDProperty() { return customerRoomID; }

    public int getCustomerBedID() { return customerBedID.get(); }
    public IntegerProperty customerBedIDProperty() { return customerBedID; }

    public int getCustomerCareType() { return customerCareType.get(); }
    public IntegerProperty customerCareTypeProperty() { return customerCareType; }

    public String getCustomerRelationName() { return customerRelationName.get(); }
    public StringProperty customerRelationNameProperty() { return customerRelationName; }

    public String getCustomerId() { return customerId.get(); }
    public StringProperty customerIdProperty() { return customerId; }

    public String getCustomerName() { return customerName.get(); }
    public StringProperty customerNameProperty() { return customerName; }

    public int getCustomerAge() { return customerAge.get(); }
    public IntegerProperty customerAgeProperty() { return customerAge; }

    public String getCustomerEnterTime() { return customerEnterTime.get(); }
    public StringProperty customerEnterTimeProperty() { return customerEnterTime; }

    public String getCustomerPhone() { return customerPhone.get(); }
    public StringProperty customerPhoneProperty() { return customerPhone; }

    public String getCustomerCareWorker() { return customerCareWorker.get(); }
    public StringProperty customerCareWorkerProperty() { return customerCareWorker; }

    public String getCustomerRelation() { return customerRelation.get(); }
    public StringProperty customerRelationProperty() { return customerRelation; }

    public String getCustomerRelationPhone() { return customerRelationPhone.get(); }
    public StringProperty customerRelationPhoneProperty() { return customerRelationPhone; }

    public void setCustomerRoomID(int customerRoomID) { this.customerRoomID.set(customerRoomID); }
    public void setCustomerBedID(int customerBedID) { this.customerBedID.set(customerBedID); }
    public void setCustomerCareType(int customerCareType) { this.customerCareType.set(customerCareType); }
    public void setCustomerRelationName(String customerRelationName) { this.customerRelationName.set(customerRelationName); }
    public void setCustomerName(String customerName) { this.customerName.set(customerName); }
    public void setCustomerAge(int customerAge) { this.customerAge.set(customerAge); }
    public void setCustomerEnterTime(String customerEnterTime) { this.customerEnterTime.set(customerEnterTime); }
    public void setCustomerCareWorker(String customerCareWorker) { this.customerCareWorker.set(customerCareWorker); }
    public void setCustomerId(String customerId) { this.customerId.set(customerId); }
    public void setCustomerPhone(String customerPhone) { this.customerPhone.set(customerPhone); }
    public void setCustomerRelationPhone(String customerRelationPhone) { this.customerRelationPhone.set(customerRelationPhone); }
    public void setCustomerRelation(String customerRelation) { this.customerRelation.set(customerRelation); }

}
