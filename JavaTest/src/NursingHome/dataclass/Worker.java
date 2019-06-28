package NursingHome.dataclass;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Worker extends WorkerBase {
    private StringProperty rank;
    private StringProperty customerRank;
    private IntegerProperty customerNumber;

    public Worker(){
        super();
        this.rank=new SimpleStringProperty("");
        this.customerRank=new SimpleStringProperty("");
        this.customerNumber=new SimpleIntegerProperty(0);
    }

    public String getWorkerInfo(){
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getAge()+"','"+this.getSalary()+"','"+this.getRank()+"','"+this.getCustomerRank()+"','"+this.getCustomerNumber()+"')";
        return temp;
    }

    public String getRank() { return rank.get(); }
    public StringProperty rankProperty() { return rank; }
    public void setRank(String rank) { this.rank.set(rank); }

    public String getCustomerRank() { return customerRank.get(); }
    public StringProperty customerRankProperty() { return customerRank; }
    public void setCustomerRank(String customerRank) { this.customerRank.set(customerRank); }

    public int getCustomerNumber() { return customerNumber.get(); }
    public IntegerProperty customerNumberProperty() { return customerNumber; }
    public void setCustomerNumber(int customerNumber) { this.customerNumber.set(customerNumber); }
}
