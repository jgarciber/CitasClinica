package org.iesalandalus.programacion.citasclinica.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.naming.OperationNotSupportedException;


public class Citas {
	private Cita [] coleccionCitas;
	private int capacidad; //capacidad máxima de citas
	private int tamano; //cantidad de citas guardas actualmente en el array coleccionCitas
	
	public Citas(int capacidad){
		if(capacidad<=0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionCitas = new Cita[capacidad];
		this.capacidad=capacidad;
		this.tamano=0;
		//Creo el array de citas con los objetos cita de longitud capacidad.
		//Inicializo tamano(cantidad de citas guardadas) a 0 y la capacidad la inicializo al NUM_MAX_CITAS 
		//que coincide con el parametro pasado capacidad
	}
	
	public void insertar(Cita cita) throws OperationNotSupportedException{
		if (cita==null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		//Buscamos si ya existe la cita.
		if(buscar(cita)!=null) {//es decir sí ha encontrado una cita ya existente. Recuerda que tamano+1 indica que no se ha encontrado
			throw new OperationNotSupportedException("ERROR: Ya existe una cita para esa fecha y hora.");
		}
		
		
		if (capacidadSuperada(tamano)==true) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		}else {
			coleccionCitas[tamano]=new Cita(cita); //añade una cita justo despues de la última almacenada
			System.out.println("Cita introducida correctamente.");
			tamano++;
		}		
	}
	
	private int buscarIndice(Cita cita) {
		int indice=tamano+1; //se inicializa a tamano+1 para el caso en que no se encuentre indice.
		int i;
		for (i=0; i<tamano;i++) {
			if (coleccionCitas[i].equals(cita)){ 
				//solo se comparan las fechas obviando los nombres.
				//se utiliza el método equals para el array ya que es un array de objetos
				indice=i;
			}
		}
		return indice ;
	}
	
	private boolean tamanoSuperado (int nuevoTamano) {
		boolean superado=false;
		if (nuevoTamano>=tamano) {
			superado=true;
		}
		return superado;
	}
	
	
	private boolean capacidadSuperada(int nuevoTamano) {
		boolean superado=false;
		if (nuevoTamano>=capacidad) {
			superado=true;
		}
		return superado;
	}
	
	public Cita buscar(Cita cita) throws OperationNotSupportedException  {
		if(cita==null) {
			throw new NullPointerException("ERROR: No se puede buscar una cita nula.");
		}
		int indice;
		Cita citaEncontrada = null; //si no encuentra cita este método devuelve null.
		indice=buscarIndice(cita);
		if (indice!=tamano+1) {
			citaEncontrada=new Cita(coleccionCitas[indice]);//obtengo una copia de la cita
		}

		return citaEncontrada; //devuelvo una copia de la cita encontrada
	}

	public void borrar(Cita cita) throws OperationNotSupportedException {
		if (cita==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		int indice;
		indice=buscarIndice(cita);
		if (indice!=tamano+1){ //es decir la cita existe y la ha encontrado el metodo buscarIndice ya que ha devuelto tamano+1
			desplazarUnaPosicionHaciaIzquierda(indice);
			System.out.println("Cita borrada correctamente.");
			tamano--;
		}else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita para esa fecha y hora.");
		}
	}
	
	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i;
		for (i=0;i<tamano;i++) { 
			if (i>indice){
				coleccionCitas[i-1]=coleccionCitas[i];
			}
		}
		coleccionCitas[i]=null;//al último objeto vacío del array le asocio null para saber que está vacío
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public int getTamano() {
		return tamano;
	}
	
	public Cita[] getCitas() {
		return coleccionCitas;
	}
	
	public Cita[] getCitas(LocalDate fecha) {
		if (fecha==null) {
			throw new NullPointerException("ERROR: No se pueden devolver las citas para un día nulo.");
		}
		Cita[] coleccionCitasFecha= new Cita[tamano];
	
		int i,j=0;
		for (i=0; i<tamano;i++) {
			if(coleccionCitas[i].getFechaHora().toLocalDate().equals(fecha)){
			//if(coleccionCitas[i].equals(fecha)){
				coleccionCitasFecha[j]= new Cita(coleccionCitas[i]);
				j++;
			}
		}
		return coleccionCitasFecha;
	}
}
