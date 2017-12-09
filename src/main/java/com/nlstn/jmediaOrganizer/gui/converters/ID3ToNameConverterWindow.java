package com.nlstn.jmediaOrganizer.gui.converters;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nlstn.jmediaOrganizer.Settings;
import com.nlstn.jmediaOrganizer.processing.MP3File;

public class ID3ToNameConverterWindow extends JDialog {
	private static final long	serialVersionUID	= -9218566319600619223L;

	private int					width				= 600;
	private int					height				= 300;

	private JTextField			txtPattern;

	private JTextArea			lblExample;

	private JCheckBox			chkEnabled;

	private String				exampleArtist		= "Linkin Park";
	private String				exampleAlbum		= "A Thousand Suns";
	private String				exampleTrack		= "12";
	private String				exampleTitle		= "Iridescent";

	public ID3ToNameConverterWindow(JFrame mainFrame, MP3File preview) {
		super(mainFrame, "ID3Tag to Name Converter", true);
		setSize(width, height);
		setLocationRelativeTo(mainFrame);
		setLayout(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				saveSettings();
			}
		});

		chkEnabled = new JCheckBox();
		chkEnabled.setBounds(10, 7, 15, 15);
		chkEnabled.addActionListener((ActionEvent e) -> onToggleEnabled());
		add(chkEnabled);

		JLabel lblEnabled = new JLabel("Enabled");
		lblEnabled.setBounds(30, 7, 50, 15);
		add(lblEnabled);

		lblExample = new JTextArea();
		lblExample.setBounds(10, 27, 560, 130);
		lblExample.setEditable(false);

		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border insets = new EmptyBorder(5, 5, 5, 5);

		lblExample.setBorder(new CompoundBorder(lineBorder, insets));
		lblExample.setMargin(new Insets(5, 5, 5, 5));
		add(lblExample);

		JLabel lblPattern = new JLabel("Pattern:");
		lblPattern.setBounds(10, 167, 50, 28);
		add(lblPattern);

		txtPattern = new JTextField();
		txtPattern.setBounds(70, 167, 460, 28);
		txtPattern.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				lblExample.setText(buildPreview());
			}

			public void removeUpdate(DocumentEvent e) {
				lblExample.setText(buildPreview());
			}

			public void changedUpdate(DocumentEvent e) {
				lblExample.setText(buildPreview());
			}

		});
		add(txtPattern);

		if (preview != null)
			setPreviewData(preview.getTrack(), preview.getTitle(), preview.getArtist(), preview.getAlbum());

		loadSettings();

		lblExample.setText(buildPreview());

		setVisible(true);
	}

	private void onToggleEnabled() {
		txtPattern.setEnabled(chkEnabled.isSelected());
		lblExample.setEnabled(chkEnabled.isSelected());
	}

	private void loadSettings() {
		chkEnabled.setSelected(Settings.getID3ToNameEnabled());
		txtPattern.setText(Settings.getID3ToNamePattern());
		onToggleEnabled();
	}

	private void saveSettings() {
		Settings.setID3ToNameEnabled(chkEnabled.isSelected());
		Settings.setID3ToNamePattern(txtPattern.getText());
		Settings.save();
	}

	public void setPreviewData(String track, String title, String artist, String album) {
		exampleTrack = track;
		exampleTitle = title;
		exampleArtist = artist;
		exampleAlbum = album;
	}

	private String buildPreview() {
		StringBuilder builder = new StringBuilder();
		builder.append("Track (%track%):\t").append(exampleTrack).append("\n");
		builder.append("Title (%title%):\t").append(exampleTitle).append("\n");
		builder.append("Artist (%artist%):\t").append(exampleArtist).append("\n");
		builder.append("Album (%album%):\t").append(exampleAlbum).append("\n");
		builder.append("\n");
		builder.append("Preview:\t");
		if (txtPattern.getText() != null) {
			String pattern = txtPattern.getText().replace("%track%", exampleTrack).replace("%title%", exampleTitle).replace("%artist%", exampleArtist).replace("%album%", exampleAlbum);
			builder.append(pattern);
		}
		return builder.toString();
	}

}
