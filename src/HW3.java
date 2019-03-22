import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class HW3 extends JFrame{

	// Layout Variables
	private static HW3 frame;
	private JPanel contentPane;
	
	private JPanel moviePanel;
	private JLabel movieLable;
	
	private JPanel genresPanel;
	private JPanel genresLabelPanel;
	private JLabel genresLabel;
	private JScrollPane genresScrollPane;
	
	private JPanel country1Panel;
	private JPanel country1LabelPanel;
	private JLabel country1Label;
	private JScrollPane country1ScrollPane;
	
	private JPanel movieYearPanel;
	private JPanel movieYearLabelPanel;
	private JLabel movieYearLabel;
	private JLabel fromLabel;
	private JLabel toLabel;
	
	private JDatePanelImpl fromDatePickerPanel;
	private JDatePickerImpl fromDatePicker;
	private JDatePanelImpl toDatePickerPanel;
	private JDatePickerImpl toDatePicker;
	private UtilDateModel model;
	private UtilDateModel fromModel;
	private UtilDateModel toModel;
	private Properties properties;
	private Properties fromProperties;
	private Properties toProperties;
	
	private JPanel actorPanel;
	private JPanel directorPanel;
	private JPanel castLabelPanel;
	private JLabel castLabel;
	private JLabel actorLabel;
	private JLabel directorLabel;
	
	private JComboBox actor1;
	private JComboBox actor2;
	private JComboBox actor3;
	private JComboBox actor4;
	private JComboBox director;
	
	private JPanel tagPanel;
	private JPanel tagLabelPanel;
	private JLabel tagLabel;
	private JScrollPane tagScrollPane;
	private JPanel tagWeight;
	
	private JLabel tagWeightLabel;
	private JComboBox tagWeightOperators;
	private JLabel valueLabel;
	private JTextField tagWeightValue;
	
	private JPanel movieResultPanel;
	private JPanel movieResultLabelPanel;
	private JLabel movieResultLabel;
	private JScrollPane movieResultScrollPane;
	
	private JPanel userResultPanel;
	private JPanel userResultLabelPanel;
	private JLabel userResultLabel;
	private JScrollPane userResultScrollPane;
	
	private JButton btnExecuteMovieQuery;
	private JTextArea movieResultsCount;
	
	private JButton btnExecuteUserQuery;
	private JTextArea userResultsCount;
	
	private JTextArea querryDisplay;
	
	private JComboBox genreSelection;
	private JButton btnExecuteGenre;
	private JComboBox countrySelection;
	private JButton btnExecuteCountry;
	private JComboBox castSelection;
	private JButton btnExecuteCast;;
	private JComboBox tagSelection;
	private JButton btnExecuteTag;
	private JComboBox movieSelection;
	
	private static final String binaryOperators[] = new String[] { "AND", "OR" };
	private static final String conditionalOperators[] = new String[] { "     <     ","     <=      ", "     =      ", "     >=     ", "     >     " };
	
	// Dynamic Layout Components
	private GridBagConstraints gridBagConstraints;
	
	// Database Connection Variables
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String CONNECTION = "jdbc:oracle:thin:@localhost:1521:oracle";
	private static final String USER = "scott";
	private static final String PASSWORD = "tiger";
	public Connection dbConnection = null;
	
	//Query Variables
	private String finalQuery;
	private String finalGenresQuery;
	private String finalCountryQuery;
	private String finalActorQuery;
	private String finalDirectorQuery;
	private String finalTagsQuery;
	private String finalMovieQuery;
	
	//Lists
	List<String> Actors = new ArrayList<>();
	List<String> Directors = new ArrayList<>();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					JFrame frame = new HW3();
					frame.setTitle("Welcome to Movie Search - Vivek Rajaram");
					frame.setSize(1000, 700);
					frame.setLocation(100, 25);
					frame.setResizable(false);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
			}
		});
	}

	public HW3() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// Main Content
		contentPane = new JPanel();
		contentPane.setForeground(Color.YELLOW);
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//Movie Panel
		moviePanel = new JPanel();
		movieLable = new JLabel("Movie Attributes");
		moviePanel.setBorder(new LineBorder(Color.YELLOW));
		
		moviePanel.setForeground(Color.RED);
		moviePanel.setBackground(Color.GRAY);
		moviePanel.setLayout(null);
		
		moviePanel.setBounds(20, 10, 560, 30);
		movieLable.setBounds(180, 5, 200, 20);
		
		movieLable.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		movieLable.setHorizontalAlignment(SwingConstants.CENTER);
		movieLable.setForeground(Color.BLACK);
		
		moviePanel.add(movieLable);
		
		contentPane.add(moviePanel);
		
		//Genres Panel
		genresPanel = new JPanel();
		genresLabel = new JLabel("Genres");	
		genresLabelPanel = new JPanel();
		genresScrollPane = new JScrollPane(genresPanel);
		
		genresLabelPanel.setBounds(20, 40, 140, 20);
		genresLabel.setBounds(0, 0, 140, 20);
		genresPanel.setBounds(20, 60, 140, 250);
		genresScrollPane.setBounds(20, 60, 140, 250);
		
		genresPanel.setLayout(new GridLayout(22,1));
		genresLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		genresLabel.setOpaque(true);
		genresLabel.setHorizontalAlignment(SwingConstants.CENTER);
		genresLabel.setForeground(Color.BLACK);
		genresLabel.setBackground(Color.LIGHT_GRAY);
		genresLabel.setBorder(new LineBorder(Color.YELLOW));
		
		genresLabelPanel.setLayout(null);
		
		genresScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		genresScrollPane.setBorder(new LineBorder(Color.YELLOW));
		genresScrollPane.setPreferredSize(new Dimension(264, 426));
		genresScrollPane.setViewportView(genresPanel);
		
		genresLabelPanel.add(genresLabel);
		contentPane.add(genresLabelPanel);
		contentPane.add(genresScrollPane);
		
		// Movie Year
		movieYearPanel = new JPanel();
		movieYearLabel = new JLabel("Movie Year");
		movieYearLabelPanel = new JPanel();
		
		movieYearLabelPanel.setBounds(20, 310, 140, 20);
		movieYearLabel.setBounds(0, 0, 140, 20);
		movieYearPanel.setBounds(20, 330, 140, 50);
		
		
		movieYearLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		movieYearLabel.setOpaque(true);
		movieYearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		movieYearLabel.setBackground(Color.LIGHT_GRAY);
		movieYearLabel.setForeground(Color.BLACK);
		movieYearLabel.setBorder(new LineBorder(Color.YELLOW));
		
		movieYearLabelPanel.setLayout(null);
		
		// Date Pick Panel
		
		model = new UtilDateModel();
		properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		new JDatePanelImpl(model, properties);
		
		movieYearPanel.setLayout(null);
		
		fromLabel = new JLabel("From");
		fromLabel.setBounds(1, 4, 30, 20);
		movieYearPanel.add(fromLabel);
		fromModel = new UtilDateModel();
		fromProperties = new Properties();
		fromProperties.put("text.today", "Today");
		fromProperties.put("text.month", "Month");
		fromProperties.put("text.year", "Year");
		fromDatePickerPanel = new JDatePanelImpl(fromModel, fromProperties);
		fromDatePicker = new JDatePickerImpl(fromDatePickerPanel, new DateLabelFormatter());
		fromDatePicker.setBounds(31, 4, 110, 20);
		movieYearPanel.add(fromDatePicker);
		
		toLabel = new JLabel("To");
		toLabel.setBounds(1, 26, 30, 20);
		movieYearPanel.add(toLabel);
		toModel = new UtilDateModel();
		toProperties = new Properties();
		toProperties.put("text.today", "Today");
		toProperties.put("text.month", "Month");
		toProperties.put("text.year", "Year");
		toDatePickerPanel = new JDatePanelImpl(toModel, toProperties);
		toDatePicker = new JDatePickerImpl(toDatePickerPanel, new DateLabelFormatter());
		toDatePicker.setBounds(31, 26, 110, 20);
		movieYearPanel.add(toDatePicker);
		
		movieYearLabelPanel.add(movieYearLabel);
		contentPane.add(movieYearLabelPanel);
		contentPane.add(movieYearPanel);
		
		country1Panel = new JPanel();
		country1Label = new JLabel("Country");	
		country1LabelPanel = new JPanel();
		country1ScrollPane = new JScrollPane(country1Panel);
		
		country1LabelPanel.setBounds(160, 40, 140, 20);
		country1Label.setBounds(0, 0, 140, 20);
		country1Panel.setBounds(160, 60, 140, 320);
		country1ScrollPane.setBounds(160, 60, 140, 320);
		
		country1Panel.setLayout(new GridLayout(200,1));
		country1Label.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		country1Label.setOpaque(true);
		country1Label.setHorizontalAlignment(SwingConstants.CENTER);
		country1Label.setForeground(Color.BLACK);
		country1Label.setBackground(Color.LIGHT_GRAY);
		country1Label.setBorder(new LineBorder(Color.YELLOW));
		
		country1LabelPanel.setLayout(null);
		
		country1ScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		country1ScrollPane.setBorder(new LineBorder(Color.YELLOW));
		country1ScrollPane.setPreferredSize(new Dimension(264, 426));
		country1ScrollPane.setViewportView(country1Panel);
		
		country1LabelPanel.add(country1Label);
		contentPane.add(country1LabelPanel);
		contentPane.add(country1ScrollPane);
		
		// Cast Panel
		actorPanel = new JPanel();
		directorPanel = new JPanel();
		castLabel = new JLabel("Cast");
		castLabelPanel = new JPanel();
		
		castLabelPanel.setBounds(300, 40, 140, 20);
		castLabel.setBounds(0, 0, 140, 20);
		actorPanel.setBounds(300, 60, 140, 270);
		directorPanel.setBounds(300, 330, 140, 50);
		
		actorPanel.setLayout(new GridBagLayout());
		directorPanel.setLayout(new GridBagLayout());
		actorPanel.setBorder(new LineBorder(Color.YELLOW));
		directorPanel.setBorder(new LineBorder(Color.YELLOW));
		
		castLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		castLabel.setOpaque(true);
		castLabel.setHorizontalAlignment(SwingConstants.CENTER);
		castLabel.setForeground(Color.BLACK);
		castLabel.setBackground(Color.LIGHT_GRAY);
		castLabel.setBorder(new LineBorder(Color.YELLOW));
		
		castLabelPanel.setLayout(null);
		
		actorLabel = new JLabel("Actor / Actress");
		directorLabel = new JLabel("Director");
		
		actorLabel.setBounds(0, 2, 140, 20);
		actorLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		actorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actorLabel.setForeground(Color.BLACK);
		
		directorLabel.setBounds(0, 2, 140, 20);
		directorLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		directorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		directorLabel.setForeground(Color.BLACK);
		
		actorPanel.add(actorLabel);
		directorPanel.add(directorLabel);
		
		actor1 =  new JComboBox();
		actor1.setBounds(0, 22, 140, 30);
		actor1.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		actor2 =  new JComboBox();
		actor2.setBounds(0, 52, 140, 30);
		actor2.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		actor3 =  new JComboBox();
		actor3.setBounds(0, 82, 140, 30);
		actor3.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		actor4 =  new JComboBox();
		actor4.setBounds(0, 112, 140, 30);
		actor4.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		director =  new JComboBox();
		director.setBounds(0, 22, 140, 30);
		director.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		
		actor1.addItem("Search Actor");
		actor2.addItem("Search Actor");
		actor3.addItem("Search Actor");
		actor4.addItem("Search Actor");
		director.addItem("Search Director");
		
		actorPanel.add(actor1);
		actorPanel.add(actor2);
		actorPanel.add(actor3);
		actorPanel.add(actor4);
		directorPanel.add(director);
		
		actorPanel.setLayout(null);
		directorPanel.setLayout(null);
		
		castLabelPanel.add(castLabel);
		contentPane.add(castLabelPanel);
		contentPane.add(actorPanel); 
		contentPane.add(directorPanel); 
		
		
		// Tags Panel
		tagPanel = new JPanel();
		tagLabel = new JLabel("Tag IDs and Values");
		tagLabelPanel = new JPanel();
		tagScrollPane = new JScrollPane(tagPanel);
		
		tagLabelPanel.setBounds(440, 40, 140, 20);
		tagLabel.setBounds(0, 0, 140, 20);
		tagPanel.setBounds(440, 60, 140, 250);
		tagScrollPane.setBounds(440, 60, 140, 250);
		
		tagPanel.setLayout(new GridLayout(200,1));
		tagLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		tagLabel.setOpaque(true);
		tagLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tagLabel.setForeground(Color.BLACK);
		tagLabel.setBackground(Color.LIGHT_GRAY);
		tagLabel.setBorder(new LineBorder(Color.YELLOW));
		
		tagLabelPanel.setLayout(null);
		
		tagScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tagScrollPane.setBorder(new LineBorder(Color.YELLOW));
		tagScrollPane.setPreferredSize(new Dimension(264, 426));
		tagScrollPane.setViewportView(tagPanel);
		
		tagLabelPanel.add(tagLabel);
		contentPane.add(tagLabelPanel);
		contentPane.add(tagScrollPane);
		
		tagWeightLabel = new JLabel("Tag Weight :");
		tagWeightLabel.setBounds(0, 2, 100, 20);
		tagWeightLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		tagWeightLabel.setForeground(Color.BLACK);
		
		
		
		valueLabel = new JLabel("Value :");
		valueLabel.setBounds(0, 42, 40, 20);
		valueLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		valueLabel.setForeground(Color.BLACK);
		
		tagWeightOperators = new JComboBox(conditionalOperators);
		tagWeightOperators.setBounds(30, 22, 105, 20);
		
		tagWeightValue = new JTextField();
		tagWeightValue.setBounds(40, 42, 95, 20);
		
		tagWeight = new JPanel();
		tagWeight.setLayout(null);
		tagWeight.setBounds(440, 310, 140, 70);
		tagWeight.setBorder(new LineBorder(Color.YELLOW));
		tagWeight.add(tagWeightLabel);
		tagWeight.add(valueLabel);
		tagWeight.add(tagWeightOperators);
		tagWeight.add(tagWeightValue);
		contentPane.add(tagWeight);
		
		
		// Movie Results Panel
		movieResultPanel = new JPanel();
		movieResultLabel = new JLabel("Movie Results");
		movieResultLabelPanel = new JPanel();
		movieResultScrollPane = new JScrollPane(movieResultPanel);
		
		movieResultLabelPanel.setBounds(650, 10, 330, 30);
		movieResultLabel.setBounds(0, 0, 330, 30);
		movieResultPanel.setBounds(650, 35, 330, 345);
		movieResultScrollPane.setBounds(650, 35, 330, 345);
		
		movieResultPanel.setLayout(new GridLayout(1000,1));
		movieResultLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		movieResultLabel.setOpaque(true);
		movieResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		movieResultLabel.setForeground(Color.BLACK);
		movieResultLabel.setBackground(Color.GRAY);
		movieResultLabel.setBorder(new LineBorder(Color.YELLOW));
		movieResultPanel.setBorder(new LineBorder(Color.YELLOW));
		movieResultLabelPanel.setLayout(null);
		
		movieResultScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		movieResultScrollPane.setBorder(new LineBorder(Color.YELLOW));
		movieResultScrollPane.setPreferredSize(new Dimension(264, 426));
		movieResultScrollPane.setViewportView(movieResultPanel);
		
		movieResultLabelPanel.add(movieResultLabel);
		contentPane.add(movieResultLabelPanel);
		contentPane.add(movieResultScrollPane);
		
		// user Results Panel
		userResultPanel = new JPanel();
		userResultLabel = new JLabel("User Results");
		userResultLabelPanel = new JPanel();
		userResultScrollPane = new JScrollPane(userResultPanel);
		
		userResultLabelPanel.setBounds(650, 420, 330, 30);
		userResultLabel.setBounds(0, 0, 380, 30);
		userResultPanel.setBounds(650, 450, 330, 210);
		userResultScrollPane.setBounds(650, 450, 330, 210);
		
		userResultPanel.setLayout(new GridLayout(1000,1));
		userResultLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		userResultLabel.setOpaque(true);
		userResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userResultLabel.setForeground(Color.BLACK);
		userResultLabel.setBackground(Color.GRAY);
		userResultLabel.setBorder(new LineBorder(Color.YELLOW));
		
		userResultLabelPanel.setLayout(null);
		
		userResultScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		userResultScrollPane.setBorder(new LineBorder(Color.YELLOW));
		userResultScrollPane.setPreferredSize(new Dimension(264, 426));
		userResultScrollPane.setViewportView(userResultPanel);
		
		userResultLabelPanel.add(userResultLabel);
		contentPane.add(userResultLabelPanel);
		contentPane.add(userResultScrollPane);
		
		
		// Bottom Panel
		
		btnExecuteMovieQuery = new JButton("Execute Movie Query");
		btnExecuteMovieQuery.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnExecuteMovieQuery.setForeground(Color.BLACK);
		btnExecuteMovieQuery.setBackground(Color.GRAY);
		btnExecuteMovieQuery.setOpaque(true);
		btnExecuteMovieQuery.setBounds(20, 630, 165, 40);
		btnExecuteMovieQuery.setBorder(new LineBorder(Color.YELLOW));
		
		movieResultsCount = new JTextArea();
		movieResultsCount.setForeground(Color.BLACK);
		movieResultsCount.setBounds(190, 630, 100, 40);
		movieResultsCount.setBorder(new LineBorder(Color.YELLOW));
		
		btnExecuteUserQuery = new JButton("Execute User Query");
		btnExecuteUserQuery.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnExecuteUserQuery.setForeground(Color.BLACK);
		btnExecuteUserQuery.setBackground(Color.GRAY);
		btnExecuteUserQuery.setOpaque(true);
		btnExecuteUserQuery.setBounds(320, 630, 165, 40);
		btnExecuteUserQuery.setBorder(new LineBorder(Color.YELLOW));
		
		userResultsCount = new JTextArea();
		userResultsCount.setForeground(Color.BLACK);
		userResultsCount.setBounds(490, 630, 100, 40);
		userResultsCount.setBorder(new LineBorder(Color.YELLOW));
		
		contentPane.add(btnExecuteMovieQuery);
		contentPane.add(movieResultsCount);
		contentPane.add(btnExecuteUserQuery);
		contentPane.add(userResultsCount);
		
		// Query Panel
		querryDisplay = new JTextArea();
		querryDisplay.setLineWrap(true);
		querryDisplay.setForeground(Color.BLACK);
		querryDisplay.setBounds(20, 420, 600, 200);
		querryDisplay.setBorder(new LineBorder(Color.YELLOW));
		querryDisplay.setFont(new Font("Tahoma", Font.PLAIN, 10));
		contentPane.add(querryDisplay);
		
		// Selection Panel
		genreSelection = new JComboBox(binaryOperators);
		genreSelection.setBounds(20, 390, 47, 20);
		genreSelection.setBorder(new LineBorder(Color.YELLOW));
		
		btnExecuteGenre = new JButton("Execute-1");
		btnExecuteGenre.setBounds(70, 390, 90, 20);
		btnExecuteGenre.setBorder(new LineBorder(Color.YELLOW));
		
		countrySelection = new JComboBox(binaryOperators);
		countrySelection.setBounds(161, 390, 47, 20);
		countrySelection.setBorder(new LineBorder(Color.YELLOW));
		
		btnExecuteCountry = new JButton("Execute-2");
		btnExecuteCountry.setBounds(210, 390, 90, 20);
		btnExecuteCountry.setBorder(new LineBorder(Color.YELLOW));
		
		castSelection = new JComboBox(binaryOperators);
		castSelection.setBounds(301, 390, 47, 20);
		castSelection.setBorder(new LineBorder(Color.YELLOW));
		
		btnExecuteCast = new JButton("Execute-3");
		btnExecuteCast.setBounds(350, 390, 90, 20);
		btnExecuteCast.setBorder(new LineBorder(Color.YELLOW));
		
		tagSelection = new JComboBox(binaryOperators);
		tagSelection.setBounds(441, 390, 47, 20);
		tagSelection.setBorder(new LineBorder(Color.YELLOW));
		
		btnExecuteTag = new JButton("Execute-4");
		btnExecuteTag.setBounds(490, 390, 90, 20);
		btnExecuteTag.setBorder(new LineBorder(Color.YELLOW));
		
		movieSelection = new JComboBox(binaryOperators);
		movieSelection.setBounds(680, 390, 47, 20);
		movieSelection.setBorder(new LineBorder(Color.YELLOW));
		
		contentPane.add(genreSelection);
		contentPane.add(btnExecuteGenre);
		contentPane.add(countrySelection);
		contentPane.add(btnExecuteCountry);
		contentPane.add(castSelection);
		contentPane.add(btnExecuteCast);
		contentPane.add(tagSelection);
//		contentPane.add(btnExecuteTag);
//		contentPane.add(movieSelection);
		
		btnExecuteGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(validateGenres()){
					if (genreSelection.getSelectedIndex() == 0) {
						getCountries();
					} else {
						getCountriesForOr();
					}
				}
				
			}
		});
		
		btnExecuteCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if (countrySelection.getSelectedIndex() == 0) {
						getActors();
						getDirectors();
					} else {
						getActorsOr();
						getDirectorsOr();
					}
			}
		});
		
		btnExecuteCast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if (castSelection.getSelectedIndex() == 0) {
						getTags();
					} else {
						getTagsOr();
					}
			}
		});
		
		btnExecuteMovieQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(validateGenres()){
					if (tagSelection.getSelectedIndex() == 0) {
						getMovies();
					} else {
						getMoviesOr();
					}
				}
			}
		});
		
		btnExecuteUserQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(validateMovies()){
					getUsers();
				}
			}
		});
		
		// Database Connectivity 
		try {
			Class.forName(DRIVER);
			dbConnection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			if (dbConnection != null) {
				System.out.println("Successfully Connected to: " + dbConnection.toString());
				addGenres();
			}else{
				System.out.println("Could Not Connect To Database");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error in Connecting Driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error in Database Connectivity");
			e.printStackTrace();
		}
		
	}
	
	private void addGenres() {
		PreparedStatement statement;
		finalQuery = "SELECT DISTINCT genre FROM Movie_Genres";

//		String tagsQuery = "select value from Tags";

		System.out.println("Query" + finalQuery);
		System.out.println("Query Result : " + finalQuery);

		try {
			boolean emptyResult = true;
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);

			while (resultSet.next()) {
				JCheckBox checkBox = new JCheckBox(resultSet.getString(1));
				genresPanel.add(checkBox, gridBagConstraints);
			   /* checkBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
//						if (genreSelection.getSelectedIndex() == 0) {
//							getCountries();
//						} else {
//							getCountriesForOr();
//						}
					}
				});*/
				genresPanel.revalidate();
				emptyResult = false;
			}

			if (emptyResult) {
				genresPanel.removeAll();
				genresPanel.revalidate();
				genresPanel.repaint();
//				filmingLocationPanel.removeAll();
//				filmingLocationPanel.revalidate();
//				filmingLocationPanel.repaint();
			}
			// To generate Tags Section
//			statement = (PreparedStatement) connection.prepareStatement(tagsQuery);
//			resultSet = ((java.sql.Statement) statement).executeQuery(tagsQuery);
//			String labels = "";
//			while (resultSet.next()) {
//				// System.out.println(resultSet.getString(1));
//				labels = labels + "\n\r" + resultSet.getString(1);
//			}
//			textAreaMovieTags.setText(labels);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//Get Selected Genres
	private ArrayList<String> getGenres() {
		ArrayList<String> selectedGenres = new ArrayList<String>();
		for (Component c : genresPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				selectedGenres.add(((JCheckBox) c).getText());
			}
		}

		return selectedGenres;
	}
	
	private ArrayList<String> getSelectedCountries() {
		ArrayList<String> countries = new ArrayList<String>();
		for (Component c : country1Panel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				countries.add(((JCheckBox) c).getText());
			}
		}

		return countries;
	}
	
	// Genres Validation
	private boolean validateGenres() {

		for (Component c : genresPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {

				return true;
			}
		}

		JOptionPane.showMessageDialog(null, "Please select atleast one Genre for the search...!");
		return false;
	}
	
	private boolean validateMovies(){
		for (Component c : movieResultPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {

				return true;
			}
		}

		JOptionPane.showMessageDialog(null, "Please select atleast one Movie for the search...!");
		return false;
	}
	
	
	private void getTags() {
		PreparedStatement statement;
		
		String actorName1 = actor1.getSelectedItem().toString();
		String actorName2 = actor2.getSelectedItem().toString();
		String actorName3 = actor3.getSelectedItem().toString();
		String actorName4 = actor4.getSelectedItem().toString();
		
		List<String> selectedActors = new ArrayList<>();
		if(!actorName1.equals("Search Actor")){
			selectedActors.add(actorName1);
		}
		if(!actorName2.equals("Search Actor")){
			selectedActors.add(actorName2);
		}
		if(!actorName3.equals("Search Actor")){
			selectedActors.add(actorName3);
		}
		if(!actorName4.equals("Search Actor")){
			selectedActors.add(actorName4);
		}
		
		String directorName = director.getSelectedItem().toString();
		
		String queryActor = "";
		String queryDirector = "";
		
		for (int i = 0; i < selectedActors.size(); i++) {
			if (i > 0) {
				queryActor += " AND M" + (i - 1) + ".movieID IN ";
			}
			queryActor += "(SELECT M" + i + ".movieID FROM movie_actors M" + i + " WHERE M" + i + ".actorName = '"
					+ selectedActors.get(i) + "'";
		}
		
		for (int i = 0; i < selectedActors.size(); i++) {
			queryActor += ")";
		}
		
		if(!directorName.equals("Search Director")){
			queryDirector = "(SELECT M.movieID FROM movie_directors M where M.directorName = '" + directorName + "')";
		}
		
		if(queryActor.isEmpty() && queryDirector.isEmpty()){
			queryActor = "(" + finalCountryQuery + ")";
		}else if(queryActor.isEmpty() && !queryDirector.isEmpty()){
			queryActor = queryDirector;
			queryActor = "(" + queryActor + " INTERSECT " + "(" + finalActorQuery +")"+")";
		}else if(!queryActor.isEmpty() && queryDirector.isEmpty()){
			queryActor = "(" + queryActor + " INTERSECT " + "(" + finalActorQuery +")"+")";
		}
		else if(!queryActor.isEmpty() && !queryDirector.isEmpty()){
			
			queryActor = "(" + queryActor +" INTERSECT " + queryDirector + ")";
			queryActor = "(" + queryActor + " INTERSECT " + "(" + finalActorQuery +")"+")";
		}
		
		
		String finalQuery = "SELECT id, value FROM tags WHERE id IN (SELECT DISTINCT tagID FROM movie_tags WHERE movieID IN " + queryActor + ")";
		finalTagsQuery = "SELECT DISTINCT M.movieID FROM movie_tags M WHERE M.movieID IN " + queryActor;
		
		try {
			if (finalQuery.isEmpty()) {
				tagPanel.removeAll();
				tagPanel.revalidate();
				tagPanel.repaint();
				return;
			}
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			tagPanel.removeAll();
			tagPanel.revalidate();
			tagPanel.repaint();
			
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty() && null != resultSet.getString(2) && !resultSet.getString(2).isEmpty()) {
					JCheckBox checkBox = new JCheckBox(resultSet.getString(1) + "  "+ resultSet.getString(2));
					tagPanel.add(checkBox, gridBagConstraints);
					tagPanel.revalidate();
					emptyResult = false;
				}
			}
			
			if (emptyResult) {
				tagPanel.removeAll();
				tagPanel.revalidate();
				tagPanel.repaint();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
	}
	
	private void getTagsOr() {
		PreparedStatement statement;
		
		String actorName1 = actor1.getSelectedItem().toString();
		String actorName2 = actor2.getSelectedItem().toString();
		String actorName3 = actor3.getSelectedItem().toString();
		String actorName4 = actor4.getSelectedItem().toString();
		
		List<String> selectedActors = new ArrayList<>();
		if(!actorName1.equals("Search Actor")){
			selectedActors.add(actorName1);
		}
		if(!actorName2.equals("Search Actor")){
			selectedActors.add(actorName2);
		}
		if(!actorName3.equals("Search Actor")){
			selectedActors.add(actorName3);
		}
		if(!actorName4.equals("Search Actor")){
			selectedActors.add(actorName4);
		}
		
		String directorName = director.getSelectedItem().toString();
		
		String queryActorOr = "";
		String queryDirectorOr = "";
		if(!selectedActors.isEmpty()){
			queryActorOr = "(SELECT movieID from movie_actors WHERE actorName = '";
			for (int i = 0; i < selectedActors.size(); i++) {
				if (i == 0) {
					queryActorOr += selectedActors.get(i) + "'";
				}
				if (i > 0) {
					queryActorOr += " OR actorName = '" + selectedActors.get(i) + "'" ;
				}
				
			}
			queryActorOr += ")";
		}
		
		if(!directorName.equals("Search Director")){
			queryDirectorOr = "(SELECT M.movieID FROM movie_directors M where M.directorName = '" + directorName + "')";
		}
		
		if(queryActorOr.isEmpty() && queryDirectorOr.isEmpty()){
			queryActorOr = "(" + finalCountryQuery + ")";
		}else if(queryActorOr.isEmpty() && !queryDirectorOr.isEmpty()){
			queryActorOr = queryDirectorOr;
			queryActorOr = "(" + queryActorOr + " INTERSECT " + "(" + finalActorQuery +")"+")";
		}else if(!queryActorOr.isEmpty() && queryDirectorOr.isEmpty()){
			queryActorOr = "(" + queryActorOr + " INTERSECT " + "(" + finalActorQuery +")"+")";
		}
		else if(!queryActorOr.isEmpty() && !queryDirectorOr.isEmpty()){
			
			queryActorOr = "(" + queryActorOr +" UNION " + queryDirectorOr + ")";
			queryActorOr = "(" + queryActorOr + " INTERSECT " + "(" + finalActorQuery +")"+")";
		}
		
		
		String finalQuery = "SELECT id, value FROM tags WHERE id IN (SELECT DISTINCT tagID FROM movie_tags WHERE movieID IN " + queryActorOr + ")";
		finalTagsQuery = "SELECT DISTINCT M.movieID FROM movie_tags M WHERE M.movieID IN " + queryActorOr;
		
		try {
			if (finalQuery.isEmpty()) {
				tagPanel.removeAll();
				tagPanel.revalidate();
				tagPanel.repaint();
				return;
			}
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			tagPanel.removeAll();
			tagPanel.revalidate();
			tagPanel.repaint();
			
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty() && null != resultSet.getString(2) && !resultSet.getString(2).isEmpty()) {
					JCheckBox checkBox = new JCheckBox(resultSet.getString(1) + "  "+ resultSet.getString(2));
					tagPanel.add(checkBox, gridBagConstraints);
					tagPanel.revalidate();
					emptyResult = false;
				}
			}
			
			if (emptyResult) {
				tagPanel.removeAll();
				tagPanel.revalidate();
				tagPanel.repaint();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
	}
	
	private void getMovies(){
		PreparedStatement statement;
		
		ArrayList<String> selectedtags = (ArrayList<String>) getSelectedTags();
		String queryTags = "";
		
		for (int i = 0; i < selectedtags.size(); i++) {
			if (i > 0) {
				queryTags += " AND M" + (i - 1) + ".movieID IN ";
			}
			queryTags += "(SELECT M" + i + ".movieID FROM movie_tags M" + i + " WHERE M" + i + ".tagID = '"
					+ selectedtags.get(i) + "'";
		}
		
		for (int i = 0; i < selectedtags.size(); i++) {
			
			if(tagWeightValue.getText().isEmpty()){
				queryTags += ")";
			}else{
				String val = tagWeightValue.getText();
				String oper = tagWeightOperators.getSelectedItem().toString().trim();
				queryTags +=  " AND M" + (selectedtags.size()-1-i) + ".tagWeight "+ oper + " " + val+ ")";
			}
		}
		
		if(queryTags.isEmpty() && !tagWeightValue.getText().isEmpty()){
			
			String val = tagWeightValue.getText();
			String oper = tagWeightOperators.getSelectedItem().toString().trim();
			queryTags = "(SELECT movieID from movie_tags WHERE tagWeight " + oper + val + ")";
			queryTags = "(" + queryTags +" INTERSECT "+"("+ finalTagsQuery +")"+")";
		}else if(!queryTags.isEmpty()){
			queryTags = "(" + queryTags +" INTERSECT "+ "("+finalTagsQuery +")"+")";
		}else{
			queryTags =  "("+finalTagsQuery +")";
		}
		
		String finalQuery = "SELECT DISTINCT M.id, M.title, M.year, MC.country, M.rtAudienceRating, M.rtAudienceNumRatings FROM movies M, movie_countries MC WHERE M.id = MC.movieID AND M.id IN " + queryTags;
		finalMovieQuery = "SELECT DISTINCT M.movieID FROM movies M WHERE M.movieID IN " + queryTags;
		
		try {
			if (finalQuery.isEmpty()) {
				movieResultPanel.removeAll();
				movieResultPanel.revalidate();
				movieResultPanel.repaint();
				return;
			}
			
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			movieResultPanel.removeAll();
			movieResultPanel.revalidate();
			movieResultPanel.repaint();
			
			querryDisplay.removeAll();
			querryDisplay.revalidate();
			querryDisplay.repaint();
			
			movieResultsCount.removeAll();
			movieResultsCount.revalidate();
			movieResultsCount.repaint();
			
			userResultPanel.removeAll();
			userResultPanel.revalidate();
			userResultPanel.repaint();
			
			int Count = 0;
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					JCheckBox checkBox = new JCheckBox(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " " +  resultSet.getString(4) + " " + resultSet.getFloat(5) + " " + resultSet.getInt(6));
					movieResultPanel.add(checkBox, gridBagConstraints);
					movieResultPanel.revalidate();
					emptyResult = false;
					Count++;
				}
			}
			
			querryDisplay.setText("Movie Query : " + finalQuery);
			movieResultsCount.setText(String.valueOf(Count));
			
			if (emptyResult) {
				movieResultPanel.removeAll();
				movieResultPanel.revalidate();
				movieResultPanel.repaint();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
	}
	
	private void getMoviesOr(){
		PreparedStatement statement;
		
		ArrayList<String> selectedtags = (ArrayList<String>) getSelectedTags();
		String queryTagsOr = "";
		if (!selectedtags.isEmpty()) {
			queryTagsOr = "(SELECT movieID FROM movie_tags WHERE tagID ='";
			for (int i = 0; i < selectedtags.size(); i++) {
				if (i == 0) {
					queryTagsOr += selectedtags.get(i) + "'";
				}
				if (i > 0) {
					queryTagsOr += " OR tagID = '" + selectedtags.get(i) + "'";
				}
			}

			if (tagWeightValue.getText().isEmpty()) {
				queryTagsOr += ")";
			} else {
				String val = tagWeightValue.getText();
				String oper = tagWeightOperators.getSelectedItem().toString().trim();
				queryTagsOr += " OR tagWeight " + oper + " " + val + ")";
			}

		}
		
		
		if(queryTagsOr.isEmpty() && !tagWeightValue.getText().isEmpty()){
			
			String val = tagWeightValue.getText();
			String oper = tagWeightOperators.getSelectedItem().toString().trim();
			queryTagsOr = "(SELECT movieID from movie_tags WHERE tagWeight " + oper + val + ")";
			queryTagsOr = "(" + queryTagsOr +" INTERSECT "+"("+ finalTagsQuery +")"+")";
		}else if(!queryTagsOr.isEmpty()){
			queryTagsOr = "(" + queryTagsOr +" INTERSECT "+ "("+finalTagsQuery +")"+")";
		}else{
			queryTagsOr =  "("+finalTagsQuery +")";
		}
		
		String finalQuery = "SELECT DISTINCT M.id, M.title, M.year, MC.country, M.rtAudienceRating, M.rtAudienceNumRatings FROM movies M, movie_countries MC WHERE M.id = MC.movieID AND M.id IN " + queryTagsOr;
		finalMovieQuery = "SELECT DISTINCT M.movieID FROM movies M WHERE M.movieID IN " + queryTagsOr;
		
		try {
			if (finalQuery.isEmpty()) {
				movieResultPanel.removeAll();
				movieResultPanel.revalidate();
				movieResultPanel.repaint();
				return;
			}
			
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			movieResultPanel.removeAll();
			movieResultPanel.revalidate();
			movieResultPanel.repaint();
			
			querryDisplay.removeAll();
			querryDisplay.revalidate();
			querryDisplay.repaint();
			
			movieResultsCount.removeAll();
			movieResultsCount.revalidate();
			movieResultsCount.repaint();
			
			int Count = 0;
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					JCheckBox checkBox = new JCheckBox(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3) + " " +  resultSet.getString(4) + " " + resultSet.getFloat(5) + " " + resultSet.getInt(6));
					movieResultPanel.add(checkBox, gridBagConstraints);
					movieResultPanel.revalidate();
					emptyResult = false;
					Count++;
				}
			}
			
			querryDisplay.setText("Movie Query : " + finalQuery);
			movieResultsCount.setText(String.valueOf(Count));
			
			if (emptyResult) {
				movieResultPanel.removeAll();
				movieResultPanel.revalidate();
				movieResultPanel.repaint();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void getDirectors() {
		
		PreparedStatement statement;
		ArrayList<String> selectedCountries = (ArrayList<String>) getSelectedCountries();
		
		String queryCountry = "";
		
		for (int i = 0; i < selectedCountries.size(); i++) {
			if (i > 0) {
				queryCountry += " AND M" + (i - 1) + ".movieID IN ";
			}
			queryCountry += "(SELECT M" + i + ".movieID FROM movie_countries M" + i + " WHERE M" + i + ".country = '"
					+ selectedCountries.get(i) + "'";
		}
		
		for (int i = 0; i < selectedCountries.size(); i++) {
			queryCountry += ")";
		}
		
		if(queryCountry.isEmpty()){
			queryCountry = "(" + finalCountryQuery + ")";
		}else{
			
			queryCountry = "(" + queryCountry + " INTERSECT " + "(" + finalCountryQuery +")"+")";
		}
		
		
		String finalQuery = "SELECT DISTINCT M.directorName FROM movie_directors M WHERE M.movieID IN " + queryCountry;
		finalDirectorQuery = "SELECT DISTINCT M.movieID FROM movie_directors M WHERE M.movieID IN " + queryCountry;
		
		try {
			if (finalQuery.isEmpty()) {
				return;
			}
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			Directors.clear();
			director.removeAllItems();
			
			director.addItem("Search Director");
			
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					director.addItem(resultSet.getString(1));
					
					Directors.add(resultSet.getString(1));
					emptyResult = false;
				}
			}
			
			System.out.println(Directors.size());
			
			
//			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	private void getDirectorsOr() {
			
		PreparedStatement statement;
		ArrayList<String> selectedCountries = (ArrayList<String>) getSelectedCountries();
		System.out.println("Inside actors or");
		String queryCountryOr = "";
		boolean flag = false;
		queryCountryOr = "(SELECT DISTINCT M.movieID FROM movie_countries M WHERE M.country = '";
		for (int i = 0; i < selectedCountries.size(); i++) {
			if(i == 0){
				queryCountryOr += selectedCountries.get(i) + "'";
			}
			if (i > 0) {
				queryCountryOr += " OR M.country = '" + selectedCountries.get(i) + "'" ;
			}
			flag = true;
		}
		queryCountryOr += ")";
		
		
		
		
		if(!flag){
			queryCountryOr = "(" + finalCountryQuery + ")";
		}else{
			
			queryCountryOr = "(" + queryCountryOr + " INTERSECT " + "(" + finalCountryQuery +")"+")";
		
		}
		
		String finalQuery = "SELECT DISTINCT M.directorName FROM movie_directors M WHERE M.movieID IN " + queryCountryOr;
		finalDirectorQuery = "SELECT DISTINCT M.movieID FROM movie_directors M WHERE M.movieID IN " + queryCountryOr;
		
		try {
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			Directors.clear();
			director.removeAllItems();
			
			director.addItem("Search Director");
			
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					director.addItem(resultSet.getString(1));
					
					Directors.add(resultSet.getString(1));
					emptyResult = false;
				}
			}
			
			System.out.println(Directors.size());
			
			
//			for (String s : Actors) {
//				System.out.println(s);
//			}
			
			if (emptyResult) {
//				country1Panel.removeAll();
//				country1Panel.revalidate();
//				country1Panel.repaint();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	
	private void getActors() {
		PreparedStatement statement;
		ArrayList<String> selectedCountries = (ArrayList<String>) getSelectedCountries();
		ArrayList<String> selectedGenres = (ArrayList<String>) getGenres();
		
		
		
		String queryCountry = "";
		for (int i = 0; i < selectedCountries.size(); i++) {
			if (i > 0) {
				queryCountry += " AND M" + (i - 1) + ".movieID IN ";
			}
			queryCountry += "(SELECT M" + i + ".movieID FROM movie_countries M" + i + " WHERE M" + i + ".country = '"
					+ selectedCountries.get(i) + "'";
		}
		for (int i = 0; i < selectedCountries.size(); i++) {
			queryCountry += ")";
		}
		
		if(queryCountry.isEmpty()){
			queryCountry = "(" + finalCountryQuery + ")";
		}else{
			
			queryCountry = "(" + queryCountry + " INTERSECT " + "(" + finalCountryQuery +")"+")";
		}
		
		String finalQuery = "SELECT DISTINCT M.actorName  FROM movie_actors M WHERE M.movieID IN " + queryCountry;
		finalActorQuery = "SELECT DISTINCT M.movieID  FROM movie_actors M WHERE M.movieID IN " + queryCountry;
		
		try {
			if (finalQuery.isEmpty()) {
//				country1Panel.removeAll();
//				country1Panel.revalidate();
//				country1Panel.repaint();
				return;
			}
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			Actors.clear();
			actor1.removeAllItems();
			actor2.removeAllItems();
			actor3.removeAllItems();
			actor4.removeAllItems();
			
			actor1.addItem("Search Actor");
			actor2.addItem("Search Actor");
			actor3.addItem("Search Actor");
			actor4.addItem("Search Actor");
			
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					actor1.addItem(resultSet.getString(1));
					actor2.addItem(resultSet.getString(1));
					actor3.addItem(resultSet.getString(1));
					actor4.addItem(resultSet.getString(1));
					
					Actors.add(resultSet.getString(1));
					emptyResult = false;
				}
			}
			
			System.out.println(Actors.size());
			
			
//			for (String s : Actors) {
//				System.out.println(s);
//			}
			
			if (emptyResult) {
//				country1Panel.removeAll();
//				country1Panel.revalidate();
//				country1Panel.repaint();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void getActorsOr() {
		PreparedStatement statement;
		ArrayList<String> selectedCountries = (ArrayList<String>) getSelectedCountries();
		System.out.println("Inside actors or");
		String queryCountryOr = "";
		boolean flag = false;
		queryCountryOr = "(SELECT DISTINCT M.movieID FROM movie_countries M WHERE M.country = '";
		for (int i = 0; i < selectedCountries.size(); i++) {
			if(i == 0){
				queryCountryOr += selectedCountries.get(i) + "'";
			}
			if (i > 0) {
				queryCountryOr += " OR M.country = '" + selectedCountries.get(i) + "'" ;
			}
			flag = true;
		}
		queryCountryOr += ")";
		
		
		
		
		if(!flag){
			queryCountryOr = "(" + finalCountryQuery + ")";
		}else{
			
			queryCountryOr = "(" + queryCountryOr + " INTERSECT " + "(" + finalCountryQuery +")"+")";
		}
		
		String finalQuery = "SELECT DISTINCT M.actorName  FROM movie_actors M WHERE M.movieID IN " + queryCountryOr;
		finalActorQuery = "SELECT DISTINCT M.movieID  FROM movie_actors M WHERE M.movieID IN " + queryCountryOr;
		
		try {
			if (!flag) {
//				country1Panel.removeAll();
//				country1Panel.revalidate();
//				country1Panel.repaint();
//				return;
			}
			boolean emptyResult = true;
			System.out.println(finalQuery);
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			Actors.clear();
			actor1.removeAllItems();
			actor2.removeAllItems();
			actor3.removeAllItems();
			actor4.removeAllItems();
			
			actor1.addItem("Search Actor");
			actor2.addItem("Search Actor");
			actor3.addItem("Search Actor");
			actor4.addItem("Search Actor");
			
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					actor1.addItem(resultSet.getString(1));
					actor2.addItem(resultSet.getString(1));
					actor3.addItem(resultSet.getString(1));
					actor4.addItem(resultSet.getString(1));
					
					Actors.add(resultSet.getString(1));
					emptyResult = false;
				}
			}
			
			System.out.println(Actors.size());
			
			
//			for (String s : Actors) {
//				System.out.println(s);
//			}
			
			if (emptyResult) {
//				country1Panel.removeAll();
//				country1Panel.revalidate();
//				country1Panel.repaint();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	private void getCountries() {
		PreparedStatement statement;
		ArrayList<String> selectedGenres = (ArrayList<String>) getGenres();
		
		String fromYear = getFromYear();
		String toYear = getToYear();
		String query = "";
		
		if(fromYear.equals(null) || fromYear.isEmpty() || toYear.equals(null) || toYear.isEmpty()){
			
			for (int i = 0; i < selectedGenres.size(); i++) {
				if (i > 0) {
					query += " AND M" + (i - 1) + ".movieID IN ";
				}
				query += "(SELECT M" + i + ".movieID FROM Movie_genres M" + i + " WHERE M" + i + ".Genre = '"
						+ selectedGenres.get(i) + "'";
			}
			for (int i = 0; i < selectedGenres.size(); i++) {
				query += ")";
			}
		}else{
			
			String yearQuery = "";
			for (int i = 0; i < selectedGenres.size(); i++) {
				if (i > 0) {
					query += " AND M" + (i - 1) + ".movieID IN ";
				}
				query += "(SELECT M" + i + ".movieID FROM Movie_genres M" + i + " WHERE M" + i + ".Genre = '"
						+ selectedGenres.get(i) + "'";
			}
			for (int i = 0; i < selectedGenres.size(); i++) {
				query += ")";
			}
			yearQuery = "(select id from movies where year between " +fromYear+" AND " +toYear+")";
			if (!query.isEmpty()) {
				query ="("+ query + " INTERSECT " + yearQuery + ")";
			}
		}
		
		finalGenresQuery = query;
		finalQuery = "SELECT DISTINCT M.country  FROM movie_countries M WHERE M.movieID IN " + query;
		finalCountryQuery = "SELECT DISTINCT M.movieID  FROM Movie_countries M WHERE M.movieID IN " + query;

		System.out.println(query);
		System.out.println(finalQuery);

		try {
			if (query.isEmpty()) {
				country1Panel.removeAll();
				country1Panel.revalidate();
				country1Panel.repaint();
				return;
			}
			boolean emptyResult = true;
			statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
			country1Panel.removeAll();
			country1Panel.revalidate();
			country1Panel.repaint();
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					JCheckBox checkBox = new JCheckBox(resultSet.getString(1));
					country1Panel.add(checkBox, gridBagConstraints);
					System.out.println(resultSet.getString(1));
					country1Panel.revalidate();
					emptyResult = false;
				}
			}
			
			if (emptyResult) {
				country1Panel.removeAll();
				country1Panel.revalidate();
				country1Panel.repaint();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	private void getCountriesForOr() {
		PreparedStatement statement;
		ArrayList<String> genreSelected = (ArrayList<String>) getGenres();
		
		String fromYear = getFromYear();
		String toYear = getToYear();
		String queryOr = "";
		
		if(fromYear.equals(null) || fromYear.isEmpty() || toYear.equals(null) || toYear.isEmpty()){
			 queryOr = "(SELECT DISTINCT MC.country FROM Movie_genres MG, Movie_countries MC WHERE MC.movieID = MG.movieID AND ";
			for (int i = 0; i < genreSelected.size(); i++) {
				if (i > 0) {
					queryOr += " OR ";
				}
				queryOr += "MG.Genre = '" + genreSelected.get(i) + "'";
		
			}
			queryOr += ")";
		}else{
			Boolean flag = false;
			String yearQuery = "";
			 queryOr = "(SELECT DISTINCT MC.country FROM Movie_genres MG, Movie_countries MC WHERE MC.movieID = MG.movieID AND ";
					for (int i = 0; i < genreSelected.size(); i++) {
						if (i > 0) {
							queryOr += " OR ";
						}
						queryOr += "MG.Genre = '" + genreSelected.get(i) + "'";
						flag = true;
					}
					queryOr += ")";
			yearQuery = "( Select distinct country from movie_countries where movieID in (select id from movies where year between " +fromYear+" AND " +toYear+"))";
			if (flag) {
				queryOr = queryOr + " UNION " + yearQuery ;
			}
			finalCountryQuery = "SELECT DISTINCT M.movieID FROM Movie_countries M WHERE M.movieID IN " + queryOr;
		}
		
		if (genreSelected.size() == 0) {
			country1Panel.removeAll();
			country1Panel.revalidate();
			country1Panel.repaint();
			return;
		}
		System.out.println(queryOr);
		try {
			boolean emptyResult = true;
			statement = (PreparedStatement) dbConnection.prepareStatement(queryOr);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(queryOr);
			country1Panel.removeAll();
			country1Panel.revalidate();
			country1Panel.repaint();
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					JCheckBox cb = new JCheckBox(resultSet.getString(1));
					country1Panel.add(cb, gridBagConstraints);
					country1Panel.revalidate();
					emptyResult = false;
				}
			}
			if (emptyResult) {
				country1Panel.removeAll();
				country1Panel.revalidate();
				country1Panel.repaint();
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	private void getUsers(){
		PreparedStatement statement;
		ArrayList<String> selectedMovies = (ArrayList<String>) getSelectedMovies();
		ArrayList<String> selectedTags = (ArrayList<String>) getSelectedTags();
		
		String userQueryfromMoives = "";
		String userQueryfromTags = "";
		String finalQuery = "";
		
		if(selectedTags.isEmpty()){
			JOptionPane.showMessageDialog(null, "Please select atleast one Tag for the search...!");
		}else{
			userQueryfromMoives = "(SELECT DISTINCT M.userID FROM user_taggedmovies M WHERE M.movieID = '";
			for (int i = 0; i < selectedMovies.size(); i++) {
				if(i == 0){
					userQueryfromMoives += selectedMovies.get(i) + "'";
				}
				if (i > 0) {
					userQueryfromMoives += " OR M.movieID = '" + selectedMovies.get(i) + "'" ;
				}
			}
			userQueryfromMoives += ")";
			
			userQueryfromTags = "(SELECT DISTINCT M.userID FROM user_taggedmovies M WHERE M.tagID = '";
			for (int i = 0; i < selectedTags.size(); i++) {
				if(i == 0){
					userQueryfromTags += selectedTags.get(i) + "'";
				}
				if (i > 0) {
					userQueryfromTags += " OR M.tagID = '" + selectedTags.get(i) + "'" ;
				}
			}
			userQueryfromTags += ")";
			
			finalQuery = userQueryfromTags + " INTERSECT " + userQueryfromMoives;
		}
			
			
			if (finalQuery.isEmpty()) {
				userResultPanel.removeAll();
				userResultPanel.revalidate();
				userResultPanel.repaint();
				return;
			}
			System.out.println(finalQuery);
			try {
				boolean emptyResult = true;
				statement = (PreparedStatement) dbConnection.prepareStatement(finalQuery);
				ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);
				userResultPanel.removeAll();
				userResultPanel.revalidate();
				userResultPanel.repaint();
				userResultsCount.removeAll();
				userResultsCount.revalidate();
				userResultsCount.repaint();
				querryDisplay.setText("User Query : " + finalQuery);
				
				int Count = 0;
				while (resultSet.next()) {
					if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
						JCheckBox cb = new JCheckBox(resultSet.getString(1));
						userResultPanel.add(cb, gridBagConstraints);
						userResultPanel.revalidate();
						emptyResult = false;
						Count++;
					}
				}
				
				userResultsCount.setText(String.valueOf(Count));
				querryDisplay.setText("User Query : " + finalQuery);
				
				if (emptyResult) {
					userResultPanel.removeAll();
					userResultPanel.revalidate();
					userResultPanel.repaint();
					userResultsCount.removeAll();
					userResultsCount.revalidate();
					userResultsCount.repaint();
				}
				

			} catch (SQLException exception) {
				exception.printStackTrace();
			}
			
	}
	
	
	private ArrayList<String> getSelectedTags() {
		ArrayList<String> selectedTags = new ArrayList<String>();
		for (Component c : tagPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				String[] tagId = ((JCheckBox) c).getText().split(" ");
				selectedTags.add(tagId[0]);
			}
		}
		return selectedTags;
	}
	
	
	private ArrayList<String> getSelectedMovies() {
		ArrayList<String> selectedMovies = new ArrayList<String>();
		for (Component c : movieResultPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				String[] MovieID = ((JCheckBox) c).getText().split(" ");
				selectedMovies.add(MovieID[0]);
			}
		}
		return selectedMovies;
	}
	
	
	private String getFromYear() {
		Date selectedDate = null;
		int year;
		String selectedYear = "";
		try {
			if (null != fromDatePicker.getModel().getValue()) {
				selectedDate = (Date) fromDatePicker.getModel().getValue();
				year = Integer.parseInt(new SimpleDateFormat("yyyy").format(selectedDate));
				selectedYear = String.valueOf(year);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectedYear;
	}
	

	private String getToYear() {
		Date selectedDate = null;
		int year;
		String selectedYear = "";
		try {
			if (null != toDatePicker.getModel().getValue()) {
				selectedDate = (Date) toDatePicker.getModel().getValue();
				year = Integer.parseInt(new SimpleDateFormat("yyyy").format(selectedDate));
				selectedYear = String.valueOf(year);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectedYear;
	}
	

}
