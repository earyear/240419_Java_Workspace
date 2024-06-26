package ex_240509.mini.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import ex_240509.mini.dto.MemberDTO;



public class ServiceDB {

	// 비지니스 로직 처리. 
	
	// 추가
	// 회원 가입 버튼을 누를 때, 
	// 입력 정보들이 어디 담겨 있나요? MemberDTO memberDTO
	public boolean insertMember(MemberDTO memberDTO) {
		boolean ok = false;

		Connection con = null; // 연결
		PreparedStatement pstmt = null; // 명령
		
		// MemberDTO memberDTO, 객체, 인스턴스, 게터, 세터. 
		// 게터로 각 요소를 하나씩 꺼내기. 
		int id = memberDTO.getId();
		String name = memberDTO.getName();
		String email = memberDTO.getEmail();
		String password = memberDTO.getPassword();
		

		try {

			con = ConnectionDB.getConn();
			
			String sql = "insert into member501(" + "id,name,email,password" + ") "
					+ "values(member501_seq.NEXTVAL,?,?,?)";

			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, password);

			int r = pstmt.executeUpdate(); // 실행 -> 저장

			if (r > 0) {
				System.out.println("insert 성공");
				ok = true;
			} else {
				System.out.println("insert 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				// 사용한 자원을 finally 문을 이용해서 반납한다.
				// 자원 반납은 사용했던 객체의 역순으로 하며 모두 공통적으로
				// close() 메소드를 사용한다.
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return ok;
	}

	// 조회
	public String selectMember() {
		Connection con = null; // 연결
		PreparedStatement pstmt = null; // 명령
		ResultSet rs = null; // 결과
		
		// 메모리 낭비 안하고, 임시로 담아둘 문자열 도구. 
		StringBuffer stringBuffer = new StringBuffer();

		try {

			con = ConnectionDB.getConn();
			String sql = "select * from member501 order by id desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				
				stringBuffer.append("아이디 : " + id + ", 이름 : " + name + ", 이메일 : " + email + ", 비밀번호: " + password +"\n");

			
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
				// 사용한 자원을 finally 문을 이용해서 반납한다.
				// 자원 반납은 사용했던 객체의 역순으로 하며 모두 공통적으로
				// close() 메소드를 사용한다.
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return stringBuffer.toString();

	}

	// 삭제
	// 조회
		public void deleteMember(int user_id) {

			Connection con = null; // 연결
			PreparedStatement pstmt = null; // 명령

			try {

				con = ConnectionDB.getConn();
				
				String sql = "DELETE  FROM member501 WHERE id = ?";

				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, user_id);
				

				int r = pstmt.executeUpdate(); // 실행 -> 저장

				if (r > 0) {
					System.out.println("삭제 성공");
					JOptionPane.showMessageDialog(null,
							user_id+"번 삭제 성공", "Message",
							JOptionPane.ERROR_MESSAGE);
					
				} else {
					System.out.println("삭제 실패");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
					// 사용한 자원을 finally 문을 이용해서 반납한다.
					// 자원 반납은 사용했던 객체의 역순으로 하며 모두 공통적으로
					// close() 메소드를 사용한다.
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	

	// 수정
		public void updateMember(int user_id, String userName, String userEmail, String password) {

			Connection con = null; // 연결
			PreparedStatement pstmt = null; // 명령

			try {

				con = ConnectionDB.getConn();
				//UPDATE member501 SET name = 'ddd2' , email = 'ddd2' , password = 'ddd2' WHERE id = 5;
				String sql = "UPDATE member501 SET name = ? , email = ? , password = ? WHERE id = ?";				

				pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, userName);
				pstmt.setString(2, userEmail);
				pstmt.setString(3, password);
				pstmt.setInt(4, user_id);
				
//				System.out.println("user_id : " + user_id);
//				System.out.println("user_name : " + userName);
//				System.out.println("user_email : " + userEmail);
//				System.out.println("user_password : " + password);
				
				int r = pstmt.executeUpdate(); // 실행 -> 저장

				if (r > 0) {
					System.out.println("수정 성공");
					JOptionPane.showMessageDialog(null,
							user_id+"번 수정 성공", "Message",
							JOptionPane.ERROR_MESSAGE);
					
				} else {
					System.out.println("수정 실패");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
					// 사용한 자원을 finally 문을 이용해서 반납한다.
					// 자원 반납은 사용했던 객체의 역순으로 하며 모두 공통적으로
					// close() 메소드를 사용한다.
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
}






