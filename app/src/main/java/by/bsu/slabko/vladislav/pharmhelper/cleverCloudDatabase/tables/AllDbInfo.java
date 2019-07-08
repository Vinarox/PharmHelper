package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables;

public class AllDbInfo {
    private int idManuf;
    private String manufacturers;
    private int idMedicine;
    private String medecine;
    private int idPharmacy;
    private int idCity;
    private int quantity;
    private double price;
    private String pharmacyName;
    private String address;
    private String district;
    private String phone;
    private double longitude;
    private double latitude;

    public AllDbInfo(){}

    public AllDbInfo(int idManuf, String manufacturers, int idMedicine, String medecine,
                     int idPharmacy, int idCity, int quantity, double price,
                     String pharmacyName, String address, String district, String phone,
                     double longitude, double latitude) {
        this.idManuf = idManuf;
        this.manufacturers = manufacturers;
        this.idMedicine = idMedicine;
        this.medecine = medecine;
        this.idPharmacy = idPharmacy;
        this.idCity = idCity;
        this.quantity = quantity;
        this.price = price;
        this.pharmacyName = pharmacyName;
        this.address = address;
        this.district = district;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getIdManuf() {
        return idManuf;
    }

    public void setIdManuf(int idManuf) {
        this.idManuf = idManuf;
    }

    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers;
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
