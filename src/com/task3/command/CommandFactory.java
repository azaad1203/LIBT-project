package com.task3.command;

import java.lang.InstantiationException;
public class CommandFactory {
	public static final Command getCommandByClass(Class<?> commandClass) 
		throws InstantiationException, IllegalAccessException, ClassCastException{
            Command command = (Command) commandClass.newInstance();
            return command;
    }
    
    public static final Command getCommandByName(String className, String packageName) 
    		throws InstantiationException, IllegalAccessException, ClassCastException {
        try {
            String name = "com.task3." + packageName+"." + className + "Command";
            Class<?> commandClass = Class.forName(name);
            return getCommandByClass(commandClass);
        } catch (ClassNotFoundException e) {
        	return null;
        }
    }
    
}