/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ramirez.angel.editor;

import javax.swing.*;

/**
 *
 * @author ar275
 */
public class Ventana extends JFrame{
    //constrcutor 
    public Ventana(){
        setBounds(300, 300, 300, 300);
        setTitle("Text Editor");
        
        add( new Panel(this));
    }
}
