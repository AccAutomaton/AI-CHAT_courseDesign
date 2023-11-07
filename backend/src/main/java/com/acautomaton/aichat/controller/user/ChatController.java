package com.acautomaton.aichat.controller.user;

import cn.hutool.json.JSONUtil;
import com.acautomaton.aichat.entity.Recharge;
import com.acautomaton.aichat.entity.User;
import com.acautomaton.aichat.entity.dialog.Dialog;
import com.acautomaton.aichat.entity.dialog.SimpleDialog;
import com.acautomaton.aichat.enums.UserType;
import com.acautomaton.aichat.response.PageHelperResponse;
import com.acautomaton.aichat.response.Response;
import com.acautomaton.aichat.service.inte.RechargeService;
import com.acautomaton.aichat.service.inte.UserService;
import com.acautomaton.aichat.service.inte.dialog.DialogService;
import com.acautomaton.aichat.utils.JudgeUnificationUtil;
import com.acautomaton.aichat.utils.PrincipalUtil;
import com.acautomaton.aichat.utils.TimeUtil;
import com.acautomaton.aichat.utils.exception.IllegalException;
import com.acautomaton.aichat.utils.exception.NullException;
import com.acautomaton.aichat.utils.exception.OtherException;
import com.acautomaton.aichat.vo.chat.CallbackVO;
import com.acautomaton.aichat.vo.chat.DialogVO;
import com.acautomaton.aichat.vo.chat.MessageVO;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {
    DialogService dialogService;
    RechargeService rechargeService;
    UserService userService;
    @Autowired
    public ChatController(DialogService dialogService, RechargeService rechargeService, UserService userService) {
        this.dialogService = dialogService;
        this.rechargeService = rechargeService;
        this.userService = userService;
    }

    @Value("${project.ai.apikey}")
    private String apiKey;
    @Value("${project.ai.secretkey}")
    private String secretKey;


    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    static String accessToken;

    @PostMapping("/create")
    public Response createChat(@RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("title"));
            String title = request.get("title");

            User user = PrincipalUtil.getUser();
            List<SimpleDialog> dialogList = new ArrayList<>();
            dialogList.add(new SimpleDialog("assistant", TimeUtil.SerializeTimestamp(new Date()), "你好，我是文心一言，有什么可以帮助您的吗？"));
            DialogVO dialogVO = new DialogVO(null, user.getUid(), title, dialogList, new Date(), new Date(), 0);
            Dialog dialog = new Dialog(dialogVO);
            dialogService.addDialog(dialog);
            if (dialog.getId() < 100000001) {
                throw new OtherException("System error");
            }
            dialogVO = new DialogVO(dialog);
            return Response.success(Map.of(
                    "id", dialogVO.getId(),
                    "title", dialogVO.getTitle(),
                    "dialogs", dialogVO.getDialogList()
            ));
        }
        catch (NullException | OtherException e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/get/{chatId}")
    public Response getChat(@PathVariable Integer chatId) {
        try {
            User user = PrincipalUtil.getUser();
            Dialog dialog = dialogService.getDialogById(chatId);
            if (dialog == null) {
                throw new NullException("对话不存在");
            }
            if (!user.getUid().equals(dialog.getUid()) && user.getUserType() != UserType.ROOT) {
                throw new IllegalException("无权访问");
            }
            DialogVO dialogVO = new DialogVO(dialog);
            return Response.success(dialogVO.getDialogList());
        }
        catch (IllegalException | NullException e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/getList/{pageNum}/{pageSize}")
    public Response getChatList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        User user = PrincipalUtil.getUser();
        PageHelperResponse pageHelperResponse = new PageHelperResponse(
                PageHelper.startPage(pageNum, pageSize > 100 ? 100 : pageSize).doSelectPageInfo(() ->
                        dialogService.getDialogListByUid(user.getUid()))
        );
        return Response.success(pageHelperResponse);
    }

    @PostMapping("/set/title/{chatId}")
    public Response setTitle(@PathVariable Integer chatId, @RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("newTitle"));
            String newTitle = request.get("newTitle");
            User user = PrincipalUtil.getUser();
            Dialog dialog = dialogService.getDialogById(chatId);
            if (dialog == null) {
                throw new NullException("对话不存在");
            }
            if (!user.getUid().equals(dialog.getUid()) && user.getUserType() != UserType.ROOT) {
                throw new IllegalException("无权访问");
            }
            dialog.setTitle(newTitle);
            dialog.setUpdateTime(new Date());
            dialogService.updateTitleOfDialogById(dialog);
            return Response.success();
        }
        catch (IllegalException | NullException e) {
            return Response.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{chatId}")
    public Response deleteChat(@PathVariable Integer chatId) {
        try {
            User user = PrincipalUtil.getUser();
            Dialog dialog = dialogService.getDialogById(chatId);
            if (dialog == null) {
                throw new NullException("对话不存在");
            }
            if (!user.getUid().equals(dialog.getUid()) && user.getUserType() != UserType.ROOT) {
                throw new IllegalException("无权访问");
            }
            dialogService.deleteDialogById(dialog.getId());
            return Response.success();
        }
        catch (IllegalException | NullException e) {
            return Response.error(e.getMessage());
        }
    }

    @PostMapping("/doChat/{chatId}")
    public Response doChat(@PathVariable Integer chatId, @RequestBody Map<String, String> request) {
        try {
            JudgeUnificationUtil.parameterNotNull(request, List.of("content"));
            String content = request.get("content");
            User user = PrincipalUtil.getUser();
            Dialog dialog = dialogService.getDialogById(chatId);
            if (dialog == null) {
                throw new NullException("对话不存在");
            }
            if (!user.getUid().equals(dialog.getUid()) && user.getUserType() != UserType.ROOT) {
                throw new IllegalException("无权访问");
            }

            DialogVO dialogVO = new DialogVO(dialog);
            while (dialogVO.getDialogList().getLast().getRole().equals("user")) {
                dialogVO.getDialogList().removeLast();
            }

            dialogVO.getDialogList().add(new SimpleDialog("user", TimeUtil.SerializeTimestamp(new Date()), content));
            String returnMessage = sendMessage(new MessageVO(dialogVO.getDialogList()));
            if (returnMessage == null) {
                throw new OtherException("System error");
            }
            SimpleDialog returnSimpleDialog = new SimpleDialog("assistant", TimeUtil.SerializeTimestamp(new Date()), returnMessage);
            dialogVO.getDialogList().add(returnSimpleDialog);
            dialogVO.setUpdateTime(new Date());
            dialogService.updateDialogListOfDialogById(new Dialog(dialogVO));
            return Response.success(returnSimpleDialog);
        }
        catch (NullException | OtherException | IOException | IllegalException e) {
            return Response.error(e.getMessage());
        }
    }

    private String sendMessage(MessageVO message) throws IOException {
        User user = PrincipalUtil.getUser();
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSONUtil.toJsonStr(message), mediaType);
        Request request = new Request.Builder()
            .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=" + accessToken)
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build();
        okhttp3.Response response = HTTP_CLIENT.newCall(request).execute();
        if (response.body() != null) {
            CallbackVO callback = JSONUtil.toBean(response.body().string(), CallbackVO.class);
            Integer newBalance = user.getBalance() - callback.getUsage().getTotal_tokens();
            user.setBalance(newBalance);
            Date nowDate = new Date();
            user.setUpdateTime(nowDate);
            userService.updateBalanceByUid(user);
            rechargeService.addRecharge(new Recharge(null, user.getUid(),
                    -callback.getUsage().getTotal_tokens(), newBalance, nowDate,0));
            return callback.getResult();
        }
        return null;
    }

    @PostConstruct
    private void getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create("", mediaType);
        Request request = new Request.Builder()
            .url("https://aip.baidubce.com/oauth/2.0/token?client_id=" + apiKey +
                    "&client_secret=" + secretKey + "&grant_type=client_credentials")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build();
        okhttp3.Response response = HTTP_CLIENT.newCall(request).execute();
        if (response.body() != null) {
            accessToken = (String) JSONUtil.parseObj(response.body().string()).get("access_token");
            log.info("--- starting --- get baidu accessToken success");
        }
    }
}
