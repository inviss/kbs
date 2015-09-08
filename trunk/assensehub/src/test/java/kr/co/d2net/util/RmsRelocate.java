package kr.co.d2net.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import kr.co.d2net.dao.BaseDaoConfig;
import kr.co.d2net.dto.CodeTbl;
import kr.co.d2net.dto.ContentsInstTbl;
import kr.co.d2net.dto.ContentsTbl;
import kr.co.d2net.service.CodeManagerService;
import kr.co.d2net.service.ContentsManagerService;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RmsRelocate extends BaseDaoConfig {

	@Autowired
	private CodeManagerService codeManagerService;
	
	@Autowired
	private ContentsManagerService contentsManagerService;
	
	@Test
	public void testExcel2Read(){
		Workbook w;
		try {
			// success.txt 내용을 읽어서 id를 임시로 저장
			List<String> s = new ArrayList<String>();
			// fail.txt 내용을 읽어서 id를 임시로 저장
			List<String> f = new ArrayList<String>();

			File success = new File("D:\\success.txt");
			File fail = new File("D:\\fail.txt");
			
			String sourceTarget = File.separator+"mnt2";
				sourceTarget +=File.separator+"rms_tmp"; // 여러폴더가 있음.(rms_tmp, rms_tmp2,rms_tmp_sec,rms_tmp_thr)
			
			if(success.exists() && success.length() > 0) {
				BufferedReader in = new BufferedReader(new FileReader(success));
				String clip = "";
				while((clip = in.readLine()) != null) {
					s.add(clip);
				}
			}

			if(fail.exists() && fail.length() > 0) {
				BufferedReader in = new BufferedReader(new FileReader(success));
				String clip = "";
				while((clip = in.readLine()) != null) {
					f.add(clip);
				}
			}

			RandomAccessFile raf1 = new RandomAccessFile(success, "rw");
			RandomAccessFile raf2 = new RandomAccessFile(fail, "rw");

			//File inputWorkbook = new File("D:\\tmp\\nick.xls");
			File inputWorkbook = new File("D:\\rms.xls");

			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet[] sheets = w.getSheets();

			for(Sheet sheet : sheets) {
				if("KBS미디어".equals(sheet.getName())) {
					for (int j = 1; j < sheet.getRows(); j++) {
						// id가 success list에 존재한다면 skip
						if(j < 3) continue;

						ContentsInstTbl contentsInstTbl = new ContentsInstTbl();
						ContentsTbl contentsTbl = new ContentsTbl();
						Map<String, Object> params = new HashMap<String, Object>();
						String id ="";
						try {
							for (int i = 0; i < sheet.getColumns(); i++) {

								Cell cell = sheet.getCell(i, j);

								/*
								 * 필요할 경우 bean을 하나 생성해서 컬럼별로 setter에 주입
								 * 아마도 metadata, content, content_inst 등.... 필요에 맞게 생성해야할듯..
								 */
								switch((i+1)) {
								case 1:
									// bean.setId(id)
									id = cell.getContents();
									break;
								case 2:
									// 소스구분 (비디오, 오디오)
									// bean.setAvGubun(V)
									if(cell.getContents().equals("동영상"))
										contentsInstTbl.setAvGubun("V");
									else
										contentsInstTbl.setAvGubun("A");
									break;
								case 4:
									// 프로그램 코드
									contentsTbl.setPgmCd(cell.getContents());
									break;
								case 9:
									// 파일경로
									contentsInstTbl.setFlPath(cell.getContents());
									break;
								case 10:
									// 파일명
									contentsInstTbl.setOrgFileNm(cell.getContents());
									break;
								case 11:
									// 파일확장자
									contentsInstTbl.setFlExt(cell.getContents());
									break;
								case 12:
									// 인물정보 id
									contentsTbl.setsSeq(cell.getContents().trim());  //인물 아이디 
									break;
								case 13:
									// 인물정보 텍스트
									contentsTbl.setPersonInfo(cell.getContents().trim()); // 인물정보 
									break;
								default:
									break;
								}
							}
							
							contentsTbl.setUseYn("Y");
							contentsTbl.setRegrid("RMS");
							contentsTbl.setTrimmSte("N");
							contentsTbl.setCtCla("01");
							contentsTbl.setCtTyp("99");
							contentsTbl.setPgmNum(0);
							
							contentsInstTbl.setBitRt("-1");
							contentsInstTbl.setColorCd("");
							contentsInstTbl.setVdHresol(-1);
							contentsInstTbl.setVdVresol(-1);
							contentsInstTbl.setDrpFrmYn("N");
							
							params.put("clfCd", "FMAT");
							if(contentsInstTbl.getAvGubun().equals("A"))
								params.put("sclCd", contentsInstTbl.getFlExt()+"3");
							else
								params.put("sclCd", contentsInstTbl.getFlExt()+"1");

							CodeTbl codeTbl = new CodeTbl();
							codeTbl = codeManagerService.getCode(params);

							contentsInstTbl.setCtiFmt((codeTbl.getClfNm()==null)?"":codeTbl.getClfNm());
							
							//기존 패스 정보
							System.out.println("Old Fileinfo:"+sourceTarget+
									contentsInstTbl.getFlPath().substring(contentsInstTbl.getFlPath().indexOf("movie01")-1)
									+ contentsInstTbl.getOrgFileNm()+"."+contentsInstTbl.getFlExt());
							
							File fi = new File(sourceTarget+
									contentsInstTbl.getFlPath().substring(contentsInstTbl.getFlPath().indexOf("movie01")-1)
									+ contentsInstTbl.getOrgFileNm()+"."+contentsInstTbl.getFlExt());
							
							contentsInstTbl.setFlSz(fi.length());
							
							if (!fi.exists() || StringUtils.isBlank(contentsInstTbl.getOrgFileNm())) {
								f.add(id);
								raf2.seek(fail.length());
								raf2.writeBytes(id+"\r\n");
								continue;
							}
								
							// 동일한 ID가 있다면 pass
							if(s.contains(id)) continue;
							/*
							 * 여기에서 bean에 취합된 정보를 DB에 등록하고 파일도 이동시킴
							 */
							// bean에 주입된 데이타를 DB에 저장
							contentsManagerService.insertContents(contentsTbl, contentsInstTbl);
							// 파일 이동 부분.
							
							
							// 등록이 성공하면 success에 add, 실패하면 fail에 add
							// 엑셀 자료에 중복 자료가 있을지 모르므로 성공 ID값을 List에 add
							
							s.add(id);
							raf1.seek(success.length());
							raf1.writeBytes(id+"\r\n");
							
						} catch (Exception e) {
							e.printStackTrace();
							f.add(id);
							raf2.seek(fail.length());
							raf2.writeBytes(id+"\r\n");
						} finally {
							//if(j > 5) break;
						}
					}
				}
			}
			if(raf1 != null) raf1.close();
			if(raf2 != null) raf2.close();

			// 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
