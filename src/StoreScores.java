import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;

public class StoreScores {

	FileWriter writeUserScores;
	static BufferedWriter writer = null;
	BufferedReader reader; 
	File scores;
	Wave wave = new Wave();

	ArrayList<String> nameList = new ArrayList<String>(10);
	ArrayList<Integer> scoreList = new ArrayList<Integer>(10);
	ArrayList<Integer> waveList = new ArrayList<Integer>(10);
	ArrayList<Integer> floorList = new ArrayList<Integer>(10);

	public void main(){
		if (nameList.isEmpty()) {
			System.out.println("is empty");
			for (int i = 0; i < 10; i++) {
				nameList.add(Darklight2.Name());
				scoreList.add(Darklight2.yourScore);
				waveList.add(Darklight2.waveNum);
				floorList.add(Darklight2.floorNum);
			}
		}
		int i = 0;
			try {


				writer = new BufferedWriter(new FileWriter("xlist.txt"));
				for(i = 0; i < 10 ; i++) {
					//nameList[i] = Darklight2.Name();
					writer.write(nameList.get(i) + "               ");
					writer.write(floorList.get(i).toString() + "                    ");
					writer.write(waveList.get(i).toString() + "                    ");
					writer.write(scoreList.get(i).toString());
					System.out.println(i);
					writer.newLine();
				}
				writer.close();

			}catch(IOException e) {
				System.out.println("Exception");
			}
	}
	
	public ArrayList<String> String(String[] NameList) {
		return nameList;
	}
	
	public void Read() {
		try {
			reader = new BufferedReader(new FileReader("xlist.txt"));
			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
								
				nameList.add(i, line);
				i++;
			}
		}catch(IOException e) {
			System.out.println("Exception");
		}
	}
}