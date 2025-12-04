package com.tubes.command;

import com.tubes.order.OrderServiceException;

public interface Command {
    void execute() throws OrderServiceException;
}