package net.epam.study.controller.command;

import net.epam.study.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<CommandName, Command> commands = new HashMap<>();

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
        commands.put(CommandName.DBINFODELETE, new AdminDelete());
        commands.put(CommandName.LOGOUT, new Logout());
        commands.put(CommandName.ITEMDELETE, new CartItemDelete());
        commands.put(CommandName.ORDERSTATUS, new AdminChangeOrderStatus());
        commands.put(CommandName.USERROLE, new AdminUserRole());
        commands.put(CommandName.GOTOPAYMENTPAGE, new GoToPaymentPage());
        commands.put(CommandName.SAVEPAYMENT, new SaveNewPayment());
        commands.put(CommandName.SAVEMENUITEM, new AdminSaveNewMenuItem());
        commands.put(CommandName.DELETEMENUITEM, new AdminDeleteMenuItem());
    }


    public Command takeCommand(String name) {
        CommandName commandName;
        commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

    public boolean isContains(String key) {

        if (key == null) {
            return false;
        }
        CommandName commandName;

        try {
            commandName = CommandName.valueOf(key.toUpperCase());

        } catch (IllegalArgumentException e) {

            return false;
        }

        return commands.containsKey(commandName);
    }

}
