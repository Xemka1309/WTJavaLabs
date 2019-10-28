package dao_shop.servicelayer.implementation;

import dao_shop.beans.Product;
import dao_shop.beans.User;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import dao_shop.servicelayer.AdminService;
import dao_shop.servicelayer.exceptions.ServiceException;

public class AdminServiceImpl implements AdminService {
    @Override
    public Product getProduct(int id) {
        return null;
    }

    @Override
    public Product[] getProducts() {
        return new Product[0];
    }

    @Override
    public void addProduct(Product product) throws ServiceException {
        try {
            FileDataWorkerFactory.getInstance().getProductDataWorker().addProduct(product);
        } catch (DAOException e) {
            throw new ServiceException("Can't add product in dao layer");
        }

    }

    @Override
    public void removeProduct(Product product) {

    }

    @Override
    public void modifyProduct(Product oldProduct, Product newProduct) {

    }

    @Override
    public void registration(User user) throws ServiceException {

    }

    @Override
    public User signIn(String login, String password) {
        return null;
    }

    @Override
    public void signOut(String login) {

    }
}
