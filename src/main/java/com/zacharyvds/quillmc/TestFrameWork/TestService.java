package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.plugin.annotations.QuillService;
import com.zacharyvds.quillmc.domain.service.CustomService;
import org.bukkit.command.CommandSender;

@QuillService
public class TestService extends CustomService {
    public void someServiceMethod(CommandSender sender){
        sender.sendMessage("Test commando succesvol uitgevoerd!");
    }
}
