package ru.pflb;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;

public class App
{
    public static void main( String[] args ){
        AutoManager autoManager = AutoManager.giveInstance();
        UIManger uiManger = UIManger.giveInstance();
        uiManger.bind(autoManager);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        uiManger.greetUser();
        while (uiManger.isAlive()){
            try {
                input = br.readLine();
                uiManger.processInput(input);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotBoundException e){
                e.printStackTrace();
            }
        }
    }
}
