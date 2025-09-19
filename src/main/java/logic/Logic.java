package logic;

import data.*;
import entities.*;
import java.util.LinkedList;

public class Logic {

    private DataPerson dp;
    private DataStadium ds;
    private DataClub dc;

    public Logic() {
        dp = new DataPerson();
        ds = new DataStadium();
        dc = new DataClub();
    }

    public LinkedList<Person> getAllPeople() {

        return dp.getAll();

    }

    public Person getPersonById(Person p) {

        return dp.getById(p);

    }

    public LinkedList<Person> getPersonByFullname(String fullname) {

        return dp.getByFullname(fullname);

    }

    public void addPerson(Person p) {

        dp.add(p);

    }

    public void updatePerson(Person p) {

        dp.update(p);

    }

    public void deletePerson(Person p) {

        dp.delete(p);

    }

    public LinkedList<Stadium> getAllStadiums() {

        return ds.getAll();

    }

    public Stadium getStadiumById(Stadium s) {

        return ds.getById(s);

    }

    public LinkedList<Stadium> getStadiumByName(String name) {

        return ds.getByName(name);

    }

    public void addStadium(Stadium s) {

        ds.add(s);

    }

    public void updateStadium(Stadium s) {

        ds.update(s);

    }

    public void deleteStadium(Stadium s) {

        ds.delete(s);

    }

    public LinkedList<Club> getAllClubs() {
        return dc.getAll();
    }

    public Club getClubById(Club club) {
        return dc.getById(club);
    }

    public LinkedList<Club> getClubByName(String name) {
        return dc.getByName(name);
    }

    public void addClub(Club c) {
        dc.add(c);
    }

    public void updateClub(Club c) {
        dc.update(c);
    }

    public void deleteClub(Club c) {
        dc.delete(c);
    }

}
