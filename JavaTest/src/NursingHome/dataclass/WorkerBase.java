package NursingHome.dataclass;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class WorkerBase {
    private StringProperty id;
    private StringProperty name;
    private StringProperty age;
    private DoubleProperty salary;

    public WorkerBase(){ }

    public void setId(String id) { this.id.set(id); }
    public void setAge(String age) { this.age.set(age); }
    public void setName(String name) { this.name.set(name); }
    public void setSalary(double salary) { this.salary.set(salary); }

    public StringProperty idProperty() { return id; }
    public DoubleProperty salaryProperty() { return salary; }
    public StringProperty ageProperty() { return age; }
    public StringProperty nameProperty() { return name; }
}
