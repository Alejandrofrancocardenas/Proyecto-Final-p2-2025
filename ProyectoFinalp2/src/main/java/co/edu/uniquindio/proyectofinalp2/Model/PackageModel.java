package co.edu.uniquindio.proyectofinalp2.Model;

public class PackageModel {
    private String packageId;
    private double weight;
    private double volume;

    public PackageModel(String packageId, double weight, double volume) {
        this.packageId = packageId;
        this.weight = weight;
        this.volume = volume;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "PackageModel{" +
                "packageId='" + packageId + '\'' +
                ", weight=" + weight +
                ", volume=" + volume +
                '}';
    }
}
