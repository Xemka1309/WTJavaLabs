package dao_shop.data;

import dao_shop.beans.ShoppingCart;

public interface ShoppingCartDataWorker {
    public ShoppingCart[] getCarts();
    public ShoppingCart getCart(int id);
    public void addCart(ShoppingCart cart);
    public void removeCart(int id);
    public void modifyCart(int id, ShoppingCart newCart);
}
