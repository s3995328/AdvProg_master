package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OrderFormController {
	@FXML
	private ComboBox FoodItemComboBox;
	@FXML
	private TextField itemQtyTextField;
	@FXML
	private Button addToOrderButton;
	@FXML
	private Button backToMenuButton;
	@FXML
	private Label statusLabel;
	@FXML
	private Label orderCountLabel;

	private Restaurant restaurant;
	private Order activeOrder;
	
	private LinkedList<FoodItem> items = new LinkedList<FoodItem>();
	
	
	// Event Listener on Button[#backToMenuButton].onAction
	@FXML
	public void homeButtonClick(ActionEvent event) throws IOException {
//		Stage stage = (Stage) statusLabel.getScene().getWindow();
//	    stage.close();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
		loader.load();
		
		}
	
	@FXML
	public void addToOrderButtonClick(ActionEvent event) {
		
		if (FoodItemComboBox.getValue() != null) {
			
			String itemQty = itemQtyTextField.getText();
			FoodItem selectedItem =(FoodItem) FoodItemComboBox.getValue();
			int qty = 0;
					
			try {
				qty = Integer.valueOf(itemQty);
			} catch (Exception e) {
				qty = 0;
			}
			
			if (qty > 0) {
				int quantity = qty;
				selectedItem.addQuantity(quantity);
				this.activeOrder.addFoodItem(selectedItem);
				statusLabel.setText("Order updated");
			}else {
				statusLabel.setText("Please enter valid quantity");
			}
		}else {
			statusLabel.setText("Please select item first");
		}
		
		showSalesReport();
		
		}
	

	public void initMethod(String name) {
		restaurant = new Restaurant(name);
		activeOrder = new Order();
		statusLabel.setText("");
		showOrderCount();

		items.add(new Burrito(Restaurant.getPrice("Burrito"), 1));
		items.add(new Fries(Restaurant.getPrice("Fries"), 1));
		items.add(new Soda(Restaurant.getPrice("Soda"), 1));
		//items.add(new Soda(Restaurant.getPrice("Meal"), 1));

		FoodItemComboBox.getItems().addAll(items);
		FoodItemComboBox.getSelectionModel().select(new Burrito(Restaurant.getPrice("Burrito"), 1));
		
	}
	
	public void showOrderCount() {
		orderCountLabel.setText("Items added to order : "+this.activeOrder.getItems().size());
	}
	
	public void showSalesReport() {
    	HashMap<String, Integer> quantities = new HashMap<String, Integer>();
    	HashMap<String, Double> prices = new HashMap<String, Double>();
    	quantities.put("Burrito", 0);
    	quantities.put("Fries", 0);
    	quantities.put("Soda", 0);
    	quantities.put("Meal", 0);
    	prices.put("Burrito", 0.0);
    	prices.put("Fries", 0.0);
    	prices.put("Soda", 0.0);
    	prices.put("Meal", 0.0);
    	for(Order order: this.restaurant.getAllOrders()) {
    		for(FoodItem item: order.getItems()) {
    			String key = item.getClass().getName();
    			int oldQ = quantities.get(key);
    			double oldP = prices.get(key);
    			quantities.put(key, oldQ + item.getQuantity());
    			prices.put(key, oldP + item.getTotalPrice());
    		}
    	}
    	
    	String desc = "Unsold Serves of Fries: " + this.restaurant.getRemainedFries() +"\n\nTotal Sales:\n";
    	desc += "Burritos             "+ quantities.get("Burrito")+ String.format("$%.2f", prices.get("Burrito"))+"\n";
    	desc += "Fries               "+ quantities.get("Fries")+ String.format("$%.2f", prices.get("Fries"))+"\n";
    	desc += "Soda                 "+ quantities.get("Soda")+ String.format("$%.2f", prices.get("Soda"))+"\n";
    	desc += "Meal                 "+ quantities.get("Meal")+ String.format("$%.2f", prices.get("Meal"))+"\n";
    	desc += new String(new char[27]).replace('\u0000', '-');
    	
    	int totalQ = quantities.get("Burrito") + quantities.get("Fries") + quantities.get("Soda") + quantities.get("Meal");
    	double totalP = prices.get("Burrito") + prices.get("Fries") + prices.get("Soda") + prices.get("Meal");
    	orderCountLabel.setText(desc);
    	
    }
}
