package org.example.controller;

import org.example.enumerator.CardRole;
import org.example.model.Commission;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.controller.Main.*;

public class CommissionController {
    public static void changeCommission() {
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
                //case 3 -> updateCommission();
                case 0 -> adminMenu();
                default -> System.out.println("No command");
            }
        } catch (InputMismatchException e) {
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
        switch (command) {
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
        switch (command1) {
            case "1" -> reseiverCardRole = CardRole.HUMO;
            case "2" -> reseiverCardRole = CardRole.UZCARD;
            case "3" -> reseiverCardRole = CardRole.VISA;
        }

        System.out.println("Enter commission: ");
        double commission = scanInt.nextDouble();

        commissionService.add(new Commission(serderCardRole, reseiverCardRole, commission));
        System.out.println("Commission successfully created ✅");
    }

    private static void showCommission() {
        ArrayList<Commission> commissions = commissionService.getAll();
        if (commissions.isEmpty()) {
            System.out.println("No commissions 🦕");
            return;
        }
        int i = 1;
        for (Commission commission : commissions) {
            System.out.println(i++ + ". " + commission.getSenderCardRole()
                    + commission.getReseiverCardRole() + commission.getCommission());
        }
    }

  /*  private static void updateCommission() {
    }*/

}

