package accounting;


import accounting.model.AccountingSystem;

import java.io.*;

public class ObjectIO {

    public static void WriteObjectToFile(AccountingSystem accountingSystem) {
        try {
            FileOutputStream fileOut = new FileOutputStream(new File("newAccountingSystem.txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(accountingSystem);
            objectOut.close();
            System.out.println("Accounting system was succesfully written to a file " + "newAccountingSystem.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static AccountingSystem readObjectFromFile(AccountingSystem accountingSystem) throws IOException, ClassNotFoundException {
        try {
            FileInputStream fileIn = new FileInputStream(new File("accountingSystem.txt"));
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            // Read objects
            accountingSystem = (AccountingSystem) objectIn.readObject();

            objectIn.close();
            fileIn.close();
            System.out.println("New system successfully loaded");

        } catch (FileNotFoundException e) {
            System.out.println("File accountingSystem.txt was not found. Make sure it is in Accounting folder");
        }
        return accountingSystem;
    }
}
