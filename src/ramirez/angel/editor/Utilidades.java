/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ramirez.angel.editor;

import java.awt.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;


/**
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

//------------------------------Seccion de apariencia-------------------------------------
    public static void aBackground(int contador, String tipo, ArrayList<JTextPane> list, int size){
       if(tipo.equals("W")){
           for(int i=0; i<contador; i++){
               list.get(i).selectAll();
              StyleContext sc = StyleContext.getDefaultStyleContext();
              //accion para el color de texto
               AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
               
               //para el tipo de texto
               aset= sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
               //para el tamaño de texto
               aset=sc.addAttribute(aset, StyleConstants.FontSize, size);
           
                list.get(i).setCharacterAttributes(aset, true);
                list.get(i).setBackground(Color.white);
            }
        } else if (tipo.equals("D")) {
            for (int i = 0; i < contador; i++) {
                list.get(i).selectAll();
                StyleContext sc = StyleContext.getDefaultStyleContext();
                //accion para el color de texto
                AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, new Color(161,145,123));

                //para el tipo de texto
                aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
                //para el tamaño de texto
                aset=sc.addAttribute(aset, StyleConstants.FontSize, size);
                list.get(i).setCharacterAttributes(aset, true);
                list.get(i).setBackground(new Color(32,33,36));
            }
        }
    }
//--------------------------------------------------------------------------------------

//----------------------------------Button----------------------------------------------   
    public static JButton addButton(URL url, Object objContenedor, String rotulo){
        //cracion del boton 
        JButton button = new JButton(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button.setToolTipText(rotulo);
        
        ((Container) objContenedor).add(button);
        return button ;
    }
//--------------------------------------------------------------------------------------
    //Tamaño de texto
    public static void sizeText(int size, int contador, ArrayList<JTextPane> list){
        for (int i=0; i<contador; i++){
            //selecciona todo el texto del area del texto
            list.get(i).selectAll();
            //cambia el tamaño del texto
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,StyleConstants.FontSize,size);
            //
            list.get(i).setCharacterAttributes(aset, false);
        }
    }
    
}
