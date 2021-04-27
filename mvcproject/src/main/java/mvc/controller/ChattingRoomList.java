package mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.fx.AbstractController;
import mvc.fx.ModelAndView;
import mvc.model.ChatRoomDTO;
import mvc.service.ChatService;

public class ChattingRoomList extends AbstractController{
	ChatService chatService = ChatService.getInstance(); 
	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		ModelAndView mv = new ModelAndView();
		List<ChatRoomDTO> list = new ArrayList<ChatRoomDTO>();
		mv.setViewName("/WEB-INF/views/roomlist.jsp");
		try {
			list = chatService.getChattingRoomList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		mv.addObject("list", list);
		return mv;
	}
}
