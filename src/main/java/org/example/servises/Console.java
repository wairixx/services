package org.example.servises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Console {
    private static Scanner scanner = new Scanner(System.in);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void printMainMenu() {
        System.out.println("1-login to account");
        System.out.println("2-sign up");
    }

    public void choiceMenu() {
        System.out.println("1-yes");
        System.out.println("2-no");
    }

    public void servicesMenu() {
        System.out.println("1-shop");
        System.out.println("2-treatment");
    }

    public void shopMenu() {
        System.out.println("1-show products");
        System.out.println("2-show bought products");
        System.out.println("3-exit");

    }

    public void printLoggedMenu() {
        System.out.println("1-show info");
        System.out.println("2-show services");
        System.out.println("3-exit");
    }

    public void printRetryLoginMenu() {
        System.out.println("1-try another login or password");
        System.out.println("2-exit");
    }

    public String readStringFromConsole() {
        return scanner.nextLine();
    }

    public Integer readIntFromConsole() {
        return scanner.nextInt();
    }

    public String readStringFromConsole(String message) {
        System.out.println(message);
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return line;
    }

    public int readNumberFromConsole(int from, int to){
        int fromCopy = from;
        int number;
        while(true) {
            try {
                from = fromCopy;
                number = Integer.parseInt(scanner.next());
                for(;from<=to;from++){
                    if(number==from){
                        return number;
                    }
                }
                System.out.println("Please input number between "+ fromCopy + " and "+ to);
            }catch (Exception e){
                System.out.print("Please input positive valid number: ");
                continue;
            }
        }
    }
}
