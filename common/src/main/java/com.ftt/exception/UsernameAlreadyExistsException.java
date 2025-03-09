
package com.ftt.exception;

public class UsernameAlreadyExistsException extends  BaseException {
    public UsernameAlreadyExistsException(){}
    public UsernameAlreadyExistsException(String message){
        super(message);
    }
}

