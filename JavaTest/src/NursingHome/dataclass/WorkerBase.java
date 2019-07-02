package NursingHome.dataclass;

import javafx.beans.property.*;

public class WorkerBase {
    private StringProperty id;
    private StringProperty name;
    private StringProperty date;
    private DoubleProperty salary;

    public WorkerBase(){
        this.id=new SimpleStringProperty("");
        this.name=new SimpleStringProperty("");
        this.date=new SimpleStringProperty("");
        this.salary=new SimpleDoubleProperty(0);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getDate() { return date.get(); }
    public double getSalary() { return salary.get(); }

    public void setId(String id) { this.id.set(id); }
    public void setDate(String date) { this.date.set(date); }
    public void setName(String name) { this.name.set(name); }
    public void setSalary(double salary) { this.salary.set(salary); }

    public StringProperty idProperty() { return id; }
    public DoubleProperty salaryProperty() { return salary; }
    public StringProperty dateProperty() { return date; }
    public StringProperty nameProperty() { return name; }
}
