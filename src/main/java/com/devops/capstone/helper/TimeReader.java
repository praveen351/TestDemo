package com.devops.capstone.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeReader {

	public static String[] generateStartStopTime() {
		String itime = "";
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String strDate = dateFormat.format(date);
		String strTime = formatter.format(date);

		int ihr = Integer.parseInt(strTime.split(":")[0]) - 12;
		String imin = Integer.toString(Integer.parseInt(strTime.split(":")[1]) - 1);

		if (ihr == 0)
			itime = "12" + ":" + imin + " PM";
		else if (ihr > 0)
			itime = Integer.toString(ihr) + ":" + imin + " PM";
		else
			itime = strTime.split(":")[0] + ":" + imin + " AM";

		String fstrDate = strDate.split("/")[0] + "/" + Integer.toString(Integer.parseInt(strDate.split("/")[1]) + 1)
				+ "/" + strDate.split("/")[2];

		String[] starstopdate = new String[] { strDate, fstrDate, itime };
		return starstopdate;
	}
}
