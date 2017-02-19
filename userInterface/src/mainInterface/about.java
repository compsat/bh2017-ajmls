package mainInterface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;

public class about {

	private JFrame frame;
	private JPanel panelMessage;
	private JLabel lblNewLabel;
	private JLabel lblArnoldSchwarzenegger;
	private JTextArea txtrThisProjectAims;
	private JLabel lblAbout;
	private JPanel panelAbout;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					about window = new about();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public about() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 730, 629);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panelAbout = new BgPanel();
		panelAbout.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelAbout.setBounds(0, 0, 714, 590);
		frame.getContentPane().add(panelAbout);
		panelAbout.setLayout(null);

		panelMessage = new JPanel();
		panelMessage.setBackground(new Color(245, 255, 250));
		panelMessage.setBorder(new LineBorder(new Color(68, 115, 107), 2, true));
		panelMessage.setBounds(28, 168, 653, 334);
		panelAbout.add(panelMessage);
		panelMessage.setLayout(null);
		
		lblNewLabel = new JLabel("\"The future is green energy, sustainability, renewable energy.\"");
		lblNewLabel.setFont(new Font("Arial", lblNewLabel.getFont().getStyle() | Font.ITALIC, 16));
		lblNewLabel.setBounds(78, 43, 484, 53);
		panelMessage.add(lblNewLabel);
		
		lblArnoldSchwarzenegger = new JLabel("~ Arnold Schwarzenegger");
		lblArnoldSchwarzenegger.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblArnoldSchwarzenegger.setBounds(410, 88, 197, 38);
		panelMessage.add(lblArnoldSchwarzenegger);
		
		txtrThisProjectAims = new JTextArea();
		txtrThisProjectAims.setBackground(new Color(245, 255, 250));
		txtrThisProjectAims.setFont(new Font("Maiandra GD", Font.PLAIN, 16));
		txtrThisProjectAims.setColumns(30);
		txtrThisProjectAims.setText("This project aims to raise awareness to the alarming details of our yearly energy consumption distributions, most of which are from non renewables that could permanently damage our environment.\r\n\r\nProject by AJMLS at #BlueHacks2017");
		txtrThisProjectAims.setBounds(46, 158, 560, 121);
		txtrThisProjectAims.setLineWrap(true);
		txtrThisProjectAims.setEditable(false);
		txtrThisProjectAims.setFocusable(false);
		panelMessage.add(txtrThisProjectAims);
		
		lblAbout = new JLabel("About");
		lblAbout.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 36));
		lblAbout.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbout.setBounds(222, 92, 256, 54);
		panelAbout.add(lblAbout);
	}
}


