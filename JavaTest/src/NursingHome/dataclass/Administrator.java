package NursingHome.dataclass;

import javafx.beans.property.*;

public class Administrator {
    private StringProperty id;
    private StringProperty name;
    private StringProperty date;
    private StringProperty position;
    private DoubleProperty salary;

    public Administrator() {
        this.id=new SimpleStringProperty("");
        this.name=new SimpleStringProperty("");
        this.date=new SimpleStringProperty("");
        this.position=new SimpleStringProperty("");
        this.salary=new SimpleDoubleProperty(0.0);
    }

    public Administrator(StringProperty id, StringProperty name, StringProperty date, StringProperty position, DoubleProperty salary) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.position = position;
        this.salary = salary;
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getDate() { return date.get(); }
    public StringProperty DateProperty() { return date; }
    public void setDate(String age) { this.date.set(age); }

    public String getPosition() { return position.get(); }
    public StringProperty positionProperty() { return position; }
    public void setPosition(String position) { this.position.set(position); }

    public double getSalary() { return salary.get(); }
    public DoubleProperty salaryProperty() { return salary; }
    public void setSalary(double salary) { this.salary.set(salary); }

    public String getAdminInfo() {
        String temp;
        temp="('"+this.getId()+"','"+this.getName()+"','"+this.getDate()+"','"+this.getPosition()+"','"+this.getSalary()+"')";
        return temp;
    }
}
