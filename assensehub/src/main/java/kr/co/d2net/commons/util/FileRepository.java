package kr.co.d2net.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang.SystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * tmp로 업로드된 파일을 실제로 사용할 파일명으로 변환해서 복사하는 작업이 이루어진다. 반환되는 String[] 에는 [0]: 파일경로,
 * [1]: 파일명
 * 
 * @author dekim
 * 
 */
public class FileRepository {

	public String[] saveFile(MultipartFile sourcefile, String path)
			throws IOException {
		if ((sourcefile == null) || (sourcefile.isEmpty()))
			return null;

		String[] tmp = new String[3];

		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
			if (!SystemUtils.IS_OS_WINDOWS) {
				Runtime.getRuntime().exec(
						"chown -R root.root " + f.getAbsolutePath());
			}
		}

		String orgNm = sourcefile.getOriginalFilename();
		String ext = orgNm.substring(orgNm.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();

		if (!path.endsWith(File.separator))
			path = path + File.separator;

		String targetFilePath = path + uuid + ext;

		sourcefile.transferTo(new File(targetFilePath));

		if (!SystemUtils.IS_OS_WINDOWS) {
			Runtime.getRuntime().exec("chown -R root.root " + targetFilePath);
		}

		tmp[0] = path;
		tmp[1] = uuid + ext;
		tmp[2] = orgNm;

		return tmp;
	}
	
}
