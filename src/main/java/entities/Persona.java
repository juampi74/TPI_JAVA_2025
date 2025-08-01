package entities;

import java.time.LocalDate;

public class Persona {
	private int id;
	private String dni;
	private String apellido_nombre;
	private LocalDate fecha_nacimiento;
	private String direccion;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getApellido_nombre() {
		return apellido_nombre;
	}
	public void setApellido_nombre(String apellido_nombre) {
		this.apellido_nombre = apellido_nombre;
	}
	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public String toString() {
		return "\nPersona [id=" + id + ", dni=" + dni + ", apellido_nombre=" + apellido_nombre + ", fecha_nacimiento=" + fecha_nacimiento
				+ ", direccion=" + direccion + "]";
	}

}