package org.example;

import org.example.database.Database;
import org.example.entities.Products;
import org.example.entities.User;
import org.example.servises.Console;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static User user;
    private static Integer id;
    private static Console consoleManager = new Console();
    private static Database database = new Database();
    private static Products product;

    public static void main(String[] args) {
       logOrSingUp();


    }
    public static void logOrSingUp(){
        int userChoice;
        consoleManager.printMainMenu();
        while (true) {
            userChoice = consoleManager.readNumberFromConsole(1, 2);

            switch (userChoice) {
                case 1:
                    loggedCustomer();
                    break;
                case 2:
                    signUp();
                    break;
            }
        }
    }

    public static void loggedCustomer() {
        String login;
        String password;
        int userChoiceNumber;

        while (true) {
            login = consoleManager.readStringFromConsole("Input Login: ");
            password = consoleManager.readStringFromConsole("Input Password: ");
            user = database.login(login, password);
            id = database.id(login, password);

            if (user == null) {
                consoleManager.printRetryLoginMenu();
                userChoiceNumber = consoleManager.readNumberFromConsole(1, 2);

                if (userChoiceNumber == 1) {
                    continue;
                }
                else if (userChoiceNumber == 2){
                    logOrSingUp();
                }
                break;
            }
            break;
        }
        workWithLoggedUser(user);
    }

    public static void workWithLoggedUser(User user) {
        int userChoiceNumber;
        link:
        while (true) {
            consoleManager.printLoggedMenu();
            userChoiceNumber = consoleManager.readNumberFromConsole(1, 3);
            switch (userChoiceNumber) {
                case 1:
                    for (int i = 0; i < database.getUserInfo(id).size(); i++) {
                        System.out.println(database.getUserInfo(id).get(i));
                        break;
                    }

                    break;
                case 2:
                    showServices();
                    break;
                case 3:
                    logOrSingUp();
                    break;
            }
        }
    }

    public static void signUp() {
        String login;
        String password;
        Integer money = 0;
        Integer userChoice;
        String city;
        String country;
        while (true) {
            login = consoleManager.readStringFromConsole("Input login:");
            password = consoleManager.readStringFromConsole("Input password:");
            while (true) {
                database.signUpUserWithoutAdditional(login, password, money);
                id = database.id(login, password);
                while (true) {
                    System.out.println("Do you want to add additional info?");
                    consoleManager.choiceMenu();
                    userChoice = consoleManager.readNumberFromConsole(1, 2);
                    if (userChoice == 1) {
                        city = consoleManager.readStringFromConsole("Input city");
                        country = consoleManager.readStringFromConsole("Input country");
                        database.signUpUser(id, city, country);
                        loggedCustomer();
                        break;
                    } else if (userChoice == 2) {
                        loggedCustomer();
                        break;
                    } else {
                        System.out.println("Please, write 1 or 2");
                    }
                    break;
                }
                break;
            }
            break;
        }
    }
    public static void showServices() {
        int userChoice;
        while (true) {
            System.out.println("Please, choose type of service");
            consoleManager.servicesMenu();
            userChoice = consoleManager.readNumberFromConsole(1, 2);
            if (userChoice == 1) {
                while (true) {
                    consoleManager.shopMenu();
                    userChoice = consoleManager.readNumberFromConsole(1, 3);
                    switch (userChoice) {
                        case 1:
                            for (int i = 0; i < database.getAllProducts().size(); i++) {
                                System.out.println(database.getAllProducts().get(i));
                            }
                            buyProducts();
                            break;
                        case 2:
                            //
                            break;
                        case 3:
                            showServices();
                            break;
                    }
                }
            }
                else if (userChoice == 2) {
                    //
                        }
            }
        }
        public static void buyProducts(){
        int userChoice;
        int quantity;
        int userMoney = database.money(id);
        int purchasePrice = 0;
        ArrayList<Products> products = database.getAllProducts();
            int to = products.size();
            System.out.println("Write id of product to buy");
            userChoice = consoleManager.readNumberFromConsole(1, to);
            System.out.println("Print quantity of stocks");
            quantity = consoleManager.readIntFromConsole();

            int productQuantity = database.productQuantity(userChoice);
            purchasePrice += database.price(userChoice) * quantity;

            if (userMoney >= purchasePrice) {
                if (productQuantity >= quantity) {
                    userMoney -= purchasePrice;
                    productQuantity -= quantity;
                    database.buyProducts(id, userChoice, quantity);
                    database.changeMoney(userMoney, id);
                    database.changeQuantity(productQuantity, userChoice);
                    System.out.println("payment was successful");
                    link: while (true){
                        System.out.println("Do you want to buy more?");
                        consoleManager.choiceMenu();
                        userChoice = consoleManager.readNumberFromConsole(1,2);
                        switch (userChoice){
                            case 1: buyProducts();
                            break;
                            case 2: break link;
                        }
                    }
                } else {
                    System.out.println("the product is not available");
                    link:
                    while (true) {
                        System.out.println("Choose another one?");
                        consoleManager.choiceMenu();
                        userChoice = consoleManager.readNumberFromConsole(1, 2);
                        switch (userChoice) {
                            case 1:
                                buyProducts();
                                break;
                            case 2:
                                break link;
                        }
                        break;
                    }
                }
            } else {
                System.out.println("you don't have enough money");
                System.out.println("Your money:" + database.money(id));
                topUpAccount();
                buyProducts();
            }
    }
    public static void topUpAccount(){
        int userChoice;
        int userMoney = database.money(id);
        int money = 0;
        while (true) {
            System.out.println("Do you want top up your account?");
            consoleManager.choiceMenu();
            userChoice = consoleManager.readNumberFromConsole(1, 2);
            if (userChoice == 1) {
                System.out.println("Input money");
                Scanner sc = new Scanner(System.in);
                money = sc.nextInt();
                userMoney += money;
                database.changeMoney(userMoney, id);
                System.out.println("Your money:" + database.money(id));
            } else if (userChoice == 2) {
                System.out.println("Your money:" + database.money(id));
                break;
            }
            break;
    }
    }
}