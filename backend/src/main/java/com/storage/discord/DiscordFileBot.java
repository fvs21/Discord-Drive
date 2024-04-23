package com.storage.discord;

import com.storage.filehandler.FileHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.AttachmentProxy;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiscordFileBot {
    private final JDA bot;
    private final Dotenv dotenv  = Dotenv.load();
    public DiscordFileBot() {
        this.bot = JDABuilder.createDefault(this.dotenv.get("discord_token"))
                .setActivity(Activity.playing("Storing"))
                .build();
    }
    public List<Long> sendFile(MultipartFile file) throws IOException {
        ArrayList<FileUpload> files = FileHandler.splitFile(file, file.getOriginalFilename());
        List<Long> ids = new ArrayList<>();
        for(FileUpload fileUpload : files) {
            Message message = this.bot.getTextChannelById(this.dotenv.get("channel_id")).sendFiles(fileUpload).complete();
            Long messageId = message.getIdLong();
            ids.add(messageId);
        }
        return ids;
    }
    public File getFile(String fileName, Long messageId) {
        File file = new File(fileName);
        Message message = this.bot.getTextChannelById(this.dotenv.get("channel_id")).retrieveMessageById(messageId).complete();
        List<Message.Attachment> attachments = message.getAttachments();
        AttachmentProxy proxy = attachments.get(0).getProxy();

        proxy.downloadToFile(file);
        return file;
    }
}
