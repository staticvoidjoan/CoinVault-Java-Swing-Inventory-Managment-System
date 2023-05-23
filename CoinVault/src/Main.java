import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CoinVaultForm form = new CoinVaultForm(null);
        Coin coin = form.coin;
        if (coin != null){
            System.out.println("Successful registration of:" + coin.CoinId);
        } else {
            System.out.println("Error");
        }
    }
}
