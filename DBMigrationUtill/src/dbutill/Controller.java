package dbutill;

public class Controller {
    private static final Controller instance = new Controller();
    public static Controller getInstance(){
        return instance;
    }
    public String executeCommand(String command){
        var commandItems = command.split("/");
        switch (commandItems[0].toUpperCase()){
            case "M":
                switch (commandItems[1].toLowerCase()){
                    case "orders":
                        MigrationFactory.getInstance().getOrderMigrationManager().Migrate();
                        break;
                    case "orderitems":
                        MigrationFactory.getInstance().getOrderItemMigrationManager().Migrate();
                        break;
                    case "products":
                        MigrationFactory.getInstance().getProductMigrationManager().Migrate();
                        break;
                    case "users":
                        MigrationFactory.getInstance().getUserMigrationManager().Migrate();
                        break;
                    case "shoppingcarts":
                        MigrationFactory.getInstance().getShoppingCartMigrationManager().Migrate();
                        break;
                    case "deliveryinfos":
                        MigrationFactory.getInstance().getDeliveryInfoMigrationManager().Migrate();
                        break;

                }
                break;
            case  "C":
                switch (commandItems[1].toLowerCase()){
                    case "orders":
                        MigrationFactory.getInstance().getOrderMigrationManager().CreateTable(true);
                        break;
                    case "orderitems":
                        MigrationFactory.getInstance().getOrderItemMigrationManager().CreateTable(true);
                        break;
                    case "products":
                        MigrationFactory.getInstance().getProductMigrationManager().CreateTable(true);
                        break;
                    case "users":
                        MigrationFactory.getInstance().getUserMigrationManager().CreateTable(true);
                        break;
                    case "shoppingcarts":
                        MigrationFactory.getInstance().getShoppingCartMigrationManager().CreateTable(true);
                        break;
                    case "deliveryinfos":
                        MigrationFactory.getInstance().getDeliveryInfoMigrationManager().CreateTable(true);
                        break;
                }
                break;
        }
        return "OK";
    }


}
