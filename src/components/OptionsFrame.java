package components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import files.OptionsManager;

import misc.Options;

public class OptionsFrame extends JFrame {
	
	private static final long serialVersionUID = 822027186094492381L;
	
	private JCheckBox questionMarksCheckBox = new JCheckBox();
	
	private JTextField widthTextField = new JTextField();
	
	Options options = new OptionsManager().getOptions();
	
	public OptionsFrame() {
		
		super("Options");
		
		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		questionMarksCheckBox.setBounds(10, 200, 115, 30);
		questionMarksCheckBox.setText("Question Marks");
		questionMarksCheckBox.setToolTipText("Checking this will allow question marks(on double right click)");
		add(questionMarksCheckBox);
		
		widthTextField.setBounds(100, 20, 120, 20);
		widthTextField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				char x = arg0.getKeyChar();
				if (!Character.isDigit(x)) {
					System.out.println("YO!");
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				char x = arg0.getKeyChar();
				if (!Character.isDigit(x)) {
					arg0 = null;
				}
			}
		});
		add(widthTextField);
	}
}
