package com.xwtech.mss.pub.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public abstract class DateUtils  {

    /** *//**
     * 取得日期所在周的第一天
     * @param date
     * @return
     */
    public static Date getFirstWeekDay(Date date)  {
    initCalendar(date);
    gc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return gc.getTime();
    }
    
    /** *//**
     * 取得日期所在周的最后一天
     * @param date
     * @return
     */
    public static Date getLastWeekDay(Date date)  {
    initCalendar(date);
    gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    return gc.getTime();
    }
 
    /** *//**
     * 取得日期所在月的最后一天
     * @param date
     * @return
     */
    public static Date getFirstMonthDay(Date date)  {
    initCalendar(date);
    int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
    gc.add(Calendar.DAY_OF_MONTH, 1 - dayOfMonth);
    return gc.getTime();
    }
    
    /** *//**
     * 取得日期所在月的最后一天
     * @param date
     * @return
     */
    public static Date getLastMonthDay(Date date)  {
    initCalendar(date);
    int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
    int maxDaysOfMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
    gc.add(Calendar.DAY_OF_MONTH, maxDaysOfMonth - dayOfMonth);
    return gc.getTime();
    }
    
    /** *//**
     * 取得日期所在旬的最后一天
     * @param date
     * @return
     */
    public static Date getFirstTenDaysDay(Date date)  {
    initCalendar(date);
    int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
    if(dayOfMonth <= 10)  {
        gc.set(Calendar.DAY_OF_MONTH, 1);
    } else if(dayOfMonth > 20)  {
        gc.set(Calendar.DAY_OF_MONTH, 21);
    } else  {
        gc.set(Calendar.DAY_OF_MONTH, 11);
    }
    return gc.getTime();
    }
    
    /** *//**
     * 取得日期所在旬的最后一天
     * @param date
     * @return
     */
    public static Date getLastTenDaysDay(Date date)  {
    initCalendar(date);
    int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
    if(dayOfMonth <= 10)  {
        gc.set(Calendar.DAY_OF_MONTH, 10);
    } else if(dayOfMonth > 20)  {
        gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
    } else  {
        gc.set(Calendar.DAY_OF_MONTH, 19);
    }
    return gc.getTime();
    }
    
    private static void initCalendar(Date date)  {
    if(date == null)  {
        throw new IllegalArgumentException("argument date must be not null");
    }
    
    gc.clear();
    gc.setTime(date);
    }
    
    private static GregorianCalendar gc = null;
    static  {
    gc = new GregorianCalendar(Locale.CHINA);
    gc.setLenient(true);
    gc.setFirstDayOfWeek(Calendar.MONDAY);
    }
    
	/**
	 * 返回日期所在月的最后一天
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws Exception
	 */
	public static int getLastDayIfMonth(String year, String month, String day)throws Exception
	{
		String strDate =year+month+day;
		Date date = new SimpleDateFormat("yyyyMMdd").parse(strDate);
		return DateUtils.getLastMonthDay(date).getDate();
	}
    
    /** *//**
     * @param args
     */
    public static void main(String[] args)  {
    Calendar c = Calendar.getInstance();
//    c.set(Calendar.MONTH, 7);
//    c.set(Calendar.DAY_OF_MONTH, 9);
    Date date = c.getTime();
    
    System.out.println("getFirstWeekDay = " + getFirstWeekDay(date));
    System.out.println("getLastWeekDay = " + getLastWeekDay(date));
    System.out.println("getFirstMonthDay = " + getFirstMonthDay(date));
    System.out.println("getLastMonthDay = " + getLastMonthDay(date));
    System.out.println("getFirstTenDaysDay = " + getFirstTenDaysDay(date));
    System.out.println("getLastTenDaysDay = " + getLastTenDaysDay(date));
    }
}