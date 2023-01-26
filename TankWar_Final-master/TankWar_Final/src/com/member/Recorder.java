package com.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import com.member.Enemy;
import com.member.Node;

public class Recorder {
	//record how many enemy
		private static int totalEnemies = 8;
		//record my blood
		private static int playerLives = 3;
		//record how many enemy tank destroyed
		private static int playerScore = 0;
		//recover the saved info called node
		static Vector<Node> nodes = new Vector<Node>();
		//create the writer
		private static FileWriter fw = null;
		private static BufferedWriter bw = null;
		//create the reader
		private static FileReader fr = null;
		private static BufferedReader br = null;
		
		private Vector<Enemy> enemies = new Vector<Enemy>();
		
		//give the value of each enemy in the enemies vector
		public void setEnemies(Vector<Enemy> enemies) {
			this.enemies = enemies;
		}
		
		//save the the number of destroyed enemy tank, location and direction
		public void saveInfo(){
			try {
				fw = new FileWriter("d:\\gameRecording.txt");
				bw = new BufferedWriter(fw);
				
				bw.write(playerScore+"\r\n");
//				System.out.println("ok");
				//save the survived enemy tank information
				for(int i = 0; i<enemies.size(); i++){
//					System.out.println("111");
					//get the enemy tank
					Enemy enemy = enemies.get(i);
					//if the enemy is alive then save
					if(enemy.getIsLive()){
						System.out.println("2222");
						String info = enemy.getX()+" "+enemy.getY()+" "+enemy.getDirection(); 
						bw.write(info+"\r\n");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//save the player score to file
		public static void saveScore(){
			try {
				fw = new FileWriter("d:\\gameRecording.txt");
				bw = new BufferedWriter(fw);
				
				bw.write(playerScore+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//recover the saved info
		public Vector<Node> readInfo(){
			try {
				fr = new FileReader("d:\\gameRecording.txt");
				br = new BufferedReader(fr);
				String n = "";
				n=br.readLine();
				playerScore = Integer.parseInt(n);
				while((n=br.readLine())!=null){
					String [] line = n.split(" ");
					
					Node node = new Node(Integer.parseInt(line[0]),Integer.parseInt(line[1]),(line[2]));
					nodes.add(node);
					
				}
						
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					br.close();
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return nodes;
		}
		
		//get the saved score from file
		public static void readScore(){
			try {
				fr = new FileReader("d:\\gameRecording.txt");
				br = new BufferedReader(fr);
				String n = br.readLine();
				playerScore = Integer.parseInt(n);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					br.close();
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		public static int getPlayerScore() {
			return playerScore;
		}

		public static int getTotalEnemies() {
			return totalEnemies;
		}

		public static int getPlayerLives() {
			return playerLives;
		}
		
		//the number of enemy get decreased
		public static void reduceTotalEnemies(){
			totalEnemies--;
		}
		//the number of enemy get decreased
		public static void reducePlayerLives(){
			playerLives--;
		}
		//
		public static void addPlayerScore(){
			playerScore++;
		}

}
