package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables;

public class Cities {
    private int idCity;
    private String city;

    public Cities(){}

    public Cities(int idCity, String city){
        this.idCity = idCity;
        this.city = city;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
