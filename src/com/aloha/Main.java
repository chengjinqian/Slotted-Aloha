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
	public static int base = 1;//站点基数
	public int MaxStation = 0;//站点总数
	public final int randTime = 1000;//用于产生随机时间
	public int successPost = 1000;//成功发送上限
	public final int slot = 2;//时隙大小
	public ArrayList<Data> list;//存放发送数据包时间的集合
	public Graphics g;
	//Panel panel;

	public void setNum(int num) {
		this.MaxStation = num;
	}

	public void Rand() {
		Random random = new Random();
		int total = 0;//发送包总数初始化
		int success = 0;//成功发送包数目初始化
		list = new ArrayList<Data>();
		for (int i = 0; i < MaxStation; i++) {
			Data data = new Data(random.nextInt(randTime) + 1);
			list.add(data);
		}

		Comparator<Data> comparator = new Comparator<Data>() {
			public int compare(Data d1, Data d2) {
				return d1.time - d2.time;//计算两包发送相隔时间
			}
		};

		Collections.sort(list, comparator);//对发送时间进行排序

		int count = 0;//记录经过的时间周期（时隙）
		while (true) {
			int temcount = 0;//记录每个时隙内发送的帧数
			for (int i = 0; i < MaxStation; i++) {
				if (list.get(i).time >= count * slot
						&& list.get(i).time <= (count + 1) * slot) {
					total++;//遍历所有站点，记录每个时隙发送帧的个数
					temcount++;
				} else {
					break;
				}
			}
			count++;
			if (temcount == 0) {
				continue;
			} else if (temcount == 1) {
				success += 1;//如果某个时隙只有一个帧，那么成功次数加1，同时将此帧的发送时间向后随机推延整数个时隙
				list.get(0)
						.setTime(random.nextInt(randTime) + 1 + count * slot);
				if (success > successPost) {
					break;
				}
			} else if (temcount > 1) {
				for (int j = 0; j < temcount; j++) {
					list.get(j).setTime(
							random.nextInt(randTime) + 1 + count * slot);//如果时隙内有多个包，则遍历发生冲突的站点，重新设置发送包的时间
				}
			}
			Collections.sort(list, comparator);//每个时隙内都要重新进行发送时间的排序

		}
		drawpoint((int)((1.0 * total/count)* 70 + 100), (int)(400-(10.0 * success/count)*29));//描点
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
		//绘制坐标
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
