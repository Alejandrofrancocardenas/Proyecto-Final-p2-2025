package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;

public class PackageModel implements Serializable {
    private static final long serialVersionUID = 1L;

    // Atributos de gestión
    private String idPackage;
    private String name;

    // Atributos de contenido y dimensiones
    private double weight;
    private double heightCm;
    private String description;

    // ⭐ CRÍTICO: Atributo añadido para el valor declarado
    private double declaredValue;

    /**
     * Constructor por defecto.
     */
    public PackageModel() {
    }

    /**
     * Constructor completo para inicialización.
     * Incluye declaredValue.
     */
    public PackageModel(String idPackage, String name, double weight, double heightCm, String description, double declaredValue) {
        this.idPackage = idPackage;
        this.name = name;
        this.weight = weight;
        this.heightCm = heightCm;
        this.description = description;
        this.declaredValue = declaredValue; // ASIGNACIÓN DEL VALOR DECLARADO
    }

    // --- Getters y Setters para idPackage ---
    public String getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(String idPackage) {
        this.idPackage = idPackage;
    }

    // --- Getters y Setters para name ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // --- Getters y Setters para weight ---
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // --- Getters y Setters para heightCm ---
    public double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(double heightCm) {
        this.heightCm = heightCm;
    }

    // --- Getters y Setters para description ---
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ⭐ CRÍTICO: Getters y Setters para declaredValue
    public double getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(double declaredValue) {
        this.declaredValue = declaredValue;
    }
}