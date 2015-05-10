package pl.pwr.shopassistant.fridge.simulator;

import org.joda.time.DateTime;
import pl.pwr.shopassistant.fridgeapiclient.DefaultFridgeApiClient;
import pl.pwr.shopassistant.fridgeapiclient.FridgeApiClient;
import pl.pwr.shopassistant.fridgeapiclient.ProductStatus;
import pl.pwr.shopassistant.services.hash.Sha1HashService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainWindow extends JFrame {
    private JPanel panel1;
    private JList list1;
    private JList list2;
    private JList list3;
    private JButton outButton;
    private JButton inButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton connectButton;
    private JTextField textField4;
    private JButton addButton;

    FridgeApiClient fridgeApiClient;
    java.util.List<String> waitingProducts = new ArrayList<String>();
    java.util.Map<String, Integer> fridgeProducts = new HashMap<String, Integer>();
    java.util.List<String> logs = new ArrayList<String>();

    private void log(String message) {
        logs.add(message);

        list1.setListData(logs.subList(logs.size() - 5 < 0 ? 0 : logs.size() - 5, logs.size()).toArray());
    }

    public MainWindow() throws HeadlessException {
        super("MainWindow");
        this.setContentPane(panel1);

        this.pack();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        inButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String ean = (String) list3.getSelectedValue();

                fridgeApiClient.changeProductStatus(ean, UUID.randomUUID(), ProductStatus.in, new DateTime());

                if (!fridgeProducts.containsKey(ean)) {
                    fridgeProducts.put(ean, 0);
                }
                fridgeProducts.put(ean, fridgeProducts.get(ean) + 1);

                java.util.List<String> products = new ArrayList<String>();
                for (Map.Entry<String, Integer> fridgeProduct : fridgeProducts.entrySet()) {
                    String productEAN = fridgeProduct.getKey();
                    Integer quantity = fridgeProduct.getValue();

                    products.add(String.format("%s (%d)", productEAN, quantity));
                }
                list2.setListData(products.toArray());

                log(String.format("Putting %s into the fridge", ean));
            }
        });
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Sha1HashService sha1HashService = new Sha1HashService();

                String host = textField1.getText();
                String username = textField2.getText();
                String password = textField3.getText();
                String apiKey = sha1HashService.hash(username + sha1HashService.hash(password));

                fridgeApiClient = new DefaultFridgeApiClient(host, username, apiKey);
                log(String.format("Connected to %s", host));
            }
        });
        outButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String ean = (String) list2.getSelectedValue();
                ean = ean.split(" ")[0];

                fridgeProducts.put(ean, fridgeProducts.get(ean) - 1);

                java.util.List<String> products = new ArrayList<String>();
                for (Map.Entry<String, Integer> fridgeProduct : fridgeProducts.entrySet()) {
                    String productEAN = fridgeProduct.getKey();
                    Integer quantity = fridgeProduct.getValue();

                    products.add(String.format("%s (%d)", productEAN, quantity));
                }
                list2.setListData(products.toArray());

                fridgeApiClient.changeProductStatus(ean, UUID.randomUUID(), ProductStatus.out, new DateTime());

                log(String.format("Taking %s out of the fridge", ean));
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String ean = textField4.getText();

                waitingProducts.add(ean);
                list3.setListData(waitingProducts.toArray());
            }
        });
    }
}
