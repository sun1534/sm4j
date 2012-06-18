/**
 * Copyright(C) 2012-2015 chun.cheng TBTOSOFT
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 */
package com.tbtosoft.cmpp;

import java.sql.Date;
import java.util.Calendar;


/**
 * @author stephen
 *
 */
public class MsgId {
	private int gateCode;
	private int month;
	private int day;
	private int hour;
	private int min;
	private int sec;
	private int sequence;
	public MsgId(){	
	}
	public MsgId(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setMonth(calendar.get(Calendar.MONTH)+1);
		setDay(calendar.get(Calendar.DAY_OF_MONTH));
		setHour(calendar.get(Calendar.HOUR_OF_DAY));
		setMin(calendar.get(Calendar.MINUTE));
		setSec(calendar.get(Calendar.SECOND));
	}	
	/**
	 * @return the gateCode
	 */
	public int getGateCode() {
		return gateCode;
	}
	/**
	 * @param gateCode the gateCode to set
	 */
	public void setGateCode(int gateCode) {
		this.gateCode = gateCode;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}
	/**
	 * @return the sec
	 */
	public int getSec() {
		return sec;
	}
	/**
	 * @param sec the sec to set
	 */
	public void setSec(int sec) {
		this.sec = sec;
	}
	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}	
}
