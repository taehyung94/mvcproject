package mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.service.ChatService;

public class ChattingExit extends AbstractController{
	ChatService chatService = ChatService.getInstance();
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		long member_id = Long.parseLong(session.getAttribute("id").toString());
		long chatroom_id = Long.parseLong(req.getParameter("chatroom_id"));
		ModelAndView mv = new ModelAndView();
		try {
			chatService.deleteEnroll(member_id, chatroom_id);
			mv.setViewName("redirect:/mvcproject/member/mypage");
		} catch (Exception e){
			mv.setViewName("result.jsp");
			mv.addObject("url", "/mvcproject");
			mv.addObject("msg", "잘못된 요청입니다.");
		}
		return mv;
	}
}
