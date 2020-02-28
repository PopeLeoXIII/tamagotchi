package sample.FileIO;

import java.io.*;
import java.util.ArrayList;

public class Register {

    public ArrayList<ArrayList<String>> readFolder(String path) {
        File myFolder = new File(path);
        File[] files = myFolder.listFiles();
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        for (File file:files) {
            list.add(readTxtFile(file.getPath()));
        }
        return list;
    }

    public ArrayList<String> readTxtFile(String path){
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            ArrayList<String> arrayList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                arrayList.add(line.trim());
            }
            reader.close();
            return arrayList;

        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void writeTxtFile(ArrayList<String> arrayList, String path){
        try{
            FileWriter writer = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for (String s: arrayList) {
                bufferedWriter.write(s+ "\n");
            }
            bufferedWriter.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
