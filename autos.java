/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajes.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ESTUDIANTE
 */
public class autos extends javax.swing.JFrame {

    /**
     * Creates new form autos
     */
    ArrayList listaModelo = new ArrayList();
    ArrayList listaMarca = new ArrayList();
    DefaultTableModel model;

    public autos() {
        initComponents();
        bloquear();
        bloquearboton();
        cargarMarca();
        cargarTablaAutos("");
        spnAutAnio.setEditor(new JSpinner.DefaultEditor(spnAutAnio));
        spnAutCapacidad.setEditor(new JSpinner.DefaultEditor(spnAutCapacidad));

        tblAuto.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblAuto.getSelectedRow() != -1) {
                    try {
                        bloquearbotonModificar();
                        desbloquear();
                        conexion cc = new conexion();
                        Connection cn = cc.conectar();
                        String sql = "";                       
                        sql = "select * from marca, modelo";
                        int fila = tblAuto.getSelectedRow();
                        Statement psd = cn.createStatement();
                        ResultSet rs = psd.executeQuery(sql);
                                        
                            
                         cbxAutMarca.setSelectedItem(tblAuto.getValueAt(fila, 1).toString().trim());  
                       
                                  
                        cbxAutModelo.setSelectedItem(tblAuto.getValueAt(fila, 2).toString().trim());
                        System.out.println(tblAuto.getValueAt(fila, 2).toString().trim());
                        cbxAutColor.setSelectedItem(tblAuto.getValueAt(fila, 4).toString().trim());
                        txtAutPlaca.setText(tblAuto.getValueAt(fila, 0).toString().trim());
                        spnAutAnio.setValue(Integer.valueOf(tblAuto.getValueAt(fila, 3).toString().trim()));
                        
                        spnAutCapacidad.setValue(Integer.valueOf(tblAuto.getValueAt(fila, 5).toString()));
                        txtAutObservacion.setText(tblAuto.getValueAt(fila, 6).toString().trim());
                        txtAutPlaca.setEnabled(false);

                    } catch (SQLException ex) {
                        Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

    }

    public void bloquear() {
        txtAutPlaca.setEnabled(false);
        cbxAutMarca.setEnabled(false);
        cbxAutModelo.setEnabled(false);
        spnAutAnio.setEnabled(false);
        cbxAutColor.setEnabled(false);
        spnAutCapacidad.setEnabled(false);
        txtAutObservacion.setEnabled(false);
    }

    public void desbloquear() {
        txtAutPlaca.setEnabled(true);
        cbxAutMarca.setEnabled(true);
        cbxAutModelo.setEnabled(true);
        spnAutAnio.setEnabled(true);
        cbxAutColor.setEnabled(true);
        spnAutCapacidad.setEnabled(true);
        txtAutObservacion.setEnabled(true);
    }

    public void bloquearboton() {
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnSalir.setEnabled(true);
    }

    public void bloquearbotonModificar() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(true);
    }

    public void desbloquearBotonNuevo() {
        String obsVacia = "Sin Observacion";
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(true);
    }

    public void limpiarTextos() {
        txtAutPlaca.setText("");
        cbxAutMarca.setSelectedIndex(0);
        cbxAutModelo.removeAllItems();
        spnAutAnio.setValue(1960);
        cbxAutColor.setSelectedIndex(0);
        spnAutCapacidad.setValue(0);
        txtAutObservacion.setText("");
    }

    public void guardarAuto() {
        if (txtAutPlaca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese placa");
            txtAutPlaca.requestFocus();
        } else if (cbxAutMarca.getSelectedItem().equals("Seleccione..")) {
            JOptionPane.showMessageDialog(null, "Escoja Marca");
            cbxAutMarca.requestFocus();
        } else if (cbxAutModelo.getSelectedItem().equals("Seleccione..")) {
            JOptionPane.showMessageDialog(null, "Escoja Modelo");
            cbxAutModelo.requestFocus();
        } else if (Integer.valueOf(spnAutAnio.getValue().toString()) < 1960 || Integer.valueOf(spnAutAnio.getValue().toString()) > 2019) {
            JOptionPane.showMessageDialog(null, "El año debe estar entre 1960 y el actual");
            spnAutAnio.requestFocus();
        } else if (cbxAutColor.getSelectedItem().equals("Seleccione..")) {
            JOptionPane.showMessageDialog(null, "Escoja Color");
            cbxAutColor.requestFocus();
        } else if (Integer.valueOf(spnAutCapacidad.getValue().toString()) < 2 || Integer.valueOf(spnAutCapacidad.getValue().toString()) > 15) {
            JOptionPane.showMessageDialog(null, "La capacidad debe estra enntre 2 y 15");
            spnAutCapacidad.requestFocus();
        } else {
            try {
                String obsVacia = "Sin Observacion";
                String AUT_PLACA, MOD_CODIGO, AUT_COLOR, AUT_OBSERVACION;
                Integer AUT_ANIO, AUT_CAPACIDAD;
                conexion cc = new conexion();
                Connection cn = cc.conectar();
                String sql = "";
                AUT_PLACA = txtAutPlaca.getText();
                MOD_CODIGO = cbxAutModelo.getSelectedItem().toString().substring(0, 1);
                AUT_ANIO = Integer.valueOf(spnAutAnio.getValue().toString());
                AUT_COLOR = cbxAutColor.getSelectedItem().toString();
                AUT_CAPACIDAD = Integer.valueOf(spnAutCapacidad.getValue().toString());
                AUT_OBSERVACION = txtAutObservacion.getText();
                sql = "insert into "
                        + "auto(AUT_PLACA,MOD_CODIGO, AUT_ANIO,AUT_COLOR, AUT_CAPACIDAD,AUT_OBSERVACION) "
                        + "values(?,?,?,?,?,?)";
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, AUT_PLACA);
                psd.setString(2, MOD_CODIGO);
                psd.setInt(3, AUT_ANIO);
                psd.setString(4, AUT_COLOR);
                psd.setInt(5, AUT_CAPACIDAD);
                if (txtAutObservacion.getText().isEmpty()) {
                    psd.setString(6, obsVacia);
                } else {
                    psd.setString(6, AUT_OBSERVACION);
                }
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Ok Inserción Correcta");
                    limpiarTextos();
                    bloquear();
                    bloquearboton();
                    cargarTablaAutos("");

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void cargarMarca() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select * from marca";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                listaMarca.add(rs.getString("MAR_CODIGO"));
                String id = rs.getString("MAR_CODIGO");
                String marca = rs.getString("MAR_NOM");
                cbxAutMarca.addItem(marca);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void cargarTablaAutos(String dato) {
        String[] titulos = {"PLACA", "MARCA", "MODELO", "AÑO", "COLOR", " CAPACIDAD", "OBSERVACION"};
        String[] registros = new String[7];
        model = new DefaultTableModel(null, titulos);
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select auto.AUT_PLACA,"
                    + "auto.MOD_CODIGO, "
                    + "auto.AUT_ANIO,"
                    + "auto.AUT_COLOR, "
                    + "auto.AUT_CAPACIDAD,"
                    + "auto.AUT_OBSERVACION, "
                    + "modelo.MOD_NOMBRE, "
                    + "marca.MAR_NOM "
                    + "from auto, modelo,marca "
                    + "where auto.MOD_CODIGO=modelo.MOD_CODIGO and "
                    + "marca.MAR_CODIGO=modelo.MAR_CODIGO and "
                    + "auto.AUT_PLACA LIKE'%" + dato + "%'";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("AUT_PLACA");
                registros[1] = rs.getString("MAR_NOM");
                registros[2] = rs.getString("MOD_NOMBRE");
                registros[3] = rs.getString("AUT_ANIO");
                registros[4] = rs.getString("AUT_COLOR");
                registros[5] = rs.getString("AUT_CAPACIDAD");
                registros[6] = rs.getString("AUT_OBSERVACION");
                model.addRow(registros);
            }
            tblAuto.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void cargarModelo() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select * from modelo";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                listaModelo.add(rs.getString("MOD_CODIGO"));
                String idMarca = rs.getString("MAR_CODIGO");
                String nomMod = rs.getString("MOD_NOMBRE");
                int num = cbxAutMarca.getSelectedIndex() - 1;
                //listaMarca.set(num, rs.getString(1));
                if (cbxAutMarca.getSelectedItem().equals("Seleccione..")) {
                    cbxAutModelo.setSelectedItem("Seleccione..");
                } else {
                    if (idMarca.equals(listaMarca.get(num).toString())) {
                        cbxAutModelo.addItem(nomMod);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public void cargarModeloSQL() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            int item = cbxAutMarca.getSelectedIndex();
            sql = "select * from modelo where MAR_CODIGO = '" + item + "'";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                cbxAutModelo.addItem(rs.getString("MOD_NOMBRE"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
     private void soloDigitos(java.awt.event.KeyEvent evt) {                                  
               char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || Character.isLetter(c) || c == '') || txtAutPlaca.getText().length() > 5) {
            getToolkit().beep();
            evt.consume();
        } else if (Character.isLetter(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }

    }
     
      private void verficiarObservacion(java.awt.event.KeyEvent evt) {                                  
               char c = evt.getKeyChar();
        if ((Character.isDigit(c) ||  c == '') || txtAutObservacion.getText().length() < 0) {
            getToolkit().beep();
            evt.consume();
        } 

    }
      
      public void modificarAuto(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql="";
            sql="update auto "
                    + "set MOD_CODIGO='"+cbxAutModelo.getSelectedItem().toString()
                    +"',AUT_ANIO='"+spnAutAnio.getValue().toString().trim()
                    +"',AUT_COLOR='"+cbxAutColor.getSelectedItem().toString()
                    +"',AUT_CAPACIDAD='"+spnAutCapacidad.getValue().toString().trim()
                    +"' WHERE AUT_PLACA='" + txtAutPlaca.getText() + "'";
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Se modifico correctamente");
                cargarTablaAutos("");               
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAutPlaca = new javax.swing.JTextField();
        cbxAutModelo = new javax.swing.JComboBox<>();
        cbxAutMarca = new javax.swing.JComboBox<>();
        spnAutAnio = new javax.swing.JSpinner();
        cbxAutColor = new javax.swing.JComboBox<>();
        spnAutCapacidad = new javax.swing.JSpinner();
        txtAutObservacion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAuto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtBuscarxPlaca = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("PLACA");

        jLabel2.setText("MARCA");

        jLabel3.setText("MODELO");

        jLabel4.setText("AÑO");

        jLabel5.setText("COLOR");

        jLabel6.setText("CAPACIDAD");

        jLabel7.setText("OBSERVACIÓN");

        txtAutPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutPlacaKeyTyped(evt);
            }
        });

        cbxAutModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione.." }));

        cbxAutMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione.." }));
        cbxAutMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAutMarcaActionPerformed(evt);
            }
        });

        spnAutAnio.setModel(new javax.swing.SpinnerNumberModel());
        spnAutAnio.setEditor(new javax.swing.JSpinner.NumberEditor(spnAutAnio, ""));
        spnAutAnio.setEnabled(false);
        spnAutAnio.setValue(1960);

        cbxAutColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..", "Rojo", "Azul", "Beige", "Negro", "Plateado" }));
        cbxAutColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAutColorActionPerformed(evt);
            }
        });

        spnAutCapacidad.setValue(2);

        txtAutObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutObservacionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAutPlaca)
                            .addComponent(cbxAutMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxAutModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spnAutAnio)
                            .addComponent(cbxAutColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spnAutCapacidad)))
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAutObservacion)))
                .addContainerGap(424, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAutPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbxAutMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxAutModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(spnAutAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cbxAutColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spnAutCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtAutObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");

        btnCancelar.setText("Cancelar");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblAuto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblAuto);

        jLabel8.setText("Buscar Por Placa");

        txtBuscarxPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarxPlacaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarxPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtBuscarxPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarAuto();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        desbloquear();
        desbloquearBotonNuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void cbxAutMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxAutMarcaActionPerformed
        // TODO add your handling code here:
        cbxAutModelo.removeAllItems();
        //cargarModelo();
        cargarModeloSQL();

    }//GEN-LAST:event_cbxAutMarcaActionPerformed

    private void txtBuscarxPlacaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarxPlacaKeyReleased
        // TODO add your handling code here:
        cargarTablaAutos(txtBuscarxPlaca.getText());
    }//GEN-LAST:event_txtBuscarxPlacaKeyReleased

    private void cbxAutColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxAutColorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxAutColorActionPerformed

    private void txtAutPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutPlacaKeyTyped
        // TODO add your handling code here:
        soloDigitos(evt);
    }//GEN-LAST:event_txtAutPlacaKeyTyped

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtAutObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutObservacionKeyTyped
        // TODO add your handling code here:
        verficiarObservacion(evt);
    }//GEN-LAST:event_txtAutObservacionKeyTyped

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        modificarAuto();
    }//GEN-LAST:event_btnModificarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new autos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbxAutColor;
    private javax.swing.JComboBox<String> cbxAutMarca;
    private javax.swing.JComboBox<String> cbxAutModelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JSpinner spnAutAnio;
    private javax.swing.JSpinner spnAutCapacidad;
    private javax.swing.JTable tblAuto;
    private javax.swing.JTextField txtAutObservacion;
    private javax.swing.JTextField txtAutPlaca;
    private javax.swing.JTextField txtBuscarxPlaca;
    // End of variables declaration//GEN-END:variables
}
