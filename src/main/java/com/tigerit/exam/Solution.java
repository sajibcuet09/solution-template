package com.tigerit.exam;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static com.tigerit.exam.IO.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    HashMap<String,Integer> tableName = new HashMap<>();
    HashMap<String,Integer> columnName = new HashMap<>();
    String[] columnNameList = new String[15];
    String[] outPutColumn,firstTable,secondTable;
    Integer[][][] row = new Integer[15][105][105];
    Integer[][] outPut = new Integer[210][210];
    Integer outPutLength=0,outPutColumnLength=0;
    @Override
    public void run() {
        /*// your application entry point

        // sample input process
        String stringInput = readLine();

        Integer integerInput = readLineAsInteger();

        // sample output process
        printLine(stringInput);
        printLine(integerInput);*/
        int test,cas=1,q,firstTablePos,secondTablePos,firstColumnPos,secondColumnPos;
        test=readLineAsInteger();
        for(cas=1;cas<=test;cas++) {
            printLine("Test: " + cas);
            initializeTable();
            q = readLineAsInteger();
            for (int query = 1; query <= q; query++) {
                outPutLength=0;
                String read;
                read = readLine();
                outPutColumn = read.split("[., ]");
                read = readLine();
                firstTable = read.split(" ");
                read = readLine();
                secondTable = read.split(" ");
                read = readLine();
                String[] condition = read.split("[ .]");
                firstTablePos = tableName.get(firstTable[1]);
                secondTablePos = tableName.get(secondTable[1]);
                if (outPutColumn[1].equalsIgnoreCase("*")) {
                    printLine(columnNameList[firstTablePos] + " " + columnNameList[secondTablePos]);
                } else {
                    String temp = "";
                    for (int i = 2; i < outPutColumn.length; i += 3) {
                        if (i != 2) {
                            temp += " ";
                        }
                        temp += outPutColumn[i];
                    }
                    printLine(temp);
                }
                if (condition[1].equalsIgnoreCase(firstTable[1]) || (firstTable.length == 3 &&
                        condition[1].equalsIgnoreCase(firstTable[2]))) {
                    firstColumnPos = columnName.get(firstTable[1] + "." + condition[2]);
                    secondColumnPos = columnName.get(secondTable[1] + "." + condition[5]);
                } else {
                    firstColumnPos = columnName.get(firstTable[1] + "." + condition[5]);
                    secondColumnPos = columnName.get(secondTable[1] + "." + condition[2]);
                }
                performJoinQuery(firstTablePos, secondTablePos, firstColumnPos, secondColumnPos);
                Integer[][] ans = new Integer[outPutLength][outPutColumnLength];
                for(int i=0;i<outPutLength;i++){
                    for(int j=0;j<outPutColumnLength;j++){
                        ans[i][j]=outPut[i][j];
                    }
                }
                ans=mysort(ans);
                for (int i = 0; i < outPutLength; i++) {
                    String temp = "";
                    for (int j = 0; j < outPutColumnLength; j++) {
                        if (j != 0) {
                            temp = temp + " ";
                        }
                        temp = temp + ans[i][j];
                    }
                    printLine(temp);
                }
                printLine("");
                readLine();
            }
        }
    }
    private void initializeTable(){
        String table;
        String[] input = new String[105];
        int nT,nC,nD;
        nT=readLineAsInteger();
        for(int i=1;i<=nT;i++){
            table=readLine();
            tableName.put(table,i);
            input = readLine().split(" ");
            nC=Integer.parseInt(input[0]);
            nD=Integer.parseInt(input[1]);
            columnNameList[i]=readLine();
            input=columnNameList[i].split(" ");
            for(int j=0;j<nC;j++){
                columnName.put(table+"."+input[j],j+1);
            }
            row[i][0][0]=nC;
            row[i][0][1]=nD;
            for(int j=1;j<=nD;j++){
                input=readLine().split(" ");
                for(int k=1;k<=nC;k++){
                    row[i][j][k]=Integer.parseInt(input[k-1]);
                }
            }
        }
    }

    private void performJoinQuery(int firstTablePos,int secondTablePos,int firstColumnPos,int secondColumnPos){
        for(int i=1;i<=row[firstTablePos][0][1];i++){
            for(int j=1;j<=row[secondTablePos][0][1];j++){
                if(row[firstTablePos][i][firstColumnPos]==row[secondTablePos][j][secondColumnPos]){
                    outPutColumnLength=0;
                    if(outPutColumn[1].equalsIgnoreCase("*")){
                        for(int k=1;k<=row[firstTablePos][0][0];k++){
                            outPut[outPutLength][outPutColumnLength++]=row[firstTablePos][i][k];
                        }
                        for(int k=1;k<=row[secondTablePos][0][0];k++){
                            outPut[outPutLength][outPutColumnLength++]=row[secondTablePos][j][k];
                        }
                        outPutLength++;
                    }else{
                        for(int k=1;k<outPutColumn.length;k+=3){
                            if(outPutColumn[k].equalsIgnoreCase(firstTable[2])){
                                outPut[outPutLength][outPutColumnLength++]=row[firstTablePos][i][columnName.get(firstTable[1]+
                                        "."+outPutColumn[k+1])];
                            }else{
                                outPut[outPutLength][outPutColumnLength++]=row[secondTablePos][j][columnName.get(secondTable[1]+
                                        "."+outPutColumn[k+1])];
                            }
                        }
                        outPutLength++;
                    }
                }
            }
        }
    }

    private static Integer[][] mysort(Integer[][] ar) {
        Arrays.sort(ar, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] int1, Integer[] int2) {
                for(int i=0;i<int1.length;i++){
                    if(int1[i]==int2[i]){
                        continue;
                    }
                    return int1[i].compareTo(int2[i]);
                }
                return int1[0].compareTo(int2[0]);
            }
        });
        return ar;
    }
}
