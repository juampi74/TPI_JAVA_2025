package logic;

import data.*;
import entities.*;
import enums.*;
import enums.PersonRole;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private DataUser du;

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
        du = new DataUser();

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

    public Club getClubById(int id) throws SQLException {
    	
    	return dcl.getById(id);
    	
    }

    public Club getClubWithMostContracts() throws SQLException {
    	
        return dcl.getClubWithMostContracts();
    
    }
    
    public HashMap<Integer, Club> getClassicRivalsMap() throws SQLException {
    	
    	return dcl.getClassicRivalsMap();
    	
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
    
    public LinkedList<Tournament> getTournamentsByClubId(int id) throws SQLException {

        return dto.getByClubId(id);

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
    
    public LinkedList<Match> getMatchesByClubAndTournamentId(Integer clubId, Integer tournamentId) throws SQLException {
    
    	return dm.getByClubAndTournamentId(clubId, tournamentId);
    
    }
    
    public TournamentStage getHighestStageByTournamentId(Integer id) throws SQLException {
    	
    	return dm.getHighestStageByTournamentId(id);
    	
    }
    
    public Match getNextMatch() throws SQLException {
        
    	return dm.getNextMatch();
    
    }
    
public Match getNextMatchClub(int id_club) throws SQLException {
        
    	return dm.getNextMatchClub(id_club);
    
    }
    
    public void addMatch(Match match) throws SQLException {
    
    	dm.add(match);
    
    }
    
    public void setMatchResult(Match match) throws SQLException {
        
    	dm.setResult(match);
    
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
    
    public User getUserByEmail(String email) throws SQLException {
        
    	return du.getByEmail(email);
    
    }
    
    public void registerUser(User u, int idPerson, String fullname, int idNationality) throws SQLException {
        
        du.register(u, idPerson, fullname, idNationality);
        
    }
    
    public LinkedList<User> getPendingUsers() throws SQLException {
    	
    	return du.getPendingUsers();
    	
    }
    
    public void approveUser(int userId) throws SQLException {
    	
    	du.approveUser(userId);
    	
    }
    
    public void rejectUser(int userId) throws SQLException {
    	
    	du.rejectUser(userId);
    	
    }
    
    public TreeMap<String, LinkedList<TeamStats>> calculateTables(int tournamentId) throws SQLException {
        
    	LinkedList<Match> matches = dm.getGroupStageMatchesByTournamentId(tournamentId);
        
        HashMap<Integer, String> teamToGroupMap = new HashMap<>();
        
        for (Match m : matches) {
            
        	String gName = m.getGroupName();

            if (gName != null && !gName.isEmpty() && !"Interzonal".equalsIgnoreCase(gName)) {
                
            	teamToGroupMap.put(m.getHome().getId(), gName);
                teamToGroupMap.put(m.getAway().getId(), gName);
            
            }
        
        }
        
        TreeMap<String, HashMap<Integer, TeamStats>> tempTables = new TreeMap<>();
        
        for (Match m : matches) {
            
            String homeTargetGroup = teamToGroupMap.get(m.getHome().getId());
            String awayTargetGroup = teamToGroupMap.get(m.getAway().getId());
            
            if (homeTargetGroup == null) homeTargetGroup = "Tabla General";
            if (awayTargetGroup == null) awayTargetGroup = "Tabla General";

            tempTables.putIfAbsent(homeTargetGroup, new HashMap<>());
            tempTables.putIfAbsent(awayTargetGroup, new HashMap<>());

            HashMap<Integer, TeamStats> homeTable = tempTables.get(homeTargetGroup);
            HashMap<Integer, TeamStats> awayTable = tempTables.get(awayTargetGroup);

            homeTable.putIfAbsent(m.getHome().getId(), new TeamStats(m.getHome()));
            awayTable.putIfAbsent(m.getAway().getId(), new TeamStats(m.getAway()));

            if (m.getHomeGoals() != null && m.getAwayGoals() != null) {
                
                TeamStats homeStats = homeTable.get(m.getHome().getId());
                TeamStats awayStats = awayTable.get(m.getAway().getId());

                if (m.getHomeGoals() > m.getAwayGoals()) {
                    
                	homeStats.addWin(m.getHomeGoals(), m.getAwayGoals());
                    awayStats.addLoss(m.getAwayGoals(), m.getHomeGoals());
                
                } else if (m.getHomeGoals() < m.getAwayGoals()) {
                
                	homeStats.addLoss(m.getHomeGoals(), m.getAwayGoals());
                    awayStats.addWin(m.getAwayGoals(), m.getHomeGoals());
                
                } else {
                    
                	homeStats.addDraw(m.getHomeGoals(), m.getAwayGoals());
                    awayStats.addDraw(m.getAwayGoals(), m.getHomeGoals());
                
                }
            
            }
        
        }

        TreeMap<String, LinkedList<TeamStats>> finalTables = new TreeMap<>();
        
        for (Map.Entry<String, HashMap<Integer, TeamStats>> entry : tempTables.entrySet()) {
            
        	LinkedList<TeamStats> sortedList = new LinkedList<>(entry.getValue().values());
            Collections.sort(sortedList);
            finalTables.put(entry.getKey(), sortedList);
        
        }
        
        return finalTables;
    
    }
    
    public void generatePlayoffs(int tournamentId) throws SQLException {
        
    	Tournament t = dto.getById(tournamentId);
        
        TreeMap<String, LinkedList<TeamStats>> tables = calculateTables(tournamentId);
        
        LinkedList<Match> playoffMatches = new LinkedList<>();

        if (t.getFormat() == TournamentFormat.WORLD_CUP) {

            String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
            
            int matchCounter = 1;
            
            for (int i = 0; i < letters.length; i += 2) {
                
            	String group1 = "Grupo " + letters[i];
                String group2 = "Grupo " + letters[i + 1];
                
                List<TeamStats> list1 = tables.get(group1);
                List<TeamStats> list2 = tables.get(group2);
                
                if (list1 != null && list2 != null && list1.size() >= 2 && list2.size() >= 2) {

                    String code1 = "O" + matchCounter; 
                    playoffMatches.add(createPlayoffMatch(t, list1.get(0).getClub(), list2.get(1).getClub(), TournamentStage.ROUND_OF_16, code1));
                    matchCounter++;
                    
                    String code2 = "O" + matchCounter;
                    playoffMatches.add(createPlayoffMatch(t, list2.get(0).getClub(), list1.get(1).getClub(), TournamentStage.ROUND_OF_16, code2));
                    matchCounter++;
                    
                }
            
            }
        
        } else if (t.getFormat() == TournamentFormat.ZONAL_ELIMINATION) {

            List<TeamStats> zoneA = new ArrayList<>(tables.get("Zona A"));
            List<TeamStats> zoneB = new ArrayList<>(tables.get("Zona B"));
            
            int totalTeams = zoneA.size() + zoneB.size();
            
            if (totalTeams >= 24) {
                                
                playoffMatches.add(createPlayoffMatch(t, zoneA.get(0).getClub(), zoneB.get(7).getClub(), TournamentStage.ROUND_OF_16, "O1"));
                playoffMatches.add(createPlayoffMatch(t, zoneA.get(1).getClub(), zoneB.get(6).getClub(), TournamentStage.ROUND_OF_16, "O2"));
                playoffMatches.add(createPlayoffMatch(t, zoneA.get(2).getClub(), zoneB.get(5).getClub(), TournamentStage.ROUND_OF_16, "O3"));
                playoffMatches.add(createPlayoffMatch(t, zoneA.get(3).getClub(), zoneB.get(4).getClub(), TournamentStage.ROUND_OF_16, "O4"));
                
                playoffMatches.add(createPlayoffMatch(t, zoneB.get(0).getClub(), zoneA.get(7).getClub(), TournamentStage.ROUND_OF_16, "O5"));
                playoffMatches.add(createPlayoffMatch(t, zoneB.get(1).getClub(), zoneA.get(6).getClub(), TournamentStage.ROUND_OF_16, "O6"));
                playoffMatches.add(createPlayoffMatch(t, zoneB.get(2).getClub(), zoneA.get(5).getClub(), TournamentStage.ROUND_OF_16, "O7"));
                playoffMatches.add(createPlayoffMatch(t, zoneB.get(3).getClub(), zoneA.get(4).getClub(), TournamentStage.ROUND_OF_16, "O8"));
                
            } else {
                
                playoffMatches.add(createPlayoffMatch(t, zoneA.get(0).getClub(), zoneB.get(3).getClub(), TournamentStage.QUARTER_FINAL, "C1"));
                playoffMatches.add(createPlayoffMatch(t, zoneA.get(1).getClub(), zoneB.get(2).getClub(), TournamentStage.QUARTER_FINAL, "C2"));
                playoffMatches.add(createPlayoffMatch(t, zoneB.get(0).getClub(), zoneA.get(3).getClub(), TournamentStage.QUARTER_FINAL, "C3"));
                playoffMatches.add(createPlayoffMatch(t, zoneB.get(1).getClub(), zoneA.get(2).getClub(), TournamentStage.QUARTER_FINAL, "C4"));

            }
            
        }
               
        LocalDateTime lastGroupDate = getLastGroupStageMatchDate(tournamentId);

        LocalDate nextFriday = lastGroupDate.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        
        boolean isWorldCupFormat = (t.getFormat() == TournamentFormat.WORLD_CUP);

        assignWeekendSchedule(playoffMatches, nextFriday, isWorldCupFormat);
        
        playoffMatches.sort(Comparator.comparing(Match::getDate));

        for (Match m : playoffMatches) {
            
        	dm.add(m); 
        
        }
    
    }
    
    public void generateNextStage(int tournamentId) throws SQLException {
        
        LinkedList<Match> allPlayoffs = dm.getPlayoffMatchesByTournamentId(tournamentId);
        
        LinkedList<Match> playedPlayoffs = new LinkedList<>();
        
        for (Match m : allPlayoffs) {
        
        	if (m.getHomeGoals() != null) {
            
        		playedPlayoffs.add(m);
            
        	}
        
        }
        
        if (playedPlayoffs.isEmpty()) return;
       
        TournamentStage currentMaxStage = playedPlayoffs.getLast().getStage();
        
        LinkedList<Match> currentStageMatches = new LinkedList<>();

        for (Match m : playedPlayoffs) {
        
        	if (m.getStage() == currentMaxStage) {
            
        		currentStageMatches.add(m);
            
        	}
        
        }

        int requiredMatches = 0;
        TournamentStage nextStage = null;

        switch (currentMaxStage) {
            
        	case ROUND_OF_16: 
                requiredMatches = 8; 
                nextStage = TournamentStage.QUARTER_FINAL;
                break;
                
            case QUARTER_FINAL: 
                requiredMatches = 4; 
                nextStage = TournamentStage.SEMI_FINAL;
                break;
                
            case SEMI_FINAL: 
                requiredMatches = 2; 
                nextStage = TournamentStage.FINAL;
                break;
                
            default: return;
        
        }

        if (currentStageMatches.size() < requiredMatches) return;

        Map<String, Club> winners = new HashMap<>();
        
        Map<String, Club> losers = null;
        
        if (currentMaxStage == TournamentStage.SEMI_FINAL) losers = new HashMap<>();
        
        for (Match m : currentStageMatches) {
        
        	Club winner = null;

        	if (m.getHomeGoals() > m.getAwayGoals()) {

        		winner = m.getHome();
        	
        	} else if (m.getAwayGoals() > m.getHomeGoals()) {
        	
        		winner = m.getAway();
        	
        	} else {

        	    if (m.getHomePenalties() != null && m.getAwayPenalties() != null) {
        	        
        	    	if (m.getHomePenalties() > m.getAwayPenalties()) {
        	        
        	    		winner = m.getHome();
        	        
        	    	} else {
        	        
        	    		winner = m.getAway();
        	        
        	    	}
        	    
        	    } else {

        	        winner = (Math.random() < 0.5) ? m.getHome() : m.getAway(); 

        	    }
        	
        	}

        	winners.put(m.getBracketCode(), winner);
        	
        	if (losers != null) {
                
        		Club loser = (winner == m.getHome()) ? m.getAway() : m.getHome();
                losers.put(m.getBracketCode(), loser);
            
        	}
        
        }

        LinkedList<Match> nextMatches = new LinkedList<>();
        Tournament t = dto.getById(tournamentId);

        if (nextStage == TournamentStage.QUARTER_FINAL) {

            nextMatches.add(createPlayoffMatch(t, winners.get("O1"), winners.get("O2"), nextStage, "C1"));
            nextMatches.add(createPlayoffMatch(t, winners.get("O3"), winners.get("O4"), nextStage, "C2"));
            nextMatches.add(createPlayoffMatch(t, winners.get("O5"), winners.get("O6"), nextStage, "C3"));
            nextMatches.add(createPlayoffMatch(t, winners.get("O7"), winners.get("O8"), nextStage, "C4"));
            
        } else if (nextStage == TournamentStage.SEMI_FINAL) {

            nextMatches.add(createPlayoffMatch(t, winners.get("C1"), winners.get("C2"), nextStage, "S1"));
            nextMatches.add(createPlayoffMatch(t, winners.get("C3"), winners.get("C4"), nextStage, "S2"));
            
        } else if (nextStage == TournamentStage.FINAL) {

        	nextMatches.add(createPlayoffMatch(t, losers.get("S1"), losers.get("S2"), TournamentStage.THIRD_PLACE, "T1"));
            nextMatches.add(createPlayoffMatch(t, winners.get("S1"), winners.get("S2"), nextStage, "F1"));

        }

        LocalDateTime lastDate = LocalDateTime.MIN;

        for (Match m : currentStageMatches) {
        
        	if (m.getDate().isAfter(lastDate)) lastDate = m.getDate();
        
        }
        
        LocalDate nextFriday = lastDate.toLocalDate().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        
        boolean isWorldCup = (t.getFormat() == TournamentFormat.WORLD_CUP);
        
        assignWeekendSchedule(nextMatches, nextFriday, isWorldCup);
        
        for (Match m : nextMatches) {

        	dm.add(m);
        
        }
    
    }

    private Match createPlayoffMatch(Tournament t, Club home, Club away, TournamentStage stage, String code) {

    	Match m = new Match();
        
        m.setStage(stage);
        m.setBracketCode(code);
        m.setTournament(t);
        m.setHome(home);
        m.setAway(away);
        
        return m;
    
    }
   
    public void assignWeekendSchedule(List<Match> matches, LocalDate fridayDate, boolean isWorldCupFormat) {
        
        int totalMatches = matches.size();
        if (totalMatches == 0) return;

        int quotaFriday = (int) Math.round(totalMatches * 0.25);
        int quotaSaturday = (int) Math.round(totalMatches * 0.375);
        
        if (totalMatches == 2) quotaFriday = 0;
        
        if (quotaFriday == 0 && totalMatches >= 3) quotaFriday = 1;
       
        LinkedList<Integer> fridayHours;
        LinkedList<Integer> weekendHours;
        
        TournamentStage stage = matches.get(0).getStage();   
        boolean isGroupStage = stage == TournamentStage.GROUP_STAGE;

        if (isGroupStage && !isWorldCupFormat) {
            
            fridayHours = new LinkedList<>(List.of(22, 20, 18, 16)); 
            weekendHours = new LinkedList<>(List.of(22, 20, 18, 16, 14));
            
        } else {
            
            fridayHours = new LinkedList<>(List.of(16, 18, 20, 22));
            weekendHours = new LinkedList<>(List.of(14, 16, 18, 20, 22));
            
            if (isGroupStage) {

                fridayHours.add(22);
                weekendHours.add(22);
                
            } else {

            	int hoursToRemove = 2;
            	
            	if (stage == TournamentStage.SEMI_FINAL || 
            		stage == TournamentStage.THIRD_PLACE ||
                    stage == TournamentStage.FINAL) {
                    
                    hoursToRemove = 3;
                
            	}
            	
            	if (fridayHours.size() >= hoursToRemove) fridayHours.subList(0, hoursToRemove).clear();                
                if (weekendHours.size() >= hoursToRemove) weekendHours.subList(0, hoursToRemove).clear();
            
            }
        
        }

        for (int i = 0; i < totalMatches; i++) {

        	Match m = matches.get(i);
            
        	LocalDateTime finalDate;
            
            if (i < quotaFriday) {
                
            	int hour = calculateHour(i, fridayHours);
                finalDate = fridayDate.atTime(hour, 0);
            
            } else if (i < (quotaFriday + quotaSaturday)) {
            
            	int idxSat = i - quotaFriday;
                int hour = calculateHour(idxSat, weekendHours);
                finalDate = fridayDate.plusDays(1).atTime(hour, 0);
            
            } else {
            
            	int idxSun = i - (quotaFriday + quotaSaturday);
                int hour = calculateHour(idxSun, weekendHours);
                finalDate = fridayDate.plusDays(2).atTime(hour, 0);
            
            }
            
            m.setDate(finalDate);
        
        }
    
    }
    
	private int calculateHour(int matchIndex, LinkedList<Integer> allowedHours) {

		return allowedHours.get(matchIndex % allowedHours.size());
	
	}
	
	private LocalDateTime getLastGroupStageMatchDate(int tournamentId) throws SQLException {
	    
		LinkedList<Match> allMatches = dm.getByTournamentId(tournamentId);
	    
	    LocalDateTime maxDate = LocalDateTime.MIN;
	    boolean found = false;

	    for (Match m : allMatches) {

	        if (m.getStage() == TournamentStage.GROUP_STAGE && m.getDate() != null) {
	            
	        	if (m.getDate().isAfter(maxDate)) {
	                
	        		maxDate = m.getDate();
	                found = true;
	            
	        	}
	        
	        }
	    
	    }
	    
	    return found ? maxDate : LocalDateTime.now();
	
	}
	
	public void analyzeTournamentStatus(Tournament t) throws SQLException {
	    
	    t.setCanGenerateNextStage(false);
	    t.setNextStageLabel("");
	    t.setCurrentStatusLabel("Fase en disputa");

	    TournamentFormat format = t.getFormat();
	    
	    boolean isLeague = (format == TournamentFormat.ROUND_ROBIN_ONE_LEG || 
	                        format == TournamentFormat.ROUND_ROBIN_TWO_LEGS);

	    if (isLeague) {
	        
	    	LinkedList<Match> allMatches = dm.getByTournamentId(t.getId());
	        
	        if (allMatches.isEmpty()) return;

	        boolean allPlayed = true;
	        
	        for (Match m : allMatches) {
	        
	        	if (m.getHomeGoals() == null) {
	            
	        		allPlayed = false;
	                break;
	            
	        	}
	        
	        }

	        if (allPlayed) {
	            
	        	t.setCanGenerateNextStage(true);
	            t.setNextStageLabel("Finalizar Torneo");
	            t.setCurrentStatusLabel("Torneo Finalizado (Pendiente de cierre)");
	        
	        }
	        
	        return; 

	    }

	    if (!t.isPlayoffsAlreadyGenerated()) return;

	    LinkedList<Match> playoffMatches = dm.getPlayoffMatchesByTournamentId(t.getId());
	        
	    if (playoffMatches.isEmpty()) return;

	    TournamentStage currentMaxStage = playoffMatches.getLast().getStage();
	       
	    t.setCurrentStatusLabel(currentMaxStage.getDisplayName() + " en disputa");

	    int matchesTotal = 0;
	    int matchesPlayed = 0;
	    
	    boolean isFinalsPhase = (currentMaxStage == TournamentStage.FINAL || 
	                             currentMaxStage == TournamentStage.THIRD_PLACE);
	    
	    for (Match m : playoffMatches) {
	        
	    	boolean shouldCount = false;
	        
	        if (isFinalsPhase) {

	            if (m.getStage() == TournamentStage.FINAL || m.getStage() == TournamentStage.THIRD_PLACE) {

	            	shouldCount = true;
	            
	            }
	        
	        } else {
	        
	        	if (m.getStage() == currentMaxStage) {
	            
	        		shouldCount = true;
	            
	        	}
	        
	        }

	        if (shouldCount) {
	        
	        	matchesTotal++;
	            
	        	if (m.getHomeGoals() != null) {
	            
	        		matchesPlayed++;
	            
	        	}
	        
	        }
	    
	    }

	    int requiredMatches = 0;
	    String nextLabel = "";
	    boolean readyToAdvance = false;
	    
	    switch (currentMaxStage) {
	        
	        case ROUND_OF_16: 
	    
	        	requiredMatches = 8; 
	            nextLabel = "Generar Cuartos de Final";
	            readyToAdvance = (matchesTotal == requiredMatches && matchesPlayed == requiredMatches);
	            break;
	        
	        case QUARTER_FINAL: 
	            
	        	requiredMatches = 4; 
	            nextLabel = "Generar Semifinales";
	            readyToAdvance = (matchesTotal == requiredMatches && matchesPlayed == requiredMatches);
	            break;
	        
	        case SEMI_FINAL: 
	            
	        	requiredMatches = 2; 
	            nextLabel = "Generar Final y 3° Puesto";
	            readyToAdvance = (matchesTotal == requiredMatches && matchesPlayed == requiredMatches);
	            break;
	        
	        case FINAL: 
	        case THIRD_PLACE:
	            
	        	requiredMatches = 2;
	            nextLabel = "Finalizar Torneo";
	            
	            if (matchesTotal == requiredMatches && matchesPlayed == requiredMatches) {
	                
	            	readyToAdvance = true;
	                t.setCurrentStatusLabel("Definición concluida");
	            
	            }
	            break;
	            
	        default: 
	            return;
	    
	    }
	    
	    if (readyToAdvance) {
	    
	    	t.setCanGenerateNextStage(true);
	        t.setNextStageLabel(nextLabel);
	    
	    }
	
	}
	
	public void finishTournament(int id) throws SQLException {
	 
		dto.finishTournament(id);
	
	}

}