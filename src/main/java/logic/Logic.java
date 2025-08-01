package logic;

import java.util.LinkedList;

import data.*;
import entities.*;

public class Logic {
private DataPersona dp;
	
	public Logic() {
		dp=new DataPersona();
	}

	public LinkedList<Persona> getAll(){
		return dp.getAll();
	}
	
	public Persona getById(Persona per) {
		return dp.getById(per);
	}
	
	public Persona getByDni(Persona per) {
		return dp.getByDni(per);
		
	}
	
	public LinkedList<Persona> getByApellidoNombre(String apellido_nombre) {
		return dp.getByApellidoNombre(apellido_nombre);
	}
	
	public void addPersona(Persona p) {
		dp.add(p);
	}
	
	
	public void updatePersona(Persona per) {
		dp.updatePersona(per);
	}
	
	public void deletePersona(Persona per) {
		dp.deletePersona(per);
	}

}
