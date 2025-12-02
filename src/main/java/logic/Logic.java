package logic;

import data.*;
import entities.*;
import enums.*;
import enums.PersonRole;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

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
    private DataPosition dpo;
    private DataPlayerPosition dpp;
    private DataMatch dm;
    private DataNationality dn;

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
        dpo = new DataPosition();
        dpp = new DataPlayerPosition();
        dm = new DataMatch();
        dn = new DataNationality();

    }

    public PersonRole getRoleByPersonId(int id) throws SQLException {

        return dpe.getRoleByPersonId(id);

    }
    
    public LinkedList<Person> getPeopleByNationalityId(Integer id) throws SQLException {
        
    	return dpe.getByNationalityId(id);
    
    }

    public LinkedList<Player> getAllPlayers() throws SQLException {

        return dpl.getAll();

    }
    
    public LinkedList<Player> getAvailablePlayers() throws SQLException {

        return dpl.getAvailable();

    }

    public Player getPlayerById(int id) throws SQLException {

        return dpl.getById(id);

    }

    public LinkedList<Player> getPlayerByFullname(String fullname) throws SQLException {

        return dpl.getByFullname(fullname);

    }

    public LinkedList<Player> getPlayersByClub(int id_club) throws SQLException {
        
    	return dpl.getByClub(id_club);
    
    }
    
    public Map<Integer, String> getAllPrimaryPositions() throws SQLException {
        
    	return dpl.getAllPrimaryPositions();
    
    }
    
    public Map<Integer, Club> getPlayersCurrentClubs() throws SQLException {
        
    	return dpl.getCurrentClubs();
    
    }
    
    public void addPlayer(Player p) throws SQLException {

        dpl.add(p);

    }

    public void updatePlayer(Player p) throws SQLException {

        dpl.update(p);

    }

    public void deletePlayer(int id) throws SQLException {

        dpl.delete(id);

    }

    public LinkedList<Coach> getAllCoaches() throws SQLException {

        return dc.getAll();

    }

    public LinkedList<Coach> getAvailableCoaches() throws SQLException {

        return dc.getAvailable();

    }

    public Coach getCoachById(int id) throws SQLException {

        return dc.getById(id);

    }

    public LinkedList<Coach> getCoachByFullname(String fullname) throws SQLException {

        return dc.getByFullname(fullname);

    }
    
    public Map<Integer, Club> getCoachesCurrentClubs() throws SQLException {
        
    	return dc.getCurrentClubs();
    
    }

    public void addCoach(Coach c) throws SQLException {

        dc.add(c);

    }

    public void updateCoach(Coach c) throws SQLException {

        dc.update(c);

    }

    public void deleteCoach(int id) throws SQLException {

        dc.delete(id);

    }

    public LinkedList<President> getAllPresidents() throws SQLException {

        return dpr.getAll();

    }

    public President getPresidentById(int id) throws SQLException {

        return dpr.getById(id);

    }

    public LinkedList<President> getPresidentByFullname(String fullname) throws SQLException {

        return dpr.getByFullname(fullname);

    }

    public void addPresident(President p) throws SQLException {

        dpr.add(p);

    }

    public void updatePresident(President p) throws SQLException {

        dpr.update(p);

    }

    public void deletePresident(int id) throws SQLException {

        dpr.delete(id);

    }

    public LinkedList<Stadium> getAllStadiums() throws SQLException {

        return dst.getAll();

    }

    public Stadium getStadiumById(int id) throws SQLException {

        return dst.getById(id);

    }

    public LinkedList<Stadium> getStadiumByName(String name) throws SQLException {

        return dst.getByName(name);

    }

    public void addStadium(Stadium s) throws SQLException {

        dst.add(s);

    }

    public void updateStadium(Stadium s) throws SQLException {

        dst.update(s);

    }

    public void deleteStadium(int id) throws SQLException {

        dst.delete(id);

    }

    public LinkedList<Club> getAllClubs() throws SQLException {

        return dcl.getAll();

    }
    
    public LinkedList<Club> getAllClubsByTournamentId(Integer id) throws SQLException {

        return dcl.getAllByTournamentId(id);

    }

    public Club getClubWithMostContracts() throws SQLException {
    	
        return dcl.getClubWithMostContracts();
    
    }
    
    public HashSet<Integer> getClubsWithClassicRivals() throws SQLException {
    	
    	return dcl.getClubsWithClassicRivals();
    	
    }

    public Club getClubById(int id) throws SQLException {

        return dcl.getById(id);

    }

    public Club getClubByStadiumId(int id) throws SQLException {

        return dcl.getByStadiumId(id);

    }

    public LinkedList<Club> getClubByName(String name) throws SQLException {

        return dcl.getByName(name);

    }
    
    public LinkedList<Club> getClubsByAssociationId(int id) throws SQLException {
    	
    	return dcl.getByAssociationId(id);
    	
    }

    public void addClub(Club c) throws SQLException {

        dcl.add(c);

    }
    
    public void addClassicRival(int id1, int id2) throws SQLException {
    	
    	dcl.addClassicRival(id1, id2);
    	
    }

    public void updateClub(Club c) throws SQLException {

        dcl.update(c);

    }

    public void deleteClub(int id) throws SQLException {

        dcl.delete(id);

    }
    
    public void removeClassicRival(int id) throws SQLException {
    	
    	dcl.removeClassicRival(id);
    	
    }

    public LinkedList<Association> getAllAssociations() throws SQLException {

        return das.getAll();

    }

    public Association getAssociationById(int id) throws SQLException {

        return das.getById(id);

    }

    public LinkedList<Association> getAssociationByName(String name) throws SQLException {

        return das.getByName(name);

    }
    
    public LinkedList<Association> getAssociationsByNationalityId(Integer id) throws SQLException {
        
    	return das.getByNationalityId(id);
    
    }

    public void addAssociation(Association a) throws SQLException {
        das.add(a);

    }

    public void updateAssociation(Association a) throws SQLException {
        das.update(a);

    }
    
    public void updateAssociationNationalities(int idAssociation, String[] selectedCountriesIds) throws SQLException {
        LinkedList<Integer> countriesIds = new LinkedList<>();
        
        if (selectedCountriesIds != null) {
            for (String idStr : selectedCountriesIds) {
                try {
                	countriesIds.add(Integer.parseInt(idStr));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        Association currentAssociation = das.getById(idAssociation);
        if (currentAssociation != null && currentAssociation.getType() == AssociationType.NATIONAL) {
            for (Integer countryId : countriesIds) {
                LinkedList<Association> associationsWithCountry = das.getByNationalityId(countryId);
                if (associationsWithCountry != null) {
                    for (Association a : associationsWithCountry) {
                        if (a.getType() == AssociationType.NATIONAL && a.getId() != idAssociation) {
                            throw new SQLException("La nacionalidad ya está vinculada a la asociación nacional '" + a.getName() + "'.");
                        }
                    }
                }
            }
        }

        das.updateNationalities(idAssociation, countriesIds);
    }

    public void deleteAssociation(int id) throws SQLException {

        das.delete(id);

    }

    public LinkedList<Tournament> getAllTournaments() throws SQLException {

        return dto.getAll();

    }

    public Tournament getTournamentById(int id) throws SQLException {

        return dto.getById(id);

    }

    public LinkedList<Tournament> getTournamentsByAssociationId(int id) throws SQLException {

        return dto.getByAssociationId(id);

    }

    public LinkedList<Tournament> getTournamentByName(String name) throws SQLException {

        return dto.getByName(name);

    }

    public void addTournament(Tournament t) throws SQLException {

        dto.add(t);

    }

    public void updateTournament(Tournament t) throws SQLException {

        dto.update(t);

    }

    public void deleteTournament(int id) throws SQLException {

        dto.delete(id);

    }

    public LinkedList<Contract> getAllContracts() throws SQLException {

        return dco.getAll();

    }

    public Contract getContractById(int id) throws SQLException {

        return dco.getById(id);

    }

    public Contract getNextExpiringContract() throws SQLException {
        
    	return dco.getNextExpiringContract();
    
    }

    public LinkedList<Contract> getContractsByPersonId(int id) throws SQLException {

        return dco.getByPersonId(id);

    }

    public LinkedList<Contract> getContractsByClubId(int id) throws SQLException {

        return dco.getByClubId(id);

    }
    
    public LinkedList<Contract> getPlayerHistory(int idPlayer, LocalDate from, LocalDate to) throws SQLException {
    	
    	return dco.getHistoryByPlayer(idPlayer, from, to);
    	
    }

    public void addContract(Contract c) throws SQLException {

        dco.add(c);

    }

    public void updateContract(Contract c) throws SQLException {
     
    	dco.update(c);
    
    }

    public void releaseContract(int id) throws SQLException {
        
    	dco.release(id);

    }

    public void deleteContract(int id) throws SQLException {

        dco.delete(id);

    }
    
    public LinkedList<Position> getAllPositions() throws SQLException {

        return dpo.getAll();

    }

    public Position getPositionById(int id) throws SQLException {

        return dpo.getById(id);

    }

    public LinkedList<Position> getPositionByDescription(String description) throws SQLException {

        return dpo.getByDescription(description);

    }

    public void addPosition(Position p) throws SQLException {

        dpo.add(p);

    }

    public void updatePosition(Position p) throws SQLException {

        dpo.update(p);

    }

    public void deletePosition(int id) throws SQLException {

        dpo.delete(id);

    }
    
    public LinkedList<Integer> getPlayerPositions(int playerId) throws SQLException {
    	
    	return dpp.getPlayerPositions(playerId);
    	
    }
    
    public Integer getPlayerPrincipalPosition(int playerId) throws SQLException {
    	
    	return dpp.getPrincipalPosition(playerId);
    
    }
    
    
    public void addPlayerPosition(int playerId, int posId) throws SQLException {
    	
    	dpp.add(playerId, posId);
    	
    }
    
    public void setPlayerPrimaryPosition(int playerId, int mainPosId) throws SQLException{
    	
    	dpp.setPrimary(playerId, mainPosId);
    	
    }
    
    public void deletePlayerPositions(int id) throws SQLException {
    	
    	dpp.deleteAllFromPlayer(id);
    	
    }
    
    public int getNumberPlayersWithPosition(int idPosition) throws SQLException {
    	
    	return dpp.getNumberPlayersWithPosition(idPosition);
    }
    
    public LinkedList<Match> getAllMatches() throws SQLException {
    
    	return dm.getAll();
    
    }
    
    public Match getMatchById(Integer id) throws SQLException {
    
    	return dm.getById(id);
    
    }
    
    public LinkedList<Match> getMatchesByClubId(Integer id) throws SQLException {
    
    	return dm.getByClubId(id);
    
    }
    
    public LinkedList<Match> getMatchesByTournamentId(Integer id) throws SQLException {
    	
    	return dm.getByTournamentId(id);
    
    }
    
    public LinkedList<Match> getMatchesByClubAndTournamentId(Integer id_club, Integer id_tournament) throws SQLException {
    
    	return dm.getByClubAndTournamentId(id_club, id_tournament);
    
    }
    
    public void addMatch(Match match) throws SQLException {
    
    	dm.add(match);
    
    }
    
    public void updateMatch(Match match) throws SQLException {
    
    	dm.update(match);
    
    }
    
    public void deleteMatch(Integer id) throws SQLException {
    
    	dm.delete(id);
    
    }
    
    public LinkedList<Nationality> getAllNationalities() throws SQLException {
        
    	return dn.getAll();
    
    }
    
    public Nationality getNationalityById(Integer id) throws SQLException {
    
    	return dn.getById(id);
    
    }
    
    public Nationality getNationalityByName(String name) throws SQLException {
        
    	return dn.getByName(name);
    
    }
    
    public Nationality getNationalityByIsoCode(String isoCode) throws SQLException {
        
    	return dn.getByIsoCode(isoCode);
    
    }
    
    public LinkedList<Nationality> getAllNationalitiesByAssociationId(int id) throws SQLException {
        
    	return dn.getAllByAssociationId(id);
    
    }
        
    public void addNationality(Nationality nationality) throws SQLException {
    
    	dn.add(nationality);
    
    }
    
    public void updateNationality(Nationality nationality) throws SQLException {
    
    	dn.update(nationality);
    
    }
    
    public void deleteNationality(Integer id) throws SQLException {
    
    	dn.delete(id);
    
    }
    
}