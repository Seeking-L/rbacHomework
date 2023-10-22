package com.example.rbac_homework.controller;

import com.example.rbac_homework.pojo.Message;
import com.example.rbac_homework.pojo.User;
import com.example.rbac_homework.service.MessageService;
import com.example.rbac_homework.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/mainPage")
public class MainPageController {
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @PostMapping("sendMessage")
    public String SendMessage(HttpSession session,
                              HttpServletResponse response,
                              @RequestParam("content") String content){
        Message message=new Message(((User)session.getAttribute("user")).getUserId(),content);
        messageService.sendMessage(message);
        return "redirect:/mainPage/Page";
    }

    @PostMapping("/deleteMessage")
    public String DeleteMessage(HttpSession session,
                                HttpServletResponse response,
                                @RequestParam("messageId") String messageId){
        messageService.deleteMessage(Integer.parseInt(messageId));
        return "redirect:/mainPage/Page";
    }

    @RequestMapping("/Page")
    public void checkPermission(HttpSession session, HttpServletResponse response) throws IOException {
        System.out.println("--------------checking Permission-------------------");
        HashSet<Integer> permissions = (HashSet<Integer>) session.getAttribute("permissions");
        PrintWriter pw = response.getWriter();
        writeHead(pw);

        System.out.println("permission: "+permissions.toString());

        if (permissions.contains(Integer.valueOf(1))) {//能读帖子
            if (!permissions.contains(Integer.valueOf(4))) //能看内容，不能看发帖者
                showMessagesWithoutPublisher(pw);
            else //能看内容和发帖者
                showMessagesWithPublisher(pw);
        }
        if (permissions.contains(Integer.valueOf(2))) {//能写帖子
            showMessageSendBox(pw);
        }
        if(permissions.contains(Integer.valueOf(3))){//能删除帖子
            showDeleteTool(pw);
        }

        pw.write("</body></html>");
        pw.flush();
    }

    private void showMessagesWithoutPublisher(PrintWriter pw) {
        List<Message> messages = messageService.getAllMessages();
        for (Message message : messages) {
            pw.write("<br>\n" +
                    "-------------------------------------------------------------<br>" +
                    "<h3>" +message.getMessageId()+"---</h3><h3>"+ message.getContent() + "</h3>" +
                    "<h5>create time:" + message.getCreateTime().toString() + "</h5><br>");
        }
    }

    private void showMessagesWithPublisher(PrintWriter pw) {
        List<Message> messages = messageService.getAllMessages();
        for (Message message : messages) {
            pw.write("<br>\n" +
                    "-------------------------------------------------------------<br>" +
                    "<h3>"+message.getMessageId()+"---</h3><h3>" + message.getContent() + "</h3>" +
                    "<h5>publisherId:" + message.getUserId() + "&nbsp;&nbsp;create time:" + message.getCreateTime().toString() + "</h5><br>");
        }
    }

    private void writeHead(PrintWriter pw) {
        pw.write("<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<meta charset=\"utf-8\">\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n");
    }

    private void showMessageSendBox(PrintWriter pw) {
        pw.write(
                """
                                       <h1>-----------------------------------------------------</h1>
                                        <h1>SendMessage</h1>
                                        <div>

                                            <div id="overlay">
                                                <div class="popup">
                                                    <form role="form" action="sendMessage" method="post" enctype="multipart/form-data">
                                                        <div class="form-group">
                                                            <label>CONTENT</label>
                                                            <br>
                                                            <textarea class="from-control" rows="5" name="content"></textarea>
                                                        </div>

                                                        <div class="popup_btn">
                                                            <button type="submit"  onclick="hidePopup()">submit</button>
                                                            <button type="button"  onclick="closePopup()">cancel</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                        """);
    }

    private void showDeleteTool(PrintWriter pw){
        pw.write("""
                <h1>--------------------------------------------------------</h1>
                <h1>DeleteMessage</h1>
                <div>
                <form role="form" action="deleteMessage" method="post" enctype="multipart/form-data">
                                                        <div class="form-group">
                                                            <label>MessageId</label>
                                                            <br>
                                                            <textarea class="from-control" rows="1" name="messageId"></textarea>
                                                        </div>

                                                        <div class="popup_btn">
                                                            <button type="submit"  onclick="hidePopup()">Delete</button>
                                                        </div>
                                                    </form>
                                                    <div>
                """);
    }

}
