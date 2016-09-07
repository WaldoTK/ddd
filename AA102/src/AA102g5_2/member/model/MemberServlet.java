package AA102g5_2.member.model;

import idv.ron.server.main.ImageUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

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
		MemberDAO_interface memberDAO = new MemberDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);
		System.out.println("jsonObject: " + jsonObject);
		
		if(action.equals("Login")){
			String loginJson = jsonObject.get("account").getAsString();
			System.out.println(loginJson);
			MemberVO memberVO1 = memberDAO.login(loginJson);		
			writeText(response, gson.toJson(memberVO1));
			
		}

		if (action.equals("getAll")) {
			List<MemberVO> spots = memberDAO.getAll();
			writeText(response, gson.toJson(spots));
		} else if (action.equals("getImage")) {
			OutputStream os = response.getOutputStream();
			int id = jsonObject.get("id").getAsInt();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = memberDAO.getImage(id);
			if (image != null) {
				image = ImageUtil.shrink(image, imageSize);
				response.setContentType("image/jpeg");
				response.setContentLength(image.length);
			}
			os.write(image);
		} else if (action.equals("memberInsert") || action.equals("memberUpdate")) {
			String registerJson = jsonObject.get("register").getAsString();
			MemberVO memberVO = gson.fromJson(registerJson, MemberVO.class);
//			String imageBase64 = jsonObject.get("imageBase64").getAsString();
//			byte[] image = Base64.decodeBase64(imageBase64);
			int count = 0;
			if (action.equals("memberInsert")) {
				count = memberDAO.insert(memberVO);
			} else if (action.equals("spotUpdate")) {
//				count = memberDAO.update(memberVO, image);
			}
		
			writeText(response, String.valueOf(count));
		} //else if (action.equals("spotDelete")) {
//			String spotJson = jsonObject.get("spot").getAsString();
//			MemberVO memberVO = gson.fromJson(spotJson, MemberVO.class);
//			int count = memberDAO.delete(ParseInt(memberVO.getId()));
//			writeText(response, String.valueOf(count));
		} //else if (action.equals("findById")) {
//			int id = jsonObject.get("id").getAsInt();
//			Spot spot = spotDao.findById(id);
//			writeText(response, gson.toJson(spot));
		/*}*/ 
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO_interface memberDAO = new MemberDAO();
		List<MemberVO> spots = memberDAO.getAll();
		writeText(response, new Gson().toJson(spots));
	}

	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		// System.out.println("outText: " + outText);
		out.print(outText);
	}
}