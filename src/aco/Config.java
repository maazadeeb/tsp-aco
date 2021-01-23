package aco;

public class Config {
    // power of the pheromone, helps decide how much emphasis on existing trails. Typically lesser than beta to protect against not discovering more paths
    private static final double ALPHA = 1;
    // power of distance, helps decide how much emphasis on distance
    private static final double BETA = 2;
    // rate of evaporation, to help reduce emphasis on existing paths
    private static final double RHO = 0.5;
    // Just a constant
    private static final double Q3 = 100;

    public static double getPheromoneStrength() {
        return ALPHA;
    }

    public static double getEdgeWeightStrength() {
        return BETA;
    }

    public static double getRateOfEvaporation() {
        return RHO;
    }

    public static double getQ3() {
        return Q3;
    }
}
