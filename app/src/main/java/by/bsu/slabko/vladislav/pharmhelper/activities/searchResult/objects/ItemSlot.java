package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects;

public class ItemSlot {
    public String name;
    public float price;
    public int quantity;
    public String manuf;
    public ItemSlot(String pharmName, float price, int quantity, String manuf){
        this.name = pharmName;
        this.price = price;
        this.quantity = quantity;
        this.manuf = manuf;
    }
}
