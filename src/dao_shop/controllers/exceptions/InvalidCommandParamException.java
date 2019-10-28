package dao_shop.controllers.exceptions;

public class InvalidCommandParamException extends CommandException {
    public InvalidCommandParamException(String message){
        super(message);
    }
}
