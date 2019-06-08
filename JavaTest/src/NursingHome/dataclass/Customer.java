package NursingHome.dataclass;

import java.util.Date;

public class Customer {
    private String customerId;
    private String customerName;
    private Integer customerAge;
    private Date customerEnterTime;
    private String customerPhone;
    private String customerCareWorker;
    private String customerRelation;
    private String customerRelationPhone;

    public Customer(){
        this.customerId="";
        this.customerAge=0;
        this.customerCareWorker="";
        this.customerEnterTime=new Date();
        this.customerName="";
        this.customerPhone="";
        this.customerRelation="";
        this.customerRelationPhone="";
    }

    public Date getCustomerEnterTime() { return customerEnterTime; }
    public Integer getCustomerAge() { return customerAge; }
    public String getCustomerCareWorker() { return customerCareWorker; }
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public String getCustomerRelation() { return customerRelation; }
    public String getCustomerRelationPhone() { return customerRelationPhone; }

    public void setCustomerAge(Integer customerAge) { this.customerAge = customerAge; }
    public void setCustomerCareWorker(String customerCareWorker) { this.customerCareWorker = customerCareWorker; }
    public void setCustomerEnterTime(Date customerEnterTime) { this.customerEnterTime = customerEnterTime; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public void setCustomerRelation(String customerRelation) { this.customerRelation = customerRelation; }
    public void setCustomerRelationPhone(String customerRelationPhone) { this.customerRelationPhone = customerRelationPhone; }
}
