package application.model.viewmodel.admin;

import java.util.Date;
import java.util.Objects;

public class ChartLabelDataVM2 {
    private Date label;
    private Double data;

    public ChartLabelDataVM2() {
    }

    public ChartLabelDataVM2(Date label, Double data) {
        this.label = label;
        this.data = data;
    }

    public Date getLabel() {
        return label;
    }

    public void setLabel(Date label) {
        this.label = label;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() !=o.getClass()) return false;
        ChartLabelDataVM2 that = (ChartLabelDataVM2) o;
        return Objects.equals(label, that.label) &&
                Objects.equals(data, that.data);
    }
    @Override
    public int hashCode() {
        return Objects.hash(label, data);
    }
}
