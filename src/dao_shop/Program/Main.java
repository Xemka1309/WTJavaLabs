package dao_shop.Program;

import dao_shop.beans.*;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;
import dao_shop.view.AuthorizationForm;

public class Main{
    public static void main(String[] args){
        AuthorizationForm authorizationForm = new AuthorizationForm();
        authorizationForm.ShowWelcome();
    }
}