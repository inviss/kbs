import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
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
		}
	}

}
