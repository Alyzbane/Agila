package src.utils;

import java.time.*;
import java.time.format.*;
import java.text.*;
import java.util.*;

public class Oras {

	public static HashMap<String, Integer> dateDiff(Date choosen) 
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>(3);

		//fix the date format
 		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
 		String formattedDate = dateFormat.format(choosen);
 		Date dtnew = new Date();
		try {
			dtnew = dateFormat.parse(formattedDate);
		} catch (ParseException e) {
			return null;
		}

		if (dtnew != null) {
			//get the time difference to the localdate to current date
			LocalDate min = formatLocalDate(dtnew);
			LocalDate max = LocalDate.now();

			Period period = Period.between(min, max);

			int yrs = period.getYears();
			int days = period.getDays();
			int months = period.getMonths();

			map.put("Years", yrs);
			map.put("Days", days);
			map.put("Months", months);
		} else {
			return null;
		}

		return map;
	}

	public static LocalDate formatLocalDate(Date dt_input) {
		//will format the date into LocalDate to get the period
		//difference
		return dt_input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static java.sql.Date sqlDate(Date ldate) {
		if (ldate == null)
			return null;
		LocalDate parsedt = formatLocalDate(ldate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String sqldt = parsedt.format(formatter);

		return java.sql.Date.valueOf(parsedt);
	}
	
	public static Date formatDate(String mysqlDateString) {
	    // create a SimpleDateFormat object for parsing the MySQL date format
	    SimpleDateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd");
	    // create a SimpleDateFormat object for formatting the date in the desired format
	    SimpleDateFormat desiredFormat = new SimpleDateFormat("M-d-yyyy");
	    try {
	        // parse the MySQL date string
	        Date date = mysqlFormat.parse(mysqlDateString);
	        // format the date in the desired format
	        String formattedDate = desiredFormat.format(date);
	        // parse the formatted date string to get a Date object
	        Date localDate = desiredFormat.parse(formattedDate);
	        // return the formatted date as a Date object
	        return localDate;
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
