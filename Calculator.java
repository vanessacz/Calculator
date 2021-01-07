
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.math.BigDecimal;

public class Calculator {
	
	//set default values
	String str1 = "0";
	String str2 = "0";
	String signal = "+";
	String result = "";
	
	//state switches
	//calculating on 1 - str1; 2 - str2
	int k1= 1;
	
	//# of operators
	int k2 = 1;
	
	//1 - reset str1 to 0
	int k3 = 1;
	
	//1 - reset str2 to 0
	int k4 = 1;
	
	//1 - include '.' in the result
	int k5 = 1;
	
	
	JButton store;
	
	@SuppressWarnings("rawtypes")
	Vector vt = new Vector(20,10);
	
	JFrame frame = new JFrame("Calculator");
	
	JTextField result_TextField = new JTextField(result, 20);
	
	//clear button
	JButton button_clear = new JButton("Clear");
	
	//numbers
	JButton button0 = new JButton("0");
	JButton button1 = new JButton("1");
	JButton button2 = new JButton("2");
	JButton button3 = new JButton("3");
	JButton button4 = new JButton("4");
	JButton button5 = new JButton("5");
	JButton button6 = new JButton("6");
	JButton button7 = new JButton("7");
	JButton button8 = new JButton("8");
	JButton button9 = new JButton("9");
	
	//operators
	JButton button_dot = new JButton(".");
	JButton button_add = new JButton("+");
	JButton button_sub = new JButton("-");
	JButton button_mul = new JButton("*");
	JButton button_div = new JButton("/");
	
	//start calculating
	JButton button_eq = new JButton("=");
	
	public Calculator() {
		JPanel pan1 = new JPanel();
		pan1.setLayout(new GridLayout(4,4,5,5));

		pan1.add(button7);
		pan1.add(button8);
		pan1.add(button9);
		pan1.add(button_div);
		pan1.add(button4);
		pan1.add(button5);
		pan1.add(button6);
		pan1.add(button_mul);
		pan1.add(button1);
		pan1.add(button2);
		pan1.add(button3);
		pan1.add(button_sub);
		pan1.add(button0);
		pan1.add(button_dot);
		pan1.add(button_eq);
		pan1.add(button_add);
		pan1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JPanel pan2 = new JPanel();
		pan2.setLayout(new BorderLayout());
		pan2.add(result_TextField, BorderLayout.WEST);
		pan2.add(button_clear, BorderLayout.EAST);
		
		frame.setLocation(300,200);
		frame.setResizable(false);
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(pan1, BorderLayout.CENTER);
		frame.getContentPane().add(pan2, BorderLayout.NORTH);
		
		frame.pack();
		frame.setVisible(true);
		
		class Listener implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String ss = ((JButton) e.getSource()).getText();
				store = (JButton) e.getSource();
				vt.add(store);
				if (k1 == 1) {
					if (k3 == 1) {
						str1 = "";
						
						//reset k5
						k5 = 1;
					}
					str1 = str1 + ss;
					k3 = k3 + 1;
					result_TextField.setText(str1);
				} else if (k1 == 2) {
					if (k4 == 1) {
						str2 = "";
						
						k5 = 1;
					}
					str2 = str2 + ss;
					k4 = k4 + 1;
					result_TextField.setText(str2);
				}
			}
		}
		
		class Listener_operator implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String ss2 = ((JButton) e.getSource()).getText();
				store = (JButton) e.getSource();
				vt.add(store);
				
				if (k2 == 1){
					k1 = 2;
					signal = ss2;
					k2 = k2 + 1;
				} else {
					int a = vt.size();
					JButton c = (JButton) vt.get(a - 2);
					if (!(c.getText().equals("+"))
							&&
							!(c.getText().equals("-"))
							&&
							!(c.getText().equals("*"))
							&&
							!(c.getText().equals("/"))) {
						calculate();
						str1 = result;
						
						k1 = 2;
						k5 = 1;
						k4 = 1;
						signal = ss2;
					}
					k2 = k2 + 1;
				}
			}
		}
		
		class Listener_clear implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				store = (JButton) e.getSource();
				vt.add(store);
				k1 = 1;
				k2 = 1;
				k3 = 1;
				k4 = 1;
				k5 = 1;
				str1 = "0";
				str2 = "0";
				signal = "";
				result = "";
				
				result_TextField.setText(result);
				vt.clear();
			}
		}
		
		class Listener_eq implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				store = (JButton) e.getSource();
				vt.add(store);
				calculate();
				
				//reset k1 - k4
				k1 = 1;
				k2 = 1;
				k3 = 1;
				k4 = 1;
				str1 = result;
			}
		}
		
		class Listener_floating implements ActionListener {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				store = (JButton) e.getSource();
				vt.add(store);
				if (k5 == 1) {
					String ss2 = ((JButton) e.getSource()).getText();
					if(k1 == 1) {
						if (k3 == 1) {
							str1 = "";

							k5 = 1;
						}
						str1 = str1 + ss2;
						k3 = k3 + 1;
						result_TextField.setText(str1);
					} else if (k1 == 2) {
						if (k4 == 1) {
							str2 = "";
							
							k5 = 1;
						}
						str2 = str2 + ss2;
						k4 = k4 + 1;
						result_TextField.setText(str2);
					}
				}
				k5 = k5 + 1;
			}
		}
		
		//Listen to eq button
		Listener_eq jt_eq = new Listener_eq();
		button_eq.addActionListener(jt_eq);
		
		//Listen to number buttons
		Listener jt = new Listener();
		button0.addActionListener(jt);
		button1.addActionListener(jt);
		button2.addActionListener(jt);
		button3.addActionListener(jt);
		button4.addActionListener(jt);
		button5.addActionListener(jt);
		button6.addActionListener(jt);
		button7.addActionListener(jt);
		button8.addActionListener(jt);
		button9.addActionListener(jt);
		
		//Listen to operator buttons
		Listener_operator jt_operator = new Listener_operator();
		button_add.addActionListener(jt_operator);
		button_sub.addActionListener(jt_operator);
		button_mul.addActionListener(jt_operator);
		button_div.addActionListener(jt_operator);
		
		//Listen to clear
		Listener_clear jt_clear = new Listener_clear();
		button_clear.addActionListener(jt_clear);
		
		//Listen to dot/ floating point
		Listener_floating jt_ft = new Listener_floating();
		button_dot.addActionListener(jt_ft);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowCLosing (WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void calculate() {
		double a2;
		double b2;
		
		double result2 = 0;
		
		String c = signal;
		
		if (c.equals("")) {
			result_TextField.setText("Please input operator");
		} else {
			if (str1.equals(".")) str1 = "0.0";
			if (str2.equals(".")) str2 = "0.0";
			
			a2 = Double.valueOf(str1).doubleValue();
			b2 = Double.valueOf(str2).doubleValue();
			
			if (c.equals("+")) {
				result2 = a2 + b2;
			}
			if (c.equals("-")) {
				result2 = a2 - b2;
			}
			if (c.equals("*")) {
				BigDecimal m1 = new BigDecimal(Double.toString(a2));
				BigDecimal m2 = new BigDecimal(Double.toString(b2));
				result2 = m1.multiply(m2).doubleValue();
			}
			if (c.equals("/")) {
				if (b2 == 0) {
					result2 = 0;
				} else {
					result2 = a2 / b2;
				}
			}
			result = ((new Double(result2)).toString());
			result_TextField.setText(result);
		}
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Calculator calculator = new Calculator();
	}
}