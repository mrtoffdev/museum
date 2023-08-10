package Loot.util.crud;
/*  Author: MORS
    DOCS ==================================

    [X] Func: <FnName:ReturnT> ------------

    parseReceipt(ArrayList<BaseTransaction>):void

    [X] Notes: ----------------------------

*/

import Loot.model.BaseTransaction;

import java.io.*;
import java.util.*;

public class ReceiptParser{
    public static void parseReceipt(ArrayList<BaseTransaction> TransactionList) throws IOException{
        String FILE_URL = "Receipt";

        File f = new File(FILE_URL + ".txt");
        int id = 1;

        while (f.exists()){
            f = new File((FILE_URL + id) + ".txt");
            id++;
        }
        FileWriter fw = new FileWriter(f);
        
        fw.write("--------------------------------------------------------------\n");
        fw.write(String.format("|%-16s | %-8s | %-14s | %-10s |\n", "Item Name", "Item Price", "Item Purchased", "Total Cost"));
        fw.write("--------------------------------------------------------------\n");
        for(BaseTransaction item : TransactionList){
            double orig_price = item.getTotalCost() / item.getItemCount();
            fw.write(String.format("| %-15s | %-10.2f | %-14d | %-10.2f |\n", item.getBaseItem().getName(), orig_price, item.getItemCount(), item.getTotalCost()));
        }
        fw.close();

    }

}