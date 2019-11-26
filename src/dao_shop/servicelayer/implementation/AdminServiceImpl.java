package dao_shop.servicelayer.implementation;

import dao_shop.beans.Product;
import dao_shop.beans.User;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import dao_shop.servicelayer.AdminService;
import dao_shop.servicelayer.exceptions.ServiceException;

public class AdminServiceImpl implements AdminService {
    private User currentUser;
    @Override
    public Product getProduct(int id) throws ServiceException {
        try {
            return FileDataWorkerFactory.getInstance().getProductDataWorker().getProduct(id);
        } catch (DAOException e) {
            throw new ServiceException("Can't get product from dao" + e.getMessage());
        }
    }

    @Override
    public Product[] getProducts() throws ServiceException {
        try {
            return FileDataWorkerFactory.getInstance().getProductDataWorker().getProducts();
        } catch (DAOException e) {
            throw new ServiceException("Can't get products from dao" + e.getMessage());
        }
    }

    @Override
    public int findUserId(User user) throws ServiceException {
        try {
            return FileDataWorkerFactory.getInstance().getUserDataWorker().findUserId(user);
        } catch (DAOException e) {
            throw new ServiceException("Can't find user");
        }
    }

    @Override
    public void addProduct(Product product) throws ServiceException {
        try {
            product.setId(FileDataWorkerFactory.getInstance().getProductDataWorker().nextFreeId());
            FileDataWorkerFactory.getInstance().getProductDataWorker().addProduct(product);
        } catch (DAOException e) {
            throw new ServiceException("Can't add product in dao layer");
        }

    }

    @Override
    public void removeProduct(Product product) {
        FileDataWorkerFactory.getInstance().getProductDataWorker().removeProduct(product.getId());
    }

    @Override
    public void modifyProduct(Product oldProduct, Product newProduct) throws ServiceException {
        FileDataWorkerFactory.getInstance().getProductDataWorker().removeProduct(oldProduct.getId());
        newProduct.setId(oldProduct.getId());
        try {
            FileDataWorkerFactory.getInstance().getProductDataWorker().addProduct(newProduct);
        } catch (DAOException e) {
            throw new ServiceException("Can't modify in dao"+e.getMessage());
        }

    }

    @Override
    public void registration(User user) throws ServiceException {
        try {
            FileDataWorkerFactory.getInstance().getUserDataWorker().addUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Can't register user");
        }
    }

    @Override
    public User signIn(String login, String password) throws ServiceException {
        try {
            currentUser = FileDataWorkerFactory.getInstance().getUserDataWorker().signIn(login,password);
            return currentUser;
        } catch (DAOException e) {
            throw new ServiceException("Invalid data");
        }
    }

    @Override
    public void signOut(String login) {
        currentUser = null;
    }
}
