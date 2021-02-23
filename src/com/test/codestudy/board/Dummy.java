package com.test.codestudy.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

import com.test.codestudy.DBUtil;

public class Dummy {
   
   public static void main(String[] args) {
      
      
      // 게시판 더미
      // "insert into tblBoard (seq, subject, content, regdate, readcount, mseq, filename, orgfilename) values (seqBoard.nextVal, ?, ?, default, default, ?, ?, ?)"
      
      String[] subject = { "게시판 테스트", "페이징 테스트", "JSP 수업중", "안녕하세요", "반갑습니다", "페이징 처리하는 중", "에러가 났어요", "오라클이 안돌아가요", "배고파요", "졸려요" };
      String content = "내용입니다.";
      String[] mseq = { "1", "2", "3" };
      
      Connection conn = null;
      PreparedStatement stat = null;
      Random rnd = new Random();
      
      try {
         
         conn = DBUtil.open();
         
         //BoardDAO.java -> write()
         String sql = "insert into tblBoard (seq, subject, content, regdate, readcount, mseq, filename, orgfilename, downloadcount, thread, depth) values (seqBoard.nextVal, ?, ?, default, default, ?, null, null, default, ?, 0)";
         
         stat = conn.prepareStatement(sql);
         
         for (int i=0; i<100; i++) {
            stat.setString(1,  subject[rnd.nextInt(subject.length)]);
            stat.setString(2,  content);
            stat.setString(3,  mseq[rnd.nextInt(mseq.length)]);
            stat.setInt(4, i*1000);
            
            System.out.println(i + ":" + stat.executeUpdate());
         }
         
         stat.close();
         conn.close();
         
         
      } catch (Exception e) {
    	  
         System.out.println(e);
         
      }
   }
}