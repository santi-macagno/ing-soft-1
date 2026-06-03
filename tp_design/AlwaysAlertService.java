public class AlwaysAlertService implements AlertService {
    
    @Override
    public boolean shouldAlertCost(double cost) {
        return true;
    }

    @Override
    public boolean shouldAlertETA(int eta) {
        return true;
    }
}