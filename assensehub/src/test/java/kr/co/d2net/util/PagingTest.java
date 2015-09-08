package kr.co.d2net.util;

import java.util.ArrayList;
import java.util.List;

import kr.co.d2net.commons.util.PaginationSupport;
import kr.co.d2net.dto.xml.archive.ArchiveRes;

public class PagingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List<ArchiveRes> items = new ArrayList<ArchiveRes>();    

			int totalCount = 0;    
			int pageSize = 10;    
			int startIndex = 1;    
			int pageIndexCount = 10;    

			PaginationSupport<ArchiveRes> ps = new PaginationSupport<ArchiveRes>(items, totalCount, startIndex, pageSize, pageIndexCount);    

			for (int i = 0; i < ps.getItems().size(); i++) {    
				System.out.println(ps.getItems().get(i));    
			}    
			System.out.println("전체 아이템 수: " + ps.getTotalCount());    
			System.out.println("시작 페이지 수: " + ps.getStartIndex());    
			System.out.println("전체 페이지 수: " + ps.getPageCount());    
			System.out.println("시작 페이지" + ps.getFirstIndex());    
			System.out.println("이전 페이지" + ps.getPreviousIndex());    

			for (int i = 0; i < ps.getIndexs().length; i++) {    
				System.out.println(ps.getIndexs()[i]);    
			}    

			System.out.println("다음 페이지 수" + ps.getNextIndex());    
			System.out.println("마지막 페이지 수" + ps.getLastIndex());   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
