package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

//tweet with pictures
public class Picturetweet extends LonelyTweetModel {

	//make a class Picture for pictures somewhere else
	public Picture pictures;
	
	public Picturetweet(String text, Date timestamp, Picture pic) {
		super(text, timestamp);
		this.pictures = pic;
		
	}

	public Picturetweet(String text) {
		super(text);
	}

	@Override
	public Date getTimestamp() {
		// TODO Auto-generated method stub
		return null;
	}

}
