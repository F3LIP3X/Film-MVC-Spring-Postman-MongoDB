package com.Cine.demo.cs;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class interfazCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public String hostnameString;
	public int puerto;
	private JTextField hostField;
	private JTextField portField;
	private JTextField añoField;

	public String nombrePelicula;
	public int añoLanzamiento;
	public int duracion;
	public int notaPelicula;

	private ObjectOutputStream out;
	private JTextField notaField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					interfazCliente frame = new interfazCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public interfazCliente() {
		setTitle("CinesRafe Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 856);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 0, 0, 0));

		// ---------------------------------Nombre-App--------------------------------------------

		JPanel panelNombre = new JPanel();
		contentPane.add(panelNombre);
		panelNombre.setLayout(new GridLayout(3, 1, 0, 0));

		JLabel nombreApp = new JLabel("Cines Rafe");
		nombreApp.setHorizontalAlignment(SwingConstants.CENTER);
		nombreApp.setFont(new Font("Impact", Font.PLAIN, 90));
		panelNombre.add(nombreApp);

		JPanel panel_Imagen = new JPanel();
		panelNombre.add(panel_Imagen);
		panel_Imagen.setLayout(null);
		
		JLabel imagenLabel = new JLabel();
		imagenLabel.setBounds(253, 5, 0, 0);
		panel_Imagen.add(imagenLabel);

		ImageIcon icono = new ImageIcon("imagenes/imageCine.png");
		Image imagen = icono.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
		ImageIcon iconoEscalado = new ImageIcon(imagen);

		imagenLabel.setIcon(iconoEscalado);


		// ---------------------------------Fallo-conexión--------------------------------------------

		JPanel panelFallo = new JPanel();
		panelNombre.add(panelFallo);

		JLabel falloConecLabel = new JLabel("Las credenciales no son correctas, vuelve a intenterlo.");
		falloConecLabel.setForeground(new Color(255, 0, 0));
		falloConecLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		falloConecLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		falloConecLabel.setVisible(false);
		panelFallo.add(falloConecLabel);

		JPanel panel1 = new JPanel();
		contentPane.add(panel1);
		panel1.setLayout(new GridLayout(4, 0, 0, 0));

		// ---------------------------------Hostname--------------------------------------------

		JPanel panel_3 = new JPanel();
		panel1.add(panel_3);

		JLabel hostLabel = new JLabel("Introduzca el Hostname");
		hostLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		panel_3.add(hostLabel);

		JPanel panel_4 = new JPanel();
		panel1.add(panel_4);

		hostField = new JTextField();
		hostField.setHorizontalAlignment(SwingConstants.CENTER);
		hostField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_4.add(hostField);
		hostField.setColumns(10);

		JPanel panel_9 = new JPanel();
		panel1.add(panel_9);

		JLabel añoLabel = new JLabel("Año de lanzamiento");
		añoLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		añoLabel.setVisible(false);
		panel_9.add(añoLabel);

		JPanel panel_10 = new JPanel();
		panel1.add(panel_10);

		añoField = new JTextField();
		añoField.setHorizontalAlignment(SwingConstants.CENTER);
		añoField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		añoField.setColumns(10);
		añoField.setVisible(false);
		panel_10.add(añoField);

		JPanel panel2 = new JPanel();
		contentPane.add(panel2);
		panel2.setLayout(new GridLayout(5, 0, 0, 0));

		// ---------------------------------Puerto--------------------------------------------

		JPanel panel_5 = new JPanel();
		panel2.add(panel_5);

		JLabel portLabel = new JLabel("Introduzca el Puerto");
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		panel_5.add(portLabel);

		JPanel panel_6 = new JPanel();
		panel2.add(panel_6);

		portField = new JTextField();
		portField.setHorizontalAlignment(SwingConstants.CENTER);
		portField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_6.add(portField);
		portField.setColumns(10);

		JPanel panel_11 = new JPanel();
		panel2.add(panel_11);

		// -------------------------Nota-------------------------------

		JLabel notaLabel = new JLabel("Nota (1-10)");
		notaLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		notaLabel.setVisible(false);
		panel_11.add(notaLabel);

		JPanel panel_7 = new JPanel();
		panel2.add(panel_7);
		
		notaField = new JTextField();
		notaField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_7.add(notaField);
		notaField.setVisible(false);
		notaField.setColumns(10);

		// -------------------------Botón-Conexión-------------------------------
		JPanel panel_12 = new JPanel();
		panel2.add(panel_12);

		JButton connectButton_1 = new JButton("Conectar");
		connectButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (hostField.getText().equals("192.168.1.144") && portField.getText().equals("777")) {

					try {

						String hostName = hostField.getText();
						int portNum = Integer.parseInt(portField.getText());
						
						hostLabel.setText("Nombre");
						hostField.setText("");
						portLabel.setText("Duración");
						portField.setText("");
						falloConecLabel.setVisible(false);

						añoLabel.setVisible(true);
						añoField.setVisible(true);

						notaLabel.setVisible(true);
						notaField.setVisible(true);

					} catch (Exception e2) {
						// TODO: handle exception
					}
				} else {
					falloConecLabel.setVisible(true);
					portField.setText("");
					hostField.setText("");
				}
			}
		});
		panel_12.setLayout(new GridLayout(0, 2, 0, 0));
		connectButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_12.add(connectButton_1);

		JButton enviarButton = new JButton("Enviar");
		enviarButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		enviarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					nombrePelicula = hostField.getText();
					añoLanzamiento = Integer.parseInt(añoField.getText());
					duracion = Integer.parseInt(portField.getText());
					notaPelicula = Integer.parseInt(notaField.getText());

					Pelicula peli = new Pelicula(nombrePelicula, añoLanzamiento, duracion, notaPelicula);
					
					out.writeObject(peli);
					
//					hostField.setText("");			//192.168.1.144
//                    portField.setText("");			//777
//                    añoField.setText("");		
//                    notaField.setText("");
                    
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		panel_12.add(enviarButton);

	}

}