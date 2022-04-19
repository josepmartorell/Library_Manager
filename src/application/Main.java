/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package application;

import javax.swing.SwingUtilities;
import view.Menu;

/**
 *
 * @author jtech
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //DriverMain driver = new DriverMain();
                new Menu();
            }
        });
    }
    
}
