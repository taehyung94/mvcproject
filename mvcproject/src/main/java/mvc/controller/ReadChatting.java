package mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.model.ChatMessageDTO;
import mvc.service.ChatService;

public class ReadChatting extends AbstractController {
	private ChatService chatService = ChatService.getInstance();
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView();
		HttpSession session = req.getSession();
		long member_id = Long.parseLong(session.getAttribute("id").toString());
		long chatroom_id = Long.parseLong(req.getParameter("chatroom_id"));
		long last_id = Long.parseLong(req.getParameter("last_id"));
		System.out.println(member_id);
		System.out.println(chatroom_id);
		System.out.println(last_id);
		mv.setViewName("/WEB-INF/views/ajax.jsp");
		JSONObject check = new JSONObject();
		try {
			List<ChatMessageDTO> list = chatService.getChattingListWithPaging(member_id, chatroom_id, last_id);
			JSONArray ja = new JSONArray();
			for(ChatMessageDTO dto:list) {
				JSONObject jo = new JSONObject();
				jo.put("send_date", dto.getSend_date());
				jo.put("id", dto.getId());
				jo.put("login_id", dto.getLogin_id());
				jo.put("message", dto.getMessage());
				ja.add(jo);
			}
			check.put("list", ja);
			check.put("status", "success");
		} catch(Exception e) {
			check.put("status", "fail");
		}
		mv.addObject("check", check);
		return mv;
	}
}
