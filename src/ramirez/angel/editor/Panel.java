/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ramirez.angel.editor;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author Angel Ramirez
 */
public final class Panel extends JPanel {

    //constrcutor
    public Panel() {
        //------------Menu--------------------
        JPanel panelMenu = new JPanel();

        menu = new JMenuBar();
        archivo = new JMenu("Archivo");
        editar = new JMenu("Editar");
        seleccion = new JMenu("Seleccion");
        ver = new JMenu("Ver");
        apariencia = new JMenu("Apariencia");

        menu.add(archivo);
        menu.add(editar);
        menu.add(seleccion);
        menu.add(ver);
        //-------------------

        //---------------Elementos del menu archivo------------------
        creaItem("Nuevo Archivo", "Archivo", "Nuevo");
        creaItem("Abrir Archivo", "Archivo", "Abrir");
        creaItem("Guardar", "Archivo", "Guardar");
        creaItem("Guardar Como", "Archivo", "Guardar como");
        //--------------------------------------------------------------

        //--------------------Elementos del menu editar--------------
        creaItem("Deshacer", "Editar", "");
        creaItem("Rehacer", "Editar", "");
        editar.addSeparator();
        creaItem("Cortar", "Editar", "");
        creaItem("Copiar", "Editar", "");
        creaItem("Pegar", "Editar", "");
        //-----------------------------------------------------------------

        //-------------------Elementos del menu seleccion---------------
        creaItem("Seleccionar Todos", "Seleccion", "");
        //------------------------------------------------------------

        //---------------Elementos del menu ver-----------------
        creaItem("Numeración", "Ver", "");
        ver.add(apariencia);
        creaItem("Normal", "Apariencia", "");
        creaItem("Dark", "Apariencia", "");
        //-------------------------------------

        //creacion del menu
        panelMenu.add(menu);

        //---------------Area de texto-----------------
        tpanel = new JTabbedPane();

        listFile = new ArrayList<File>();
        ListAreaText = new ArrayList<JTextPane>();
        listScroll = new ArrayList<JScrollPane>();
        //---------------------------------------------

        //--------------------Metodos de añadir-------------
        add(panelMenu);
        add(tpanel);
        //----------------------------------------------------
    }

    public void creaItem(String rotulo, String menu, String accion) {
        elementoItem = new JMenuItem(rotulo);

        if (menu.equals("Archivo")) {
            archivo.add(elementoItem);
            if (accion.equals("Nuevo")) {//evento al dar click al boton de nuevo:
                //se utiliza el metodo abstracto de ActionListener()
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        creaPanel();
                    }
                });
            } else if (accion.equals("Abrir")) {//acacion para abrir archivos y directorios
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {//FileChooser nos permite elejir los archivos
                        creaPanel();
                        JFileChooser selectorFile = new JFileChooser();
                        selectorFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        selectorFile.showOpenDialog(ListAreaText.get(tpanel.getSelectedIndex()));
                        int resultado = selectorFile.showOpenDialog(ListAreaText.get(tpanel.getSelectedIndex()));

                        if (resultado == JFileChooser.APPROVE_OPTION) {
                            try {
                                boolean existePath = false;
                                for (int i = 0; i < tpanel.getTabCount(); i++) {
                                    File f = selectorFile.getSelectedFile();
                                    if (listFile.get(i).getPath().equals(f.getPath())) {
                                        existePath = true;
                                    }
                                }
                                if (!existePath) {
                                    File archivo = selectorFile.getSelectedFile();
                                    listFile.set(tpanel.getSelectedIndex(), archivo);

                                    FileReader entrada = new FileReader(listFile.get(tpanel.getSelectedIndex()).getPath());
                                    BufferedReader myBuffer = new BufferedReader(entrada);
                                    String linea = "";
                                    String titulo = listFile.get(tpanel.getSelectedIndex()).getName();
                                    //el titulo se agrega a la pestaña del panel que se crea donde se encuentra archivo que el usuario selecciones
                                    tpanel.setTitleAt(tpanel.getSelectedIndex(), titulo);
                                    while (linea != null) {
                                        //en el ciclo se lee linea por linea del archivo y se almacena en el string linea
                                        linea = myBuffer.readLine();
                                        if (linea != null) //ListAreaText.get(tpanel.getSelectedIndex()).setText(linea);
                                        {
                                            Utilidades.append(linea + "\n", ListAreaText.get(tpanel.getSelectedIndex()));
                                        }
                                    }
                                } else {
                                    //si el archivo ya está abierto seleccionamos el panel donde está el texto
                                    for (int i = 0; i < tpanel.getTabCount(); i++) {
                                        File f = selectorFile.getSelectedFile();
                                        if (listFile.get(i).getPath().equals(f.getPath())) {
                                            tpanel.setSelectedIndex(i);
                                            //remueve el panel que se crea demas al abrir el mismo archivo
                                            ListAreaText.remove(tpanel.getTabCount() - 1);
                                            listScroll.remove(tpanel.getTabCount() - 1);
                                            listFile.remove(tpanel.getTabCount() - 1);
                                            tpanel.remove(tpanel.getTabCount() - 1);
                                            contadorPanel--;
                                            break;
                                        }
                                    }
                                }
                            } catch (IOException e1) {
                            }
                        } else {
                            //si el usuario cancela al elegir el archivo se elimina el area de texto que se crea por defecto
                            int seleccion = tpanel.getSelectedIndex();
                            if (seleccion != -1) {
                                ListAreaText.remove(tpanel.getTabCount() - 1);
                                listScroll.remove(tpanel.getTabCount() - 1);
                                listFile.remove(tpanel.getTabCount() - 1);
                                tpanel.remove(tpanel.getTabCount() - 1);
                                contadorPanel--;
                            }
                        }
                    }
                });
            } //-----------------Accion para guardar cambios en el archivo----------------
            else if (accion.equals("Guardar")) {
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //guardar si el archivo no existe
                        if (listFile.get(tpanel.getSelectedIndex()).getPath().equals("")) {
                            JFileChooser guardarArchivos = new JFileChooser();
                            int opc = guardarArchivos.showSaveDialog(null);

                            if (opc == JFileChooser.APPROVE_OPTION) {
                                File f = guardarArchivos.getSelectedFile();
                                listFile.set(tpanel.getSelectedIndex(), f);
                                tpanel.setTitleAt(tpanel.getSelectedIndex(), f.getName());

                                try {
                                    FileWriter fw = new FileWriter(listFile.get(tpanel.getSelectedIndex()).getPath());
                                    String txt = ListAreaText.get(tpanel.getSelectedIndex()).getText();

                                    for (int i = 0; i < txt.length(); i++) {
                                        fw.write(txt.charAt(i));
                                    }
                                    fw.close();
                                } catch (IOException ex) {
                                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        } else {
                            try {
                                FileWriter fw = new FileWriter(listFile.get(tpanel.getSelectedIndex()).getPath());
                                String txt = ListAreaText.get(tpanel.getSelectedIndex()).getText();

                                for (int i = 0; i < txt.length(); i++) {
                                    fw.write(txt.charAt(i));
                                }
                                fw.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });

            } else if (accion.equals("Guardar como")) {

            }
        } else if (menu.equals("Editar")) {
            editar.add(elementoItem);
        } else if (menu.equals("Seleccion")) {
            seleccion.add(elementoItem);
        } else if (menu.equals("Ver")) {
            ver.add(elementoItem);
        } else if (menu.equals("Apariencia")) {
            apariencia.add(elementoItem);
        }

    }

    public void creaPanel() {

        ventana = new JPanel();

        listFile.add(new File(""));
        ListAreaText.add(new JTextPane());
        listScroll.add(new JScrollPane(ListAreaText.get(contadorPanel)));

        ventana.add(listScroll.get(contadorPanel));

        tpanel.addTab("Archivo", ventana);
        tpanel.setSelectedIndex(contadorPanel);
        contadorPanel++;
        existenPanel = true;
    }

    //---------------------------Elementos no visibles-------------------------------
    private int contadorPanel = 0;//cuenta los paneles que se crean
    private boolean existenPanel = false;//nos dice si existe un panel creados
    //-------------------------------------------

    //-----------------------Elementos visuales------------------------------
    private JTabbedPane tpanel;
    private JPanel ventana;
    //private JTextPane areaText;   
    private ArrayList<JTextPane> ListAreaText;
    private ArrayList<File> listFile;
    private ArrayList<JScrollPane> listScroll;
    private JMenuBar menu;
    private JMenu archivo, editar, seleccion, ver, apariencia;
    private JMenuItem elementoItem;
}
