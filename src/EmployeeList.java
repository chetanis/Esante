
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.xml.crypto.Data;

public class EmployeeList extends JFrame {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public EmployeeList(String nss,Timestamp date,String service,int i) {
        conn = db.java_db();
        
        if(i==1){
           setTitle("Medecin list");
        }else{
           setTitle("Infermier list");
        }
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a scroll pane for the employee panels
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        // Create a panel for the employee panels
        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new BoxLayout(employeePanel, BoxLayout.Y_AXIS));
        if(i==1){
           rs = getAllMed(); 
        }else{
           rs = getAllinf();
        }
       
        
        try {
            while (rs.next()) {
                int id =rs.getInt("code_employe");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String secteur = rs.getString("secteur");
                String specialite = rs.getString("specialite");
                


                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

                // Add a label with the employee name
                JLabel nameLabel = new JLabel(nom);
                panel.add(nameLabel);
                JLabel nameLabel4 = new JLabel(prenom);
                panel.add(nameLabel4);
                JLabel nameLabel2 = new JLabel(specialite);
                panel.add(nameLabel2);

                // Add an edit button
                JButton detailButton = new JButton("Choisire");
                panel.add(detailButton);
                detailButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Code to display the employee details
                        try {
                            String sql = "INSERT INTO consultation (nss,code_employe, date_consultation, etat, service) VALUES (?, ?, ?, ?, ?)";

                            PreparedStatement pstmt = conn.prepareStatement(sql);

                            pstmt.setString(1, nss);
                            pstmt.setInt(2, id);
                            pstmt.setTimestamp(3, date);
                            pstmt.setString(4, "en attente");
                            pstmt.setString(5, service);
                           


                            pstmt.executeUpdate();
                            dispose();

                        } catch (Exception ex) {
                            String nssE = "java.sql.SQLIntegrityConstraintViolationException: Cannot add or update a child row: a foreign key constraint fails (`esante`.`reservation`, CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`nss`) REFERENCES `patient` (`nss`))";
                            if (e.toString().contains(nssE)) {
                                JOptionPane.showMessageDialog(null, "patient n'existe pas");
                            } else {
                                JOptionPane.showMessageDialog(null, ex);
                            }

                        } finally {
                            try {

                            } catch (Exception exx) {
                                JOptionPane.showMessageDialog(null, exx);
                            }
                        }
                    }
                });
                employeePanel.add(panel);
            }
        } catch (Exception e) {
        }
        

        // Add the employee panel to the scroll pane
        scrollPane.setViewportView(employeePanel);

        // Add the scroll pane to the frame
        add(scrollPane);

        // Display the JFrame
        setVisible(true);
    }

    

    public ResultSet getAllMed() {

        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM employe where secteur='médical' or secteur='Médical'";

            // Create a PreparedStatement object to execute the query
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Execute the query and get the ResultSet object
            rs = pstmt.executeQuery();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        return rs;
    }
    public ResultSet getAllinf() {

        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM employe where secteur='paramédical' or secteur='Paramédical'";

            // Create a PreparedStatement object to execute the query
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Execute the query and get the ResultSet object
            rs = pstmt.executeQuery();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
        return rs;
    }
}
