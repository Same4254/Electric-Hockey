package Levels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import FieldElements.Collider;

public class LevelDesignerValuesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField xMaxField;
	private JTextField xMinField;
	private JTextField yMaxField;
	private JTextField yMinField;
	private JTextField angleRateField;
	private JTextField xStepField;
	private JLabel lblYStep;
	private JTextField yStepField;
	private JButton btnStart;
	private JButton btnReset;
	
	public LevelDesignerValuesPanel(LevelViewPanel viewPanel) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 75, 100, 75};
		gridBagLayout.rowHeights = new int[] {25};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel lblXStep = new JLabel("X Step:");
		lblXStep.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblXStep = new GridBagConstraints();
		gbc_lblXStep.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblXStep.insets = new Insets(0, 0, 5, 5);
		gbc_lblXStep.gridx = 0;
		gbc_lblXStep.gridy = 0;
		add(lblXStep, gbc_lblXStep);
		
		xStepField = new JTextField();
		GridBagConstraints gbc_xStepField = new GridBagConstraints();
		gbc_xStepField.anchor = GridBagConstraints.WEST;
		gbc_xStepField.insets = new Insets(0, 0, 5, 5);
		gbc_xStepField.gridx = 1;
		gbc_xStepField.gridy = 0;
		add(xStepField, gbc_xStepField);
		xStepField.setColumns(10);
		xStepField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxStep(getValue(xStepField));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxStep(getValue(xStepField));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxStep(getValue(xStepField));
			}
		});
		
		JLabel lblYMax = new JLabel("Y Max:");
		lblYMax.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblYMax = new GridBagConstraints();
		gbc_lblYMax.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblYMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblYMax.gridx = 2;
		gbc_lblYMax.gridy = 0;
		add(lblYMax, gbc_lblYMax);
		
		yMaxField = new JTextField();
		GridBagConstraints gbc_yMaxField = new GridBagConstraints();
		gbc_yMaxField.anchor = GridBagConstraints.WEST;
		gbc_yMaxField.insets = new Insets(0, 0, 5, 0);
		gbc_yMaxField.gridx = 3;
		gbc_yMaxField.gridy = 0;
		add(yMaxField, gbc_yMaxField);
		yMaxField.setColumns(10);
		yMaxField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyMax(getValue(yMaxField));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyMax(getValue(yMaxField));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyMax(getValue(yMaxField));
			}
		});
		
		lblYStep = new JLabel("Y Step: ");
		lblYStep.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblYStep = new GridBagConstraints();
		gbc_lblYStep.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblYStep.insets = new Insets(0, 0, 5, 5);
		gbc_lblYStep.gridx = 0;
		gbc_lblYStep.gridy = 1;
		add(lblYStep, gbc_lblYStep);
		
		yStepField = new JTextField();
		GridBagConstraints gbc_yStepField = new GridBagConstraints();
		gbc_yStepField.anchor = GridBagConstraints.WEST;
		gbc_yStepField.insets = new Insets(0, 0, 5, 5);
		gbc_yStepField.gridx = 1;
		gbc_yStepField.gridy = 1;
		add(yStepField, gbc_yStepField);
		yStepField.setColumns(10);
		JLabel lblXMax = new JLabel("X Max:");
		lblXMax.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblXMax = new GridBagConstraints();
		gbc_lblXMax.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblXMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblXMax.gridx = 2;
		gbc_lblXMax.gridy = 1;
		add(lblXMax, gbc_lblXMax);
		
		yStepField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyStep(getValue(yStepField));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyStep(getValue(yStepField));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyStep(getValue(yStepField));
			}
		});
		
		xMaxField = new JTextField();
		GridBagConstraints gbc_xMaxField = new GridBagConstraints();
		gbc_xMaxField.anchor = GridBagConstraints.WEST;
		gbc_xMaxField.insets = new Insets(0, 0, 5, 0);
		gbc_xMaxField.gridx = 3;
		gbc_xMaxField.gridy = 1;
		add(xMaxField, gbc_xMaxField);
		xMaxField.setColumns(10);
		xMaxField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxMax(getValue(xMaxField));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxMax(getValue(xMaxField));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxMax(getValue(xMaxField));
			}
		});
		
		JLabel lblXMin = new JLabel("X Min:");
		lblXMin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblXMin = new GridBagConstraints();
		gbc_lblXMin.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblXMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblXMin.gridx = 0;
		gbc_lblXMin.gridy = 2;
		add(lblXMin, gbc_lblXMin);
		
		xMinField = new JTextField();
		GridBagConstraints gbc_xMinField = new GridBagConstraints();
		gbc_xMinField.anchor = GridBagConstraints.WEST;
		gbc_xMinField.insets = new Insets(0, 0, 5, 5);
		gbc_xMinField.gridx = 1;
		gbc_xMinField.gridy = 2;
		add(xMinField, gbc_xMinField);
		xMinField.setColumns(10);
		xMinField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxMin(getValue(xMinField));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxMin(getValue(xMinField));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setxMin(getValue(xMinField));
			}
		});
		
		JLabel lblAngleRate = new JLabel("Angle Rate: ");
		lblAngleRate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblAngleRate = new GridBagConstraints();
		gbc_lblAngleRate.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAngleRate.insets = new Insets(0, 0, 5, 5);
		gbc_lblAngleRate.gridx = 2;
		gbc_lblAngleRate.gridy = 2;
		add(lblAngleRate, gbc_lblAngleRate);
		
		angleRateField = new JTextField();
		GridBagConstraints gbc_angleRateField = new GridBagConstraints();
		gbc_angleRateField.anchor = GridBagConstraints.WEST;
		gbc_angleRateField.insets = new Insets(0, 0, 5, 0);
		gbc_angleRateField.gridx = 3;
		gbc_angleRateField.gridy = 2;
		add(angleRateField, gbc_angleRateField);
		angleRateField.setColumns(10);
		
		angleRateField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setAngleRate(getValue(angleRateField));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setAngleRate(getValue(angleRateField));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setAngleRate(getValue(angleRateField));
			}
		});
		
		JLabel lblYMin = new JLabel("Y Min: ");
		lblYMin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblYMin = new GridBagConstraints();
		gbc_lblYMin.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblYMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblYMin.gridx = 0;
		gbc_lblYMin.gridy = 3;
		add(lblYMin, gbc_lblYMin);
		
		yMinField = new JTextField();
		GridBagConstraints gbc_yMinField = new GridBagConstraints();
		gbc_yMinField.anchor = GridBagConstraints.WEST;
		gbc_yMinField.insets = new Insets(0, 0, 5, 5);
		gbc_yMinField.gridx = 1;
		gbc_yMinField.gridy = 3;
		add(yMinField, gbc_yMinField);
		yMinField.setColumns(10);
		
		yMinField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyMin(getValue(yMinField));
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyMin(getValue(yMinField));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(LevelDesigner.editCollider != null)
					LevelDesigner.editCollider.setyMin(getValue(yMinField));
			}
		});
		
		btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 5;
		add(btnStart, gbc_btnStart);
		
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewPanel.getLevel().getSimulation().alternateRunState();
				
				if(btnStart.getText().equals("Start"))
					btnStart.setText("Stop");
				else
					btnStart.setText("Start");
			}
		});
		
		btnReset = new JButton("Reset");
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.insets = new Insets(0, 0, 5, 5);
		gbc_btnReset.anchor = GridBagConstraints.NORTH;
		gbc_btnReset.gridx = 2;
		gbc_btnReset.gridy = 5;
		add(btnReset, gbc_btnReset);
		
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewPanel.getLevel().getSimulation().reset();
				btnStart.setText("Start");
			}
		});
	}

	public void changeCollider(Collider collider) {
		if(collider == null) {
			xStepField.setText("0.0");
			yStepField.setText("0.0");
			yMinField.setText("0.0");
			yMaxField.setText("0.0");
			xMinField.setText("0.0");
			xMaxField.setText("0.0");
			angleRateField.setText("0.0");
		} else {
			xStepField.setText(Double.toString(collider.getxStep()));
			yStepField.setText(Double.toString(collider.getyStep()));
			yMinField.setText(Double.toString(collider.getyMin()));
			yMaxField.setText(Double.toString(collider.getyMax()));
			xMinField.setText(Double.toString(collider.getxMin()));
			xMaxField.setText(Double.toString(collider.getxMax()));
			angleRateField.setText(Double.toString(collider.getAngleRate()));
		}
	}
	
	private double getValue(JTextField textField) {
		try {
			return Double.parseDouble(textField.getText());
		} catch(NumberFormatException e) {
			return 0;
		}
	}
}
