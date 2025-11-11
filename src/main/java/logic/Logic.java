package logic;

import data.*;
import entities.*;
import enums.PersonRole;

import java.util.LinkedList;

public class Logic {

    private DataPerson dpe;
	private DataPlayer dpl;
    private DataCoach dc;
    private DataPresident dpr;
    private DataStadium dst;
    private DataClub dcl;
    private DataAssociation das;
    private DataTournament dto;
    private DataContract dco;

    public Logic() {
        
    	dpe = new DataPerson();
    	dpl = new DataPlayer();
        dc = new DataCoach();
        dpr = new DataPresident();
    	dst = new DataStadium();
        dcl = new DataClub();
        das = new DataAssociation();
        dto = new DataTournament();
        dco = new DataContract();
    
    }
    
    public PersonRole getRoleByPersonId(int id) {

        return dpe.getRoleByPersonId(id);

    }

    public LinkedList<Player> getAllPlayers() {

        return dpl.getAll();

    }
    
    public LinkedList<Player> getAvailablePlayers() {

        return dpl.getAvailable();

    }

    public Player getPlayerById(int id) {

        return dpl.getById(id);

    }

    public LinkedList<Player> getPlayerByFullname(String fullname) {

        return dpl.getByFullname(fullname);

    }
    
    public LinkedList<Player> getPlayersByClub(int id_club) {
    	return dpl.getByClub(id_club);
    }

    public void addPlayer(Player p) {

        dpl.add(p);

    }

    public void updatePlayer(Player p) {

        dpl.update(p);

    }

    public void deletePlayer(int id) {

        dpl.delete(id);

    }
    
    public LinkedList<Coach> getAllCoaches() {

        return dc.getAll();

    }

    public Coach getCoachById(int id) {

        return dc.getById(id);

    }

    public LinkedList<Coach> getCoachByFullname(String fullname) {

        return dc.getByFullname(fullname);

    }

    public void addCoach(Coach c) {

        dc.add(c);

    }

    public void updateCoach(Coach c) {

        dc.update(c);

    }

    public void deleteCoach(int id) {

        dc.delete(id);

    }
    
    public LinkedList<President> getAllPresidents() {

        return dpr.getAll();

    }

    public President getPresidentById(int id) {

        return dpr.getById(id);

    }

    public LinkedList<President> getPresidentByFullname(String fullname) {

        return dpr.getByFullname(fullname);

    }

    public void addPresident(President p) {

        dpr.add(p);

    }

    public void updatePresident(President p) {

        dpr.update(p);

    }

    public void deletePresident(int id) {

        dpr.delete(id);

    }

    public LinkedList<Stadium> getAllStadiums() {

        return dst.getAll();

    }

    public Stadium getStadiumById(int id) {

        return dst.getById(id);

    }

    public LinkedList<Stadium> getStadiumByName(String name) {

        return dst.getByName(name);

    }

    public void addStadium(Stadium s) {

        dst.add(s);

    }

    public void updateStadium(Stadium s) {

        dst.update(s);

    }

    public void deleteStadium(int id) {

        dst.delete(id);

    }

    public LinkedList<Club> getAllClubs() {
        
    	return dcl.getAll();
    
    }

    public Club getClubById(int id) {
        
    	return dcl.getById(id);
    
    }
    
    public Club getClubByStadiumId(int id) {
        
    	return dcl.getByStadiumId(id);
    
    }

    public LinkedList<Club> getClubByName(String name) {
        
    	return dcl.getByName(name);
    
    }

    public void addClub(Club c) {
        
    	dcl.add(c);
    
    }

    public void updateClub(Club c) {
        
    	dcl.update(c);
    
    }

    public void deleteClub(int id) {
    
    	dcl.delete(id);
    
    }
    
    public LinkedList<Association> getAllAssociations() {
        
    	return das.getAll();
    
    }

    public Association getAssociationById(int id) {
        
    	return das.getById(id);
    
    }

    public LinkedList<Association> getAssociationByName(String name) {
        
    	return das.getByName(name);
    
    }

    public void addAssociation(Association a) {
        
    	das.add(a);
    
    }

    public void updateAssociation(Association a) {
        
    	das.update(a);
    
    }

    public void deleteAssociation(int id) {
    
    	das.delete(id);
    
    }
    
    public LinkedList<Tournament> getAllTournaments() {
        
    	return dto.getAll();
    
    }

    public Tournament getTournamentById(int id) {
        
    	return dto.getById(id);
    
    }
    
    public LinkedList<Tournament> getTournamentsByAssociationId(int id) {
        
    	return dto.getByAssociationId(id);
    
    }

    public LinkedList<Tournament> getTournamentByName(String name) {
        
    	return dto.getByName(name);
    
    }

    public void addTournament(Tournament t) {
        
    	dto.add(t);
    
    }

    public void updateTournament(Tournament t) {
        
    	dto.update(t);
    
    }

    public void deleteTournament(int id) {
    
    	dto.delete(id);
    
    }
    
    public LinkedList<Contract> getAllContracts() {
        
    	return dco.getAll();
    
    }

    public Contract getContractById(int id) {
        
    	return dco.getById(id);
    
    }
    
    public LinkedList<Contract> getContractsByPersonId(int id) {
        
    	return dco.getByPersonId(id);
    
    }
    
    public LinkedList<Contract> getContractsByClubId(int id) {
        
    	return dco.getByClubId(id);
    
    }

    public void addContract(Contract c) {
        
    	dco.add(c);
    
    }
    
    public void releaseContract(int id) {
        
    	dco.release(id);
    
    }

    public void deleteContract(int id) {
    
    	dco.delete(id);
    
    }

}