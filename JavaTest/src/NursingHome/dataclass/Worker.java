package NursingHome.dataclass;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Worker extends WorkerBase {
    private StringProperty rank;

    public Worker(){
        super();
        this.rank=new SimpleStringProperty("");
    }

    public String getWorkerInfo(){
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getAge()+"','"+this.getSalary()+"','"+this.getRank()+"')";
        return temp;
    }

    public String getRank() { return rank.get(); }
    public StringProperty rankProperty() { return rank; }
    public void setRank(String rank) { this.rank.set(rank); }
}
