package NursingHome.dataclass;

import javafx.beans.property.StringProperty;

public class Doctor extends WorkerBase{
    private StringProperty major;

    public void setMajor(String major) { this.major.set(major); }

    public StringProperty majorProperty() { return major; }
}
