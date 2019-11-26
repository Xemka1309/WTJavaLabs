package dbutill;

public class MigrationFactory {
    private static ProductMigrationManager productMigrationManager = new ProductMigrationManager();
    private static OrderItemMigrationManager orderItemMigrationManager = new OrderItemMigrationManager();
    private static OrderMigrationManager orderMigrationManager = new OrderMigrationManager();
    private static ShoppingCartMigrationManager shoppingCartMigrationManager = new ShoppingCartMigrationManager();
    private static DeliveryInfoMigrationManager deliveryInfoMigrationManager = new DeliveryInfoMigrationManager();
    private static UserMigrationManager userMigrationManager = new UserMigrationManager();
    private static MigrationFactory instance = new MigrationFactory();
    public static MigrationFactory getInstance(){
        return instance;
    }
    public ProductMigrationManager getProductMigrationManager(){
        return productMigrationManager;
    }

    public OrderMigrationManager getOrderMigrationManager() {
        return orderMigrationManager;
    }

    public DeliveryInfoMigrationManager getDeliveryInfoMigrationManager() {
        return deliveryInfoMigrationManager;
    }

    public ShoppingCartMigrationManager getShoppingCartMigrationManager() {
        return shoppingCartMigrationManager;
    }

    public OrderItemMigrationManager getOrderItemMigrationManager() {
        return orderItemMigrationManager;
    }

    public UserMigrationManager getUserMigrationManager() {
        return userMigrationManager;
    }
}
