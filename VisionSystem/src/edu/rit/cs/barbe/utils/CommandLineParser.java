package edu.rit.cs.barbe.utils;


import java.util.Scanner;

public class CommandLineParser {

    private Scanner in;

    public CommandLineParser() {
        in = new Scanner(System.in);
    }

    public int readIntFromUser(String prompt) {
        Integer myInt = null;

        while (myInt == null) {
            System.out.print(prompt + " ");

            if (in.hasNextInt()) {
                myInt = in.nextInt();
                in.nextLine();
            } else {
                in.nextLine();
                System.out.println("You must enter an int.");
            }
        }
        return myInt;
    }

    public byte readByteFromUser(String prompt) {
        Byte myByte = null;

        while (myByte == null) {
            System.out.print(prompt + " ");

            if (in.hasNextByte()) {
                myByte = in.nextByte();
                in.nextLine();
            } else {
                in.nextLine();
                System.out.println("You must enter a byte.");
            }
        }
        return myByte;
    }

    public float readFloatFromUser(String prompt) {
        Float myFloat = null;

        while (myFloat == null) {
            System.out.print(prompt + " ");

            if (in.hasNextFloat()) {
                myFloat = in.nextFloat();
                in.nextLine();
            } else {
                in.nextLine();
                System.out.println("You must enter a float.");
            }
        }
        return myFloat;
    }

}
