package com.facturacion.facturacion.exception;

public class InvoiceDetailsAlreadyExistException extends Exception{
    public InvoiceDetailsAlreadyExistException (String msg){
        super(msg);
    }
}
