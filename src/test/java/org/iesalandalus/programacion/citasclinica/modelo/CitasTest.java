package org.iesalandalus.programacion.citasclinica.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.naming.OperationNotSupportedException;

import org.junit.BeforeClass;
import org.junit.Test;

public class CitasTest {

	private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
	private static final String ERROR_INSERTAR_CITA_NULA = "ERROR: No se puede insertar una cita nula.";
	private static final String ERROR_BORRAR_CITA_NULA = "ERROR: No se puede borrar una cita nula.";
	private static final String ERROR_NO_MAS_CITAS = "ERROR: No se aceptan más citas.";
	private static final String ERROR_CITA_EXISTE = "ERROR: Ya existe una cita para esa fecha y hora.";
	private static final String ERROR_CITA_BORRAR_NO_EXISTE = "ERROR: No existe ninguna cita para esa fecha y hora.";
	private static final String ERROR_DIA_NULO = "ERROR: No se pueden devolver las citas para un día nulo.";
	private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
	private static final String CITA_NULA = "Debería haber saltado una excepción indicando que no se puede operar con una cita nula.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
	private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
	private static final String CITAS_NO_CREADAS = "Debería haber creado las citas correctamente.";
	private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
	private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
	private static final String CITA_NO_ESPERADA = "La cita devuelta no es la que debería ser.";
	private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";
	
	private static Paciente paciente1;
	private static Paciente paciente2;
	private static Paciente paciente3;
	private static LocalDateTime fechaHora1;
	private static LocalDateTime fechaHora2;
	private static LocalDateTime fechaHora3;
	private static Cita cita1;
	private static Cita cita2;
	private static Cita cita3;
	private static Cita citaRepetida1;
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		paciente1 = new Paciente("José Ramón Jiménez Reyes", "11223344B", "950112233");
		paciente2 = new Paciente("Andrés Rubio Del Río", "22334455Y", "666223344");
		paciente3 = new Paciente("Bob Esponja", "33445566R", "600334455");
		fechaHora1 = LocalDateTime.of(2019, 10, 15, 9, 0);
		fechaHora2 = LocalDateTime.of(2019, 10, 15, 9, 30);
		fechaHora3 = LocalDateTime.of(2019, 10, 16, 9, 0);
		cita1 = new Cita(paciente1, fechaHora1);
		cita2 = new Cita(paciente2, fechaHora2);
		cita3 = new Cita(paciente3, fechaHora3);
		citaRepetida1 = new Cita(paciente2, fechaHora1);
	}
	
	@Test
	public void constructorCapacidadValidaCreaCitasCorrectamente() {
		Citas citas = new Citas(5);
		assertThat(CITAS_NO_CREADAS, citas, not(nullValue()));
		assertThat(CITAS_NO_CREADAS, citas.getCapacidad(), is(5));
		assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(0));
	}
	
	@Test
	public void constructorCapacidadNoValidaLanzaExcepcion() {
		Citas citas = null;
		try {
			citas = new Citas(0);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, citas, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		try {
			citas = new Citas(-1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, citas, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarCitaValidaConCitasVaciasInsertaCitaCorrectamente() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, citas.getCitas()[0], is(cita1));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarDosCitasValidasInsertaCitasCorrectamente() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, citas.getCitas()[0], is(cita1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita2), not(sameInstance(cita2)));
			assertThat(OPERACION_NO_REALIZADA, citas.getCitas()[1], is(cita2));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarTresCitasValidasInsertaCitasCorrectamente() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, citas.getCitas()[0], is(cita1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita2), not(sameInstance(cita2)));
			assertThat(OPERACION_NO_REALIZADA, citas.getCitas()[1], is(cita2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita3), is(cita3));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita3), not(sameInstance(cita3)));
			assertThat(OPERACION_NO_REALIZADA, citas.getCitas()[2], is(cita3));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarCitaNulaLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(null);
			fail(CITA_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_INSERTAR_CITA_NULA));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(0));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarCitaRepetidaLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			citas.insertar(citaRepetida1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita2);
			citas.insertar(cita1);
			citas.insertar(cita3);
			citas.insertar(citaRepetida1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita2);
			citas.insertar(cita3);
			citas.insertar(cita1);
			citas.insertar(citaRepetida1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarCitaValidaConCitasLlenasLanzaExcepcion() throws OperationNotSupportedException {
		Citas citas = new Citas(2);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_NO_MAS_CITAS));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita1), not(sameInstance(cita1)));
			assertThat(OPERACION_NO_REALIZADA, citas.getCitas()[0], is(cita1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(REFERENCIA_NO_ESPERADA, citas.buscar(cita2), not(sameInstance(cita2)));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarCitaExistenteBorraCitaCorrectamente() throws OperationNotSupportedException {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.borrar(cita1);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(0));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.borrar(cita1);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.borrar(cita2);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			citas.borrar(cita1);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(nullValue()));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita3), is(cita3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			citas.borrar(cita2);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(nullValue()));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita3), is(cita3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
			citas.borrar(cita3);
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita3), is(nullValue()));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita1), is(cita1));
			assertThat(CITA_NO_ESPERADA, citas.buscar(cita2), is(cita2));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarCitaNoExistenteLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.borrar(cita2);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.borrar(cita3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarCitaNulaLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.borrar(null);
			fail(CITA_NULA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BORRAR_CITA_NULA));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getCitasDiaValidoDevuelveLasCitasDeEseDia() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		Cita[] citasDia = citas.getCitas(LocalDate.of(2019, 10, 15));
		assertThat(CITA_NO_ESPERADA, citasDia[0], is(cita1));
		assertThat(CITA_NO_ESPERADA, citasDia[1], is(cita2));
		assertThat(TAMANO_NO_ESPERADO, citasDia[2], is(nullValue()));
		citasDia = citas.getCitas(LocalDate.of(2019, 10, 16));
		assertThat(CITA_NO_ESPERADA, citasDia[0], is(cita3));
		assertThat(TAMANO_NO_ESPERADO, citasDia[1], is(nullValue()));
		assertThat(TAMANO_NO_ESPERADO, citasDia[2], is(nullValue()));
	}
	
	@Test
	public void getCitasDiaNuloLanzaExcepcion() {
		Citas citas = new Citas(5);
		try {
			citas.insertar(cita1);
			citas.insertar(cita2);
			citas.insertar(cita3);
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		Cita[] citasDia = null;
		try {
			citasDia = citas.getCitas(null);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_DIA_NULO));
			assertThat(OBJETO_DEBERIA_SER_NULO, citasDia, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}

}
