package NursingHome.dataclass;

import javafx.beans.property.*;

public class Administrator {
    private StringProperty id;
    private StringProperty name;
    private IntegerProperty age;
    private StringProperty position;
    private DoubleProperty salary;

    public Administrator() {
        this.id=new SimpleStringProperty("");
        this.name=new SimpleStringProperty("");
        this.age=new SimpleIntegerProperty(0);
        this.position=new SimpleStringProperty("");
        this.salary=new SimpleDoubleProperty(0.0);
    }

    public Administrator(StringProperty id, StringProperty name, IntegerProperty age, StringProperty position, DoubleProperty salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.salary = salary;
    }

    public String getId() { return id.get(); }
    public StringProperty idProperty() { return id; }
    public void setId(String id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public int getAge() { return age.get(); }
    public IntegerProperty ageProperty() { return age; }
    public void setAge(int age) { this.age.set(age); }

    public String getPosition() { return position.get(); }
    public StringProperty positionProperty() { return position; }
    public void setPosition(String position) { this.position.set(position); }

    public double getSalary() { return salary.get(); }
    public DoubleProperty salaryProperty() { return salary; }
    public void setSalary(double salary) { this.salary.set(salary); }
}
