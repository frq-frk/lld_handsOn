package org.practice.lld.employee_composition;

import java.util.List;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {

    }
}

/**
 * Create an organization with Employee(s),
 * and Employee is a IndividualContributor or manager
 * A manager has other list of Employee
 * Employee has details, and total Count of direct reportees
 * Manager can remove his direct reportees
 *
 * I - Employee
 * C - Manager, Individual, Organization -> this provides an entry point api for client to initate operations
 *
 * */

interface Employee{
    String getDetails();
    int getDirectReportCount();
    int getId();
}

class Manager implements Employee{

    int id;
    String name;
    List<Employee> reports;

    public Manager(int id, String name, List<Employee> reports) {
        this.id = id;
        this.name = name;
        this.reports = reports;
    }

//    this get details method in practical can store much more in depth details including reporting manager, a parent link from child
    @Override
    public String getDetails() {
        return "Name : "+name+" Id : "+id;
    }

    @Override
    public int getDirectReportCount() {
        return reports.size();
    }

    @Override
    public int getId() {
        return this.id;
    }

    public boolean isReport(Employee emp){
        return(reports.stream().filter(r -> r.getId() == emp.getId()).count() == 1);
    }

    public void removeReportById(int id){
        reports = reports.stream().filter(r -> r.getId() != id).toList();
//        assuming GC takes of no referenced objects created.
    }
}

class Individual implements Employee{

    int id;
    String name;

    public Individual(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getDetails() {
        return "Name : "+name+" Id : "+id;
    }

    @Override
    public int getDirectReportCount() {
        return 0;
    }

    @Override
    public int getId() {
        return id;
    }
}

class Organization{

    Employee ceo;

    Organization(Employee ceo){
        this.ceo = ceo;
    }

    Employee getManagerOfEmployee(Employee employee){
        Employee mgr = Util.getManagerOfEmployee(ceo, employee);

        if(mgr == null) throw new NoSuchElementException("The employee with details: "+employee.getDetails()+" is no longer part of this organization");

        return mgr;
    }

    void fireEmployeeById(Employee employee){
        Employee mgr = Util.getManagerOfEmployee(ceo, employee);
        if(mgr == null) throw new NoSuchElementException("The employee with details: "+employee.getDetails()+" is no longer part of this organization");
        ((Manager)mgr).removeReportById(employee.getId());
    }

    void addEmployee(Employee mgr, Employee employee){

    }
}

class Util{
    public static Employee getManagerOfEmployee(Employee manager, Employee employee){
        Employee temp = manager;

        if(temp instanceof Individual) return null;

        if(((Manager)temp).isReport(employee)) return temp;
        for(Employee r : ((Manager) temp).reports){
            if(r instanceof Manager){
                Employee mgr = getManagerOfEmployee(r, employee);
                if( mgr != null) return mgr;
            }
        }
        return null;
    }
}
