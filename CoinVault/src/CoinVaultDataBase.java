import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CoinVaultDataBase extends JDialog{
    private JTable table1;
    private JButton clearButton;
    private JButton displayDataButton;
    private JLabel CoinVaultLabel;
    private JPanel Vault;
    private JTextField textField1;


    public CoinVaultDataBase(JFrame parent){
        super(parent);
        setTitle("The Vault");
        setContentPane(Vault);
        setModal(true);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        displayDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    final String db_url = "jdbc:mysql://localhost:3306/coinvault";
                    final String USERNAME = "root";
                    final String PASSWORD = "";
                    Connection connect = DriverManager.getConnection(db_url,USERNAME,PASSWORD);
                    Statement st = connect.createStatement();
                    String Query= "SELECT * FROM coin";
                    ResultSet rs = st.executeQuery(Query);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i = 0; i < cols; i++){
                        colName[i] = rsmd.getColumnName(i+1);
                    }
                    model.setColumnIdentifiers(colName);
                    String coin_id,orign,coinyear,material,rarity,condition,weight,buydate,buyprice,currentvalue;
                    while(rs.next()){
                        coin_id = rs.getString(1);
                        orign = rs.getString(2);
                        coinyear =rs.getString(3);
                        material = rs.getString(4);
                        rarity = rs.getString(5);
                        condition = rs.getString(6);
                        weight = rs.getString(7);
                        buydate = rs.getString(8);
                        buyprice = rs.getString(9);
                        currentvalue = rs.getString(10);
                        String[] row = {coin_id,orign,coinyear,material,rarity,condition,weight,buydate,buyprice,currentvalue};
                        model.addRow(row);
                    }
                    st.close();
                    connect.close();



                } catch (ClassNotFoundException ex) {

                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(new DefaultTableModel());
            }
        });
        setVisible(true);
    }

}

