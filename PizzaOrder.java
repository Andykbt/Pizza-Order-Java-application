//Andy Truong, 19766993
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.event.*;
import javax.swing.text.*;
import java.text.*;
import java.util.Vector;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.*;


public class PizzaOrder extends JFrame {
	/*###  variables to save cost of pizza selections  ###*/
	private static double pCost;
	private static double basesAndSaucesCost;
	private static double toppingsCost;
	private static double pizzaCost = 0;
	private static double totalCost = 0;

	private static JLabel costLabel = new JLabel();	//label to hold the price of the pizza
	private static JLabel totalCostLabel = new JLabel(); //label to hold the total price of the order
	private static JCheckBox delivery = new JCheckBox(); //checkbox for delivery
	private static Vector<String> pizzaOrder = new Vector<String>(); //holds all the pizzas that are added to order

	/*variables to hold the selections*/
	private static String pizzaChoice;
	private static String baseChoice = "";
	private static String sauceChoice = "no sauce";
	private static Object toppingsArr[];
	private static int pizzaCount;
	private static int toppingsCount;

	public void paint(Graphics g) {
		super.paint(g);

		//draw pizza logo on top left
		Image logo = new ImageIcon("pizzaLogo.png").getImage(); //logo found at https://www.flaticon.com/free-icon/pizza_3132693
		g.drawImage(logo, 10, 25, 32, 32, this);

		//draw pizza image on bottom left
		Image pizzaImage = new ImageIcon("pizza.png").getImage(); //logo found at https://www.flaticon.com/free-icon/pizza_3014573?term=pizza&page=1&position=28
		g.drawImage(pizzaImage, 85, 250, 128, 128, this);

		//draw title
		String title = "Pizza Order";
		int string_Width = g.getFontMetrics().stringWidth(title);
		g.setFont(g.getFont().deriveFont(30f));
		g.drawString(title, 60, 50);
		
		//draw heading rectangles
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(50, 70, 110, 25, 10, 10);
		//draw headings
		String heading1 = "Pizza";
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString(heading1, 55, 90);

		//draw heading rectangles
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(210, 70, 150, 25, 10, 10);
		//draw headings
		String heading2 = "Bases & Sauces";
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString(heading2, 215, 90);

		//draw heading rectangles
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(390, 70, 110, 25, 10, 10);
		//draw headings
		String heading3 = "Toppings";
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString(heading3, 395, 90);

		//draw heading rectangles
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(550, 70, 110, 25, 10, 10);
		//draw headings
		String heading4 = "Order";
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString(heading4, 555, 90);

		//header line
		g.setColor(Color.DARK_GRAY);
		g.drawLine(0, 60, 750, 60);	
	}

	public static void main(String[] args) {
		JFrame f = new PizzaOrder();
		f.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 75));	//set flowlayout of frame
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		totalCostLabel.setText(Double.toString(totalCost));

		/* ######	ADDING PIZZA JLIST TO APP	##### */
		String pizzas[] = {"Supremo Supreme", "Supreme", "Chicken", "Aussie", "Veggie", "Hawaiian"};
		JList p = new JList(pizzas);
		f.add(new JScrollPane(p));
		p.setVisibleRowCount(6); //effetive when using JScrollPane
		p.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		p.addListSelectionListener(
	    	new ListSelectionListener() {
		    	public void valueChanged(ListSelectionEvent e) {
		    		try {
						pizzaChoice = p.getSelectedValue().toString();
			    		if (pizzaChoice == "Supremo Supreme")
			    			pCost = 13;
			    		else if (pizzaChoice == "Supreme")
			    			pCost = 12;
			    		else if (pizzaChoice== "Chicken")
			    			pCost = 11.50;
			    		else if (pizzaChoice == "Aussie")
			    			pCost = 12.50;
			    		else if (pizzaChoice == "Veggie")
			    			pCost = 10.50;
			    		else if (pizzaChoice == "Hawaiian")
			    			pCost = 11;
			    		pizzaCost = pCost + basesAndSaucesCost + toppingsCost;
			    		costLabel.setText(Double.toString(pizzaCost));
			    	} catch (NullPointerException nullPointer) {
			    		System.out.println("Pizza JList caught null pointer");
			    	}
    		}});

		/* ######	ADDING BASES JLIST TO APP	##### */
		String bases[] = {"Thin & Crispy", "Pan", "Cheese-filled Crust"};
		JList b = new JList(bases);
		b.setVisibleRowCount(3); //only show 3 rows
		b.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //User can only select one row in JList
		b.addListSelectionListener(
	    	new ListSelectionListener() {
		    	public void valueChanged(ListSelectionEvent e) {
		    		try {
			    		baseChoice = b.getSelectedValue().toString();	//get chosen base
			    		if (b.getSelectedValue() == "Pan")	//get costs of chosen base
			    			basesAndSaucesCost = 1.50;
			    		else
			    			basesAndSaucesCost = 0;
			    		pizzaCost = pCost + basesAndSaucesCost + toppingsCost;	//update cost of pizza
			    		costLabel.setText(Double.toString(pizzaCost));
			    	} catch (NullPointerException nullPointer) {	//catch nullpointerexception
			    		System.out.println("Bases JList caught null pointer");
			    	}
	    	}});

		JPanel BaseSaucePanel = new JPanel();
		BaseSaucePanel.setLayout(new BoxLayout(BaseSaucePanel, BoxLayout.Y_AXIS));
		BaseSaucePanel.add(new JScrollPane(b));
		
		/* ######	ADDING SAUCES RADIOBUTTONS TO APP	##### */
		ButtonGroup group = new ButtonGroup();
		JRadioButton tomatoButton = new JRadioButton("Tomato");
		tomatoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tomatoButton.isSelected())
					sauceChoice = "tomato sauce";	//chosen sauce is tomato sauce is checked
			}
		});

		JRadioButton barbequeButton = new JRadioButton("Barbeque");
		barbequeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (barbequeButton.isSelected())
					sauceChoice = "barbeque sauce";	//chosen sauce is barbeque sauce is checked
			}
		});

		//Add buttonGroup to frame
		group.add(tomatoButton);
		group.add(barbequeButton);
		BaseSaucePanel.add(tomatoButton);
		BaseSaucePanel.add(barbequeButton);
		f.add(BaseSaucePanel);

		/* #####	ADDING TOPPINGS JLIST TO APP     ###### */
		String toppings[] = {"Pepperoni", "Salami", "Ham", "Bacon", "Chicken", "Onion"};
		JList t = new JList(toppings);
		f.add(new JScrollPane(t));
		t.setVisibleRowCount(6); 
		t.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//allows user to select multiple toppings

		t.addListSelectionListener(
	    	new ListSelectionListener() {
		    	public void valueChanged(ListSelectionEvent e) {
		    		try {
			    		toppingsArr = t.getSelectedValuesList().toArray();	//save selected toppings to array
			    		toppingsCount = t.getSelectedValuesList().size();	//save number of toppings selected to variable

			    		toppingsCost = t.getSelectedValuesList().size() * 1.50;	//get cost of toppings
			    		pizzaCost = pCost + basesAndSaucesCost + toppingsCost;	//update pizza cost
			    		costLabel.setText(Double.toString(pizzaCost));	//update label
			    	} catch (NullPointerException nullPointer) {
			    		System.out.println("Toppings JList caught null pointer");
			    	}
	    	}});

		/* #####	ADDING ORDER DESCRIPTION TO APP	##### */
		JPanel descriptionPanel = new JPanel(new GridLayout(3, 1));
		JPanel pizzaDesc1 = new JPanel();	
		pizzaDesc1.add(new JLabel("Number of pizzas: "));

		/* only allow numbers as input using JFormattedTextField*/
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(0);
		numberFormatter.setMaximum(10);
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setCommitsOnValidEdit(true);
		JFormattedTextField pizzaCount = new JFormattedTextField(numberFormat);
		pizzaCount.setValue(new Integer(1));	//set default value of textfield to 1
		pizzaCount.setColumns(3);
		pizzaDesc1.add(pizzaCount);
		descriptionPanel.add(pizzaDesc1);
		
		/* #####	Price of pizza to Panel	##### */
		JPanel pizzaDesc2 = new JPanel();
		pizzaDesc2.add(new JLabel("Price of pizza: $"));
		pizzaDesc2.add(costLabel);
		descriptionPanel.add(pizzaDesc2);

		/* #####	Add to order button to panel	##### */
		JPanel pizzaDesc3 = new JPanel();
		JButton AddOrderButton = new JButton("Add to Order");
		AddOrderButton.setToolTipText("Add current pizza to order");
		AddOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pizzaChoice != null) {	//dont do anything is pizza is not selected
					int c = Integer.parseInt(pizzaCount.getValue().toString());	//get count

					totalCost += pizzaCost * c;	//calculate costs
					totalCostLabel.setText(Double.toString(totalCost));	//update jlabel

					String order = c + " x " +pizzaChoice + " " + baseChoice	//create string for order
					+ " pizza with " + sauceChoice + " and ";

					//append all toppings to order
					if (toppingsCount == 0) {
						order += "no toppings";
					} else {
						for (int i = 0; i < toppingsCount; i++)
							order += toppingsArr[i] + " ";
					}
					if (delivery.isSelected()) {
						order += " (delivery)";
					} else {
						order += " (pick up)";
					}

					for (int i = 0; i < pizzaOrder.size(); i++) {
						String s1 = order.replaceAll("[0-9]","");	//save orders without count into s1
						String s2 = pizzaOrder.get(i).replaceAll("[0-9]","");	//save order without count into s2

						if (s1.equals(s2)){	//if orders are the same
							int num1 = Integer.parseInt(order.replaceAll("\\D+",""));	//save the count of order 1
							int num2 = Integer.parseInt(pizzaOrder.get(i).replaceAll("\\D+",""));	//save the count of order 2

							int sum = num1 + num2;	//add counts together
							order = order.replaceAll("[0-9]","");	//remove numbers from the order
							order = Integer.toString(sum) + order;	//add new count to order

							pizzaOrder.removeElementAt(i);	//remove element from vector
						}
					}
					pizzaOrder.add(order);	//add order to vector
				}
			}});
		pizzaDesc3.add(AddOrderButton);
		descriptionPanel.add(pizzaDesc3);
		f.add(descriptionPanel);

		/* #####	Add prices	##### */
		JPanel priceNote = new JPanel();
		
		priceNote.add(new JLabel("<html>extra $1.50 for Pan<br>"
					+ "extra $1.10 for each topping<br>"
					+ "delivery fee of $5.00 waived for over $26 orders<br>"
					+ "all prices shown include GST</html>"));
		f.add(priceNote);

		/* #####	Adding footer to app	##### */
		JPanel footer = new JPanel();;
		delivery.addActionListener(new ActionListener() {	//deivery button
			public void actionPerformed(ActionEvent e) {
				//calculations to waive delivery fee when over 26
				if (delivery.isSelected() && totalCost <= 26) {
					totalCost += 5;
				} else if (totalCost > 26) {
					totalCost += 0;
				} else {
					totalCost -= 5;
				}
				totalCostLabel.setText(Double.toString(totalCost));	//update text
			}
		});

		JButton clearOrder = new JButton("Clear order");	//clear orders
		clearOrder.setToolTipText("Clear entire order");
		clearOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clear all vectors, JLists, buttonGroups, price values, total values, etc.
				p.clearSelection();
				b.clearSelection();
				t.clearSelection();
				group.clearSelection();
				delivery.setSelected(false);
				pizzaOrder.clear();
				pizzaCount.setValue(new Integer(1));
				costLabel.setText("0");
				totalCost = 0;
				totalCostLabel.setText(Double.toString(totalCost));
			}
		});

		/* #####	submit order button to panel	##### */
		JButton submitOrder = new JButton("Make order");
		submitOrder.setToolTipText("Submit the order and get the invoice");
		submitOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrder.size() > 0) {	//dont do anything if no pizzas added to order
					JDialog dBox = new JDialog(f, "Order Summary", true);	//create jdialog

					JPanel dBoxHeader = new JPanel();	//make header panel for jdialog
					ImageIcon billIcon=new ImageIcon("bill.png");	//add image
		            JLabel label = new JLabel(billIcon); //image found at https://www.flaticon.com/free-icon/invoice_846047?term=receipt&page=1&position=1
		            JLabel title = new JLabel("Receipt");	//add jlabel
		            dBoxHeader.add(label);
		            dBoxHeader.add(title);


		            JPanel dBoxCenter = new JPanel();
					BoxLayout boxlayout = new BoxLayout(dBoxCenter, BoxLayout.Y_AXIS);
					dBoxCenter.setLayout(boxlayout);
					/* PRINT ALL PIZZAS ADDED TO ORDER */
					for (int i = 0; i < pizzaOrder.size(); i++) {
						System.out.println(pizzaOrder.get(i));
						dBoxCenter.add(new JLabel(pizzaOrder.get(i)));
					}

					JPanel dBoxFooter = new JPanel();	//DISPLAY TOTAL COST
					dBoxFooter.add(new JLabel("Total Cost: " + totalCost));
					title.setFont(new Font("MonoSpaced", Font.BOLD, 15));

					//SET BORDERS
					dBoxHeader.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
					dBoxFooter.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
					
					//ADD TO JDIALOG WITH LAYOUT
					dBox.add(BorderLayout.NORTH, dBoxHeader);
					dBox.add(BorderLayout.CENTER, dBoxCenter);
					dBox.add(BorderLayout.SOUTH, dBoxFooter);
					
					dBox.pack();	//MAKES JDIALOG MATCH CONTENT SIZE
					dBox.setVisible(true);
				}
			}
		});

		/* Add footer components to footer */
		footer.add(new JLabel("Delivery: "));
		footer.add(delivery);
		footer.add(new JLabel("Total Cost: $"));
		footer.add(totalCostLabel);
		footer.add(clearOrder);
		footer.add(submitOrder);
		
		//add footer panel to app
		f.add(footer);

		//set borders
		descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		priceNote.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		footer.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		f.setTitle("Pizza Order");	//set title
		f.setSize(750, 500);	//set size
		f.setResizable (false);	//dont allow resizing
		f.setVisible(true);
	}
}