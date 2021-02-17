package com.test.codestudy.board;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/board/download.do")
public class Download extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//다운로드 횟수 증가 (+1 씩)
		BoardDAO dao = new BoardDAO();
		dao.updateDownloadcount(request.getParameter("seq"));
	
		
		//파일 다운로드 
		String fileName = request.getParameter("filename");
		String orgfileName = request.getParameter("orgfilename");

		String savePath = "files";	//WebContent > files 라는 뜻으로 적어놓은 것
		ServletContext context = getServletContext();
		String sDownloadPath = context.getRealPath(savePath);


		String sFilePath = sDownloadPath + "/" + fileName;
		byte b[] = new byte[4096];
		FileInputStream in = new FileInputStream(sFilePath);
		String sMimeType = getServletContext().getMimeType(sFilePath);
		System.out.println("sMimeType>>>" + sMimeType);

		if (sMimeType == null)
			sMimeType = "application/octet-stream";//브라우저 반환 MIME
			//text/html
			//image/gif
			//application/zip

		response.setContentType(sMimeType);
		String agent = request.getHeader("User-Agent");
		boolean ieBrowser = (agent.indexOf("MSIE") > -1) || (agent.indexOf("Trident") > -1);

		if (ieBrowser) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("/+", "%20");
		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}

		//새로 추가한 부분
		//파일 다운로드 받을때 한글이 깨져서 나옴
		orgfileName = new String(orgfileName.getBytes("UTF-8"), "ISO-8859-1");
		
		response.setHeader("Content-Disposition", "attachment; filename= " + orgfileName);

		ServletOutputStream out2 = response.getOutputStream();
		int numRead;

		while ((numRead = in.read(b, 0, b.length)) != -1) {
			out2.write(b, 0, numRead);
		}
		out2.flush();
		out2.close();
		in.close();
		

	}

}
