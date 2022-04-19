/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;

/**
 *
 * @author jtech
 */
public class EventoOpcionMenu extends ActionEvent{
    
        public EventoOpcionMenu(Object source, int id, String command) {
        super(source, id, command);   
    }
    
}
