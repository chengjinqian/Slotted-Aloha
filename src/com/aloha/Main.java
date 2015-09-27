package com.aloha;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame {
	public static int base = 1;
	public int MaxStation = 0;
	public final int randTime = 1000;
	public int successPost = 1000;
	public final int slot = 2;
	public ArrayList<Data> list;
	public Graphics g;
	//Panel panel;

	public void setNum(int num) {
		this.MaxStation = num;
	}

	public void Rand() {
		Random random = new Random();
		int total = 0;
		int success = 0;
		list = new ArrayList<Data>();
		for (int i = 0; i < MaxStation; i++) {
			Data data = new Data(random.nextInt(randTime) + 1);
			list.add(data);
		}

		Comparator<Data> comparator = new Comparator<Data>() {
			public int compare(Data d1, Data d2) {
				return d1.time - d2.time;
			}
		};

		Collections.sort(list, comparator);

		int count = 0;
		while (true) {
			int temcount = 0;
			for (int i = 0; i < MaxStation; i++) {
				if (list.get(i).time >= count * slot
						&& list.get(i).time <= (count + 1) * slot) {
					total++;
					temcount++;
				} else {
					break;
				}
			}
			count++;
			if (temcount == 0) {
				continue;
			} else if (temcount == 1) {
				success += 1;
				list.get(0)
						.setTime(random.nextInt(randTime) + 1 + count * slot);
				if (success > successPost) {
					break;
				}
			} else if (temcount > 1) {
				for (int j = 0; j < temcount; j++) {
					list.get(j).setTime(
							random.nextInt(randTime) + 1 + count * slot);
				}
			}
			Collections.sort(list, comparator);

		}
		drawpoint((int)((1.0 * total/count)* 70 + 100), (int)(400-(10.0 * success/count)*29));
	}

	public static void main(String args[]) {
		Main m = new Main();
		m.initUI();
		m.paint();
		
		int i = 0;
		while(true){
			i++;
			m.setNum(base*i);
			m.Rand();
			
		}
	}

	public void paint() {
		super.paint(g);
		draw(g);
	}

	public void draw(Graphics g) {
		// TODO 自动生成的方法存根
		g.setColor(Color.RED);
		g.drawLine(100, 400, 500, 400);
		g.drawLine(100, 100, 100, 400);
		g.drawString("0", 90, 405);

		for (int i = 0; i <= 5; i++) {
			g.drawString("|", 100 + 70 * i, 398);
			g.drawString(i + "", 100 + 70 * i, 413);
		}
		
		g.drawString("G(每包时尝试次数)", 260, 430);
		
		for(int i=1;i<=10;i++){
			g.drawString("-", 100, 400-29*i);
			if(i!=10){
				g.drawString("0."+i, 80, 400-29*i);
			}else{
				g.drawString(i/10+".0",80 , 400-29*i);
			}
		}
	    int Stringx = 60;
	    int Stringy = 180;
	    g.drawString("每", Stringx, Stringy);
	    g.drawString("包", Stringx, Stringy+14);
	    g.drawString("时", Stringx, Stringy + 14*2);
	    g.drawString("的", Stringx, Stringy + 14*3);
	    g.drawString("吞", Stringx, Stringy + 14*4);
	    g.drawString("吐", Stringx, Stringy + 14*5);
	    g.drawString("量", Stringx, Stringy + 14*6);
	    g.drawString("S", Stringx, Stringy + 14*7+10);
	    g.setColor(Color.BLACK);
		
	}
	
	public void drawpoint(int x,int y){
		g.drawLine(x, y, x, y);
	}
	
	public void initUI(){
		//panel=new Panel();
		//FlowLayout flowLayout=new FlowLayout();
		this.setTitle("Slotted Aloha");
		//this.setLayout(flowLayout);
		//this.add(panel);
		this.setSize(640,480);
		this.setResizable(false);//用户不可以调整对话框大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.g=this.getGraphics();
		
	}
	

}
