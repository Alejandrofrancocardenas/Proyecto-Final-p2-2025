package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;

public class PackageModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idPackage;
    private String name;

    private double weight;
    private double heightCm;
    private String description;

    private double declaredValue;


    public PackageModel() {
    }

    public PackageModel(String idPackage, String name, double weight, double heightCm, String description, double declaredValue) {
        this.idPackage = idPackage;
        this.name = name;
        this.weight = weight;
        this.heightCm = heightCm;
        this.description = description;
        this.declaredValue = declaredValue; // ASIGNACIÓN DEL VALOR DECLARADO
    }

    public String getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(String idPackage) {
        this.idPackage = idPackage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(double heightCm) {
        this.heightCm = heightCm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(double declaredValue) {
        this.declaredValue = declaredValue;
    }
}