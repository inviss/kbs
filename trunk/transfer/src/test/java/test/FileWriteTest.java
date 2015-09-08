package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileWriteTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// success.txt 내용을 읽어서 id를 임시로 저장
			List<String> s = new ArrayList<String>();
			// fail.txt 내용을 읽어서 id를 임시로 저장
			List<String> f = new ArrayList<String>();

			File success = new File("D:\\success.txt");
			File fail = new File("D:\\fail.txt");

			if(success.exists() && success.length() > 0) {
				BufferedReader in = new BufferedReader(new FileReader(success));
				String clip = "";
				while((clip = in.readLine()) != null) {
					s.add(clip);
				}
			}

			if(success.exists() && success.length() > 0) {
				BufferedReader in = new BufferedReader(new FileReader(success));
				String clip = "";
				while((clip = in.readLine()) != null) {
					f.add(clip);
				}
			}

			RandomAccessFile raf1 = new RandomAccessFile(success, "rw");
			RandomAccessFile raf2 = new RandomAccessFile(fail, "rw");

			// 임시 저장 List에 동일한 id가 있다면 skip, 없다면 add
			for(int i=0; i<150; i++) {
				if(!s.contains(String.valueOf(i))) {
					// 내용 끝으로 이동
					raf1.seek(success.length());
					raf1.writeBytes(i+"\r\n");
					Thread.sleep(10);
					s.add(String.valueOf(i));
				}
			}
			// 임시 저장 List에 동일한 id가 있다면 skip, 없다면 add
			for(int i=0; i<100; i++) {
				if(!f.contains(String.valueOf(i))) {
					// 내용 끝으로 이동
					raf2.seek(fail.length());
					raf2.writeBytes(i+"\r\n");
					Thread.sleep(10);
					f.add(String.valueOf(i));
				}
			}
			raf1.close();
			raf2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
