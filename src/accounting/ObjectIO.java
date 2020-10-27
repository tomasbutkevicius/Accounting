package accounting;


import accounting.model.AccountingSystem;

import java.io.*;

public class ObjectIO {

    public static String WriteObjectToFile(AccountingSystem accountingSystem) {
        try {
            FileOutputStream fileOut = new FileOutputStream(new File("lastSave.txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(accountingSystem);
            objectOut.close();
            return "Accounting system was successfully \n written to a file lastSave.txt";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Something went wrong saving accounting to file";
        }
    }

    public static AccountingSystem readObjectFromFile(AccountingSystem accountingSystem, String fileName) throws IOException, ClassNotFoundException {
        try {
            FileInputStream fileIn = new FileInputStream(new File(fileName));
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            // Read objects
            accountingSystem = (AccountingSystem) objectIn.readObject();

            objectIn.close();
            fileIn.close();
            System.out.println("New system successfully loaded");

        } catch (FileNotFoundException e) {
            System.out.println("File" + fileName + " was not found. Make sure it is in Accounting folder");
        }
        return accountingSystem;
    }
}
