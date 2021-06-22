package components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.Main;

public class MinesCountChooser {
	
	private JFrame frame;
	private JButton okButton = new JButton("OK!");
	private JTextField minesCountTextField = new JTextField();
	private int minesCount;
	private final int minesCountLimit;
	
	public MinesCountChooser(int minesCountLimit) {
		
		this.minesCountLimit = minesCountLimit;
		okButton.setBounds(35, 50, 60, 30);
		minesCountTextField.setBounds(15, 10, 100, 30);
	}
	
	private void errorMessages(String message) {
		JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	public void getMinesCount() {
		
		frame = new JFrame("Mines Count");
		frame.setLayout(null);
		frame.setSize(200, 150);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		okButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (minesCountTextField.getText().length() > 3) {
					errorMessages("The length of the input string should be no more than 3" +
							"digits long!");
					minesCountTextField.setText("");
				} else if (minesCountTextField.getText().length() == 0) {
					errorMessages("Please enter number of mines!");
				} else {
					try {
						minesCount = Integer.parseInt(minesCountTextField.getText());
						if (minesCount <= 0) {
							errorMessages("The entered number must be positive!");
							minesCountTextField.setText("");
						} else if (minesCount > minesCountLimit) {
							errorMessages("The mines must be less that " + minesCountLimit + "!");
							minesCountTextField.setText("");
						} else {
							Main.board.minesCount = minesCount;
							Main.board.startGame();
							frame.dispose();
						}
					} catch (NumberFormatException nfe) {
						errorMessages("Please input a number!");
						minesCountTextField.setText("");
					}
				}
			}
		});
		frame.add(okButton); frame.add(minesCountTextField);
		frame.setVisible(true);
	}
}
