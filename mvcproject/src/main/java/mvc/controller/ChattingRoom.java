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
import mvc.model.ChatRoomDAO;
import mvc.service.ChatService;

public class ChattingRoom extends AbstractController {
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		ChatService chatService = ChatService.getInstance();
		ModelAndView mv = new ModelAndView();
		String room_id = req.getParameter("chatroom_id");
		HttpSession session = req.getSession();
		if(session.getAttribute("id")==null) {
			mv.setViewName("/WEB-INF/views/result.jsp");
			mv.addObject("url", "/mvcproject");
			mv.addObject("msg", "로그인이 필요합니다.");
			return mv;
		} 
		if(room_id == null) {
			mv.setViewName("/WEB-INF/views/result.jsp");
			mv.addObject("url", "/mvcproject");
			mv.addObject("msg", "잘못된 요청입니다.");
			return mv;
		}
		long chatroom_id = Long.parseLong(room_id);
		long member_id = Long.parseLong(session.getAttribute("id").toString());
		try {
			if(chatService.findIt(chatroom_id) == 0) {
				mv.setViewName("WEB-INF/views/result.jsp");
				mv.addObject("url", "/mvcproject");
				mv.addObject("msg", "잘못된 요청입니다.");
				return mv;
			}
			if(chatService.checkEnrollMember(member_id, chatroom_id)) {
				List<ChatMessageDTO> list =chatService.getChattingList(member_id, chatroom_id);
				JSONArray ja = new JSONArray();
				for(ChatMessageDTO dto:list) {
					JSONObject jo = new JSONObject();
					jo.put("send_date", dto.getSend_date());
					jo.put("id", dto.getId());
					jo.put("login_id", dto.getLogin_id());
					jo.put("message", dto.getMessage());
					ja.add(jo);
				}
				//mv.addObject("list", chatService.getChattingList(member_id, chatroom_id));
				mv.addObject("list", ja);
			} else {
				chatService.enroll(member_id, chatroom_id);
			}
			mv.setViewName("/WEB-INF/views/chatroom.jsp");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
