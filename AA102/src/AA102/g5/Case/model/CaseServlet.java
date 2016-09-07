package AA102.g5.Case.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import AA102g5_2.member.model.MemberVO;

@SuppressWarnings("serial")
@WebServlet("/CaseServlet")
public class CaseServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CaseDAO_interface caseDAO = new CaseDAO();
		List<CaseVO> caseList = caseDAO.getSome();
		writeText(response, new Gson().toJson(caseList));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MMM-dd").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		CaseDAO_interface caseDAO = new CaseDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getSome")) {
			List<CaseVO> caseList = caseDAO.getSome();
			writeText(response, gson.toJson(caseList));      
		}else if (action.equals("getWhere")) {
			String condition = jsonObject.get("condition").getAsString();
			System.out.println(condition);
			List<CaseVO> caseList = caseDAO.getWhere(condition);
			writeText(response, gson.toJson(caseList));  
		}
		
		//else if(action.equals("getOneInfo")){
//			CaseVO caseList = caseDao.findByPrimaryKey(jsonObject.get("case_no").getAsInt());
//			writeText(response, gson.toJson(caseList));
//		}
		else if (action.equals("caseInsert") || action.equals("caseUpdate")) {
			String addCaseJson = jsonObject.get("addcase").getAsString();
			CaseVO caseVO = gson.fromJson(addCaseJson, CaseVO.class);
//			String imageBase64 = jsonObject.get("imageBase64").getAsString();
//			byte[] image = Base64.decodeBase64(imageBase64);
			int count = 0;
			if (action.equals("caseInsert")) {
				count = caseDAO.insert(caseVO);
			} else if (action.equals("spotUpdate")) {
//				count = memberDAO.update(memberVO, image);
			}
			writeText(response, String.valueOf(count));
		}
	}

	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("outText: " + outText);
		out.print(outText);
	}
}