package dao_shop.datalayer.fileworkers;

import dao_shop.datalayer.*;

public final class FileDataWorkerFactory {
    private final ShoppingCartDataWorker shoppingCartDataWorker = new FileShoppingCartDataWorker("ShoppingCarts");
    private final OrderItemDataWorker orderItemDataWorker = new FileOrderItemDataWorker("OrderItems");
    private final DeliveryInfoDataWorker deliveryInfoDataWorker = new FileDeliveryInfoDataWorker("Deliveryes");
    private final OrderDataWorker orderDataWorker = new FileOrderDataWorker("Orders");
    private final ProductDataWorker productDataWorker = new FileProductDataWorker("Products");
    private final UserDataWorker userDataWorker = new FileUserDataWorker("Users");
    private static final FileDataWorkerFactory instance = new FileDataWorkerFactory();

    private FileDataWorkerFactory() {
    }


    public static FileDataWorkerFactory getInstance() {
        return instance;
    }

    public OrderDataWorker getOrderDataWorker() {
        return orderDataWorker;
    }

    public ProductDataWorker getProductDataWorker() {
        return productDataWorker;
    }

    public UserDataWorker getUserDataWorker() {
        return userDataWorker;
    }

    public DeliveryInfoDataWorker getDeliveryInfoDataWorker() {
        return deliveryInfoDataWorker;
    }

    public ShoppingCartDataWorker getShoppingCartDataWorker() {
        return shoppingCartDataWorker;
    }

    public OrderItemDataWorker getOrderItemDataWorker() {
        return orderItemDataWorker;
    }
}
