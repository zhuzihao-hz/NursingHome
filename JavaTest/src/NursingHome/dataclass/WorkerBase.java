package NursingHome.dataclass;

import javafx.beans.property.*;

public class WorkerBase {
    private StringProperty id;
    private StringProperty name;
    private IntegerProperty age;
    private DoubleProperty salary;

    public WorkerBase(){
        this.id=new SimpleStringProperty("");
        this.name=new SimpleStringProperty("");
        this.age=new SimpleIntegerProperty(0);
        this.salary=new SimpleDoubleProperty(0);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public Integer getAge() { return age.get(); }
    public double getSalary() { return salary.get(); }

    public void setId(String id) { this.id.set(id); }
    public void setAge(Integer age) { this.age.set(age); }
    public void setName(String name) { this.name.set(name); }
    public void setSalary(double salary) { this.salary.set(salary); }

    public StringProperty idProperty() { return id; }
    public DoubleProperty salaryProperty() { return salary; }
    public IntegerProperty ageProperty() { return age; }
    public StringProperty nameProperty() { return name; }
}
