package kr.co.d2net.task.diagram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import kr.co.d2net.task.Worker;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("storageCheckControlWorker")
public class StorageCheckControlWorker implements Worker {

	final Logger logger = LoggerFactory.getLogger(getClass());

	public static Map<String, String[]> params = new HashMap<String,  String[]>();

	@Override
	public void work() {

		String targetValue[] = { "cms02", "cms01" };
		String s = "";
		String s2 = "";
		try {
			if (SystemUtils.IS_OS_LINUX) {
				String[] runData = { "/bin/sh", "-c",
						"df -m | grep " + targetValue[0] };
				String[] runData2 = { "/bin/sh", "-c",
						"df -m | grep " + targetValue[1] };
				Process ps = Runtime.getRuntime().exec(runData);
				Process ps2 = Runtime.getRuntime().exec(runData2);

				ps.waitFor();
				ps2.waitFor();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(
						ps.getInputStream()));
				BufferedReader br2 = new BufferedReader(new InputStreamReader(
						ps2.getInputStream()));
				while ((s = br.readLine()) != null) {
					s2 = br2.readLine();
					String[] gu = StringUtils.splitByWholeSeparator(s, null	, 0);
					String[] gu2 = StringUtils.splitByWholeSeparator(s2, null	, 0);
					/*
					for (int k = 0; k < gu.length; k++) {
						if (k == 1) {
							String re = gu[k].replace("%", "").trim();
							int lager = Integer.parseInt(re);
						}
					}
					*/
					params.put("contents", gu);
					params.put("contents2", gu2);
				}
			}
		} catch (Exception e) {
			logger.error("Check available NAS storage", e);
		}

	}
	
	public static void init(){
		String targetValue[] = { "cms02", "cms01" };
		String s = "";
		String s2 = "";
		try {
			if (SystemUtils.IS_OS_LINUX) {
				String[] runData = { "/bin/sh", "-c", "df -m | grep " + targetValue[0] };
				String[] runData2 = { "/bin/sh", "-c", "df -m | grep " + targetValue[1] };
				
				Process ps = Runtime.getRuntime().exec(runData);
				Process ps2 = Runtime.getRuntime().exec(runData2);

				ps.waitFor();
				ps2.waitFor();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
				BufferedReader br2 = new BufferedReader(new InputStreamReader(ps2.getInputStream()));
				
				while ((s = br.readLine()) != null) {
					s2 = br2.readLine();
					
					String[] gu = StringUtils.splitByWholeSeparator(s, null	, 0);
					String[] gu2 = StringUtils.splitByWholeSeparator(s2, null	, 0);
					
					params.put("contents", gu);
					params.put("contents2", gu2);
				}
			} else {
				String[] gu = StringUtils.splitByWholeSeparator("192.168.10.85:/cms01 11189546144 4234746464 7018752032  38% /mnt", null, 0);
				String[] gu2 = StringUtils.splitByWholeSeparator("192.168.10.85:/cms02 11189546144 4234746464 7018752032  38% /mnt2", null, 0);
				
				params.put("contents", gu);
				params.put("contents2", gu2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
