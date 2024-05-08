package org.example.controller;

import org.example.enumerator.CardRole;
import org.example.model.Commission;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.controller.Main.*;

public class CommissionConteroller {
    public static void changeComission() {
        System.out.println("""
                1.Add commission
                2.Read commission
                3.Update commission
                """);
        try {
            int command = scanInt.nextInt();
            switch (command) {
                case 1 -> addComission();
                case 2 -> showCommission();
                case 3 -> updateCommission();
                case 0 -> adminMenu();
                default -> System.out.println("No command");
            }
        } catch (InputMismatchException e){
            scanInt = new Scanner(System.in);
            System.out.println("Error Entered?");
        }
    }
    private static void addComission() {
        System.out.println("""
                1.HUMO
                2.UZCARD
                3.VISA
                """);
        String command = scanStr.nextLine();
        CardRole serderCardRole = null;
        switch (command){
            case "1" -> serderCardRole = CardRole.HUMO;
            case "2" -> serderCardRole = CardRole.UZCARD;
            case "3" -> serderCardRole = CardRole.VISA;
        }
        System.out.println("""
                1.HUMO
                2.UZCARD
                3.VISA
                """);
        String command1 = scanStr.nextLine();
        CardRole reseiverCardRole = null;
        switch (command1){
            case "1" -> reseiverCardRole = CardRole.HUMO;
            case "2" -> reseiverCardRole = CardRole.UZCARD;
            case "3" -> reseiverCardRole = CardRole.VISA;
        }

        System.out.println("Enter commission: ");
        Double commission = scanInt.nextDouble();
        commissionService.add(new Commission(serderCardRole,reseiverCardRole,commission));
    }
   /* private static void showCommission() {
        commissionService.getAll();
    }*/
    private static void updateCommission() {
    }



   /* private static CardRole getCardRole() {
        CardRole[] cardTypes = CardRole.values();
        int i = 0;
        for (CardRole cardType : cardTypes) {
            System.out.println(i++ + " : " + cardType.name());
        }
        System.out.print("choice: ");
        int choice = scanInt.nextInt();
        return cardTypes[choice];
    }*/


}
       /* System.out.println("""
                1.HUMO PREVOD
                2.VISA PEREVOD
                2>UZCARD PEREVOD
                """);
        try {
        int command = scanInt.nextInt();
        switch (command) {
            case 1 -> humoPerevod();
            case 2 -> visaPerevod();
            case 3 -> uzcardPerevod();
            case 0 -> adminMenu();
            default -> System.out.println("No command");
        }
    } catch (InputMismatchException e){
            scanInt = new Scanner(System.in);
            System.out.println("Error Entered?");
        }
    }
    private static void humoPerevod() {

    }

    private static void uzcardPerevod() {

    }

    private static void visaPerevod() {

    }*/


    // men humoPrevod metodka kirganim CardRole.HUMO -> CardRole.Uzcard = commission chiqishikerak

//1. HUMO <-> UZCARD 2. VISA <-> HUMO 4. VISA <-> UZCARD 5. VISA <-> VISA 6. HUMO <-> HUMO 7. UZCARD <-> UZCARD

