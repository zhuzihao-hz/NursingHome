package NursingHome.dataclass;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Doctor extends WorkerBase{
    private StringProperty major;

    public Doctor(){
        super();
        this.major=new SimpleStringProperty("");
    }

    public String getDoctorInfo() {
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getAge()+"','"+this.getSalary()+"','"+this.getMajor()+"')";
        return temp;
    }

    public void setMajor(String major) { this.major.set(major); }
    public String getMajor() { return major.get(); }
    public StringProperty majorProperty() { return major; }
}
