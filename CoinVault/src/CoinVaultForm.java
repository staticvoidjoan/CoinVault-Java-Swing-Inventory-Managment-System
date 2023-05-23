import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CoinVaultForm extends JDialog {
    private JTextField OriginField;
    private JTextField CoinYearField;
    private JTextField MaterialField;
    private JTextField RarityField;
    private JTextField ConditionField;
    private JTextField WeightField;
    private JTextField BuyDateField;
    private JTextField BuyPriceField;
    private JTextField CurrentValField;
    private JTextField CoinIDField;
    private JButton addCoinToVaultButton;
    private JButton openVaultButton;
    private JButton exitButton;
    private JPanel VaultForm;
    private JRadioButton commonRadioButton;
    private JRadioButton rareRadioButton;
    private JRadioButton extremelyRareRadioButton;

    private String RarityENUM;
    public Coin coin;

    public CoinVaultForm(JFrame parent){
        super(parent);
       setTitle("CoinVault");
       //setMinimumSize(new Dimension(480,600));
       setContentPane(VaultForm);
       setModal(true);
       pack();
       setLocationRelativeTo(parent);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);

       addCoinToVaultButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (commonRadioButton.isSelected()){
                   RarityENUM = "Common";
               } else if (rareRadioButton.isSelected()){
                   RarityENUM = "Rare";
               } else if (extremelyRareRadioButton.isSelected()){
                   RarityENUM = "Extremely Rare";
               }
               addCoinToVault();
           }
       });

       exitButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               dispose();
           }
       });
        openVaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    CoinVaultDataBase openvault = new CoinVaultDataBase(parent);

            }
        });

       setVisible(true);




    }

    private void addCoinToVault(){
        String CoinId = CoinIDField.getText();
        String Origin = OriginField.getText();
        String CoinYear = CoinYearField.getText();
        String Material = MaterialField.getText();
        String Rarity = RarityENUM;
        String Condition = ConditionField.getText();
        String Weight = WeightField.getText();
        String BuyDate = BuyDateField.getText();
        String BuyPrice = BuyPriceField.getText();
        String CurrentValue = CurrentValField.getText();

        if (CoinId.isEmpty() || Origin.isEmpty() || CoinYear.isEmpty() || Material.isEmpty() ||
            Condition.isEmpty() || Weight.isEmpty() || BuyDate.isEmpty() || BuyPrice.isEmpty() || CurrentValue.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please insert all field","Try Again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //To do add failed to add
        coin = addToDatabase(CoinId,Origin,CoinYear,Material,Rarity,Condition,Weight,BuyDate,BuyPrice,CurrentValue);

    }

    private Coin addToDatabase(String coin_id, String Origin, String CoinYear, String Material, String Rarity,
                               String Condition, String Weight, String BuyDate, String BuyPrice, String CurrentValue ){

        Coin coin = null;
        final String db_url = "jdbc:mysql://localhost/coinvault";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection connect = DriverManager.getConnection(db_url,USERNAME,PASSWORD);
            String query = "INSERT INTO coin (coin_id,Origin,CoinYear,Material,Rarity,CoinCondition,Weight,BuyDate,BuyPrice,CurrentValue)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1,coin_id);
            preparedStatement.setString(2,Origin);
            preparedStatement.setString(3,CoinYear);
            preparedStatement.setString(4,Material);
            preparedStatement.setString(5,Rarity);
            preparedStatement.setString(6,Condition);
            preparedStatement.setString(7,Weight);
            preparedStatement.setString(8,BuyDate);
            preparedStatement.setString(9,BuyPrice);
            preparedStatement.setString(10,CurrentValue);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0){
                coin = new Coin();
                coin.CoinId = coin_id;
                coin.Origin = Origin;
                coin.CoinYear = CoinYear;
                coin.Material = Material;
                coin.Rarity = Rarity;
                coin.Condition = Condition;
                coin.Weight = Weight;
                coin.BuyPrice = BuyPrice;
                coin.BuyDate = BuyDate;
                coin.CurrentValue = CurrentValue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coin;
    }



}
