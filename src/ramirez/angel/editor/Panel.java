/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ramirez.angel.editor;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import javax.swing.undo.*;

/**
 * @author Angel Ramirez
 */
public final class Panel extends JPanel {
    //constrcutor
    public Panel(JFrame Ventana) {
        setLayout(new BorderLayout());
        
        //------------Menu--------------------
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        
        
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
        creaItem("Deshacer", "Editar", "deshacer");
        creaItem("Rehacer", "Editar", "rehacer");
        editar.addSeparator();
        creaItem("Cortar", "Editar", "cortar");
        creaItem("Copiar", "Editar", "copiar");
        creaItem("Pegar", "Editar", "pegar");
        //-----------------------------------------------------------------

        //-------------------Elementos del menu seleccion---------------
        creaItem("Seleccionar Todos", "Seleccion", "seleccion");
        //------------------------------------------------------------

        //---------------Elementos del menu ver-----------------
        creaItem("Numeración", "Ver", "numeracion");
        ver.add(apariencia);
        creaItem("Normal", "Apariencia", "normal");
        creaItem("Dark", "Apariencia", "dark");
        //-------------------------------------

        //creacion del menu
        panelMenu.add(menu, BorderLayout.NORTH);

        //---------------Area de texto-----------------
        tpanel = new JTabbedPane();

        listFile = new ArrayList<File>();
        ListAreaText = new ArrayList<JTextPane>();
        listScroll = new ArrayList<JScrollPane>();
        ListManager = new ArrayList<UndoManager>();
        //---------------------------------------------
        
        //-------------------Barra de herramientas--------------------------
        herramientas = new JToolBar(JToolBar.VERTICAL);//boton para eliminar la ventana
        url = Panel.class.getResource("/ramirez/angel/img/marca-x.png");
        Utilidades.addButton(url, herramientas, "Cerrar pestaña actual").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion=tpanel.getSelectedIndex();
                
                if(seleccion != -1){//si existen pestañas abiertas eliminamos la que esta seleccionada
                    listScroll.get(tpanel.getSelectedIndex()).setRowHeader(null);
                    tpanel.remove(seleccion);
                    ListAreaText.remove(seleccion);
                    listScroll.remove(seleccion);
                    ListManager.remove(seleccion);
                    listFile.remove(seleccion);
                    
                    contadorPanel--;
                    if(tpanel.getSelectedIndex() ==-1){
                        existenPanel=false;
                    }
                }
            }
        });
     
        //
        url= Panel.class.getResource("/ramirez/angel/img/mas (1).png");
        Utilidades.addButton(url, herramientas, "Nuevo archivo").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creaPanel();
            }
        });
        
        //------------------------------------------------------------------
        
        //---------------------------------Panel extra-------------------------------
        panelExtra= new JPanel();
        panelExtra.setLayout(new BorderLayout());
        //alfiler
        JPanel panelIzquierdo = new JPanel();
        labelAlfiler = new JLabel();
        labelAlfiler.setToolTipText("Fijar ventana");
        url=Panel.class.getResource("/ramirez/angel/img/alfiler.png");
        labelAlfiler.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        labelAlfiler.addMouseListener(new MouseAdapter() {
            //cambia de imagen cuando el cursor pasa sobre esta
            public void mouseEntered(MouseEvent e){
                url= Panel.class.getResource("/ramirez/angel/img/alfilerseleccion.png");
                labelAlfiler.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
                
            }
            //detecta cuando se deja de presionar
            public void mouseExited(MouseEvent e){
                if(estadoAlfiler){
                    url= Panel.class.getResource("/ramirez/angel/img/alfilerseleccion.png");
                    labelAlfiler.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
                }else{
                   url=Panel.class.getResource("/ramirez/angel/img/alfiler.png");
                    labelAlfiler.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH))); 
                }
            }
            //
            public void mousePressed(MouseEvent e){
                estadoAlfiler=! estadoAlfiler;
                Ventana.setAlwaysOnTop(estadoAlfiler);
            }
        });
    
        
        JPanel panelCentro = new JPanel();
        slider= new JSlider(8,38,14);
        panelCentro.add(slider);
        slider.setMajorTickSpacing(6); //separacion entre las barras mas grandes
        slider.setMinorTickSpacing(2);//separacion entre las barras mas pequeñas
        //slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setToolTipText("Tamaño de letra");//
        //accion para cambiar el tamaño de texto
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Utilidades.sizeText(slider.getValue(), contadorPanel, ListAreaText);
            }
        });
        
        panelIzquierdo.add(labelAlfiler);
        
        panelExtra.add(panelIzquierdo, BorderLayout.WEST);
        panelExtra.add(panelCentro, BorderLayout.CENTER);
        //------------------------------------------------------------------

        //--------------------Metodos de añadir-------------
        add(panelMenu, BorderLayout.NORTH);
        add(tpanel, BorderLayout.CENTER);
        add(herramientas, BorderLayout.WEST);
        add(panelExtra, BorderLayout.SOUTH);
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
                                    Utilidades.aBackground(contadorPanel, tipoStyle, ListAreaText, slider.getValue());
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
                        //guardar como si el archivo no existe
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
                            //si el archivo existe guardar los cambios
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
                //boton de guardare como
            } else if (accion.equals("Guardar como")) {
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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
                    }
                });
            }
        } else if (menu.equals("Editar")) {
            editar.add(elementoItem);
            if (accion.equals("deshacer")) {
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (ListManager.get(tpanel.getSelectedIndex()).canUndo())//if para verificar si se pueden deshacer cambios
                        {
                            ListManager.get(tpanel.getSelectedIndex()).undo();//undo para deshacer
                        }
                    }
                });
            } else if (accion.equals("rehacer")) {
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (ListManager.get(tpanel.getSelectedIndex()).canRedo())//if para verificar si se pueden rehacer cambios
                        {
                            ListManager.get(tpanel.getSelectedIndex()).redo();//redo para rehacer  
                        }
                    }
                });
                //DefaultEditorKit implementa clases para relizar las acciones de cortar, copiar y pegar
            } else if (accion.equals("cortar")) {
                elementoItem.addActionListener(new DefaultEditorKit.CutAction());
            } else if (accion.equals("copiar")) {
                elementoItem.addActionListener(new DefaultEditorKit.CopyAction());
            } else if (accion.equals("pegar")) {
                elementoItem.addActionListener(new DefaultEditorKit.PasteAction());
            }
        } else if (menu.equals("Seleccion")) {
            seleccion.add(elementoItem);
            if (accion.equals("seleccion")) {
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ListAreaText.get(tpanel.getSelectedIndex()).selectAll();
                    }
                });
            }
        } else if (menu.equals("Ver")) {
            ver.add(elementoItem);
            if(accion.equals("numeracion")){
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numeracion= ! numeracion;
                       Utilidades.viewNumeracion(contadorPanel, numeracion, ListAreaText, listScroll);
                    }
                });
            }           
        } else if (menu.equals("Apariencia")) { 
            apariencia.add(elementoItem);
            if(accion.equals("normal")){
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tipoStyle="W";
                        if(tpanel.getTabCount()>0){
                            Utilidades.aBackground(contadorPanel, tipoStyle, ListAreaText, slider.getValue());
                        }
                    }
                });
            }else if(accion.equals("dark")){
                elementoItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tipoStyle="D";
                        if(tpanel.getTabCount()>0){
                            Utilidades.aBackground(contadorPanel, tipoStyle, ListAreaText, slider.getValue());
                        }
                    }
                });
            }
            
        }
    }

    public void creaPanel() {

        ventana = new JPanel();
        ventana.setLayout(new BorderLayout());
        
        listFile.add(new File(""));
        ListAreaText.add(new JTextPane());
        listScroll.add(new JScrollPane(ListAreaText.get(contadorPanel)));
        ListManager.add(new UndoManager());//sirve para rastrear los cambios del area de texto
        
        ListAreaText.get(contadorPanel).getDocument().addUndoableEditListener(ListManager.get(contadorPanel));

        ventana.add(listScroll.get(contadorPanel), BorderLayout.CENTER);
        tpanel.addTab("Archivo", ventana);
        
        Utilidades.viewNumeracionInicio(numeracion, ListAreaText.get(contadorPanel), listScroll.get(contadorPanel));   
        tpanel.setSelectedIndex(contadorPanel);
        contadorPanel++;
        Utilidades.aBackground(contadorPanel, tipoStyle, ListAreaText, slider.getValue() );
        existenPanel = true;
    }

    //---------------------------Elementos no visibles-------------------------------
    private int contadorPanel = 0;//cuenta los paneles que se crean
    private boolean existenPanel = false;//nos dice si existe un panel creados
    private boolean numeracion=false;
    private String tipoStyle= "W";
    //-------------------------------------------------------------------------------

    //-----------------------Elementos visuales------------------------------    
    private JTabbedPane tpanel;
    private JPanel ventana;
    private JPanel panelExtra;
    //private JTextPane areaText;   
    private ArrayList<JTextPane> ListAreaText;
    private ArrayList<File> listFile;
    private ArrayList<JScrollPane> listScroll;
    private ArrayList<UndoManager> ListManager;
    private JMenuBar menu;
    private JMenu archivo, editar, seleccion, ver, apariencia;
    private JMenuItem elementoItem;
    private JToolBar herramientas;
    private URL url;
    
    private boolean estadoAlfiler=false;
    private JLabel labelAlfiler;
    private JSlider slider;
}
