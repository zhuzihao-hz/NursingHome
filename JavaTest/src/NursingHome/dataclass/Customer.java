package NursingHome.dataclass;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Customer {
    private StringProperty customerId;
    private StringProperty customerName;
    private IntegerProperty customerAge;
    private StringProperty customerEnterTime;
    private StringProperty customerPhone;
    private StringProperty customerCareWorker;
    private StringProperty customerRelation;
    private StringProperty customerRelationPhone;

    public Customer(StringProperty customerId, StringProperty customerName, IntegerProperty customerAge, StringProperty customerEnterTime, StringProperty customerPhone, StringProperty customerCareWorker, StringProperty customerRelation, StringProperty customerRelationPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAge = customerAge;
        this.customerEnterTime = customerEnterTime;
        this.customerPhone = customerPhone;
        this.customerCareWorker = customerCareWorker;
        this.customerRelation = customerRelation;
        this.customerRelationPhone = customerRelationPhone;
    }

    public Customer() {
    }


    public String getCustomerId() {
        return customerId.get();
    }

    public StringProperty customerIdProperty() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public int getCustomerAge() {
        return customerAge.get();
    }

    public IntegerProperty customerAgeProperty() {
        return customerAge;
    }

    public void setCustomerAge(int customerAge) {
        this.customerAge.set(customerAge);
    }

    public String getCustomerEnterTime() {
        return customerEnterTime.get();
    }

    public StringProperty customerEnterTimeProperty() {
        return customerEnterTime;
    }

    public void setCustomerEnterTime(String customerEnterTime) {
        this.customerEnterTime.set(customerEnterTime);
    }

    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public StringProperty customerPhoneProperty() {
        return customerPhone;
    }

    public String getCustomerCareWorker() {
        return customerCareWorker.get();
    }

    public StringProperty customerCareWorkerProperty() {
        return customerCareWorker;
    }

    public void setCustomerCareWorker(String customerCareWorker) {
        this.customerCareWorker.set(customerCareWorker);
    }

    public String getCustomerRelation() {
        return customerRelation.get();
    }

    public StringProperty customerRelationProperty() {
        return customerRelation;
    }

    public String getCustomerRelationPhone() {
        return customerRelationPhone.get();
    }

    public StringProperty customerRelationPhoneProperty() {
        return customerRelationPhone;
    }

    public void setCustomerId(String customerId) {
        this.customerId.set(customerId);
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone.set(customerPhone);
    }

    public void setCustomerRelationPhone(String customerRelationPhone) {
        this.customerRelationPhone.set(customerRelationPhone);
    }

    public void setCustomerRelation(String customerRelation) {
        this.customerRelation.set(customerRelation);
    }

}
