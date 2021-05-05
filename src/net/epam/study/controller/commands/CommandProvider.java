package net.epam.study.controller.commands;

import net.epam.study.controller.commands.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.REGISTRATION, new GoToRegistrPage());
		commands.put(CommandName.SAVENEWUSER, new SaveNewUser());
		commands.put(CommandName.GOTOLOGINPAGE, new GoToLoginPage());
		commands.put(CommandName.GOTOADMINPAGE, new GoToAdminPage());
		commands.put(CommandName.GOTOMAINPAGE, new GoToMainPage());
		commands.put(CommandName.CHECKLOGINANDPASSWORD, new CheckLoginAndPassword());
		commands.put(CommandName.GOTOMENUPAGE, new GoToMenuPage());
		commands.put(CommandName.ADDTOCART, new AddToCart());
		commands.put(CommandName.GOTOBASKETPAGE, new GoToBasketPage());
		commands.put(CommandName.SAVENEWORDER, new SaveNewOrder());
		commands.put(CommandName.GOTOBILLPAGE, new GoToBillPage());
//		commands.put(CommandName.LOGOUT, new Logout());
	}
	
	
	public Command takeCommand(String name) {
		CommandName commandName;
		commandName = CommandName.valueOf(name.toUpperCase());
		return commands.get(commandName);
	}

}
