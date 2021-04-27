package mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.model.MemberDTO;
import mvc.service.MemberService;

public class MemberSignUpAction extends AbstractController{
	private MemberService memberService = MemberService.getInstance();
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView();
		try {
			String login_id = req.getParameter("login_id");
			String login_password = req.getParameter("login_password");
			String login_password_check = req.getParameter("login_password_check");
			if(login_password.equals(login_password_check)) {
				MemberDTO dto = new MemberDTO();
				dto.setLoginId(login_id);
				dto.setLoginPassword(login_password);
				memberService.signUp(dto);
				mv.addObject("msg", "가입 성공");
				mv.setViewName("redirect:/mvcproject");
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			mv.addObject("msg", "가입 실패!");
			mv.addObject("url", "javascript:history.back();");
			mv.setViewName("/WEB-INF/views/result.jsp");
			e.printStackTrace();
		} finally {
		}
		return mv;
	}
}
