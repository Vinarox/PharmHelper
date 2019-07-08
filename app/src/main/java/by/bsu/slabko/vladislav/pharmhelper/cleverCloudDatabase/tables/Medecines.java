package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables;

public class Medecines {
    private int idMedicine;
    private String medecine;

    public Medecines(){}

    public Medecines(int idMedicine, String medecine){
        this.idMedicine = idMedicine;
        this.medecine = medecine;
    }

    public int getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(int idMedicine) {
        this.idMedicine = idMedicine;
    }

    public String getMedecine() {
        return medecine;
    }

    public void setMedecine(String medecine) {
        this.medecine = medecine;
    }

    @Override
    public String toString() {
        return medecine;
    }
}
