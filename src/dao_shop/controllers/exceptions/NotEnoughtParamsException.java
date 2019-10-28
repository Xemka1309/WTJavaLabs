package dao_shop.controllers.exceptions;

public class NotEnoughtParamsException extends CommandException {
    public NotEnoughtParamsException(String message){
        super(message);
    }
}
