package mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.model.MemberDTO;
import mvc.service.MemberService;

public class LoginAction extends AbstractController{
	private MemberService memberService = MemberService.getInstance();
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		String login_id = req.getParameter("login_id");
		String login_password= req.getParameter("login_password");
		ModelAndView mv = new ModelAndView();
		Long id = null;
		try {
			MemberDTO dto = new MemberDTO();
			dto.setLoginId(login_id);
			dto.setLoginPassword(login_password);
			id = memberService.login(dto);
		} catch (Exception e){
		}
		finally {
			if(id != null) {
				mv.setViewName("redirect:/mvcproject/member/mypage");
				HttpSession session = req.getSession();
				session.setAttribute("id", id);
				session.setAttribute("login_id", login_id);
			} else {
				mv.addObject("url", "javascript:history.back();");
				mv.addObject("msg", "로그인 실패!");
				mv.setViewName("/WEB-INF/views/result.jsp");
			}
		}
		return mv;
	}
}
