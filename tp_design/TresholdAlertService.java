public class TresholdAlertService implements AlertService {

    private final double maxCost;
    private final int maxEta;

    public TresholdAlertService(double maxCost, int maxEta) {
        this.maxCost = maxCost;
        this.maxEta = maxEta;
    }

    @Override
    public boolean shouldAlertCost(double cost) {
        return cost > maxCost;
    }

    @Override
    public boolean shouldAlertETA(int ETA) {
        return ETA > maxEta;
    }
}
