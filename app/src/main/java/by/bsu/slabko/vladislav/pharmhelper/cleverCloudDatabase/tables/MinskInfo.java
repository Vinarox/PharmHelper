package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables;

public class MinskInfo {
    private int idMed;
    private int idPharmacy;
    private int idCity;
    private int quantity;
    private double price;
    private int idManuf;

    public MinskInfo(){}

    public MinskInfo(int idMed, int idPharmacy, int idCity, int quantity, double price, int idManuf){
        this.idMed = idMed;
        this.idPharmacy = idPharmacy;
        this.idCity = idCity;
        this.quantity = quantity;
        this.price = price;
        this.idManuf = idManuf;
    }

    public int getIdMed() {
        return idMed;
    }

    public void setIdMed(int idMed) {
        this.idMed = idMed;
    }

    public int getIdPharmacy() {
        return idPharmacy;
    }

    public void setIdPharmacy(int idPharmacy) {
        this.idPharmacy = idPharmacy;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdManuf() {
        return idManuf;
    }

    public void setIdManuf(int idManuf) {
        this.idManuf = idManuf;
    }
}
