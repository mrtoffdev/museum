package Loot.util.crud;
/*  Author: LANCE (parseFile & saveFile), YNA (parseCategs)
    DOCS ==================================

    [X] Func: <FnName:ReturnT> ------------

    parseFile(String):ArrayList<BaseItem>
    parseCategs(String):ArrayList<BaseCateg>

    saveFile(ArrayList<BaseItem>):void

    [X] Notes: ----------------------------

*/
import java.io.*;
import java.util.*;

import Loot.model.BaseCateg;
import Loot.model.BaseItem;

public class Parser {
    public static ArrayList<BaseItem>   parseFile(String FileURL) throws FileNotFoundException{
        File f = new File(FileURL);
        Scanner scanner = new Scanner(f);
        
        ArrayList<BaseItem> InventoryList = new ArrayList<BaseItem>();
        String input = "";

        // File Format


        while(scanner.hasNext()){
            input = scanner.nextLine();
            if (input.equals("++")) { break; }
            String[] inputArr = input.split(":");
            BaseItem baseItem = new BaseItem(inputArr[0],                           // ItemName
                                            Double.parseDouble(inputArr[1]),        // ItemPrice
                                            Integer.parseInt(inputArr[2]),          // ItemStock
                                            inputArr[3],                            // ItemID
                                            inputArr[4],                            // ItemCategID
                                            inputArr[5]);                           // ItemSplashURL
            InventoryList.add(baseItem);
        }
        return InventoryList;
    }
    public static ArrayList<BaseCateg>  parseCategs(String FileURL) throws FileNotFoundException{
        File f = new File(FileURL);
        Scanner scanner = new Scanner(f);
        
        ArrayList<BaseCateg> CategList = new ArrayList<BaseCateg>();
        String input = "";
        
        while(!(scanner.nextLine().equals("++"))){}

        while(scanner.hasNext()){
            input = scanner.nextLine();
            String[] inputArr = input.split(":");
            BaseCateg baseCateg = new BaseCateg(inputArr[0], inputArr[1]);
            CategList.add(baseCateg);
        }
        
        return CategList;
    }
    
    public static void saveFile(ArrayList<BaseItem> InventoryList, ArrayList<BaseCateg> CategList, String FileURL) throws IOException{
        File f = new File(FileURL);

        if(!f.exists() || !f.isFile()){
            f.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(f);

            for (BaseItem baseItem : InventoryList) {
                fileWriter.write(baseItem.getName() + ":");
                fileWriter.write(baseItem.getPrice() + ":");
                fileWriter.write(baseItem.getStock() + ":");
                fileWriter.write(baseItem.getID() + ":");
                fileWriter.write(baseItem.getCategID() + ":");
                fileWriter.write(baseItem.getIcon() + "\n");
            }
            fileWriter.write("++\n");
            for (BaseCateg baseCateg : CategList) {
                fileWriter.write(baseCateg.getCategName() + ":");
                fileWriter.write(baseCateg.getCategID() + "\n");
            }
            fileWriter.close();
    }
}
