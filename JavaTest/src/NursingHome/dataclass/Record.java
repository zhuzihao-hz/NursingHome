package NursingHome.dataclass;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Record {
    private StringProperty id;
    private StringProperty customerId;
    private StringProperty customerName;
    private StringProperty doctorId;
    private StringProperty date;
    private StringProperty context;

    public Record() {
        this.id=new SimpleStringProperty("");
        this.customerId=new SimpleStringProperty("");
        this.customerName=new SimpleStringProperty("");
        this.doctorId=new SimpleStringProperty("");
        this.date=new SimpleStringProperty("");
        this.context=new SimpleStringProperty("");
    }

    public Record(StringProperty id, StringProperty customerId, StringProperty customerName, StringProperty doctorId, StringProperty date, StringProperty context) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.doctorId = doctorId;
        this.date = date;
        this.context = context;
    }

    public String getRecordInfo(){
        String temp;
        temp="('"+this.getId()+"','"+this.getCustomerId()+"','"+this.getCustomerName()+"','"+this.getDoctorId()+"','"+this.getDate()+"','"+this.getContext()+"')";
        return temp;
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public String getCustomerId() { return customerId.get(); }
    public StringProperty customerIdProperty() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId.set(customerId); }

    public String getCustomerName() { return customerName.get(); }
    public StringProperty customerNameProperty() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName.set(customerName); }

    public String getDoctorId() { return doctorId.get(); }
    public StringProperty doctorIdProperty() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId.set(doctorId); }

    public String getDate() { return date.get(); }
    public StringProperty dateProperty() { return date; }
    public void setDate(String date) { this.date.set(date); }

    public String getContext() { return context.get(); }
    public StringProperty contextProperty() { return context; }
    public void setContext(String context) { this.context.set(context); }
}
