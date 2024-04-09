package org.example.App;

import org.example.Domain.MainController;

public class App {
    public static void main(String[] args) {
        MainController controller = new MainController();
        System.out.println(controller.isUserAuthenticated());
    }
}