import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;



public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Locale.setDefault(new Locale("pl"));
                ResourceBundle bundle = ResourceBundle.getBundle("jezyk");
                test _test = new test(true);
                //test2 _test2 = new test2();
                //test3 _test3 = new test3();
            }
        });



    }
}
