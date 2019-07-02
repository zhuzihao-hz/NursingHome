package NursingHome.dataclass;

import javafx.beans.property.*;

public class Worker extends WorkerBase {
    private StringProperty rank;
    private IntegerProperty customerRank;
    private IntegerProperty customerNumber;
    private DoubleProperty customerVisPos;

    public Worker(){
        super();
        this.rank=new SimpleStringProperty("");
        this.customerRank=new SimpleIntegerProperty(0);
        this.customerNumber=new SimpleIntegerProperty(0);
        this.customerVisPos=new SimpleDoubleProperty(0.0);
    }

    public String getWorkerInfo(){
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getDate()+"','"+this.getSalary()+"','"+this.getRank()+"','"+this.getCustomerRank()+"','"+this.getCustomerNumber()+"','"+this.getCustomerVisPos()+"')";
        return temp;
    }

    public String getRank() { return rank.get(); }
    public StringProperty rankProperty() { return rank; }
    public void setRank(String rank) { this.rank.set(rank); }

    public int getCustomerRank() { return customerRank.get(); }
    public IntegerProperty customerRankProperty() { return customerRank; }
    public void setCustomerRank(int customerRank) { this.customerRank.set(customerRank); }

    public int getCustomerNumber() { return customerNumber.get(); }
    public IntegerProperty customerNumberProperty() { return customerNumber; }
    public void setCustomerNumber(int customerNumber) { this.customerNumber.set(customerNumber); }

    public double getCustomerVisPos() { return customerVisPos.get(); }
    public DoubleProperty customerVisPosProperty() { return customerVisPos; }
    public void setCustomerVisPos(double customerVisPos) { this.customerVisPos.set(customerVisPos); }
}
