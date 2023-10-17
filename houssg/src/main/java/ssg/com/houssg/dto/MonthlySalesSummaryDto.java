package ssg.com.houssg.dto;

import java.util.Map;

public class MonthlySalesSummaryDto {
	private String yearMonth;
	private Map<String, Integer> ListOfAccom;

	public MonthlySalesSummaryDto(String yearMonth, Map<String, Integer> ListOfAccom) {
		this.yearMonth = yearMonth;
		this.ListOfAccom = ListOfAccom;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Map<String, Integer> getListOfAccom() {
		return ListOfAccom;
	}

	public void setListOfAccom(Map<String, Integer> ListOfAccom) {
		this.ListOfAccom = ListOfAccom;
	}

}
