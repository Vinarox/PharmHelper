package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables;

public class Manufacturers {
    private int idManuf;
    private String manufacturers;

    public Manufacturers(){}

    public Manufacturers(int idManuf, String manufacturers){
        this.idManuf = idManuf;
        this.manufacturers = manufacturers;
    }

    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers;
    }

    public int getIdManuf() {
        return idManuf;
    }

    public void setIdManuf(int idManuf) {
        this.idManuf = idManuf;
    }
}
