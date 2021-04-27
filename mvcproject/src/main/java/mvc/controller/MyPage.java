package mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.model.ChatRoomDTO;
import mvc.service.MemberService;

public class MyPage extends AbstractController{
	MemberService memberService = MemberService.getInstance();
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		// TODO Auto-generated method stub
		List<ChatRoomDTO> list = new ArrayList<ChatRoomDTO>();
		try {
			HttpSession session = req.getSession();
			list = memberService.getEnrollList(Long.parseLong(session.getAttribute("id").toString()));
			mv.addObject("list", list);
			mv.setViewName("/WEB-INF/views/mypage.jsp");
		} catch (Exception e){
			e.printStackTrace();
			mv.setViewName("/WEB-INF/views/result.jsp");
			mv.addObject("msg", "로그인이 필요합니다.");
			mv.addObject("url", "/mvcproject");
		}
		return mv;
	}
}
