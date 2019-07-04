package NursingHome.dataclass;

import javafx.beans.property.*;

public class Administrator extends WorkerBase{
    private StringProperty position;

    public Administrator() {
        super();
        this.position=new SimpleStringProperty("");
    }

    public String getPosition() { return position.get(); }
    public StringProperty positionProperty() { return position; }
    public void setPosition(String position) { this.position.set(position); }

    public String getAdminInfo() {
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getDate()+"','"+this.getPosition()+"','"+this.getSalary()+"')";
        return temp;
    }
}
