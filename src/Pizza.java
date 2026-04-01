public class Pizza {

    private int id;
    private String size;
    private String toppings;
    private double price;

    public Pizza() {}

    public Pizza(int id, String size, String toppings, double price) {
        this.id = id;
        this.size = size;
        this.toppings = toppings;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
