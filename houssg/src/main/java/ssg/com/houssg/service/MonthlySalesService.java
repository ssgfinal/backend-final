package ssg.com.houssg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssg.com.houssg.dao.MonthlySalesDao;
import ssg.com.houssg.dto.MonthlySalesDto;
import ssg.com.houssg.dto.MonthlySalesSummaryDto;

@Service
public class MonthlySalesService {
	
	@Autowired
    private MonthlySalesDao dao;

	
	public List<MonthlySalesSummaryDto> getMonthlySales(String id) {
	    List<MonthlySalesDto> monthlySalesList = dao.checkMonthlySales(id);
	    
	    // 날짜별로 숙소명과 매출액을 묶어서 생성
	    Map<String, Map<String, Integer>> monthlySalesMap = new HashMap<>();
	    for (MonthlySalesDto monthlySales : monthlySalesList) {
	        String yearMonth = monthlySales.getYearMonth();
	        String accomName = monthlySales.getAccomName();
	        int sales = monthlySales.getSales();

	        monthlySalesMap.putIfAbsent(yearMonth, new HashMap<>());
	        Map<String, Integer> monthlySummary = monthlySalesMap.get(yearMonth);
	        monthlySummary.put(accomName, sales);
	    }

	    List<MonthlySalesSummaryDto> MonthlySummaryList = new ArrayList<>();
	    for (String yearMonth : monthlySalesMap.keySet()) {
	        Map<String, Integer> monthlySummary = monthlySalesMap.get(yearMonth);
	        MonthlySalesSummaryDto summaryDto = new MonthlySalesSummaryDto(yearMonth, monthlySummary);
	        MonthlySummaryList.add(summaryDto);
	    }

	    return MonthlySummaryList;
	}
	
	 public List<String> havingAccom(String id) {
	        return dao.havingAccom(id);
	    }


}
