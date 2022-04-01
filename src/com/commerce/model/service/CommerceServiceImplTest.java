package com.commerce.model.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.commerce.model.RegionDto;

class CommerceServiceImplTest {
	
	//@Test(timeout=5000)  : 시간단위 : 밀리초
	//@Test(expected=RuntimeException.class) : RuntimeException이 발생해야 성공
	//@Ignore(value=”test”)
   
	//@Before 해당 테스트 클래스의 객체를 초기화하는 작업
	//@After 해당 테스트  실행 수 실행
   
	//@BeforeClass 테스트 클래스 실행 전 한번 실행
	//@AfterClass 테스트 클래스 실행 후 한번 실행

	private CommerceServiceImpl c = CommerceServiceImpl.getInstance();;
	private List<RegionDto> list;
	
//	@BeforeClass
//	private void setBeforeClass() {
//		c = CommerceServiceImpl.getInstance();
//	}
	
	
	@Test
	void testGetSidoList() {
		try {
			list = c.getSidoList();
			for (RegionDto r : list) {
				System.out.println(r.toString());
			}
		} catch (SQLException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
	}

	@Test
	void testGetGugunList() {
		try {
			list = c.getGugunList("11");
			for (RegionDto r : list) {
				System.out.println(r.toString());
			}
		} catch (SQLException e) {
			fail("없는 시도 코드");
			e.printStackTrace();
		}
	}

	@Test
	void testGetDongList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLargeList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMiddleList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCommerceList() {
		fail("Not yet implemented");
	}

}
