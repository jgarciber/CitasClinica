package org.iesalandalus.programacion.citasclinica;


import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.citasclinica.modelo.Cita;
import org.iesalandalus.programacion.citasclinica.modelo.Citas;
import org.iesalandalus.programacion.citasclinica.modelo.Paciente;
import org.iesalandalus.programacion.citasclinica.vista.Consola;
import org.iesalandalus.programacion.citasclinica.vista.Opciones;
import org.iesalandalus.programacion.utilidades.Entrada;

public class MainApp {
	private static final int NUM_MAX_CITAS = 30;
	private static Citas citasClinica;
	public static void main(String[] args) {
		Opciones opcion;
		citasClinica=new Citas(NUM_MAX_CITAS);
		do {
			System.out.println("Programa para gestionar las citas de la Clínica.");
			Consola.mostrarMenu();
			opcion=Consola.elegirOpcion();
			ejecutarOpcion(opcion);
		}while(opcion!=Opciones.SALIR);
	}

	private static void ejecutarOpcion(Opciones opcionMenu) {
		switch (opcionMenu){
		case SALIR://no hace nada por tanto acabará la ejecución del programa ya que la condicion del bucle do-while del método main se cumplirá
			break;
		case INSERTAR_CITA:
			inserterCita();
			break;
		case BUSCAR_CITA:
			buscarCita();
			break;
		case BORRAR_CITA:
			borrarCita();
			break;
		case MOSTRAR_CITAS:
			mostrarCitas();
			break;
		case MOSTRAR_CITAS_DIA:
			mostrarCitasDia();
			break;
		}
	}
	
	private static void inserterCita() {
		Cita cita;
		try {
			cita=new Cita(Consola.leerCita());
			citasClinica.insertar(cita);
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void buscarCita() {
		LocalDateTime fecha;
		Cita citaABuscar,citaEncontrada;
		//tengo que crear un cita para utilizar el método buscar ya que este recibe como parametro una cita
		//por tanto creo un paciente de ejemplo llamado pacienteEjemplo
		//la fecha introducida junto con el pacienteEjemplo forma la cita
		//Realmente no se comparará con el pacienteEjemplo, es cuestión de respetar los parametros
		//el metodo buscar solo comparara la fecha e ignorará el paciente
		//lo mejor hubiese sido crear otro método buscar que recibe una fecha en vez de una cita
		try {
			Paciente pacienteEjemplo = new Paciente("Ejemplo Ejemplo Ejemplo","11223344b","950999999");
			fecha=Consola.leerFechaHora();//podria estar fuera del try
			citaABuscar=new Cita(pacienteEjemplo,fecha);
			if(citasClinica.buscar(citaABuscar)==null) {
				System.out.println("No se ha encontrado la cita");
			}else {
				System.out.println(citasClinica.buscar(citaABuscar).toString());
			}
		}catch (NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void borrarCita() {
		LocalDateTime fecha;
		Cita citaABorrar,citaEncontrada;
		try {
			Paciente pacienteEjemplo = new Paciente("Ejemplo Ejemplo Ejemplo","11223344b","950999999");
			fecha=Consola.leerFechaHora();//podria estar fuera del try
			citaABorrar=new Cita(pacienteEjemplo,fecha);
			if(citasClinica.buscar(citaABorrar)==null) {
				System.out.println("No se ha encontrado la cita");
			}else {
				citaEncontrada=new Cita(citasClinica.buscar(citaABorrar));
				citasClinica.borrar(citaEncontrada);
			}
		}catch (IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void mostrarCitasDia() {
		Cita[] citasEncontradas;
		LocalDate fecha;
		boolean coincidencias = false;
		fecha=Consola.leerFecha();
		citasEncontradas= new Cita[citasClinica.getTamano()];
		try {
			citasEncontradas= citasClinica.getCitas(fecha);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i;
		for(i=0 ; i<citasEncontradas.length ; i++) {
			if(citasEncontradas[i]!=null) { //solo mostramos las coincidencias no nulas.
				System.out.println(citasEncontradas[i].toString());
				coincidencias=true;
			}
		}
		if(coincidencias==false) {
			System.out.println("No hay citas en el día indicado");
		}
		
	}
	
	private static void mostrarCitas() {
		int i;
		boolean coincidencias = false;

		Cita[] citasAMostrar= citasClinica.getCitas();
	
		for(i=0 ; i<citasClinica.getTamano() ; i++) {
			System.out.println(citasAMostrar[i].toString());
			coincidencias=true;
		}	
		if(coincidencias==false) {
			System.out.println("No hay citas almacenadas");
		}
		
	}
	 
}
