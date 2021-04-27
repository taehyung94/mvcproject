package mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.service.ChatService;

public class MakeChattingRoom extends AbstractController{
	ChatService chatService = ChatService.getInstance();
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		System.out.println("호출횟수 확인");
		ModelAndView mv = new ModelAndView("/WEB-INF/views/ajax.jsp");
		JSONObject check = new JSONObject();
		String status="";
		HttpSession session = req.getSession();
		long member_id = Long.parseLong(session.getAttribute("id").toString());
		try {
			String name = req.getParameter("name");
			chatService.makeChattingRoom(name, member_id);
			status="success";
		} catch (Exception e){
			status="fail";
		}
		check.put("status", status);
		mv.addObject("check", check);
		return mv;
	}
}
