package mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.service.MemberService;

public class MemberIdCheck extends AbstractController{
	private MemberService memberService = MemberService.getInstance();
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView("/WEB-INF/views/ajax.jsp");
		JSONObject check = new JSONObject();
		String status = "";
		try {
			if(memberService.idCheck(req.getParameter("loginId"))) {
				System.out.println(req.getParameter("loginId"));
				status="success";
			} else {
				status="redundant";
			}
		} catch(Exception e) {
			status="fail";
			e.printStackTrace();
		}
		check.put("status", status);
		mv.addObject("check", check);
		return mv;
	}

}
