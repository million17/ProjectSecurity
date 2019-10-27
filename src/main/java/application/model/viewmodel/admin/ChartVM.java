package application.model.viewmodel.admin;

import java.util.List;

public class ChartVM {
    private List<ChartLabelDataVM> labelDataVMS;

    public List<ChartLabelDataVM> getLabelDataVMS() {
        return labelDataVMS;
    }

    public void setLabelDataVMS(List<ChartLabelDataVM> labelDataVMS) {
        this.labelDataVMS = labelDataVMS;
    }

    public ChartVM(List<ChartLabelDataVM> labelDataVMS) {
        this.labelDataVMS = labelDataVMS;
    }

    public ChartVM() {
    }
}
