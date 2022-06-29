/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ramirez.angel.editor;

import java.util.*;
import javax.swing.*;
import javax.swing.text.*;


/**
 *
 * @author ar275
 */
public class Utilidades {
//-------------------------------Agrega texto al final-----------------------------

    public static void append(String linea, JTextPane areaText) {
        try {
            Document doc = areaText.getDocument();
            doc.insertString(doc.getLength(), linea, null);

        } catch (BadLocationException exc) {

        }
    }
//-----------------------------------------------------------------------------------

//--------------------------Metodo para mostrar numeracion-------------------------------   
    public static void viewNumeracionInicio(boolean numeacion, JTextPane txtArea, JScrollPane scroll) {
        if(numeacion){
            scroll.setRowHeaderView(new TextLineNumber(txtArea));
        }else{
            scroll.setRowHeaderView(null);
        }
    }
    
    public static void viewNumeracion(int contador, boolean numeracion, ArrayList<JTextPane> textarea, ArrayList<JScrollPane> scroll) {
        if (numeracion) {
            for (int i = 0; i < contador; i++) {
                scroll.get(i).setRowHeaderView(new TextLineNumber(textarea.get(i)));
            }
        } else {
            for (int i = 0; i < contador; i++) {
                scroll.get(i).setRowHeaderView(null);
            }
        }
    }
//-----------------------------------------------------------------------------------------  
}
