package vast2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controlP5.*;
import processing.core.PApplet;

public class GUI {

	PApplet paApplet;
	ControlP5 cp5;
	
	Textlabel dayLabel;
	RadioButton dayRadioBtn;
	
	Textlabel hourLabel;
	List<String> hourListBox = new ArrayList<String>();
	DropdownList hourDropDownList;
	
	Textlabel sliderLabel;
	Slider slider;
	
	Button startBtn;
	
	@SuppressWarnings("deprecation")
	public GUI(PApplet paApplet){
		this.paApplet = paApplet;
		
		cp5 = new ControlP5(paApplet);
		
		dayLabel = cp5.addTextlabel("dayLabel")
        .setText("Choose a day")
        .setPosition(17,80)
        .setColor(255);
		
		dayRadioBtn = cp5.addRadioButton("dayRadioBtn")
		.setPosition(20,100)
		.setSize(20,20)
		.setColorForeground(paApplet.color(1,108,158))
		.setColorBackground(paApplet.color(50))
		.setColorActive(paApplet.color(1,108,158))
		.setColorLabel(paApplet.color(255))
		.setItemsPerRow(3)
		.setSpacingColumn(40)		
		.addItem("FRD",1)
		.addItem("STD",2)
		.addItem("SND",3)
		.activate(0);
		
		hourLabel = cp5.addTextlabel("hourLabel")
		.setText("Choose an hour")
		.setPosition(17,140)
		.setColor(255);
		
		for (int i = 0; i < 23; i++) {
			hourListBox.add(i+":00 - "+ (i+1) +":00");
		}
		
		hourDropDownList = cp5.addDropdownList("Choose an hour")
		.setPosition(20, 180)
		.setBackgroundColor(paApplet.color(0))
		.setWidth(160)
		.setItemHeight(20)
		.setBarHeight(20)
		.setColorBackground(paApplet.color(50))
		.setColorActive(paApplet.color(255, 128));
		hourDropDownList.captionLabel().style().marginTop = 6;
		hourDropDownList.valueLabel().style().marginTop = 5;
		
		for (int i = 8; i < 19;i ++) {
			hourDropDownList.addItem(i+":00 - "+ (i+1) +":00", i);
		};
		hourDropDownList.setIndex(0);		

		sliderLabel = cp5.addTextlabel("sliderLabel")
		.setText("Choose a speed")
		.setPosition(17, 20)
		.setColor(255);		
		
		slider = cp5.addSlider("",1, 30, 1, 20, 40, 160, 20);
		slider.setSliderMode(Slider.FLEXIBLE)
		.setColorBackground(paApplet.color(50));
		
		startBtn = cp5.addButton("START")
				.setValue(100)
				.setPosition(0,560)
				.setSize(200,40)
				.setColorBackground(paApplet.color(1,108,158))
				.addCallback(new CallbackListener() {
					public void controlEvent(CallbackEvent event) {
						if (event.getAction() == ControlP5.ACTION_RELEASED) {
							System.out.println(getDay() +" "+ getHour());
							Main mainFrame = (Main) paApplet;
							try {
								mainFrame.startMap(getDay(), getHour(), getSpeed());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
		startBtn.captionLabel().style().marginLeft = 85;
	}	
	
	public String getDay() {
		switch ((int) dayRadioBtn.getValue()) {
			case 1: {
				return "friday";
			}
			case 2: {
				return "saturday";
			}
			default: {
				return "sunday";
			}
		}
	}
	
	public int getSpeed() {
		return (int) slider.getValue();
	}
	
	public int getHour() {
		return (int) hourDropDownList.getValue();
	}
}
