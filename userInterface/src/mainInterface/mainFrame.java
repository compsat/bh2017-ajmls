package mainInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class mainFrame {

	private JFrame mainFrame;
	private JPanel panelHome;
	private JPanel panelBanner;
	private JPanel panelChart;
	private JMenuBar menuBar;
	
	//about global variables
	private JPanel panelMessage;
	private JLabel lblNewLabel;
	private JLabel lblArnoldSchwarzenegger;
	private JTextArea txtrThisProjectAims;
	private JLabel lblAbout;
	private JPanel panelAbout;
	private JPanel mainPanel;
	private JMenuItem aboutMi;
	private JMenu fileMenu;
	private JMenuItem backMi;
	private JMenuItem mainMenu;
	
	private ArrayList<ArrayList<Double>> data; //1973 - 2014
	private ArrayList<ArrayList<Double>> matrix; //predictions;
	private int startYear;
	private int duration;
	private int currInt;
	
	private ChartPanel chartPanel = null;
	
	private JLabel lblPredictionArguments;

	private ProcessBuilder pb;
	private Process p;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame window = new mainFrame();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public mainFrame() {
		data = new ArrayList<ArrayList<Double>>();
		
		readCSV();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Energy General Allocation Predictor");
		mainFrame.setResizable(false);
		mainFrame.setBounds(100, 100,730, 629);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout(0, 5));
		
		menuBarFunction();
		
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 724, 600);
		mainFrame.getContentPane().add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 5));
		mainPanel.setVisible(true);
		
		
		homePageFunction();
	}
	
	public void menuBarFunction(){
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 724, 20);
		mainFrame.getContentPane().add(menuBar);
		
		fileMenu = new JMenu("Options");
		menuBar.add(fileMenu);
		aboutMi = new JMenuItem("About");
		mainMenu = new JMenuItem("Main");
		mainMenu.setVisible(false);
		
		JMenuItem exitMi = new JMenuItem("Exit");
		
		exitMi.setToolTipText("Exit application");
		
		fileMenu.add(mainMenu);		
		fileMenu.add(aboutMi);
		fileMenu.addSeparator();
		fileMenu.add(exitMi);
		fileMenu.addSeparator();
		
		aboutMi.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mainPanel.removeAll();
				aboutPage();
				mainPanel.revalidate();
				mainPanel.repaint();
				mainFrame.repaint();
				mainFrame.revalidate();
				
				aboutMi.setVisible(false);
				mainMenu.setVisible(true);
			}		
		});
		
		exitMi.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mainFrame.dispose();
			}		
		});
		
		mainMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainPanel.removeAll();
				homePageFunction();
				mainPanel.revalidate();
				mainPanel.repaint();
				mainFrame.repaint();
				mainFrame.revalidate();
				
				aboutMi.setVisible(true);
				mainMenu.setVisible(false);
			}
			
		});
	}
	
	public void homePageFunction(){
		matrix = new ArrayList<ArrayList<Double>>();
		
		panelHome = new JPanel();
		panelHome.setBounds(0, 0, 724, 600);
		mainPanel.add(panelHome);
		panelHome.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelHome.setBackground(new Color(192, 207, 163));
		panelHome.setLayout(null);
		
		panelBanner = new JPanel();
		panelBanner.setBounds(10, 0, 704, 74);
		panelBanner.setBorder(null);
		panelBanner.setBackground(new Color(144, 172, 81));
		panelHome.add(panelBanner);
		panelBanner.setLayout(null);
		
		JSpinner spinner = new JSpinner();
		
		
	    spinner.setValue(2014);
	    spinner.setFocusable(false);
	    spinner.setModel(new SpinnerNumberModel(2014, 2014, 2050, 1));
	    spinner.setBounds(89, 170, 119, 29);
	    JFormattedTextField tf=((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
	    panelHome.add(spinner);
		panelHome.setVisible(true);
		tf.setEditable(false);
		
		JLabel lblStartYear = new JLabel("Start Year:");
		lblStartYear.setBounds(10, 170, 67, 29);
		panelHome.add(lblStartYear);
		
		JLabel lblDuration = new JLabel("Duration:");
		lblDuration.setBounds(10, 210, 67, 29);
		panelHome.add(lblDuration);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(1, 1, 50, 1));
		spinner_1.setBounds(153, 212, 55, 25);
		JFormattedTextField tf1=((JSpinner.DefaultEditor)spinner_1.getEditor()).getTextField();
		tf1.setEditable(false);
		panelHome.add(spinner_1);
		
		panelChart = new JPanel();
		panelChart.setBounds(229, 96, 485, 493);
		panelChart.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelHome.add(panelChart);
		panelChart.setLayout(new BorderLayout(0,5));
		
		JButton btnPredict = new JButton("Predict");
		btnPredict.setBounds(30, 281, 178, 38);
		panelHome.add(btnPredict);
		
		btnPredict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				panelChart.removeAll();
				
				startYear = (int) spinner.getValue();
				duration = (int) spinner_1.getValue();
				
				pb = new ProcessBuilder("python","assets/try.py",Integer.toString(startYear), Integer.toString(duration + 4));
				
				
				matrix = new ArrayList<ArrayList<Double>>();
				currInt = 0;
				
				
				
				try {
					p = pb.start();
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = "";
					while((line = in.readLine()) != null) {
						//Printing Part
						System.out.println(line);
						line = line.replace('[', ' ');
						line = line.replace(']', ' ');
						line = line.trim();
						
						StringTokenizer st = new StringTokenizer(line);
						
						ArrayList<Double> container = new ArrayList<Double>();
						container.add(Double.parseDouble(st.nextToken())*100.0);
						container.add(Double.parseDouble(st.nextToken())*100.0);
						container.add(Double.parseDouble(st.nextToken())*100.0);
						container.add(Double.parseDouble(st.nextToken())*100.0);
						
						matrix.add(container);
						
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 0; i < matrix.size(); i++) {
					for(int j = 0; j < matrix.get(0).size(); j++) {
						if(j == 0)System.out.print(matrix.get(i).get(j));
						else System.out.print(" " + matrix.get(i).get(j));
					}
					System.out.println();
				}
				
				chartPanel = createChart();
				panelChart.add(chartPanel, BorderLayout.CENTER);
			    panelChart.setVisible(true);
				
				JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				panel.add(createSlider());
				
				panelChart.add(panel, BorderLayout.SOUTH);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
			}
		});
		
		lblPredictionArguments = new JLabel("Prediction Arguments");
		lblPredictionArguments.setBounds(20, 93, 209, 66);
		panelHome.add(lblPredictionArguments);
		lblPredictionArguments.setFont(new Font("Sitka Text", Font.BOLD, 14));
		lblPredictionArguments.setHorizontalAlignment(SwingConstants.CENTER);
		lblPredictionArguments.setHorizontalTextPosition(SwingConstants.CENTER);
		
		
	}
	
	private JSlider createSlider() {
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, duration - 1, 0);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(duration / 10);
		slider.setPaintTicks(true);
		
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				currInt = slider.getValue();
				
				/*for(int i = 0; i < matrix.get(currInt).size(); i++) {
					System.out.print(matrix.get(currInt).get(i) + " ");
				}
				System.out.println();*/
				
				panelChart.remove(chartPanel);
				chartPanel = createChart();
				
				panelChart.add(chartPanel, BorderLayout.CENTER);
				panelChart.repaint();
				panelChart.revalidate();
			}
			
		});
		return slider;
	}
	
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Coal", matrix.get(currInt).get(0));
		dataset.setValue("Renewable", matrix.get(currInt).get(1));
		dataset.setValue("Natural gas", matrix.get(currInt).get(2));
		dataset.setValue("Oil", matrix.get(currInt).get(3));
		
		return dataset;
	}
	
	private ChartPanel createChart() {
		PieDataset pieData = createDataset();
		JFreeChart chart = ChartFactory.createPieChart("General Allocation Predictor", pieData, true, true, false);
		return new ChartPanel(chart);
				
	}
	
	public void aboutPage(){
		panelAbout = new BgPanel();
		panelAbout.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelAbout.setBounds(0, 0, 714, 590);
		mainPanel.add(panelAbout);
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
	
	public void readCSV() {
		FileReader fr = null;
		BufferedReader textReader = null;
		try {
			fr = new FileReader("assets/percentage.csv");
			textReader = new BufferedReader(fr);
			
			String line = textReader.readLine();
			line = textReader.readLine();
			
			while(line != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				
				ArrayList<Double> container = new ArrayList<Double>();
				container.add(Double.parseDouble(st.nextToken()));
				container.add(Double.parseDouble(st.nextToken()));
				container.add(Double.parseDouble(st.nextToken()));
				container.add(Double.parseDouble(st.nextToken()));
				
				data.add(container);
				line = textReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class BgPanel extends JPanel {
    Image bg = new ImageIcon("about.jpg").getImage();
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
    }
}