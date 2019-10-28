package dao_shop.data.fileworkers;

import dao_shop.data.OrderDataWorker;
import dao_shop.data.ProductDataWorker;
import dao_shop.data.UserDataWorker;

// TODO:other methods
public final class DataWorker {
    private final OrderDataWorker orderDataWorker = new FileOrderDataWorker("Orders");
    private final ProductDataWorker productDataWorker = new FileProductDataWorker();
    private final UserDataWorker userDataWorker = new FileUserDataWorker("Users");
    private static final DataWorker instance = new DataWorker();
    private DataWorker(){};
    public static DataWorker getInstance(){
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
}
