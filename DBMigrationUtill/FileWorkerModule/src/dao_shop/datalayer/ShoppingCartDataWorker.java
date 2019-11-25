package dao_shop.datalayer;

import dao_shop.beans.ShoppingCart;
import dao_shop.datalayer.exceptions.DAOException;

public interface ShoppingCartDataWorker {
    public ShoppingCart[] getCarts() throws DAOException;
    public ShoppingCart getCart(int id) throws DAOException;
    public void addCart(ShoppingCart cart) throws DAOException;
    public void removeCart(int id);
    public void modifyCart(int id, ShoppingCart newCart) throws DAOException;
    public int nextFreeId();
}
