public class NeverAlertService implements AlertService {
    @Override
    public boolean shouldAlertCost(double cost) {
        return false;
    }

    @Override
    public boolean shouldAlertETA(int eta) {
        return false;
    }
}