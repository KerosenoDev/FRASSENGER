package com.frassenger.modelo;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.frassenger.vista.PanelCliente;

/**
 * Cliente
 * 
 * @author Francisco Rodríguez García
 */
public class Cliente {
	private static final int PUESRTO_REGISTRADO_MAXIMO = 49151;
	private static final int PUERTO_REGISTRADO_MINIMO = 1024;
	//=====================================================================================================
	// VARIABLES ESTÁTICAS
	//=====================================================================================================
	

	//=====================================================================================================
	// MÉTODO PRINCIPAL
	//=====================================================================================================
	public static void main(String[] args) {
		//=================================================================================================
		// VARIABLES
		//=================================================================================================
		PanelCliente panel;
		JFrame ventana;
		String host, nombreUsuario;
		int puerto;
		Socket cliente;
		DataOutputStream flujoSalida;
		DataInputStream flujoEntrada;
		String mensajeRecibido;

		//=================================================================================================
		// DESARROLLO
		//=================================================================================================
		try {
			ventana = new JFrame();
			panel = new PanelCliente();
			ventana.setContentPane(panel);

			// Se declara el host y el puerto.
			host = solicitarHost(ventana);
			puerto = solicitarPuerto(ventana);
			nombreUsuario = solicitarNombreUsuario(ventana);

			// Se inicia el cliente.
			cliente = new Socket(host, puerto);
			System.out.println("Programa cliente iniciado");

			// Se abre el flujo de salida.
			flujoSalida = new DataOutputStream(cliente.getOutputStream());

			// Se envía un mensaje al servidor.
			flujoSalida.writeUTF("Saludos al servidor desde el cliente");

			// Se abre el flujo de entrada.
			flujoEntrada = new DataInputStream(cliente.getInputStream());

			// Se recibe un mensaje del servidor.
			mensajeRecibido = flujoEntrada.readUTF();
			System.out.println("He recibido del servidor: " + mensajeRecibido);

			// Se cierran los flujos y el cliente.
			flujoEntrada.close();
			flujoSalida.close();
			cliente.close();
		}

		catch (ConnectException e) {
			System.out.println("Imposible conectar con el servidor.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	/**
	 * Solicita un host.
	 * @param ventana 
	 * @return Devuelve el host, en caso de que sea válido.
	 */
	private static String solicitarHost(JFrame ventana) {
		// Variables.
		String host = null;
		InetAddressValidator validadorIp;
		boolean hayError;
		int continuar;

		// Se inicializa el validador de IP.
		validadorIp = new InetAddressValidator();

		try {
			do {
				// Se inicializan los flags.
				hayError = false;
				continuar = JOptionPane.CANCEL_OPTION;

				// Se solicita el host.
				host = JOptionPane.showInputDialog(ventana, "Introduce la IP del servidor:", "FRASSENGER", JOptionPane.QUESTION_MESSAGE);

				// Si el host no es 'localhost' ni una IP válida, hay un error.
				if (!host.equalsIgnoreCase("localhost") && !validadorIp.isValid(host)) {
					hayError = true;

					// Se pregunta si se quiere reintentar.
					continuar = JOptionPane.showConfirmDialog(ventana, "La IP no es correcta\n¿Quiere introducir otra?", "FRASSENGER", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			// Se repite el proceso si hay fallo y se quiere reintentar.
			} while (hayError && continuar == JOptionPane.OK_OPTION);

			// Si hay fallo y no se quiere reintentar, se termina la aplicación.
			if (hayError && continuar == JOptionPane.NO_OPTION) {
				System.exit(0);
			}

		// Si se pulsa cancelar en el JOptionPane, salta la excepción, y se sale.
		} catch (NullPointerException e) {
			System.exit(0);
		}

		return host;
	}

	/**
	 * Solicita un puerto.
	 * @param ventana 
	 * @return
	 */
	private static int solicitarPuerto(JFrame ventana) {
		// Variables
		int puerto = 0, continuar;
		boolean hayError;

		try {
			do {
				// Se inicializan los flags.
				hayError = true;
				continuar = JOptionPane.CANCEL_OPTION;
				
				try {
					// Se solicita el puerto.
					puerto = Integer.parseInt(JOptionPane.showInputDialog(ventana, "Introduce el puerto del servidor:", "FRASSENGER", JOptionPane.QUESTION_MESSAGE));
					
					// Si el puerto no entra dentro del rango de puertos registrados disponibles, hay error.
					if (puerto < PUERTO_REGISTRADO_MINIMO || puerto > PUESRTO_REGISTRADO_MAXIMO) {
						hayError = true;
					}
					
				// Si el puerto introducido no es un entero, hay error.
				} catch (NumberFormatException e) {
					hayError = true;
				}
				
				// Si hay error, se pregunta si se quiere volver a intentar.
				if (hayError) {
					continuar = JOptionPane.showConfirmDialog(ventana, "El puerto no es correcto\n¿Quiere introducir otro?", "FRASSENGER", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			
			// Se repite el proceso si hay error y se quiere volver a intentar.
			} while (hayError && continuar == JOptionPane.OK_OPTION);
			
			// Si hay error y no se quiere volver a intentar, se termina la aplicación.
			if (hayError && continuar == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
			
		// Si se pulsa cancelar en el JOptionPane, salta la excepción, y se sale.
		} catch (NullPointerException e) {
			System.exit(0);
		}

		return puerto;
	}

	/**
	 * Solicita un nombre de usuario.
	 * @param ventana 
	 * @return
	 */
	private static String solicitarNombreUsuario(JFrame ventana) {
		// TODO Auto-generated method stub
		return null;
	}
}