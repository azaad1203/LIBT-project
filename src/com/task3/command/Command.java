package com.task3.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Command {
    public void execute();
    public void init(HttpServletRequest request, HttpServletResponse response);
    public String getErrorMessage();
}
