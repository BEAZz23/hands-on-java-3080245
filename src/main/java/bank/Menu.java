package bank;

import java.sql.SQLException;
import java.util.Scanner;
import bank.exceptions.AmountException;

import javax.security.auth.login.LoginException;

public class Menu {

  private Scanner scanner;

  public static void main(String[] args){
    System.out.println("Benvenuti su BEABANK");

    Menu menu=new Menu();
    menu.scanner= new Scanner(System.in);
    Customer customer=menu.authenticateUser();

    if(customer != null){
      Account account = DataSource.getAccount(customer.getAccountId());

      menu.showMenu(customer,account);
      



    }


    menu.scanner.close();
  }

  private Customer authenticateUser(){
    System.out.println("Inserisci la tua Username");
    String username= scanner.next();

    System.out.println("Inserisci la tua password");
    String password = scanner.next();

    Customer customer=null;
try{
    customer= Authenticator.login(username, password);}
   catch (LoginException e) {
     System.out.println("Ci scusiamo per il disagio, c'è stato un errore:" + 
     e.getMessage());
   }
return customer;
}


private void showMenu(Customer customer, Account account){
  int selezione =0;
  while(selezione!= 4 && customer.isAuthenticated()){
System.out.println("===========");
System.out.println("Selezione una delle seguenti opzioni");
System.out.println("1: Deposito");
System.out.println("2: Prelievo");
System.out.println("3: Controllo Saldo");
System.out.println("4: Esci");
System.out.println("===========");

selezione = scanner.nextInt();

double amount=0;

switch (selezione) {
  case 1:
    System.out.println("Quanto vuoi depositare?");
    amount=scanner.nextDouble();
    try{account.deposit(amount);} catch(AmountException e)
    {System.out.println(e.getMessage());
      System.out.println("Riprova");
    } 
    break;
  case 2:
  System.out.println("Quanto vuoi ritirare?");
    amount=scanner.nextDouble();
    try {
      account.withdraw(amount);
    } catch (AmountException e) {
      System.out.println(e.getMessage());
      System.out.println("Riprova");
    }
break;
    case 3:
      System.out.println("Saldo attuale "+ account.getBalance());
    break;
    case 4:
      Authenticator.logout(customer);
      break;
      default:
        System.out.println("Opzione non valida" );
        break;

}

  }
}
}
