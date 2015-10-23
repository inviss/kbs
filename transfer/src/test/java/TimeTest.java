import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*try {
			Timestamp t = new Timestamp(6000000);
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			System.out.println("start time: "+sf.format(t));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Timestamp t = new Timestamp(6502000);
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
			System.out.println("end time: "+sf.format(t));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			String aa = "T2011-0893_S000_20120428_PS-2012072771-01-000_02_M4H21000";
			String[] bb = aa.split("\\_");
			System.out.println(bb[2]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
