
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author METREF ROA
 */
public class Admin extends javax.swing.JFrame {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    /**
     * Creates new form Admin
     */
    public Admin() {
        initComponents();
        conn = db.java_db();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2,
                size.height / 2 - getHeight() / 2);

        Update_table_patient();
        Update_table_employe();
        update_Table_Reservation();
        update_Table_consultation();
        ResAnnulee();
    }

    private void Update_table_patient() {
        try {

            String sql = "SELECT * FROM patient ";

            // Create a PreparedStatement object to execute the query
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Execute the query and get the ResultSet object
            ResultSet rs = pstmt.executeQuery();

            // Get the TableModel object from the JTable
            DefaultTableModel model = (DefaultTableModel) patient_table.getModel();

            // Clear the existing data from the model
            model.setRowCount(0);

            // Loop through the ResultSet and add each row to the model
            while (rs.next()) {
                String nss = rs.getString("nss");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String adresse = rs.getString("adresse");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                Date date_naissance = rs.getDate("date_naissance");
                Object[] row = {nss, nom, prenom, date_naissance, adresse, telephone, email};

                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }

    private void clear_patient() {
        NSSTextField.setText("");
        NomPatientTextField.setText("");
        PrenomPatientTextField.setText("");
        DatePatientTextField.setText("");
        AdressePatientTextField.setText("");
        TelphonePatientTextField.setText("");
        EmailPatientTextField.setText("");

    }

    private void clear_employe() {
        IDtext.setText("0");
        nomEmployeTextField.setText("");
        prenomEmployeTextField.setText("");
        specialiteEmployeTextField.setText("");
        ancienneteEmployeTextField.setText("");
        loginEmployeTextField.setText("");
        passwordEmployeTextField.setText("");
        SecteurEmployeComboBox.setSelectedIndex(0);

    }

    private void Update_table_employe() {
        try {

            String sql = "SELECT * FROM employe";

            // Create a PreparedStatement object to execute the query
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Execute the query and get the ResultSet object
            ResultSet rs = pstmt.executeQuery();

            // Get the TableModel object from the JTable
            DefaultTableModel model = (DefaultTableModel) employe_table.getModel();

            // Clear the existing data from the model
            model.setRowCount(0);

            // Loop through the ResultSet and add each row to the model
            while (rs.next()) {
                int id = rs.getInt("code_employe");
                int anciennete = rs.getInt("anciennete");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String secteur = rs.getString("secteur");
                String specialite = rs.getString("specialite");
                String login = rs.getString("login");
                String password = rs.getString("password");
                Object[] row = {id, nom, prenom, secteur, specialite, anciennete, login, password};

                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }

    private void update_Table_Reservation() {
        try {
            String sql = "SELECT numero_reservation, nss, date_reservation, TIME(date_reservation) AS heur, type, service, etat FROM reservation where etat<2";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) reservation_Table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int numRes = rs.getInt("numero_reservation");
                String nss = rs.getString("nss");
                Date date = rs.getDate("date_reservation");
                String heure = rs.getString("heur");
                heure = heure.substring(0, 5);
                String type = rs.getString("type");
                String service = rs.getString("service");
                int etat = rs.getInt("etat");
                Object[] row = {numRes, nss, date, heure, type, service, etat};

                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void clear_rservation() {

        NumResText.setText("0");
        nssResTextField.setText("");
        dateResTextField.setText("");
        heureResTextField.setText("");
        serviceResTextField.setText("");
        TypeResComboBox.setSelectedIndex(0);
        EtatResComboBox.setSelectedIndex(0);
    }

    private void update_Table_consultation() {
        try {
            String sql = "SELECT numero_consultation, nss, date_consultation, TIME(date_consultation) AS heur, service FROM consultation where etat='terminé'";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) consultation_Table.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int numRes = rs.getInt("numero_consultation");
                String nss = rs.getString("nss");
                Date date = rs.getDate("date_consultation");
                String heure = rs.getString("heur");
                heure = heure.substring(0, 5);
                String service = rs.getString("service");
                Object[] row = {numRes, nss, date, heure, service};

                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    private void clear_avis(){
        NSSjLabel.setText("0");
        CommentairejTextArea.setText("");
        NotejTextField.setText("");
        numConText.setText("0");
    }
    public void ResAnnulee() {


        ResultSet rs = null;
        try {
            String sql = "select count(*) as nb from reservation where etat=2;";

                // Create a PreparedStatement object to execute the query
                PreparedStatement pstmt = conn.prepareStatement(sql);

                // Execute the query and get the ResultSet object
                rs = pstmt.executeQuery();
            while (rs.next()) {
                String nb = rs.getString("nb");
                ResAnul.setText(nb);
                
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
    public void nbrvisiteEmp(){


        ResultSet rs = null;
        try {
            int id =Integer.parseInt(idEmp.getText());
            String sql = "SELECT count(*) as nb FROM consultation where code_employe="+id;

                // Create a PreparedStatement object to execute the query
                PreparedStatement pstmt = conn.prepareStatement(sql);

                // Execute the query and get the ResultSet object
                rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String nb = rs.getString("nb");
                nbVisites.setText(nb);
                
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "employee n'existe pas ou il a pas de visites");
        }
    }
    public void nbrvisitePat(){


        ResultSet rs = null;
        try {
            String id =nsspat.getText();
            
            String sql = "SELECT count(*) as nb FROM consultation where  nss='"+id+"'";


                // Create a PreparedStatement object to execute the query
                PreparedStatement pstmt = conn.prepareStatement(sql);

                // Execute the query and get the ResultSet object
                rs = pstmt.executeQuery();
            while (rs.next()) {
                String nb = rs.getString("nb");
                
                nbVisitesPat.setText(nb);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Patient n'existe pas ou il a pas de visites");
System.out.println(ex);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        employe_table = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        IDtext = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        nomEmployeTextField = new javax.swing.JTextField();
        prenomEmployeTextField = new javax.swing.JTextField();
        specialiteEmployeTextField = new javax.swing.JTextField();
        SecteurEmployeComboBox = new javax.swing.JComboBox<>();
        ancienneteEmployeTextField = new javax.swing.JTextField();
        loginEmployeTextField = new javax.swing.JTextField();
        passwordEmployeTextField = new javax.swing.JTextField();
        AjouterEmployeButton = new javax.swing.JButton();
        clearEmployeButton = new javax.swing.JButton();
        ModifierEmployeButton = new javax.swing.JButton();
        DeleteEmployeButton = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        SearchButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        NomPatientTextField = new javax.swing.JTextField();
        PrenomPatientTextField = new javax.swing.JTextField();
        AjouterPatient = new javax.swing.JButton();
        clearPatient = new javax.swing.JButton();
        ModifierPatient = new javax.swing.JButton();
        SupprimerPatient = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        NSSTextField = new javax.swing.JTextField();
        DatePatientTextField = new javax.swing.JTextField();
        AdressePatientTextField = new javax.swing.JTextField();
        TelphonePatientTextField = new javax.swing.JTextField();
        EmailPatientTextField = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        patient_table = new javax.swing.JTable();
        searchTextField1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        SearchButton1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        nssResTextField = new javax.swing.JTextField();
        dateResTextField = new javax.swing.JTextField();
        AjouterButton2 = new javax.swing.JButton();
        clearButton2 = new javax.swing.JButton();
        ModifierButton2 = new javax.swing.JButton();
        DeleteButton2 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        heureResTextField = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        reservation_Table = new javax.swing.JTable();
        searchTextField2 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        SearchButton2 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        NumResText = new javax.swing.JLabel();
        TypeResComboBox = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        serviceResTextField = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        EtatResComboBox = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        clearButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        consultation_Table = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        numConText = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        NotejTextField = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        CommentairejTextArea = new javax.swing.JTextArea();
        NSSjLabel = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        ResAnul = new javax.swing.JLabel();
        nbVisites = new javax.swing.JLabel();
        nbVisitesPat = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        idEmp = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        nsspat = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        searchTextField4 = new javax.swing.JTextField();
        SearchButton4 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(210, 226, 242));

        jPanel7.setBackground(new java.awt.Color(210, 226, 242));

        employe_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nom", "Prenom", "Secteur", "Specialite", "Anciennete", "Login", "Password"
            }
        ));
        employe_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employe_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(employe_table);

        jLabel8.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel8.setText("ID : ");

        jLabel3.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel3.setText("Nom :");

        jLabel6.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel6.setText("Prénom :");

        jLabel7.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel7.setText("Secteur :");

        jLabel11.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel11.setText("Spécialité :");

        jLabel12.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel12.setText("Ancienneté :");

        jLabel13.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel13.setText("Username :");

        IDtext.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        IDtext.setText("0");

        jLabel14.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel14.setText("Password :");

        nomEmployeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomEmployeTextFieldActionPerformed(evt);
            }
        });

        prenomEmployeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenomEmployeTextFieldActionPerformed(evt);
            }
        });

        specialiteEmployeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specialiteEmployeTextFieldActionPerformed(evt);
            }
        });

        SecteurEmployeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Médical", "Paramédical" }));

        ancienneteEmployeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ancienneteEmployeTextFieldActionPerformed(evt);
            }
        });

        loginEmployeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginEmployeTextFieldActionPerformed(evt);
            }
        });

        passwordEmployeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordEmployeTextFieldActionPerformed(evt);
            }
        });

        AjouterEmployeButton.setText("Ajouter");
        AjouterEmployeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterEmployeButtonActionPerformed(evt);
            }
        });

        clearEmployeButton.setBackground(new java.awt.Color(240, 240, 240));
        clearEmployeButton.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        clearEmployeButton.setText("RESET");
        clearEmployeButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102), 3));
        clearEmployeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearEmployeButtonActionPerformed(evt);
            }
        });

        ModifierEmployeButton.setText("Modifier");
        ModifierEmployeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierEmployeButtonActionPerformed(evt);
            }
        });

        DeleteEmployeButton.setText("Supprimer");
        DeleteEmployeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteEmployeButtonActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 102));
        jLabel18.setText("Gestion des Employés");

        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyReleased(evt);
            }
        });

        SearchButton.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        SearchButton.setText("Entrer");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel1.setText("ID :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(25, 25, 25)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(85, 85, 85)
                                    .addComponent(specialiteEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7))
                                    .addGap(104, 104, 104)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(SecteurEmployeComboBox, 0, 328, Short.MAX_VALUE)
                                        .addComponent(IDtext)
                                        .addComponent(prenomEmployeTextField)
                                        .addComponent(nomEmployeTextField)))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14))
                                    .addGap(81, 81, 81)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(loginEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passwordEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ancienneteEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(147, 147, 147))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(AjouterEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(DeleteEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(ModifierEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(clearEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(37, 37, 37)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addGap(14, 14, 14))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(IDtext))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nomEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(prenomEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(SecteurEmployeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(specialiteEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(ancienneteEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(loginEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(passwordEmployeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(81, 81, 81)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AjouterEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModifierEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearEmployeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Employés", jPanel4);

        jPanel9.setBackground(new java.awt.Color(210, 226, 242));

        jLabel20.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel20.setText("NSS :");

        jLabel21.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel21.setText("Nom :");

        jLabel22.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel22.setText("Prénom :");

        jLabel23.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel23.setText("Adresse :");

        jLabel24.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel24.setText("Téléphone :");

        jLabel25.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel25.setText("Mail :");

        NomPatientTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomPatientTextFieldActionPerformed(evt);
            }
        });

        PrenomPatientTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrenomPatientTextFieldActionPerformed(evt);
            }
        });

        AjouterPatient.setText("Ajouter");
        AjouterPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterPatientActionPerformed(evt);
            }
        });

        clearPatient.setBackground(new java.awt.Color(240, 240, 240));
        clearPatient.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        clearPatient.setText("RESET");
        clearPatient.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102), 3));
        clearPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearPatientActionPerformed(evt);
            }
        });

        ModifierPatient.setText("Modifier");
        ModifierPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierPatientActionPerformed(evt);
            }
        });

        SupprimerPatient.setText("Supprimer");
        SupprimerPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupprimerPatientActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel26.setText("Date de naissance :");

        jLabel27.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 51, 102));
        jLabel27.setText("Gestion des Patients");

        NSSTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NSSTextFieldActionPerformed(evt);
            }
        });

        DatePatientTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DatePatientTextFieldActionPerformed(evt);
            }
        });

        AdressePatientTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdressePatientTextFieldActionPerformed(evt);
            }
        });

        TelphonePatientTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TelphonePatientTextFieldActionPerformed(evt);
            }
        });

        EmailPatientTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailPatientTextFieldActionPerformed(evt);
            }
        });

        patient_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "NSS", "Nom", "Prenom", "DateN", "Adresse", "Telephone", "Email"
            }
        ));
        patient_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patient_tableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(patient_table);

        searchTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextField1KeyReleased(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Eras Medium ITC", 0, 24)); // NOI18N
        jLabel19.setText("NSS :");

        SearchButton1.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        SearchButton1.setText("Entrer");
        SearchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AdressePatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DatePatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PrenomPatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20)
                                .addComponent(jLabel21))
                            .addGap(140, 140, 140)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(NomPatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(NSSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel25)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(EmailPatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addGap(89, 89, 89)
                            .addComponent(TelphonePatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(AjouterPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(SupprimerPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(ModifierPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SearchButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SearchButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(searchTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(NSSTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(NomPatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(PrenomPatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(DatePatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(AdressePatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(TelphonePatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(EmailPatientTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AjouterPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SupprimerPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModifierPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(70, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Patients", jPanel8);

        jPanel11.setBackground(new java.awt.Color(210, 226, 242));

        jLabel28.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel28.setText("NSS :");

        jLabel31.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel31.setText("Heure :");

        nssResTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nssResTextFieldActionPerformed(evt);
            }
        });

        dateResTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateResTextFieldActionPerformed(evt);
            }
        });

        AjouterButton2.setText("Ajouter");
        AjouterButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterButton2ActionPerformed(evt);
            }
        });

        clearButton2.setBackground(new java.awt.Color(240, 240, 240));
        clearButton2.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        clearButton2.setText("RESET");
        clearButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102), 3));
        clearButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton2ActionPerformed(evt);
            }
        });

        ModifierButton2.setText("Modifier");
        ModifierButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierButton2ActionPerformed(evt);
            }
        });

        DeleteButton2.setText("Annuler");
        DeleteButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButton2ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel34.setText("Date :");

        jLabel35.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 51, 102));
        jLabel35.setText("Gestion des Reservations");

        reservation_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "num_res", "NSS", "Date", "Heure", "Type", "Service", "Etat"
            }
        ));
        reservation_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reservation_TableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(reservation_Table);

        searchTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextField2KeyReleased(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Eras Medium ITC", 0, 24)); // NOI18N
        jLabel36.setText("NSS :");

        SearchButton2.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        SearchButton2.setText("Entrer");
        SearchButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButton2ActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel29.setText("Numero Reservation :");

        NumResText.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        NumResText.setText("0");

        TypeResComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Médical", "Paramédical" }));

        jLabel33.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel33.setText("Type :");

        jLabel37.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel37.setText("Service :");

        serviceResTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceResTextFieldActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel38.setText("Etat :");

        EtatResComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Non confirmée", "confirmée" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(AjouterButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(DeleteButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(ModifierButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(clearButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(NumResText))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(64, 64, 64)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(heureResTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateResTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nssResTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TypeResComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(serviceResTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EtatResComboBox, 0, 300, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(searchTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(NumResText))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nssResTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(dateResTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel34)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(heureResTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(TypeResComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(serviceResTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(EtatResComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AjouterButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ModifierButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Reservations", jPanel10);

        jPanel14.setBackground(new java.awt.Color(210, 226, 242));

        jToggleButton1.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        jToggleButton1.setText("AJOUTER");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        clearButton3.setBackground(new java.awt.Color(240, 240, 240));
        clearButton3.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        clearButton3.setText("RESET");
        clearButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102), 3));
        clearButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Consultations terminer");

        consultation_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "num_consultation", "NSS", "Date", "Heure", "Service"
            }
        ));
        consultation_Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                consultation_TableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(consultation_Table);

        jLabel39.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel39.setText("Note:");

        numConText.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        numConText.setText("0");

        jLabel40.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel40.setText("Numero Consultation :");

        NotejTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotejTextFieldActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel41.setText("Commentaire:");

        CommentairejTextArea.setColumns(20);
        CommentairejTextArea.setRows(5);
        jScrollPane2.setViewportView(CommentairejTextArea);

        NSSjLabel.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        NSSjLabel.setText("0");

        jLabel43.setFont(new java.awt.Font("Eras Medium ITC", 0, 20)); // NOI18N
        jLabel43.setText("NSS:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41)
                            .addComponent(jLabel39)
                            .addComponent(jLabel43)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(clearButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(numConText))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NSSjLabel)
                                    .addComponent(NotejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane7)
                        .addContainerGap())
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(numConText))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(NSSjLabel))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(NotejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel41)
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton1)
                            .addComponent(clearButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Feedback", jPanel13);

        jPanel5.setBackground(new java.awt.Color(210, 226, 242));

        jButton2.setText("calculer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        ResAnul.setText("0");

        nbVisites.setText("0");

        nbVisitesPat.setText("0");

        jLabel4.setText("entrer id d'employe :");

        jButton1.setText("calculer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setText("taux de réservations annulées:");

        jLabel15.setText("NSS :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(ResAnul))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nsspat, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(idEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(74, 74, 74)
                                .addComponent(nbVisites))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nbVisitesPat)))))
                .addContainerGap(873, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(idEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(nbVisites))
                .addGap(46, 46, 46)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nsspat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)
                        .addComponent(nbVisitesPat)))
                .addGap(48, 48, 48)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ResAnul))
                .addContainerGap(401, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Voir Statisitique", jPanel2);

        jPanel12.setBackground(new java.awt.Color(0, 51, 102));

        searchTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextField4KeyReleased(evt);
            }
        });

        SearchButton4.setFont(new java.awt.Font("Eras Medium ITC", 0, 18)); // NOI18N
        SearchButton4.setText("Entrer");
        SearchButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButton4ActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Eras Medium ITC", 0, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("NSS :");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1096, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(245, 245, 245))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addGap(11, 11, 11)
                .addComponent(jLabel9)
                .addContainerGap())
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(SearchButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1483, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchButton2ActionPerformed

    private void searchTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextField2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextField2KeyReleased

    private void reservation_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reservation_TableMouseClicked
        int row = reservation_Table.getSelectedRow();
        String num_res = reservation_Table.getModel().getValueAt(row, 0).toString();
        String nss = reservation_Table.getModel().getValueAt(row, 1).toString();
        String date = reservation_Table.getModel().getValueAt(row, 2).toString();
        String time = reservation_Table.getModel().getValueAt(row, 3).toString();
        String type = reservation_Table.getModel().getValueAt(row, 4).toString();
        String service = reservation_Table.getModel().getValueAt(row, 5).toString();
        String etat = reservation_Table.getModel().getValueAt(row, 6).toString();

        // Set the values in the text fields and combo boxes
        NumResText.setText(num_res);
        nssResTextField.setText(nss);
        dateResTextField.setText(date);
        heureResTextField.setText(time);
        serviceResTextField.setText(service);

        // Set the selected value in the TypeResComboBox
        if (type.toLowerCase().equals("medical") || type.toLowerCase().equals("médical")) {
            TypeResComboBox.setSelectedIndex(0);
        } else {
            TypeResComboBox.setSelectedIndex(1);
        }

        // Set the selected value in the EtatResComboBox
        if (etat.equals("0")) {
            EtatResComboBox.setSelectedIndex(0);
        } else if (etat.equals("1")) {
            EtatResComboBox.setSelectedIndex(1);
        }
    }//GEN-LAST:event_reservation_TableMouseClicked

    private void DeleteButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButton2ActionPerformed
        try {
            String sql = "update reservation set etat=2 WHERE numero_reservation = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(NumResText.getText()));

            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            update_Table_Reservation();
            clear_rservation();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        } finally {
            try {
//                pstmt.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }//GEN-LAST:event_DeleteButton2ActionPerformed

    private void ModifierButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierButton2ActionPerformed

        try {
            String sql = "UPDATE reservation SET nss = ?, date_reservation = ?, type = ?, service = ?, etat = ? WHERE numero_reservation = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nssResTextField.getText());
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(dateResTextField.getText() + " " + heureResTextField.getText() + ":00"));
            pstmt.setString(3, TypeResComboBox.getSelectedItem().toString());
            pstmt.setString(4, serviceResTextField.getText());
            if (EtatResComboBox.getSelectedIndex() == 0) {
                pstmt.setInt(5, 0);
            } else {
                pstmt.setInt(5, 1);
            }

            pstmt.setInt(6, Integer.parseInt(NumResText.getText()));

            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            update_Table_Reservation();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
//                pstmt.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }//GEN-LAST:event_ModifierButton2ActionPerformed

    private void clearButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton2ActionPerformed
        clear_rservation();
    }//GEN-LAST:event_clearButton2ActionPerformed

    private void AjouterButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterButton2ActionPerformed
        try {
            String sql = "INSERT INTO reservation (nss, date_reservation, type, service, etat) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nssResTextField.getText());
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(dateResTextField.getText() + " " + heureResTextField.getText() + ":00"));
            pstmt.setString(3, TypeResComboBox.getSelectedItem().toString());
            pstmt.setString(4, serviceResTextField.getText());
            if (EtatResComboBox.getSelectedIndex() == 0) {
                pstmt.setInt(5, 0);
            } else {
                pstmt.setInt(5, 1);
            }

// Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            update_Table_Reservation();
            clear_rservation();

        } catch (Exception e) {
            String nssE = "java.sql.SQLIntegrityConstraintViolationException: Cannot add or update a child row: a foreign key constraint fails (`esante`.`reservation`, CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`nss`) REFERENCES `patient` (`nss`))";
            if (e.toString().contains(nssE)) {
                JOptionPane.showMessageDialog(null, "patient n'existe pas");
            } else {
                JOptionPane.showMessageDialog(null, e);
            }

        } finally {
            try {
//                pstmt.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }//GEN-LAST:event_AjouterButton2ActionPerformed

    private void dateResTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateResTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateResTextFieldActionPerformed

    private void nssResTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nssResTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nssResTextFieldActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        try {
            String d = searchTextField.getText();

            String sql = "SELECT * FROM patient where nss= ? ";

            // Create a PreparedStatement object to execute the query
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, d);

            // Execute the query and get the ResultSet object
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                String nss = rs.getString("nss");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String adresse = rs.getString("adresse");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                String date = rs.getDate("date_naissance").toString();

                NSSTextField.setText(nss);
                NomPatientTextField.setText(nom);
                PrenomPatientTextField.setText(prenom);
                DatePatientTextField.setText(date);
                AdressePatientTextField.setText(adresse);
                TelphonePatientTextField.setText(telephone);
                EmailPatientTextField.setText(email);
            } else {
                // Handle case where no row is returned
                JOptionPane.showMessageDialog(null, "patient n'existe pas");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_SearchButtonActionPerformed

    private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyReleased

    }//GEN-LAST:event_searchTextFieldKeyReleased

    private void DeleteEmployeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteEmployeButtonActionPerformed
        try {
            String sql = "DELETE FROM employe WHERE code_employe = " + IDtext.getText();

            // Create a PreparedStatement object to execute the query
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            Update_table_employe();
            clear_employe();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_DeleteEmployeButtonActionPerformed

    private void ModifierEmployeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierEmployeButtonActionPerformed
        try {
            String sql = "UPDATE employe SET nom = ?, prenom = ?, secteur = ?, specialite = ?, anciennete = ?, login = ?, password = ? WHERE code_employe = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nomEmployeTextField.getText());
            pstmt.setString(2, this.prenomEmployeTextField.getText());
            pstmt.setString(3, SecteurEmployeComboBox.getSelectedItem().toString());
            pstmt.setString(4, specialiteEmployeTextField.getText());
            pstmt.setInt(5, Integer.parseInt(ancienneteEmployeTextField.getText()));
            pstmt.setString(6, loginEmployeTextField.getText());
            pstmt.setString(7, passwordEmployeTextField.getText());
            pstmt.setInt(8, Integer.parseInt(IDtext.getText()));

            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            Update_table_employe();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_ModifierEmployeButtonActionPerformed

    private void clearEmployeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearEmployeButtonActionPerformed
        clear_employe();
    }//GEN-LAST:event_clearEmployeButtonActionPerformed

    private void AjouterEmployeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterEmployeButtonActionPerformed
        try {
            String sql = "INSERT INTO employe(nom,prenom,secteur,specialite,anciennete,login,password) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nomEmployeTextField.getText());
            pstmt.setString(2, prenomEmployeTextField.getText());
            pstmt.setString(3, SecteurEmployeComboBox.getSelectedItem().toString());
            pstmt.setString(4, specialiteEmployeTextField.getText());
            pstmt.setInt(5, Integer.parseInt(ancienneteEmployeTextField.getText()));
            pstmt.setString(6, loginEmployeTextField.getText());
            pstmt.setString(7, passwordEmployeTextField.getText());

            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            Update_table_employe();
            clear_employe();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_AjouterEmployeButtonActionPerformed

    private void passwordEmployeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordEmployeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordEmployeTextFieldActionPerformed

    private void loginEmployeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginEmployeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginEmployeTextFieldActionPerformed

    private void ancienneteEmployeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ancienneteEmployeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ancienneteEmployeTextFieldActionPerformed

    private void specialiteEmployeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specialiteEmployeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_specialiteEmployeTextFieldActionPerformed

    private void prenomEmployeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenomEmployeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenomEmployeTextFieldActionPerformed

    private void nomEmployeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomEmployeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomEmployeTextFieldActionPerformed

    private void employe_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employe_tableMouseClicked
        int row = employe_table.getSelectedRow();
        String id = employe_table.getModel().getValueAt(row, 0).toString();
        String nom = employe_table.getModel().getValueAt(row, 1).toString();
        String prenom = employe_table.getModel().getValueAt(row, 2).toString();
        String secteur = employe_table.getModel().getValueAt(row, 3).toString();
        String specialite;
        if (secteur.toLowerCase().equals("medical") || secteur.toLowerCase().equals("médical")) {
            SecteurEmployeComboBox.setSelectedIndex(0);
            specialite = employe_table.getModel().getValueAt(row, 4).toString();
        } else {
            SecteurEmployeComboBox.setSelectedIndex(1);
            specialite = "";
        }
        String anciennete = employe_table.getModel().getValueAt(row, 5).toString();
        String login = employe_table.getModel().getValueAt(row, 6).toString();
        String password = employe_table.getModel().getValueAt(row, 7).toString();

        IDtext.setText(id);
        nomEmployeTextField.setText(nom);
        prenomEmployeTextField.setText(prenom);
        specialiteEmployeTextField.setText(specialite);
        ancienneteEmployeTextField.setText(anciennete);
        loginEmployeTextField.setText(login);
        passwordEmployeTextField.setText(password);
    }//GEN-LAST:event_employe_tableMouseClicked

    private void SearchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchButton1ActionPerformed

    private void searchTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextField1KeyReleased

    }//GEN-LAST:event_searchTextField1KeyReleased

    private void patient_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patient_tableMouseClicked
        int row = patient_table.getSelectedRow();
        String nss = patient_table.getModel().getValueAt(row, 0).toString();
        String nom = patient_table.getModel().getValueAt(row, 1).toString();
        String prenom = patient_table.getModel().getValueAt(row, 2).toString();
        String date = patient_table.getModel().getValueAt(row, 3).toString();
        String adresse = patient_table.getModel().getValueAt(row, 4).toString();
        String telephone = patient_table.getModel().getValueAt(row, 5).toString();
        String email = patient_table.getModel().getValueAt(row, 6).toString();

        NSSTextField.setText(nss);
        NomPatientTextField.setText(nom);
        PrenomPatientTextField.setText(prenom);
        DatePatientTextField.setText(date);
        AdressePatientTextField.setText(adresse);
        TelphonePatientTextField.setText(telephone);
        EmailPatientTextField.setText(email);
    }//GEN-LAST:event_patient_tableMouseClicked

    private void EmailPatientTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailPatientTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailPatientTextFieldActionPerformed

    private void TelphonePatientTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TelphonePatientTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TelphonePatientTextFieldActionPerformed

    private void AdressePatientTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdressePatientTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdressePatientTextFieldActionPerformed

    private void DatePatientTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DatePatientTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DatePatientTextFieldActionPerformed

    private void NSSTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NSSTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NSSTextFieldActionPerformed

    private void SupprimerPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupprimerPatientActionPerformed
        try {
            String sql = "DELETE FROM patient WHERE nss = ?";

            // Create a PreparedStatement object to execute the query
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, NSSTextField.getText());
            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            Update_table_patient();
            clear_patient();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_SupprimerPatientActionPerformed

    private void ModifierPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierPatientActionPerformed
        try {
            String sql = "UPDATE patient SET nom = ?, prenom = ?, date_naissance = ?, adresse = ?, telephone = ?, email = ? WHERE nss = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            Date date = Date.valueOf(DatePatientTextField.getText());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            pstmt.setString(1, NomPatientTextField.getText());
            pstmt.setString(2, PrenomPatientTextField.getText());
            pstmt.setDate(3, sqlDate);
            pstmt.setString(4, AdressePatientTextField.getText());
            pstmt.setString(5, TelphonePatientTextField.getText());
            pstmt.setString(6, EmailPatientTextField.getText());
            pstmt.setString(7, NSSTextField.getText());

            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            Update_table_patient();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_ModifierPatientActionPerformed

    private void clearPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearPatientActionPerformed
        clear_patient();
    }//GEN-LAST:event_clearPatientActionPerformed

    private void AjouterPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterPatientActionPerformed
        try {
            String sql = "INSERT INTO patient(nss,nom,prenom,date_naissance,adresse,telephone,email) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            Date date = Date.valueOf(DatePatientTextField.getText());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            pstmt.setString(1, NSSTextField.getText());
            pstmt.setString(2, PrenomPatientTextField.getText());
            pstmt.setString(3, NomPatientTextField.getText());
            pstmt.setDate(4, sqlDate);
            pstmt.setString(5, AdressePatientTextField.getText());
            pstmt.setString(6, TelphonePatientTextField.getText());
            pstmt.setString(7, EmailPatientTextField.getText());

            // Execute the query and get the ResultSet object
            pstmt.executeUpdate();

            Update_table_patient();
            clear_patient();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {

            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_AjouterPatientActionPerformed

    private void PrenomPatientTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrenomPatientTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrenomPatientTextFieldActionPerformed

    private void NomPatientTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomPatientTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomPatientTextFieldActionPerformed

    private void searchTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextField4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextField4KeyReleased

    private void SearchButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchButton4ActionPerformed

    private void serviceResTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceResTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceResTextFieldActionPerformed

    private void consultation_TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_consultation_TableMouseClicked
        int row = consultation_Table.getSelectedRow();
        String num_con = consultation_Table.getModel().getValueAt(row, 0).toString();
        String nss = consultation_Table.getModel().getValueAt(row, 1).toString();

        numConText.setText(num_con);
        NSSjLabel.setText(nss);
    }//GEN-LAST:event_consultation_TableMouseClicked

    private void clearButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton3ActionPerformed
        clear_avis();
        
    }//GEN-LAST:event_clearButton3ActionPerformed

    private void NotejTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NotejTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NotejTextFieldActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (numConText.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "selectionner consultation d'abord");
        } else if (numConText.getText().isBlank() || CommentairejTextArea.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "remplie les case");
        } else {
            try {
                String sql = "INSERT INTO avis(numero_consultation,nss,note,commentaire) VALUES (?,?,?,?)";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, Integer.parseInt(numConText.getText()));
                pstmt.setString(2, NSSjLabel.getText());
                pstmt.setFloat(3, Float.parseFloat(numConText.getText()));
                pstmt.setString(4, CommentairejTextArea.getText());

                // Execute the query and get the ResultSet object
                pstmt.executeUpdate();

                
                clear_avis();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {

                try {
                    rs.close();
                    pst.close();

                } catch (Exception e) {

                }
            }
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        nbrvisitePat();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        nbrvisiteEmp();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AdressePatientTextField;
    private javax.swing.JButton AjouterButton2;
    private javax.swing.JButton AjouterEmployeButton;
    private javax.swing.JButton AjouterPatient;
    private javax.swing.JTextArea CommentairejTextArea;
    private javax.swing.JTextField DatePatientTextField;
    private javax.swing.JButton DeleteButton2;
    private javax.swing.JButton DeleteEmployeButton;
    private javax.swing.JTextField EmailPatientTextField;
    private javax.swing.JComboBox<String> EtatResComboBox;
    private javax.swing.JLabel IDtext;
    private javax.swing.JButton ModifierButton2;
    private javax.swing.JButton ModifierEmployeButton;
    private javax.swing.JButton ModifierPatient;
    private javax.swing.JTextField NSSTextField;
    private javax.swing.JLabel NSSjLabel;
    private javax.swing.JTextField NomPatientTextField;
    private javax.swing.JTextField NotejTextField;
    private javax.swing.JLabel NumResText;
    private javax.swing.JTextField PrenomPatientTextField;
    private javax.swing.JLabel ResAnul;
    private javax.swing.JButton SearchButton;
    private javax.swing.JButton SearchButton1;
    private javax.swing.JButton SearchButton2;
    private javax.swing.JButton SearchButton4;
    private javax.swing.JComboBox<String> SecteurEmployeComboBox;
    private javax.swing.JButton SupprimerPatient;
    private javax.swing.JTextField TelphonePatientTextField;
    private javax.swing.JComboBox<String> TypeResComboBox;
    private javax.swing.JTextField ancienneteEmployeTextField;
    private javax.swing.JButton clearButton2;
    private javax.swing.JButton clearButton3;
    private javax.swing.JButton clearEmployeButton;
    private javax.swing.JButton clearPatient;
    private javax.swing.JTable consultation_Table;
    private javax.swing.JTextField dateResTextField;
    private javax.swing.JTable employe_table;
    private javax.swing.JTextField heureResTextField;
    private javax.swing.JTextField idEmp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTextField loginEmployeTextField;
    private javax.swing.JLabel nbVisites;
    private javax.swing.JLabel nbVisitesPat;
    private javax.swing.JTextField nomEmployeTextField;
    private javax.swing.JTextField nssResTextField;
    private javax.swing.JTextField nsspat;
    private javax.swing.JLabel numConText;
    private javax.swing.JTextField passwordEmployeTextField;
    private javax.swing.JTable patient_table;
    private javax.swing.JTextField prenomEmployeTextField;
    private javax.swing.JTable reservation_Table;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTextField searchTextField1;
    private javax.swing.JTextField searchTextField2;
    private javax.swing.JTextField searchTextField4;
    private javax.swing.JTextField serviceResTextField;
    private javax.swing.JTextField specialiteEmployeTextField;
    // End of variables declaration//GEN-END:variables
}
