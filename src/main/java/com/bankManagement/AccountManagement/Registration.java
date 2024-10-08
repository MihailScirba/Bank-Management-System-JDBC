package com.bankManagement.AccountManagement;

import com.bankManagement.AccountManagement.DAO_Implimentations.EmployeeAccountActions;
import com.bankManagement.AccountManagement.DAO_Implimentations.EmployeeActions;
import com.bankManagement.AccountManagement.DAO_Implimentations.UserAccountActions;
import com.bankManagement.AccountManagement.DAO_Implimentations.UserActions;
import com.bankManagement.AccountManagement.DAO_Models.Employee;
import com.bankManagement.AccountManagement.DAO_Models.EmployeeAccount;
import com.bankManagement.AccountManagement.DAO_Models.User;
import com.bankManagement.AccountManagement.DAO_Models.UserAccount;
import com.bankManagement.Features.ConsoleReading;
import com.bankManagement.Features.Colorable;
import com.bankManagement.Sources.AccountType;

/**
 * @Description: this is the class where is performed account registration
 * for an employee or user. Here is one main public method what is
 * called for registering and some private methods to increase readability
 * and make code cleaner. The class does not interact directly with
 * mySql, instead it works with DAO models and implementations
 */

public abstract class Registration {
    public static void registerAccount(AccountType accountType) {
        String firstname = ConsoleReading.readString("Enter firstname: ");
        String lastname = ConsoleReading.readString("Enter lastname: ");
        switch (accountType) {
            case employee:
                registerEmployee(firstname, lastname);
                break;
            case user:
                registerUser(firstname, lastname);
                break;
        }
    }

    private static void registerEmployee(String firstname, String lastname) {
        EmployeeActions employeeActions = new EmployeeActions();
        Employee employee = employeeActions.getEmployee(firstname, lastname);
        if (employee == null || employee.isAccount()) {
            employeeErrorMessage();
        } else {
            setEmployeeData(employee, employeeActions);
            displaySuccessMessage();
        }
        return;
    }

    private static void employeeErrorMessage() {
        System.out.println(Colorable.RED_BOLD
                + "Error [ Employee doesn't exist or already "
                + "has account ]" + Colorable.RESET);
    }

    private static void setEmployeeData(Employee employee,
                                        EmployeeActions employeeActions) {
        String login = ConsoleReading.readString("Enter login: ");
        String password = ConsoleReading.readString("Enter password: ");
        EmployeeAccount employeeAccount = new EmployeeAccount(
                login, password, "regular", employee.getId());
        EmployeeAccountActions employeeAccountActions =
                new EmployeeAccountActions();
        employeeAccountActions.addEmployeeAccount(employeeAccount);
        employee.setAccount(true);
        employeeActions.updateEmployee(employee);
    }

    private static void registerUser(String firstname, String lastname) {
        UserActions userActions = new UserActions();
        User user = userActions.getUser(firstname, lastname);
        if (user == null || user.isAccount()) {
            userErrorMessage();
        } else {
            setUserData(user, userActions);
            displaySuccessMessage();
        }
    }

    private static void userErrorMessage() {
        System.out.println(Colorable.RED_BOLD
                + "Error [ User doesn't exist or already has account ]"
                + Colorable.RESET);
    }

    private static void setUserData(User user, UserActions userActions) {
        String login = ConsoleReading.readString("Enter login: ");
        String password = ConsoleReading.readString("Enter password: ");
        UserAccount userAccount = new UserAccount(login, password, user.getId());
        UserAccountActions userAccountActions = new UserAccountActions();
        userAccountActions.addUserAccount(userAccount);
        user.setAccount(true);
        userActions.updateUser(user);
    }

    private static void displaySuccessMessage() {
        System.out.println(Colorable.GREEN_BOLD
                + "Account registered successfully"
                + Colorable.RESET);
    }
}