package application.model.viewmodel.admin;

import java.util.Objects;

public class ChartLabelDataVM {
    private String label;
    private long data;

    public ChartLabelDataVM(String label, long data) {
        this.label = label;
        this.data = data;
    }

    public ChartLabelDataVM() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() !=o.getClass())
            return false;
        ChartLabelDataVM that = (ChartLabelDataVM) o;
        return data == that.data &&
                Objects.equals(label, that.label);
    }

    @Override
    public int hashCode(){
        return Objects.hash(label,data);
    }
}
