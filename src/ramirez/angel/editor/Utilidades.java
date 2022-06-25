/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ramirez.angel.editor;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author ar275
 */
public class Utilidades {

    public static void append(String linea, JTextPane areaText) {
        try {
            Document doc = areaText.getDocument();
            doc.insertString(doc.getLength(), linea, null);

        } catch (BadLocationException exc) {

        }
    }
}
