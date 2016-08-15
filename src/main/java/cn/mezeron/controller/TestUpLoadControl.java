package cn.mezeron.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
//@RequestMapping(value="/file")
public class TestUpLoadControl {

	@RequestMapping(value="/upload", method = RequestMethod.POST,  produces = "text/html;charset=utf-8")
	public @ResponseBody Object createbooklist(@RequestParam("file") MultipartFile booklistPicture,String booklistName,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		System.out.println(booklistPicture.getOriginalFilename());
		return null;
	}
}
