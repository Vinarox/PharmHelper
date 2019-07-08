package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables;

public class Pharmacies {
    private int idPharmacy;
    private String pharmacyName;
    private String address;
    private String district;
    private String phone;
    private double longitude;
    private double latitude;

    public Pharmacies(){}

    public Pharmacies(int idPharmacy, String pharmacyName, String address, String district,
            String phone, double longitude, double latitude){
        this.idPharmacy = idPharmacy;
        this.pharmacyName = pharmacyName;
        this.address = address;
        this.district = district;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getIdPharmacy() {
        return idPharmacy;
    }

    public void setIdPharmacy(int idPharmacy) {
        this.idPharmacy = idPharmacy;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
